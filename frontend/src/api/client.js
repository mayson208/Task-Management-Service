import axios from 'axios'

const client = axios.create({
  baseURL: '/api/v1',
  headers: { 'Content-Type': 'application/json' },
})

client.interceptors.response.use(
  (response) => response,
  (error) => {
    const status = error.response?.status
    const detail = error.response?.data?.message || error.response?.data?.detail

    if (status === 404) {
      error.friendlyMessage = 'That item could not be found.'
    } else if (status === 409) {
      error.friendlyMessage = detail || 'This action conflicts with the current state.'
    } else if (status === 400) {
      error.friendlyMessage = detail || 'Invalid request — please check your input.'
    } else if (status >= 500) {
      error.friendlyMessage = 'Server error — please try again later.'
    } else {
      error.friendlyMessage = detail || 'Something went wrong.'
    }

    return Promise.reject(error)
  }
)

export default client
