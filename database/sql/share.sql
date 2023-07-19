DROP TABLE IF EXISTS share;
CREATE TABLE share (
	task INTEGER,
	member INTEGER,
	is_editable BOOLEAN default false
);

INSERT INTO share (task, member, is_editable) VALUES (1, 2, true);
INSERT INTO share (task, member, is_editable) VALUES (1, 3, true);
INSERT INTO share (task, member, is_editable) VALUES (1, 4, false);
INSERT INTO share (task, member, is_editable) VALUES (4, 3, false);
INSERT INTO share (task, member, is_editable) VALUES (5, 3, false);
INSERT INTO share (task, member, is_editable) VALUES (6, 3, false);
INSERT INTO share (task, member, is_editable) VALUES (4, 1, false);
INSERT INTO share (task, member, is_editable) VALUES (5, 1, false);
INSERT INTO share (task, member, is_editable) VALUES (6, 1, false);