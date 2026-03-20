import styles from './TopBar.module.css'

export default function TopBar({ title, action }) {
  return (
    <header className={styles.topbar}>
      <h1 className={styles.title}>{title}</h1>
      {action && <div className={styles.action}>{action}</div>}
    </header>
  )
}
