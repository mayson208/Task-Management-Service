import { NavLink } from 'react-router-dom'
import { RiLayoutGridLine, RiCheckboxMultipleLine, RiMenuLine, RiCloseLine } from 'react-icons/ri'
import styles from './Sidebar.module.css'

const NAV = [
  { to: '/',      label: 'Dashboard', Icon: RiLayoutGridLine },
  { to: '/tasks', label: 'Tasks',     Icon: RiCheckboxMultipleLine },
]

export default function Sidebar({ collapsed, onToggle }) {
  return (
    <aside className={`${styles.sidebar} ${collapsed ? styles.collapsed : ''}`}>
      <div className={styles.top}>
        <div className={styles.logo}>
          {!collapsed && <span className={styles.logoText}>TaskFlow</span>}
        </div>
        <button className={styles.toggle} onClick={onToggle} title="Toggle sidebar">
          {collapsed ? <RiMenuLine /> : <RiCloseLine />}
        </button>
      </div>

      <nav className={styles.nav}>
        {NAV.map(({ to, label, Icon }) => (
          <NavLink
            key={to}
            to={to}
            end={to === '/'}
            className={({ isActive }) =>
              `${styles.link} ${isActive ? styles.active : ''}`
            }
            title={collapsed ? label : undefined}
          >
            <Icon className={styles.icon} />
            {!collapsed && <span>{label}</span>}
          </NavLink>
        ))}
      </nav>
    </aside>
  )
}
