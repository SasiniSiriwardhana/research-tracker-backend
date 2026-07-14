-- ============================================================
--  Research Project Tracker — Sample Data
--  For: Sasini Siriwardhana | Batch 111 | IJSE | CMJD Final Project
--
--  USAGE:
--    mysql -u root research_tracker_db < sample_data.sql
--
--  NOTE: Passwords are BCrypt encoded.
--    admin@rpt.lk       -> Admin@1234
--    all others         -> Password@123
-- ============================================================

USE research_tracker_db;

-- ── Disable FK checks for clean insert ──────────────────────
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE documents;
TRUNCATE TABLE milestones;
TRUNCATE TABLE projects;
TRUNCATE TABLE users;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- USERS
-- ============================================================
INSERT INTO users (id, username, password, full_name, role, created_at) VALUES
-- ADMIN — password: Admin@1234
('u1', 'admin@rpt.lk', '$2a$10$kH1PFDLBg8Qi9yR3.X87dOntkbHgYs94CP4QguPEURsC0qVKLq7A6', 'Sasini Admin', 'ADMIN',  NOW()),

-- PRINCIPAL INVESTIGATORS — password: Password@123
('u2', 'dr.jayawardena@rpt.lk',       '$2a$10$nmaj1RMShn0Fvbm/reNtk.CARgaJrEWIGcgPL8CquZQZeYPivNl1y', 'Dr. Jayawardena', 'PI',     NOW()),
('u3', 'dr.ranasinghe@rpt.lk',        '$2a$10$nmaj1RMShn0Fvbm/reNtk.CARgaJrEWIGcgPL8CquZQZeYPivNl1y', 'Dr. Ranasinghe', 'PI',     NOW()),

-- MEMBERS — password: Password@123
('u4', 'nimal.dissanayake@rpt.lk',    '$2a$10$nmaj1RMShn0Fvbm/reNtk.CARgaJrEWIGcgPL8CquZQZeYPivNl1y', 'Nimal Dissanayake', 'MEMBER', NOW()),
('u5', 'kamala.fernando@rpt.lk',      '$2a$10$nmaj1RMShn0Fvbm/reNtk.CARgaJrEWIGcgPL8CquZQZeYPivNl1y', 'Kamala Fernando', 'MEMBER', NOW()),
('u6', 'tharaka.rathnayake@rpt.lk',   '$2a$10$nmaj1RMShn0Fvbm/reNtk.CARgaJrEWIGcgPL8CquZQZeYPivNl1y', 'Tharaka Rathnayake', 'MEMBER', NOW()),

-- VIEWER — password: Password@123
('u7', 'observer@rpt.lk',             '$2a$10$nmaj1RMShn0Fvbm/reNtk.CARgaJrEWIGcgPL8CquZQZeYPivNl1y', 'Observer User', 'VIEWER', NOW());

-- ============================================================
-- PROJECTS
-- ============================================================
INSERT INTO projects (id, title, summary, status, pi_id, tags, start_date, end_date, created_at, updated_at) VALUES
('p1', 'Smart Irrigation System Using IoT', 'Development of an intelligent irrigation system leveraging IoT sensors and machine learning to optimize water usage in paddy cultivation across Sri Lanka.', 'ACTIVE', 'u2', 'IoT, ML, Agriculture', '2025-01-15', '2025-12-31', NOW(), NOW()),
('p2', 'AI-Powered Medical Diagnosis Assistant', 'A deep learning model designed to assist rural healthcare workers in early-stage disease detection using symptom-based classification and imaging data.', 'PLANNING', 'u3', 'AI, Healthcare, Deep Learning', '2025-06-01', '2026-05-31', NOW(), NOW()),
('p3', 'Blockchain-Based Land Registry System', 'Implementing a transparent and tamper-proof land ownership registry using blockchain technology to reduce disputes in the Western Province.', 'ACTIVE', 'u2', 'Blockchain, Smart Contracts', '2024-09-01', '2025-08-31', NOW(), NOW()),
('p4', 'Renewable Energy Feasibility Study — Southern Province', 'A comprehensive feasibility analysis for solar and wind energy installations in the Southern Province to support the national green energy policy.', 'COMPLETED', 'u3', 'Renewable Energy, Solar, Wind', '2024-01-10', '2024-12-15', NOW(), NOW()),
('p5', 'E-Learning Platform for Rural Schools', 'Building an offline-first, multilingual e-learning platform tailored for under-resourced schools in rural Sri Lanka with limited internet connectivity.', 'PLANNING', 'u2', 'EdTech, E-Learning', '2025-08-01', '2026-07-31', NOW(), NOW());

-- ============================================================
-- MILESTONES
-- ============================================================
INSERT INTO milestones (id, project_id, title, description, due_date, is_completed, created_by) VALUES
('m1', 'p1', 'Sensor Procurement', 'Order and receive soil moisture and temperature sensors from vendors.', '2025-02-01', 1, 'u2'),
('m2', 'p1', 'Initial Setup', 'Install sensors in the pilot test field in Anuradhapura.', '2025-03-15', 0, 'u2'),
('m3', 'p2', 'Data Collection', 'Gather 10,000 anonymized X-Ray images for model training.', '2025-08-01', 0, 'u3'),
('m4', 'p3', 'Smart Contract Development', 'Write and test Ethereum smart contracts for land deed registration.', '2024-11-01', 1, 'u2'),
('m5', 'p4', 'Final Report Submission', 'Submit the 200-page feasibility report to the ministry.', '2024-12-10', 1, 'u3');

-- ============================================================
-- DOCUMENTS
-- ============================================================
INSERT INTO documents (id, project_id, title, url_or_path, uploaded_at, uploaded_by, description) VALUES
('d1', 'p1', 'Hardware Requirements Spec', '/uploads/p1/hardware_spec.pdf', NOW(), 'u4', 'Hardware specs'),
('d2', 'p2', 'Ethics Approval Letter', '/uploads/p2/ethics_approval.pdf', NOW(), 'u3', 'Ethics approval'),
('d3', 'p3', 'Architecture Diagram', '/uploads/p3/arch_diagram.png', NOW(), 'u5', 'Architecture diagram'),
('d4', 'p4', 'Feasibility Report Final', '/uploads/p4/feasibility_report_final.pdf', NOW(), 'u3', 'Final report'),
('d5', 'p5', 'Curriculum Outline', '/uploads/p5/curriculum_outline.pdf', NOW(), 'u6', 'Curriculum');
