import { useState, useEffect } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import toast from 'react-hot-toast'
import {
  RiAddLine, RiEditLine, RiDeleteBinLine,
  RiPlayLine, RiCheckLine, RiSearchLine, RiFilterLine,
} from 'react-icons/ri'
import { getAllTasks, deleteTask, startTask, completeTask } from '../api/tasks'
import Badge from '../components/Badge'
import Button from '../components/Button'
import Card from '../components/Card'
import ConfirmDialog from '../components/ConfirmDialog'
import EmptyState from '../components/EmptyState'
import Skeleton from '../components/Skeleton'
import TaskFormModal from './TaskFormModal'
import styles from './TasksPage.module.css'

const STATUSES = ['ALL', 'PENDING', 'IN_PROGRESS', 'COMPLETED']

export default function TasksPage() {
  const qc = useQueryClient()
  const [page, setPage] = useState(0)
  const [statusFilter, setStatusFilter] = useState('ALL')
  const [search, setSearch] = useState('')
  const [formOpen, setFormOpen] = useState(false)
  const [editTask, setEditTask] = useState(null)
  const [deleteTarget, setDeleteTarget] = useState(null)

  // Reset to page 0 when filter changes
  useEffect(() => { setPage(0) }, [statusFilter, search])

  // Keyboard shortcut: N = new task
  useEffect(() => {
    const handler = (e) => {
      if (e.key === 'n' && !e.ctrlKey && !e.metaKey && e.target.tagName !== 'INPUT' && e.target.tagName !== 'TEXTAREA') {
        setEditTask(null)
        setFormOpen(true)
      }
    }
    window.addEventListener('keydown', handler)
    return () => window.removeEventListener('keydown', handler)
  }, [])

  const queryParams = {
    page,
    size: 10,
    ...(statusFilter !== 'ALL' && { status: statusFilter }),
  }

  const { data, isLoading } = useQuery({
    queryKey: ['tasks', queryParams],
    queryFn: () => getAllTasks(queryParams),
    keepPreviousData: true,
  })

  const tasks = data?.tasks || []
  const totalPages = data?.totalPages || 0

  // Client-side search filter (keeps backend pagination for status filter)
  const filtered = search.trim()
    ? tasks.filter(t =>
        t.title.toLowerCase().includes(search.toLowerCase()) ||
        t.description?.toLowerCase().includes(search.toLowerCase())
      )
    : tasks

  const deleteMutation = useMutation({
    mutationFn: deleteTask,
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['tasks'] })
      toast.success('Task deleted.')
      setDeleteTarget(null)
    },
    onError: (err) => toast.error(err.friendlyMessage || 'Could not delete task.'),
  })

  const startMutation = useMutation({
    mutationFn: startTask,
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['tasks'] })
      toast.success('Task started.')
    },
    onError: (err) => toast.error(err.friendlyMessage || 'Could not start task.'),
  })

  const completeMutation = useMutation({
    mutationFn: completeTask,
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['tasks'] })
      toast.success('Task completed! 🎉')
    },
    onError: (err) => toast.error(err.friendlyMessage || 'Could not complete task.'),
  })

  const openEdit = (task) => { setEditTask(task); setFormOpen(true) }
  const openNew  = ()     => { setEditTask(null);  setFormOpen(true) }

  return (
    <div className={`${styles.page} animate-fade`}>
      {/* Toolbar */}
      <div className={styles.toolbar}>
        <div className={styles.searchWrap}>
          <RiSearchLine className={styles.searchIcon} />
          <input
            className={styles.search}
            placeholder="Search tasks… (N for new)"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </div>

        <div className={styles.filters}>
          <RiFilterLine className={styles.filterIcon} />
          {STATUSES.map((s) => (
            <button
              key={s}
              className={`${styles.filterBtn} ${statusFilter === s ? styles.filterActive : ''}`}
              onClick={() => setStatusFilter(s)}
            >
              {s === 'ALL' ? 'All' : s === 'IN_PROGRESS' ? 'In Progress' : s.charAt(0) + s.slice(1).toLowerCase()}
            </button>
          ))}
        </div>

        <Button size="sm" onClick={openNew}>
          <RiAddLine /> New Task
        </Button>
      </div>

      {/* Table */}
      <Card className={styles.tableCard}>
        {isLoading ? (
          <div className={styles.skeletonList}>
            {[...Array(6)].map((_, i) => (
              <div key={i} className={styles.skeletonRow}>
                <Skeleton width="35%" />
                <Skeleton width="20%" />
                <Skeleton width={70} />
                <Skeleton width={80} />
              </div>
            ))}
          </div>
        ) : filtered.length === 0 ? (
          <EmptyState
            icon="📋"
            title={search ? 'No results found' : 'No tasks yet'}
            message={search ? 'Try a different search term.' : 'Press N or click "New Task" to get started.'}
            action={!search && <Button size="sm" onClick={openNew}><RiAddLine /> New Task</Button>}
          />
        ) : (
          <>
            <div className={styles.tableHeader}>
              <span>Title</span>
              <span className={styles.hide600}>Description</span>
              <span>Status</span>
              <span className={styles.hide500}>Created</span>
              <span>Actions</span>
            </div>
            {filtered.map((task) => (
              <div key={task.id} className={styles.tableRow}>
                <span className={styles.taskTitle}>{task.title}</span>
                <span className={`${styles.taskDesc} ${styles.hide600}`}>
                  {task.description || <span className={styles.na}>—</span>}
                </span>
                <Badge status={task.status} />
                <span className={`${styles.taskDate} ${styles.hide500}`}>
                  {new Date(task.createdAt).toLocaleDateString()}
                </span>
                <div className={styles.actions}>
                  {task.status === 'PENDING' && (
                    <button
                      className={styles.actionBtn}
                      title="Start task"
                      onClick={() => startMutation.mutate(task.id)}
                    >
                      <RiPlayLine />
                    </button>
                  )}
                  {task.status === 'IN_PROGRESS' && (
                    <button
                      className={`${styles.actionBtn} ${styles.completeBtn}`}
                      title="Mark complete"
                      onClick={() => completeMutation.mutate(task.id)}
                    >
                      <RiCheckLine />
                    </button>
                  )}
                  <button
                    className={styles.actionBtn}
                    title="Edit task"
                    onClick={() => openEdit(task)}
                  >
                    <RiEditLine />
                  </button>
                  <button
                    className={`${styles.actionBtn} ${styles.dangerBtn}`}
                    title="Delete task"
                    onClick={() => setDeleteTarget(task)}
                  >
                    <RiDeleteBinLine />
                  </button>
                </div>
              </div>
            ))}
          </>
        )}
      </Card>

      {/* Pagination */}
      {totalPages > 1 && (
        <div className={styles.pagination}>
          <Button variant="secondary" size="sm" disabled={page === 0} onClick={() => setPage(p => p - 1)}>
            Previous
          </Button>
          <span className={styles.pageInfo}>Page {page + 1} of {totalPages}</span>
          <Button variant="secondary" size="sm" disabled={page >= totalPages - 1} onClick={() => setPage(p => p + 1)}>
            Next
          </Button>
        </div>
      )}

      <TaskFormModal
        open={formOpen}
        onClose={() => setFormOpen(false)}
        task={editTask}
      />

      <ConfirmDialog
        open={Boolean(deleteTarget)}
        onClose={() => setDeleteTarget(null)}
        onConfirm={() => deleteMutation.mutate(deleteTarget?.id)}
        loading={deleteMutation.isPending}
        title="Delete task?"
        message={`"${deleteTarget?.title}" will be permanently deleted.`}
      />
    </div>
  )
}
