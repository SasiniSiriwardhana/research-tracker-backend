package lk.ijse.cmjd.research_tracker.enums;

/**
 * Enumeration of user roles within the Research Project Tracker system.
 *
 * <ul>
 *   <li>ADMIN  - Full system access: manage users, projects, milestones, documents.</li>
 *   <li>PI     - Principal Investigator: manage own projects and associated members.</li>
 *   <li>MEMBER - Create and update milestones or upload documents.</li>
 *   <li>VIEWER - Read-only access to public project data.</li>
 * </ul>
 */
public enum UserRole {
    ADMIN,
    PI,
    MEMBER,
    VIEWER
}
