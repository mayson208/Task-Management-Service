import client from './client'

export const getAllTasks = (params = {}) =>
  client.get('/tasks', { params }).then((r) => r.data)

export const getTaskById = (id) =>
  client.get(`/tasks/${id}`).then((r) => r.data)

export const createTask = (data) =>
  client.post('/tasks', data).then((r) => r.data)

export const updateTask = (id, data) =>
  client.patch(`/tasks/${id}`, data).then((r) => r.data)

export const startTask = (id) =>
  client.post(`/tasks/${id}/start`).then((r) => r.data)

export const completeTask = (id) =>
  client.post(`/tasks/${id}/complete`).then((r) => r.data)

export const deleteTask = (id) =>
  client.delete(`/tasks/${id}`).then((r) => r.data)

export const getTaskStats = () =>
  client.get('/tasks/stats').then((r) => r.data)
