INSERT INTO phase (phaseId, phaseDescription, phaseName, phaseType, projectOrder, project_projectId) VALUES (1, 'Design Phase', 'Design', 1, 1, 1);
INSERT INTO phase (phaseId, phaseDescription, phaseName, phaseType, projectOrder, project_projectId) VALUES (2, 'Coding Phase', 'Coding', 2, 2, 1);
INSERT INTO phase (phaseId, phaseDescription, phaseName, phaseType, projectOrder, project_projectId) VALUES (3, 'Testing Phase', 'Testing', 3, 3, 1);

INSERT INTO task (taskId, estimatedHours, investedHours, remainingHours, endDate, startDate, taskDescription, taskName, taskSize, taskState, taskType, assignedUser_userName, phase_phaseId, project_projectId) VALUES (1, 10, 5, 5, NOW(), NOW(), 'Análisis de Requerimientos', 'Análisis de Requerimientos', 100, 1, 1, 'admin', 1, 1);

UPDATE project SET actualPhase=1 WHERE projectId=1;