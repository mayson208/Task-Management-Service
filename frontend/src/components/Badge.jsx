import styles from './Badge.module.css'

const MAP = {
  PENDING:     'pending',
  IN_PROGRESS: 'inProgress',
  COMPLETED:   'completed',
}

export default function Badge({ status }) {
  const labels = { PENDING: 'Pending', IN_PROGRESS: 'In Progress', COMPLETED: 'Completed' }
  return (
    <span className={`${styles.badge} ${styles[MAP[status] || 'pending']}`}>
      <span className={styles.dot} />
      {labels[status] || status}
    </span>
  )
}
