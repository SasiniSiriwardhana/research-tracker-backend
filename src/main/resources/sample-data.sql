-- ============================================================
-- Research Project Tracker - Sample SQL Data
-- ============================================================
-- Run this script after the application has started and
-- Hibernate has created the tables via ddl-auto=update.
-- ============================================================

-- Create the database (if not auto-created)
CREATE DATABASE IF NOT EXISTS research_tracker_db;
USE research_tracker_db;

-- ============================================================
-- USERS (passwords are BCrypt-encoded for "password123")
-- ============================================================
INSERT INTO users (id, username, password, full_name, role, created_at) VALUES
('u001', 'admin@research.edu', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'System Administrator', 'ADMIN', NOW()),
('u002', 'dr.silva@research.edu', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Dr. Kumara Silva', 'PI', NOW()),
('u003', 'dr.fernando@research.edu', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Dr. Nimal Fernando', 'PI', NOW()),
('u004', 'kamal.perera@research.edu', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Kamal Perera', 'MEMBER', NOW()),
('u005', 'nimali.dias@research.edu', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Nimali Dias', 'MEMBER', NOW()),
('u006', 'viewer@research.edu', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Guest Viewer', 'VIEWER', NOW());

-- ============================================================
-- PROJECTS
-- ============================================================
INSERT INTO projects (id, title, summary, status, pi_id, tags, start_date, end_date, created_at, updated_at) VALUES
('p001', 'AI-Driven Climate Change Analysis', 'Research on applying artificial intelligence models to predict and analyze climate change patterns in South Asia.', 'ACTIVE', 'u002', 'AI, Climate, Environment, Machine Learning', '2025-01-15', '2026-06-30', NOW(), NOW()),
('p002', 'Blockchain for Supply Chain Transparency', 'Investigating the use of blockchain technology to improve transparency and traceability in agricultural supply chains.', 'PLANNING', 'u003', 'Blockchain, Supply Chain, Agriculture', '2025-03-01', '2026-12-31', NOW(), NOW()),
('p003', 'IoT-Based Smart Campus Monitoring', 'Developing an IoT framework for real-time monitoring of energy usage and environmental conditions across campus facilities.', 'ACTIVE', 'u002', 'IoT, Smart Campus, Energy, Sustainability', '2024-09-01', '2025-12-31', NOW(), NOW());

-- ============================================================
-- MILESTONES
-- ============================================================
INSERT INTO milestones (id, project_id, title, description, due_date, is_completed, created_by) VALUES
('m001', 'p001', 'Literature Review', 'Complete comprehensive literature review on AI applications in climate science.', '2025-03-15', true, 'u004'),
('m002', 'p001', 'Data Collection', 'Gather climate datasets from national meteorological departments.', '2025-06-30', false, 'u004'),
('m003', 'p001', 'Model Development', 'Develop and train the AI prediction model.', '2025-10-30', false, 'u005'),
('m004', 'p002', 'Requirements Analysis', 'Define functional and technical requirements for the blockchain prototype.', '2025-04-15', false, 'u005'),
('m005', 'p003', 'Sensor Deployment', 'Install IoT sensors across 5 campus buildings.', '2025-01-31', true, 'u004');

-- ============================================================
-- DOCUMENTS
-- ============================================================
INSERT INTO documents (id, project_id, title, description, url_or_path, uploaded_by, uploaded_at) VALUES
('d001', 'p001', 'Project Proposal - Climate AI', 'Initial project proposal document submitted to the research council.', '/documents/p001/proposal_climate_ai.pdf', 'u002', NOW()),
('d002', 'p001', 'Literature Review Summary', 'Summary of 50+ papers reviewed on AI and climate change.', '/documents/p001/lit_review_summary.docx', 'u004', NOW()),
('d003', 'p002', 'Blockchain Architecture Diagram', 'High-level architecture for the blockchain supply chain system.', '/documents/p002/blockchain_architecture.png', 'u003', NOW()),
('d004', 'p003', 'IoT Sensor Specifications', 'Technical specifications for the selected IoT sensors.', '/documents/p003/sensor_specs.pdf', 'u004', NOW());
