const state = {
  token: localStorage.getItem("prode_token"),
  user: JSON.parse(localStorage.getItem("prode_user") || "null"),
  view: "dashboard",
  jornadas: [],
  equipos: [],
  partidos: [],
  predicciones: []
};

const $ = (selector) => document.querySelector(selector);
const $$ = (selector) => Array.from(document.querySelectorAll(selector));

function showAlert(message, isError = false) {
  const box = $("#alert");
  if (!box) return;
  box.textContent = message;
  box.className = `alert${isError ? " error" : ""}`;
  setTimeout(() => box.classList.add("hidden"), 4200);
}

function formatDate(value) {
  if (!value) return "-";
  return new Date(value).toLocaleString("es-AR", {
    day: "2-digit",
    month: "2-digit",
    hour: "2-digit",
    minute: "2-digit"
  });
}

function formData(form) {
  return Object.fromEntries(new FormData(form).entries());
}

async function api(path, options = {}) {
  const headers = { "Content-Type": "application/json", ...(options.headers || {}) };
  if (state.token) headers.Authorization = `Bearer ${state.token}`;

  const res = await fetch(path, { ...options, headers });
  const payload = await res.json().catch(() => ({ success: false, message: "Respuesta invalida" }));

  if (!res.ok || payload.success === false) {
    throw new Error(payload.message || `Error ${res.status}`);
  }
  return payload.data;
}

function setSession(auth) {
  state.token = auth.token;
  state.user = auth;
  localStorage.setItem("prode_token", auth.token);
  localStorage.setItem("prode_user", JSON.stringify(auth));
  renderSession();
  loadAll();
}

function clearSession() {
  state.token = null;
  state.user = null;
  localStorage.removeItem("prode_token");
  localStorage.removeItem("prode_user");
  renderSession();
}

function renderSession() {
  const logged = Boolean(state.token);
  $("#authView").classList.toggle("hidden", logged);
  $("#appView").classList.toggle("hidden", !logged);
  if (!logged) return;

  $("#userBadge").textContent = `${state.user.nombre} ${state.user.apellido} - ${state.user.rol}`;
  $("#adminNav").classList.toggle("hidden", state.user.rol !== "ADMIN");
}

function switchView(view) {
  state.view = view;
  $$(".view").forEach((el) => el.classList.toggle("active", el.id === view));
  $$(".nav button").forEach((btn) => btn.classList.toggle("active", btn.dataset.view === view));
  const titles = {
    dashboard: "Inicio",
    partidos: "Partidos",
    predicciones: "Mis predicciones",
    ranking: "Ranking",
    grupos: "Grupos",
    resultados: "Resultados",
    perfil: "Perfil y reglas",
    admin: "Administracion"
  };
  $("#pageTitle").textContent = titles[view] || "Prode";
}

function empty() {
  return $("#emptyTemplate").content.firstElementChild.cloneNode(true);
}

async function loadAll() {
  if (!state.token) return;
  try {
    const [perfil, estadisticas, jornadas, equipos, partidos, predicciones, ranking, grupos, resultados, reglas] =
      await Promise.all([
        api("/api/usuarios/perfil"),
        api("/api/usuarios/estadisticas"),
        api("/api/jornadas"),
        api("/api/equipos"),
        api("/api/partidos"),
        api("/api/predicciones"),
        api("/api/ranking/global"),
        api("/api/grupos"),
        api("/api/resultados"),
        api("/api/reglas")
      ]);

    state.jornadas = jornadas;
    state.equipos = equipos;
    state.partidos = partidos;
    state.predicciones = predicciones;

    renderStats(estadisticas);
    renderJornadaFilter();
    renderPartidos();
    renderPredicciones(predicciones);
    renderRanking(ranking);
    renderGrupos(grupos);
    renderResultados(resultados);
    renderPerfil(perfil, reglas);
    renderAdminOptions();
    renderAdminPartidos();
  } catch (err) {
    showAlert(err.message, true);
    if (err.message.includes("401") ||
        err.message.includes("Sesion invalida") ||
        err.message.includes("Sesion vencida")) {
      clearSession();
    }
  }
}

function renderStats(stats) {
  $("#statPuntos").textContent = stats.puntajeTotal ?? 0;
  $("#statPredicciones").textContent = stats.totalPredicciones ?? 0;
  $("#statAciertos").textContent = stats.totalAciertos ?? 0;
  $("#statEfectividad").textContent = `${Math.round(stats.efectividadPorcentaje ?? 0)}%`;
}

function renderJornadaFilter() {
  const select = $("#jornadaFilter");
  const selected = select.value;
  select.innerHTML = `<option value="">Todas las jornadas</option>` +
    state.jornadas.map((j) => `<option value="${j.id}">${j.nombre}</option>`).join("");
  select.value = selected;
}

function predictionFor(partidoId) {
  return state.predicciones.find((p) => p.partido.id === partidoId);
}

function matchCard(partido, compact = false) {
  const pred = predictionFor(partido.id);
  const blocked = partido.prediccionBloqueada || partido.estadoPartido !== "PROGRAMADO";
  const card = document.createElement("article");
  card.className = "match-card";
  card.innerHTML = `
    <div>
      <div class="teams">${partido.equipoLocal.nombre} vs ${partido.equipoVisitante.nombre}</div>
      <div class="match-meta">
        <span class="pill">${partido.jornadaNombre}</span>
        <span class="pill">${formatDate(partido.horaInicio)}</span>
        <span class="pill ${blocked ? "bad" : "good"}">${blocked ? "Bloqueado" : "Abierto"}</span>
        ${pred ? `<span class="pill good">Mi pronostico ${pred.golesLocal}-${pred.golesVisitante}</span>` : ""}
      </div>
    </div>
    ${compact ? "" : `
      <form class="predict-form" data-partido-id="${partido.id}" data-pred-id="${pred ? pred.id : ""}">
        <label>Local <input name="golesLocal" type="number" min="0" value="${pred ? pred.golesLocal : 0}" ${blocked ? "disabled" : ""}></label>
        <label>Visitante <input name="golesVisitante" type="number" min="0" value="${pred ? pred.golesVisitante : 0}" ${blocked ? "disabled" : ""}></label>
        <button class="primary" type="submit" ${blocked ? "disabled" : ""}>${pred ? "Modificar" : "Pronosticar"}</button>
      </form>
    `}
  `;
  return card;
}

function renderPartidos() {
  const jornadaId = $("#jornadaFilter").value;
  const filtered = jornadaId
    ? state.partidos.filter((p) => String(p.jornadaId) === jornadaId)
    : state.partidos;

  const list = $("#partidosList");
  list.innerHTML = "";
  if (!filtered.length) list.append(empty());
  filtered.forEach((p) => list.append(matchCard(p)));

  const home = $("#homePartidos");
  home.innerHTML = "";
  state.partidos.slice(0, 4).forEach((p) => home.append(matchCard(p, true)));
  if (!state.partidos.length) home.append(empty());
}

function renderPredicciones(predicciones) {
  const list = $("#prediccionesList");
  list.innerHTML = "";
  if (!predicciones.length) list.append(empty());
  predicciones.forEach((p) => {
    const card = document.createElement("article");
    card.className = "card";
    card.innerHTML = `
      <h4>${p.partido.equipoLocal.nombre} vs ${p.partido.equipoVisitante.nombre}</h4>
      <p class="muted">${p.partido.jornadaNombre} - ${formatDate(p.partido.horaInicio)}</p>
      <div class="match-meta">
        <span class="pill good">Pronostico ${p.golesLocal}-${p.golesVisitante}</span>
        <span class="pill">${p.resultadoPronosticado}</span>
        <span class="pill">${p.puntosObtenidos} pts</span>
      </div>
    `;
    list.append(card);
  });
}

function renderRanking(ranking) {
  const list = $("#rankingList");
  list.innerHTML = "";
  if (!ranking.length) {
    list.append(empty());
    return;
  }
  ranking.forEach((item) => {
    const row = document.createElement("div");
    row.className = `row${item.esMiPosicion ? " me" : ""}`;
    row.innerHTML = `
      <strong>#${item.posicion}</strong>
      <span>${item.nombre} ${item.apellido}</span>
      <strong>${item.puntaje} pts</strong>
    `;
    list.append(row);
  });
}

function renderGrupos(grupos) {
  const list = $("#gruposList");
  list.innerHTML = "";
  if (!grupos.length) list.append(empty());
  grupos.forEach((g) => {
    const card = document.createElement("article");
    card.className = "card";
    card.innerHTML = `
      <h4>${g.nombre}</h4>
      <p class="muted">${g.descripcion || "Sin descripcion"}</p>
      <div class="match-meta">
        <span class="pill">Codigo ${g.codigoInvitacion}</span>
        <span class="pill">${g.totalMiembros} miembros</span>
      </div>
      <div class="table-like">${g.miembros.map((m, i) => `
        <div class="row"><strong>#${i + 1}</strong><span>${m.nombreCompleto}</span><strong>${m.puntaje} pts</strong></div>
      `).join("")}</div>
    `;
    list.append(card);
  });
}

function renderResultados(resultados) {
  const list = $("#resultadosList");
  list.innerHTML = "";
  if (!resultados.length) list.append(empty());
  resultados.forEach((r) => {
    const card = document.createElement("article");
    card.className = "card";
    card.innerHTML = `
      <h4>${r.equipoLocal.nombre} ${r.golesLocalOficial}-${r.golesVisitanteOficial} ${r.equipoVisitante.nombre}</h4>
      <p class="muted">${r.jornadaNombre}</p>
      <div class="match-meta">
        <span class="pill">${r.resultadoFinal}</span>
        <span class="pill">${r.miGolesLocal == null ? "Sin pronostico" : `Mi pronostico ${r.miGolesLocal}-${r.miGolesVisitante}`}</span>
        <span class="pill good">${r.misPuntosObtenidos ?? 0} pts</span>
      </div>
    `;
    list.append(card);
  });
}

function renderPerfil(perfil, reglas) {
  $("#perfilForm").nombre.value = perfil.nombre;
  $("#perfilForm").apellido.value = perfil.apellido;
  $("#perfilForm").correo.value = perfil.correo;
  state.user = { ...state.user, nombre: perfil.nombre, apellido: perfil.apellido, correo: perfil.correo };
  localStorage.setItem("prode_user", JSON.stringify(state.user));
  $("#userBadge").textContent = `${state.user.nombre} ${state.user.apellido} - ${state.user.rol}`;
  $("#reglasBox").innerHTML = `
    <p>${reglas.descripcionGeneral}</p>
    ${reglas.sistemaPuntuacion.map((r) => `<p><strong>${r.puntos} pts:</strong> ${r.caso}</p>`).join("")}
    <p>${reglas.cierrePrediciones}</p>
  `;
}

function renderAdminOptions() {
  const equipoOptions = state.equipos.map((e) => `<option value="${e.id}">${e.nombre}</option>`).join("");
  const jornadaOptions = state.jornadas.map((j) => `<option value="${j.id}">${j.nombre}</option>`).join("");
  $$("#partidoForm select[name='equipoLocalId'], #partidoForm select[name='equipoVisitanteId']")
    .forEach((select) => select.innerHTML = equipoOptions);
  $("#partidoForm select[name='jornadaId']").innerHTML = jornadaOptions;
}

function renderAdminPartidos() {
  const list = $("#adminPartidos");
  list.innerHTML = "";
  const pendientes = state.partidos.filter((p) => p.estadoPartido !== "FINALIZADO");
  if (!pendientes.length) list.append(empty());
  pendientes.forEach((p) => {
    const accion = p.estadoPartido === "PROGRAMADO"
      ? `
        <form class="iniciar-form" data-partido-id="${p.id}">
          <button class="primary" type="submit">Iniciar</button>
        </form>
      `
      : `
        <form class="finalizar-form" data-partido-id="${p.id}">
          <label>Local <input name="golesLocal" type="number" min="0" value="0"></label>
          <label>Visitante <input name="golesVisitante" type="number" min="0" value="0"></label>
          <button class="primary" type="submit">Finalizar</button>
        </form>
      `;
    const row = document.createElement("article");
    row.className = "match-card";
    row.innerHTML = `
      <div>
        <div class="teams">${p.equipoLocal.nombre} vs ${p.equipoVisitante.nombre}</div>
        <div class="match-meta"><span class="pill">${p.estadoPartido}</span><span class="pill">${formatDate(p.horaInicio)}</span></div>
      </div>
      ${accion}
    `;
    list.append(row);
  });
}

async function savePrediction(form) {
  const partidoId = Number(form.dataset.partidoId);
  const predId = form.dataset.predId;
  const data = {
    partidoId,
    golesLocal: Number(form.golesLocal.value),
    golesVisitante: Number(form.golesVisitante.value)
  };
  if (predId) {
    await api(`/api/predicciones/${predId}`, { method: "PUT", body: JSON.stringify(data) });
  } else {
    await api("/api/predicciones", { method: "POST", body: JSON.stringify(data) });
  }
  showAlert("Prediccion guardada");
  await loadAll();
}

function bindEvents() {
  $$(".tab").forEach((tab) => tab.addEventListener("click", () => {
    $$(".tab").forEach((t) => t.classList.remove("active"));
    tab.classList.add("active");
    $("#loginForm").classList.toggle("hidden", tab.dataset.authTab !== "login");
    $("#registroForm").classList.toggle("hidden", tab.dataset.authTab !== "registro");
  }));

  $("#loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    try {
      setSession(await api("/api/auth/login", { method: "POST", body: JSON.stringify(formData(e.target)) }));
    } catch (err) {
      showAlert(err.message, true);
    }
  });

  $("#registroForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    try {
      setSession(await api("/api/auth/registro", { method: "POST", body: JSON.stringify(formData(e.target)) }));
    } catch (err) {
      showAlert(err.message, true);
    }
  });

  $$(".nav button").forEach((btn) => btn.addEventListener("click", () => switchView(btn.dataset.view)));
  $$("[data-view-shortcut]").forEach((btn) => btn.addEventListener("click", () => switchView(btn.dataset.viewShortcut)));
  $("#logoutBtn").addEventListener("click", clearSession);
  $("#refreshBtn").addEventListener("click", loadAll);
  $("#jornadaFilter").addEventListener("change", renderPartidos);

  document.addEventListener("submit", async (e) => {
    if (e.target.matches(".predict-form")) {
      e.preventDefault();
      try { await savePrediction(e.target); } catch (err) { showAlert(err.message, true); }
    }
    if (e.target.matches(".iniciar-form")) {
      e.preventDefault();
      try {
        await api(`/api/admin/partidos/${e.target.dataset.partidoId}/iniciar`, { method: "PATCH" });
        showAlert("Partido iniciado");
        await loadAll();
      } catch (err) { showAlert(err.message, true); }
    }
    if (e.target.matches(".finalizar-form")) {
      e.preventDefault();
      try {
        await api(`/api/admin/partidos/${e.target.dataset.partidoId}/finalizar`, {
          method: "PATCH",
          body: JSON.stringify({
            golesLocal: Number(e.target.golesLocal.value),
            golesVisitante: Number(e.target.golesVisitante.value)
          })
        });
        showAlert("Partido finalizado");
        await loadAll();
      } catch (err) { showAlert(err.message, true); }
    }
  });

  $("#crearGrupoForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    try {
      await api("/api/grupos", { method: "POST", body: JSON.stringify(formData(e.target)) });
      e.target.reset();
      showAlert("Grupo creado");
      await loadAll();
    } catch (err) { showAlert(err.message, true); }
  });

  $("#unirseGrupoForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    try {
      await api("/api/grupos/unirse", { method: "POST", body: JSON.stringify(formData(e.target)) });
      e.target.reset();
      showAlert("Te uniste al grupo");
      await loadAll();
    } catch (err) { showAlert(err.message, true); }
  });

  $("#perfilForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    try {
      await api("/api/usuarios/perfil", { method: "PUT", body: JSON.stringify(formData(e.target)) });
      showAlert("Perfil actualizado");
      await loadAll();
    } catch (err) { showAlert(err.message, true); }
  });

  $("#equipoForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    try {
      await api("/api/admin/equipos", { method: "POST", body: JSON.stringify(formData(e.target)) });
      e.target.reset();
      showAlert("Equipo creado");
      await loadAll();
    } catch (err) { showAlert(err.message, true); }
  });

  $("#jornadaForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    try {
      await api("/api/admin/jornadas", { method: "POST", body: JSON.stringify(formData(e.target)) });
      e.target.reset();
      showAlert("Jornada creada");
      await loadAll();
    } catch (err) { showAlert(err.message, true); }
  });

  $("#partidoForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = formData(e.target);
    data.equipoLocalId = Number(data.equipoLocalId);
    data.equipoVisitanteId = Number(data.equipoVisitanteId);
    data.jornadaId = Number(data.jornadaId);
    try {
      await api("/api/admin/partidos", { method: "POST", body: JSON.stringify(data) });
      e.target.reset();
      showAlert("Partido creado");
      await loadAll();
    } catch (err) { showAlert(err.message, true); }
  });
}

bindEvents();
renderSession();
if (state.token) loadAll();
