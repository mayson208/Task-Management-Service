import styles from './Card.module.css'

export default function Card({ children, className = '', onClick }) {
  return (
    <div
      className={`${styles.card} ${className} ${onClick ? styles.clickable : ''}`}
      onClick={onClick}
    >
      {children}
    </div>
  )
}
