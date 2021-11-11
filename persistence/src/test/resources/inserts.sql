INSERT INTO image(imageId, photoblob)
VALUES (2, null);

INSERT INTO users(userid, email, username, password, enabled, nonlocked, strikes, bandate, imageid, name, role)
VALUES (4, 'email@email.com', 'username', 'password', true, true, 0, null, null, 'name', 0);

INSERT INTO listcomment(commentid, userid, listid, description, date) VALUES (2, 4, 2, 'List Comment', current_date);
INSERT INTO mediacomment(commentid, userid, mediaid, description, date) VALUES (2, 4, 2, 'Media Comment', current_date);
