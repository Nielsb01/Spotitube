SELECT * FROM playlist;
SELECT * FROM track;

SELECT * FROM track T WHERE T.trackId NOT IN(SELECT TIP.trackId FROM playlist P JOIN tracksinplaylist TIP ON p.playlistId = tip.playlistId WHERE p.playlistId = 3 AND p.token = "123456abcd" );

SELECT * FROM tracksInPlaylist;
DELETE FROM tracksinplaylist WHERE playlistId = 1 AND trackId = 7 AND EXISTS(SELECT* FROM playlist WHERE playlistId = 1 AND token = "123456abcd");

INSERT INTO tracksInPlaylist VALUES(9,4);

SELECT SUM(t.duration) FROM playlist P JOIN tracksinplaylist TIP ON p.playlistId = tip.playlistId JOIN track T ON tip.trackId = t.trackId WHERE token = "123456abcd";
SELECT playlistId, name, owner, (SELECT SUM(t.duration) FROM playlist P JOIN tracksinplaylist TIP ON p.playlistId = tip.playlistId JOIN track T ON tip.trackId = t.trackId WHERE token = "123456abcd") as dur FROM playlist WHERE token = "123456abcd";
SELECT p.playlistId, p.name, p.owner,  CASE WHEN SUM(t.duration) is not null THEN SUM(t.duration) ELSE 'zero' END AS dur FROM playlist P JOIN tracksinplaylist TIP ON p.playlistId = tip.playlistId JOIN track T ON tip.trackId = t.trackId WHERE token = "123456abcd" GROUP BY p.playlistId