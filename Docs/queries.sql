INSERT INTO project (projectId, actualPhase, projectDescription, projectDueDate, projectName, projectPlannedDate) VALUES (1, 0, 'Administracion de proyectos de software', NOW(), 'BM', NOW());

UPDATE project SET actualPhase=1 WHERE projectId=1;

INSERT INTO user_t (userName, email, fullName, password, passwordRecoveryExpiration, passwordRecoveryTicket, permissions) VALUES ('ecampos', 'lalo@lalo.com', 'Eduardo Campos', 'ecampos', NULL, NULL, 10);
INSERT INTO user_t (userName, email, fullName, password, passwordRecoveryExpiration, passwordRecoveryTicket, permissions) VALUES ('mrangel', 'marco@marco.com', 'Marco Rangel', 'mrangel', NULL, NULL, 10);
INSERT INTO user_t (userName, email, fullName, password, passwordRecoveryExpiration, passwordRecoveryTicket, permissions) VALUES ('hgarcia', 'buho@buho.com', 'Humberto Garcia', 'hgarcia', NULL, NULL, 10);
INSERT INTO user_t (userName, email, fullName, password, passwordRecoveryExpiration, passwordRecoveryTicket, permissions) VALUES ('omondragon', 'oscar@oscar.com', 'Oscar Mondragon', 'omondragon', NULL, NULL, 20);

INSERT INTO phase (phaseId, phaseDescription, phaseName, phaseType, projectOrder, project_projectId) VALUES (1, 'Requirements Phase', 'Requirements', 0, 1, 1);
INSERT INTO phase (phaseId, phaseDescription, phaseName, phaseType, projectOrder, project_projectId) VALUES (2, 'Design Phase', 'Design', 1, 2, 1);
INSERT INTO phase (phaseId, phaseDescription, phaseName, phaseType, projectOrder, project_projectId) VALUES (3, 'Coding Phase', 'Coding', 2, 3, 1);
INSERT INTO phase (phaseId, phaseDescription, phaseName, phaseType, projectOrder, project_projectId) VALUES (4, 'Coding Review', 'Coding Review', 3, 4, 1);
INSERT INTO phase (phaseId, phaseDescription, phaseName, phaseType, projectOrder, project_projectId) VALUES (5, 'Testing Phase', 'Testing', 4, 5, 1);
INSERT INTO phase (phaseId, phaseDescription, phaseName, phaseType, projectOrder, project_projectId) VALUES (6, 'Maintenance Phase', 'Maintenance', 5, 6, 1);


INSERT INTO task (taskId, estimatedHours, investedHours, remainingHours, endDate, startDate, taskDescription, taskName, taskSize, taskState, taskType, assignedUser_userName, phase_phaseId, project_projectId) VALUES (1, 10, 5, 5, NOW(), NOW(), 'Análisis de Requerimientos', 'Análisis de Requerimientos', 100, 1, 1, 'admin', 1, 1);
INSERT INTO task (taskId, estimatedHours, investedHours, remainingHours, endDate, startDate, taskDescription, taskName, taskSize, taskState, taskType, assignedUser_userName, phase_phaseId, project_projectId) VALUES (2, 20, 5, 15, NOW(), NOW(), 'Codificacion', 'Codificacion 1', 300, 1, 0, 'ecampos', 3, 1);
INSERT INTO task (taskId, estimatedHours, investedHours, remainingHours, endDate, startDate, taskDescription, taskName, taskSize, taskState, taskType, assignedUser_userName, phase_phaseId, project_projectId) VALUES (3, 20, 0, 0, NOW(), NOW(), 'Codificacion', 'Codificacion 2', 300, 0, 0, 'ecampos', 3, 1);
INSERT INTO task (taskId, estimatedHours, investedHours, remainingHours, endDate, startDate, taskDescription, taskName, taskSize, taskState, taskType, assignedUser_userName, phase_phaseId, project_projectId) VALUES (4, 10, 2, 8, NOW(), NOW(), 'Revision', 'Revision 1', 100, 1, 1, 'ecampos', 4, 1);
INSERT INTO task (taskId, estimatedHours, investedHours, remainingHours, endDate, startDate, taskDescription, taskName, taskSize, taskState, taskType, assignedUser_userName, phase_phaseId, project_projectId) VALUES (5, 15, 7, 8, NOW(), NOW(), 'Revision', 'Revision 2', 100, 1, 2, 'ecampos', 4, 1);
INSERT INTO task (taskId, estimatedHours, investedHours, remainingHours, endDate, startDate, taskDescription, taskName, taskSize, taskState, taskType, assignedUser_userName, phase_phaseId, project_projectId) VALUES (6, 10, 12, 0, NOW(), NOW(), 'Revision', 'Revision 3', 100, 2, 3, 'ecampos', 4, 1);


