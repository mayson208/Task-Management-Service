import { useState } from 'react'
import { BrowserRouter, Routes, Route, useLocation } from 'react-router-dom'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { Toaster } from 'react-hot-toast'
import Sidebar from './components/Sidebar'
import TopBar from './components/TopBar'
import Dashboard from './pages/Dashboard'
import TasksPage from './pages/TasksPage'
import styles from './App.module.css'

const queryClient = new QueryClient({
  defaultOptions: { queries: { staleTime: 10_000, retry: 1 } },
})

const TITLES = {
  '/':      'Dashboard',
  '/tasks': 'Tasks',
}

function Shell() {
  const [collapsed, setCollapsed] = useState(false)
  const { pathname } = useLocation()
  const title = TITLES[pathname] || 'Task Manager'

  return (
    <div className={`${styles.layout} ${collapsed ? styles.sidebarCollapsed : ''}`}>
      <Sidebar collapsed={collapsed} onToggle={() => setCollapsed(c => !c)} />
      <div className={styles.main}>
        <TopBar title={title} />
        <div className={styles.content}>
          <Routes>
            <Route path="/"      element={<Dashboard />} />
            <Route path="/tasks" element={<TasksPage />} />
          </Routes>
        </div>
      </div>
    </div>
  )
}

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Shell />
        <Toaster
          position="bottom-right"
          toastOptions={{
            style: {
              background: 'var(--surface-2)',
              color: 'var(--text)',
              border: '1px solid var(--border)',
              fontSize: '0.875rem',
              borderRadius: 'var(--radius)',
            },
            success: { iconTheme: { primary: 'var(--success)', secondary: 'var(--surface-2)' } },
            error:   { iconTheme: { primary: 'var(--danger)',  secondary: 'var(--surface-2)' } },
          }}
        />
      </BrowserRouter>
    </QueryClientProvider>
  )
}
