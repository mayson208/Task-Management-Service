import styles from './Skeleton.module.css'

export default function Skeleton({ width, height = 18, radius, className = '' }) {
  return (
    <span
      className={`${styles.skeleton} ${className}`}
      style={{
        width: width || '100%',
        height,
        borderRadius: radius || 'var(--radius)',
      }}
    />
  )
}
