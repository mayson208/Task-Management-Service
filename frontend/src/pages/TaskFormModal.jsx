import { useState, useEffect } from 'react'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import toast from 'react-hot-toast'
import { createTask, updateTask } from '../api/tasks'
import Modal from '../components/Modal'
import Input from '../components/Input'
import Button from '../components/Button'
import styles from './TaskFormModal.module.css'

export default function TaskFormModal({ open, onClose, task }) {
  const isEdit = Boolean(task)
  const qc = useQueryClient()

  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [errors, setErrors] = useState({})

  useEffect(() => {
    if (open) {
      setTitle(task?.title || '')
      setDescription(task?.description || '')
      setErrors({})
    }
  }, [open, task])

  const mutation = useMutation({
    mutationFn: isEdit
      ? (data) => updateTask(task.id, data)
      : (data) => createTask(data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['tasks'] })
      toast.success(isEdit ? 'Task updated.' : 'Task created.')
      onClose()
    },
    onError: (err) => {
      toast.error(err.friendlyMessage || 'Something went wrong.')
    },
  })

  const validate = () => {
    const e = {}
    if (!title.trim()) e.title = 'Title is required.'
    if (title.trim().length > 100) e.title = 'Title must be 100 characters or fewer.'
    if (description.length > 500) e.description = 'Description must be 500 characters or fewer.'
    return e
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    const e2 = validate()
    if (Object.keys(e2).length) { setErrors(e2); return }
    mutation.mutate({ title: title.trim(), description: description.trim() || undefined })
  }

  return (
    <Modal open={open} onClose={onClose} title={isEdit ? 'Edit Task' : 'New Task'}>
      <form onSubmit={handleSubmit} className={styles.form}>
        <Input
          label="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="What needs to be done?"
          error={errors.title}
          autoFocus
        />
        <Input
          label="Description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          placeholder="Optional details..."
          error={errors.description}
          textarea
        />
        <div className={styles.actions}>
          <Button variant="secondary" type="button" onClick={onClose}>Cancel</Button>
          <Button type="submit" loading={mutation.isPending}>
            {isEdit ? 'Save changes' : 'Create task'}
          </Button>
        </div>
      </form>
    </Modal>
  )
}
