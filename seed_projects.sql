-- Insert projects and related data using actual user UUIDs from the DB
USE research_tracker_db;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE documents;
TRUNCATE TABLE milestones;
TRUNCATE TABLE projects;
SET FOREIGN_KEY_CHECKS = 1;

-- PROJECTS (pi_id = actual UUIDs from users table)
INSERT INTO projects (id, title, summary, status, pi_id, tags, start_date, end_date, created_at, updated_at) VALUES
('p1', 'Smart Irrigation System Using IoT',
 'Development of an intelligent irrigation system leveraging IoT sensors and ML to optimize water usage in paddy cultivation.',
 'ACTIVE', '83b6533f-e0b4-486e-ad0c-41401efb0451', 'IoT,ML,Agriculture', '2025-01-15', '2025-12-31', NOW(), NOW()),

('p2', 'AI-Powered Medical Diagnosis Assistant',
 'A deep learning model to assist rural healthcare workers in early-stage disease detection.',
 'PLANNING', 'fdf267a2-24bd-4f52-90ee-f5f6acf53a09', 'AI,Healthcare,DeepLearning', '2025-06-01', '2026-05-31', NOW(), NOW()),

('p3', 'Blockchain-Based Land Registry System',
 'Implementing a transparent and tamper-proof land ownership registry using blockchain technology.',
 'ACTIVE', '83b6533f-e0b4-486e-ad0c-41401efb0451', 'Blockchain,SmartContracts', '2024-09-01', '2025-08-31', NOW(), NOW()),

('p4', 'Renewable Energy Feasibility Study',
 'A comprehensive feasibility analysis for solar and wind energy installations in the Southern Province.',
 'COMPLETED', 'fdf267a2-24bd-4f52-90ee-f5f6acf53a09', 'RenewableEnergy,Solar', '2024-01-10', '2024-12-15', NOW(), NOW()),

('p5', 'E-Learning Platform for Rural Schools',
 'Building an offline-first multilingual e-learning platform for under-resourced schools in rural Sri Lanka.',
 'PLANNING', '83b6533f-e0b4-486e-ad0c-41401efb0451', 'EdTech,eLearning', '2025-08-01', '2026-07-31', NOW(), NOW());

-- MILESTONES
INSERT INTO milestones (id, project_id, title, description, due_date, is_completed, created_by) VALUES
('m1','p1','Sensor Procurement','Order and receive soil moisture and temperature sensors.','2025-02-01', 1, '83b6533f-e0b4-486e-ad0c-41401efb0451'),
('m2','p1','Pilot Field Setup','Install sensors in the pilot test field in Anuradhapura.','2025-03-15', 0, '83b6533f-e0b4-486e-ad0c-41401efb0451'),
('m3','p2','Dataset Collection','Gather 10,000 anonymized X-Ray images for model training.','2025-08-01', 0, 'fdf267a2-24bd-4f52-90ee-f5f6acf53a09'),
('m4','p3','Smart Contract Dev','Write and test Ethereum smart contracts for land deed registration.','2024-11-01', 1, '83b6533f-e0b4-486e-ad0c-41401efb0451'),
('m5','p4','Final Report Submission','Submit the 200-page feasibility report to the ministry.','2024-12-10', 1, 'fdf267a2-24bd-4f52-90ee-f5f6acf53a09');

-- DOCUMENTS
INSERT INTO documents (id, project_id, title, description, url_or_path, uploaded_at, uploaded_by) VALUES
('d1','p1','Hardware Requirements Spec','Hardware specs document.','/uploads/p1/hardware_spec.pdf', NOW(), '163bb62e-4e56-4fee-8283-849e15a2d175'),
('d2','p2','Ethics Approval Letter','Ethics board approval letter.','/uploads/p2/ethics_approval.pdf', NOW(), 'fdf267a2-24bd-4f52-90ee-f5f6acf53a09'),
('d3','p3','Architecture Diagram','System architecture diagram.','/uploads/p3/arch_diagram.png', NOW(), '163bb62e-4e56-4fee-8283-849e15a2d175'),
('d4','p4','Feasibility Report Final','Final feasibility study report.','/uploads/p4/feasibility_report_final.pdf', NOW(), 'fdf267a2-24bd-4f52-90ee-f5f6acf53a09'),
('d5','p5','Curriculum Outline','E-learning curriculum outline.','/uploads/p5/curriculum_outline.pdf', NOW(), '163bb62e-4e56-4fee-8283-849e15a2d175');
