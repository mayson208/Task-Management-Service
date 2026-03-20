import Modal from './Modal'
import Button from './Button'
import styles from './ConfirmDialog.module.css'

export default function ConfirmDialog({ open, onClose, onConfirm, title, message, loading }) {
  return (
    <Modal open={open} onClose={onClose} title={title || 'Are you sure?'} width={420}>
      <p className={styles.message}>{message || 'This action cannot be undone.'}</p>
      <div className={styles.actions}>
        <Button variant="secondary" onClick={onClose} disabled={loading}>Cancel</Button>
        <Button variant="danger" onClick={onConfirm} loading={loading}>Delete</Button>
      </div>
    </Modal>
  )
}
