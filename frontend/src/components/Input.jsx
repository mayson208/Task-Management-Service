import styles from './Input.module.css'

export default function Input({
  label,
  error,
  textarea = false,
  ...props
}) {
  const Tag = textarea ? 'textarea' : 'input'
  return (
    <div className={styles.wrapper}>
      {label && <label className={styles.label}>{label}</label>}
      <Tag
        className={`${styles.input} ${textarea ? styles.textarea : ''} ${error ? styles.hasError : ''}`}
        {...props}
      />
      {error && <span className={styles.error}>{error}</span>}
    </div>
  )
}
