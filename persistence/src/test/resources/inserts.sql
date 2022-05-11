INSERT INTO image(imageId, photoblob)
VALUES (2, null);

INSERT INTO users(userid, email, username, password, enabled, nonlocked, strikes, bandate, imageid, name, role)
VALUES (4, 'email@email.com', 'username', 'password', true, true, 0, null, null, 'name', 0);

INSERT INTO users(userid, email, username, password, enabled, nonlocked, strikes, bandate, imageid, name, role)
VALUES (5, 'mod@request.com', 'modRequestUser', 'password', true, true, 0, null, null, 'name', 0);


INSERT INTO listcomment(commentid, userid, listid, description, date)
VALUES (2, 4, 2, 'List Comment', current_date);
INSERT INTO listcomment(commentid, userid, listid, description, date)
VALUES (3, 4, 2, 'List Comment 2', current_date);
INSERT INTO mediacomment(commentid, userid, mediaid, description, date)
VALUES (2, 4, 1, 'Media Comment', current_date);
INSERT INTO mediacomment(commentid, userid, mediaid, description, date)
VALUES (3, 4, 1, 'Media Comment 2', current_date);

INSERT INTO collaborative(collabid, listid, collaboratorid, accepted)
VALUES (3, 2, 4, false);

INSERT INTO favoritemedia(userid, mediaid)
VALUES (4, 2);
INSERT INTO favoritelists(userid, medialistid)
VALUES (4, 3);

INSERT INTO collaborative(collabid, listid, collaboratorid, accepted)
VALUES (4, 3, 4, true);

INSERT INTO modrequests(requestid, userid, date)
VALUES (2, 5, current_timestamp);

INSERT INTO listreport(reportid, listid, reporteeid, report, date)
VALUES (2, 3, 4, 'Report', current_timestamp);
INSERT INTO listcommentreport(reportid, commentid, reporteeid, report, date)
VALUES (2, 3, 4, 'Report', current_timestamp);
INSERT INTO mediacommentreport(reportid, commentid, reporteeid, report, date)
VALUES (2, 3, 4, 'Report', current_timestamp);

INSERT INTO towatchmedia(watchedmediaid, userid, mediaid, watchdate)
VALUES (2, 4, 2, null); --TO WATCH
INSERT INTO towatchmedia(watchedmediaid, userid, mediaid, watchdate)
VALUES (3, 4, 3, current_timestamp);  --WATCHED

ALTER SEQUENCE collaborative_collabid_seq RESTART WITH 100;
ALTER SEQUENCE commentnotifications_notificationid_seq RESTART WITH 100;
-- ALTER SEQUENCE crew_crewid_seq RESTART WITH 100;
-- ALTER SEQUENCE director_directorid_seq RESTART WITH 100;
-- ALTER SEQUENCE genre_genreid_seq RESTART WITH 100;
ALTER SEQUENCE image_imageid_seq RESTART WITH 100;
ALTER SEQUENCE listcomment_commentid_seq RESTART WITH 100;
ALTER SEQUENCE listcommentreport_reportid_seq RESTART WITH 100;
ALTER SEQUENCE listreport_reportid_seq RESTART WITH 100;
-- ALTER SEQUENCE media_mediaid_seq RESTART WITH 100;
ALTER SEQUENCE mediacomment_commentid_seq RESTART WITH 100;
ALTER SEQUENCE mediacommentreport_reportid_seq RESTART WITH 100;
ALTER SEQUENCE medialist_medialistid_seq RESTART WITH 100;
ALTER SEQUENCE modrequests_requestid_seq RESTART WITH 100;
-- ALTER SEQUENCE staffmember_staffmemberid_seq RESTART WITH 100;
-- ALTER SEQUENCE studio_studioid_seq RESTART WITH 100;
ALTER SEQUENCE towatchmedia_watchedmediaid_seq RESTART WITH 100;
ALTER SEQUENCE users_userid_seq RESTART WITH 100;
