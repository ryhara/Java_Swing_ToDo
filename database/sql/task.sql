DROP TABLE IF EXISTS task;
CREATE TABLE task (
	id INTEGER PRIMARY KEY,
	owner INTEGER,
	name TEXT,
	description TEXT,
	is_archive BOOLEAN default false
);

INSERT INTO task (owner, name, description) VALUES (1, 'School', 'task about school is written');
INSERT INTO task (owner, name, description) VALUES (1, 'Work', 'task about Work is written');
INSERT INTO task (owner, name, description) VALUES (1, 'Hobby', 'task about Hobby is written');
INSERT INTO task (owner, name, description) VALUES (2, 'School', 'task about School is written');
INSERT INTO task (owner, name, description) VALUES (2, 'Work', 'task about Work is written');
INSERT INTO task (owner, name, description) VALUES (2, 'Programming', 'task about School is written');
INSERT INTO task (owner, name, description) VALUES (3, 'School', 'task about School is written');
INSERT INTO task (owner, name, description) VALUES (3, 'Club Activity', 'task about Club Activity is written');
INSERT INTO task (owner, name, description) VALUES (3, 'Part-time Job', 'task about Part-time Job is written');
INSERT INTO task (owner, name, description) VALUES (4, 'School', 'task about school is written');
INSERT INTO task (owner, name, description) VALUES (4, 'Work', 'task about Work is written');
INSERT INTO task (owner, name, description) VALUES (4, 'Hobby', 'task about Hobby is written');
INSERT INTO task (owner, name, description) VALUES (5, 'School', 'task about school is written');
INSERT INTO task (owner, name, description) VALUES (5, 'Work', 'task about Work is written');
INSERT INTO task (owner, name, description) VALUES (5, 'Hobby', 'task about Hobby is written');
INSERT INTO task (owner, name, description) VALUES (6, 'School', 'task about school is written');
INSERT INTO task (owner, name, description) VALUES (6, 'Work', 'task about Work is written');
INSERT INTO task (owner, name, description) VALUES (6, 'Shopping', 'task about Hobby is written');