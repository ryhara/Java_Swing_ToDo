DROP TABLE IF EXISTS member;
CREATE TABLE member (
	id INTEGER PRIMARY KEY,
	name TEXT,
	email TEXT,
	introduction TEXT default "",
	password TEXT,
	is_public BOOLEAN default 1
);

INSERT INTO member (name, email, password, is_public) VALUES ('admin', 'admin@admin.com', 'admin', false);
INSERT INTO member (name, email, password) VALUES ('hara', 'hara@gmail.com', 'hara');
INSERT INTO member (name, email, password) VALUES ('hokari', 'hokari@gmail.com', 'hokari');
INSERT INTO member (name, email, password) VALUES ('morisaki', 'morisaki@gmail.com', 'morisaki');
INSERT INTO member (name, email, password) VALUES ('matsushima', 'matsushima@gmail.com', 'matsushima');
INSERT INTO member (name, email, password, is_public) VALUES ('tanaka', 'tanaka@gmail.com', 'tanaka', true);
INSERT INTO member (name, email, password, is_public) VALUES ('suzuki', 'suzuki@gmail.com', 'suzuki', true);
INSERT INTO member (name, email, password, is_public) VALUES ('hayashi', 'hayashi@gmail.com', 'hayashi', true);
INSERT INTO member (name, email, password, is_public) VALUES ('takahashi', 'takahashi@gmail.com', 'takahashi', true);
INSERT INTO member (name, email, password, is_public) VALUES ('harada', 'harada@gmail.com', 'harada', true);
INSERT INTO member (name, email, password, is_public) VALUES ('nakahara', 'nakahara@gmail.com', 'nakahara', true);
INSERT INTO member (name, email, password, is_public) VALUES ('nakata', 'nakata@gmail.com', 'nakata', true);
INSERT INTO member (name, email, password, is_public) VALUES ('dodo', 'dodo@gmail.com', 'dodo', true);
INSERT INTO member (name, email, password, is_public) VALUES ('okamura', 'okamura@gmail.com', 'okamura', true);
INSERT INTO member (name, email, password, is_public) VALUES ('egawa', 'egawa@gmail.com', 'egawa', true);