const STORAGE_KEY = "todo-list.tasks.v1";

const state = {
  tasks: [],
  filter: "all",
};

const taskForm = document.getElementById("task-form");
const taskInput = document.getElementById("task-input");
const taskList = document.getElementById("task-list");
const taskCount = document.getElementById("task-count");
const emptyState = document.getElementById("empty-state");
const filterButtons = Array.from(document.querySelectorAll(".filter-btn"));

function loadTasks() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return [];

    const parsed = JSON.parse(raw);
    return Array.isArray(parsed) ? parsed : [];
  } catch {
    return [];
  }
}

function saveTasks() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(state.tasks));
}

function uid() {
  return `${Date.now()}-${Math.random().toString(36).slice(2, 8)}`;
}

function createTask(title) {
  const now = new Date().toISOString();
  return {
    id: uid(),
    title,
    completed: false,
    createdAt: now,
    updatedAt: now,
  };
}

function addTask(title) {
  state.tasks.unshift(createTask(title));
  saveTasks();
  render();
}

function toggleTask(id) {
  state.tasks = state.tasks.map((task) =>
    task.id === id
      ? { ...task, completed: !task.completed, updatedAt: new Date().toISOString() }
      : task,
  );
  saveTasks();
  render();
}

function editTask(id) {
  const task = state.tasks.find((item) => item.id === id);
  if (!task) return;

  const nextTitle = window.prompt("Editar tarefa:", task.title);
  if (nextTitle === null) return;

  const title = nextTitle.trim();
  if (!title) {
    window.alert("O título não pode ficar vazio.");
    return;
  }

  state.tasks = state.tasks.map((item) =>
    item.id === id ? { ...item, title, updatedAt: new Date().toISOString() } : item,
  );
  saveTasks();
  render();
}

function removeTask(id) {
  state.tasks = state.tasks.filter((task) => task.id !== id);
  saveTasks();
  render();
}

function getVisibleTasks() {
  if (state.filter === "active") {
    return state.tasks.filter((task) => !task.completed);
  }
  if (state.filter === "completed") {
    return state.tasks.filter((task) => task.completed);
  }
  return state.tasks;
}

function renderTask(task) {
  const item = document.createElement("li");
  item.className = `task-item ${task.completed ? "is-completed" : ""}`;
  item.dataset.id = task.id;

  const checkbox = document.createElement("input");
  checkbox.type = "checkbox";
  checkbox.checked = task.completed;
  checkbox.setAttribute("aria-label", `Marcar tarefa ${task.title}`);
  checkbox.addEventListener("change", () => toggleTask(task.id));

  const title = document.createElement("span");
  title.className = "task-item__title";
  title.textContent = task.title;

  const actions = document.createElement("div");
  actions.className = "task-item__actions";

  const editBtn = document.createElement("button");
  editBtn.type = "button";
  editBtn.textContent = "Editar";
  editBtn.addEventListener("click", () => editTask(task.id));

  const removeBtn = document.createElement("button");
  removeBtn.type = "button";
  removeBtn.className = "btn-danger";
  removeBtn.textContent = "Remover";
  removeBtn.addEventListener("click", () => removeTask(task.id));

  actions.append(editBtn, removeBtn);
  item.append(checkbox, title, actions);

  return item;
}

function render() {
  const visibleTasks = getVisibleTasks();
  taskList.innerHTML = "";

  visibleTasks.forEach((task) => {
    taskList.appendChild(renderTask(task));
  });

  const pendingCount = state.tasks.filter((task) => !task.completed).length;
  taskCount.textContent = `${pendingCount} pendente${pendingCount === 1 ? "" : "s"}`;

  emptyState.classList.toggle("is-hidden", visibleTasks.length > 0);

  filterButtons.forEach((button) => {
    button.classList.toggle("is-active", button.dataset.filter === state.filter);
  });
}

taskForm.addEventListener("submit", (event) => {
  event.preventDefault();
  const title = taskInput.value.trim();

  if (!title) {
    taskInput.focus();
    return;
  }

  addTask(title);
  taskForm.reset();
  taskInput.focus();
});

filterButtons.forEach((button) => {
  button.addEventListener("click", () => {
    state.filter = button.dataset.filter;
    render();
  });
});

state.tasks = loadTasks();
render();
