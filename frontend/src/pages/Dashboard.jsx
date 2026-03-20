import { useQuery } from '@tanstack/react-query'
import { Link } from 'react-router-dom'
import { RiCheckboxCircleLine, RiTimeLine, RiLoader4Line, RiTaskLine, RiArrowRightLine } from 'react-icons/ri'
import { getAllTasks, getTaskStats } from '../api/tasks'
import Card from '../components/Card'
import Badge from '../components/Badge'
import Button from '../components/Button'
import Skeleton from '../components/Skeleton'
import { useCountUp } from '../hooks/useCountUp'
import styles from './Dashboard.module.css'

function StatCard({ label, value, icon: Icon, color, loading }) {
  const animated = useCountUp(loading ? 0 : value)
  return (
    <Card className={styles.statCard}>
      <div className={styles.statIcon} style={{ background: color + '18', color }}>
        <Icon />
      </div>
      <div className={styles.statBody}>
        {loading
          ? <Skeleton width={48} height={28} />
          : <span className={styles.statValue} style={{ animation: 'countUp .5s ease both' }}>{animated}</span>
        }
        <span className={styles.statLabel}>{label}</span>
      </div>
    </Card>
  )
}

export default function Dashboard() {
  const { data: statsData, isLoading: statsLoading } = useQuery({
    queryKey: ['tasks', 'stats'],
    queryFn: getTaskStats,
  })

  const { data: recentData, isLoading: recentLoading } = useQuery({
    queryKey: ['tasks', { page: 0, size: 6 }],
    queryFn: () => getAllTasks({ page: 0, size: 6 }),
  })

  const isLoading  = statsLoading || recentLoading
  const total      = statsData?.totalTasks      ?? 0
  const completed  = statsData?.completedTasks  ?? 0
  const inProgress = statsData?.inProgressTasks ?? 0
  const pending    = statsData?.pendingTasks     ?? 0
  const recent     = recentData?.tasks || []

  return (
    <div className={`${styles.page} animate-fade`}>
      <div className={styles.stats}>
        <StatCard label="Total Tasks"   value={total}      icon={RiTaskLine}            color="var(--accent)"   loading={isLoading} />
        <StatCard label="Completed"     value={completed}  icon={RiCheckboxCircleLine}  color="var(--success)"  loading={isLoading} />
        <StatCard label="In Progress"   value={inProgress} icon={RiLoader4Line}          color="var(--accent)"   loading={isLoading} />
        <StatCard label="Pending"       value={pending}    icon={RiTimeLine}             color="var(--warning)"  loading={isLoading} />
      </div>

      <div className={styles.section}>
        <div className={styles.sectionHeader}>
          <h2 className={styles.sectionTitle}>Recent Tasks</h2>
          <Link to="/tasks">
            <Button variant="ghost" size="sm">View all <RiArrowRightLine /></Button>
          </Link>
        </div>

        {isLoading ? (
          <div className={styles.skeletonList}>
            {[...Array(5)].map((_, i) => (
              <Card key={i} className={styles.skeletonRow}>
                <Skeleton width="40%" />
                <Skeleton width={80} />
              </Card>
            ))}
          </div>
        ) : recent.length === 0 ? (
          <Card className={styles.empty}>
            <p className={styles.emptyText}>No tasks yet. <Link to="/tasks" className={styles.emptyLink}>Create one →</Link></p>
          </Card>
        ) : (
          <div className={styles.taskList}>
            {recent.map((task) => (
              <Card key={task.id} className={styles.taskRow}>
                <div className={styles.taskInfo}>
                  <span className={styles.taskTitle}>{task.title}</span>
                  {task.description && (
                    <span className={styles.taskDesc}>{task.description}</span>
                  )}
                </div>
                <div className={styles.taskMeta}>
                  <Badge status={task.status} />
                  <span className={styles.taskDate}>
                    {new Date(task.createdAt).toLocaleDateString()}
                  </span>
                </div>
              </Card>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}
