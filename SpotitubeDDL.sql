CREATE SCHEMA IF NOT EXISTS spotitube;

USE spotitube;

CREATE TABLE `track` (
  `trackId` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL,
  `performer` VARCHAR(100) NOT NULL,
  `duration` VARCHAR(50) NOT NULL,
  `album` VARCHAR(50) NOT NULL,
  `playcount` INT NOT NULL,
  `publicationDate` DATE NULL,
  `description` VARCHAR(100) NULL,
  `offlineAvailable` TINYINT(1) NULL,
  PRIMARY KEY (`trackId`));

CREATE TABLE `user` (
  `user` VARCHAR(50) NOT NULL,
  `passwordHashed` VARCHAR(255) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `token` VARCHAR(50) NULL,
  PRIMARY KEY (`user`),
  UNIQUE INDEX `usercol_UNIQUE` (`token` ASC) VISIBLE);
  
  CREATE TABLE `playlist` (
  `playlistId` INT NOT NULL AUTO_INCREMENT,
  `token` VARCHAR(50) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `owner` TINYINT(1) NOT NULL,
  PRIMARY KEY (`playlistId`));
  
  CREATE TABLE `tracksInPlaylist` (
  `trackId` INT NOT NULL,
  `playlistId` INT NOT NULL,
  PRIMARY KEY (`trackId`, `playlistId`));
  
ALTER TABLE `tracksInPlaylist` 
ADD CONSTRAINT `FK_TRACKSINPLAYLIST_TRACK_ID`
  FOREIGN KEY (`trackId`)
  REFERENCES `track` (`trackId`)
  ON UPDATE CASCADE
  ON DELETE CASCADE,

ADD CONSTRAINT `FK_TRACKSINPLAYLIST_PLAYLIST_ID`
  FOREIGN KEY (`playlistId`)
  REFERENCES `playlist` (`playlistId`)
  ON UPDATE CASCADE
  ON DELETE CASCADE;
  
ALTER TABLE `playlist` 
ADD CONSTRAINT `FK_PLAYLIST_USER_TOKEN`
  FOREIGN KEY (`token`)
  REFERENCES `user` (`token`)
  ON UPDATE CASCADE
  ON DELETE NO ACTION;
  
INSERT INTO user VALUES ("niels", "2869103419ce0e9405faf2153fbb014fa830a6da7978922a2049d0726e35a785", "niels borkes", "123456abcd");

INSERT INTO playlist (token, name, owner) VALUES ("123456abcd","Death metal",true); 
INSERT INTO playlist (token, name, owner) VALUES ("123456abcd","Pop",false); 

INSERT INTO track VALUES(1,"Song for someone", "The Frames", 350, "The cost", 0, "2001-01-11", "Description", false);
INSERT INTO track VALUES(2,"The cost", "The Frames", 423, "The cost", 37, "2006-03-19", "Title song from the Album The Cost", true);
INSERT INTO track VALUES(3,"Ocean and a rock", "Lisa Hannigan", 337, "Sea sew", 0, "2001-01-11", "Description", false);
INSERT INTO track VALUES(4,"So Long, Marianne", "Leonard Cohen", 546, "Songs of Leonard Cohen", 0, "2001-01-11", "Description", false);
INSERT INTO track VALUES(5,"One", "Metallica", 423, "album", 37, "2001-03-18", "Long version", true);
INSERT INTO track VALUES(6,"title1", "artist1", 101, "album1", 0, "2001-01-01", "Description1", true);
INSERT INTO track VALUES(7,"title2", "artist1", 101, "album1", 0, "2001-01-02", "Description2", true);
INSERT INTO track VALUES(8,"title3", "artist1", 101, "album1", 0, "2001-01-03", "Description3", true);
INSERT INTO track VALUES(9,"title4", "artist1", 101, "album1", 0, "2001-01-04", "Description4", true);

INSERT INTO tracksInPlaylist VALUES(6,1);
INSERT INTO tracksInPlaylist VALUES(7,1);
INSERT INTO tracksInPlaylist VALUES(8,1);
INSERT INTO tracksInPlaylist VALUES(9,1);
INSERT INTO tracksInPlaylist VALUES(6,2);
INSERT INTO tracksInPlaylist VALUES(7,2);

