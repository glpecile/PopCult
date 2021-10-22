-- ImageDao
CREATE TABLE IF NOT EXISTS image
(
    imageId            SERIAL PRIMARY KEY,
    photoBlob          BYTEA
);

-- UserDao
CREATE TABLE IF NOT EXISTS users
(
    userId   SERIAL PRIMARY KEY,
    email    TEXT    NOT NULL,
    username TEXT    NOT NULL,
    password TEXT    NOT NULL,
    name     VARCHAR(100),
    enabled  BOOLEAN NOT NULL,
    imageId  INT,
    role     INT     NOT NULL,
    UNIQUE (email),
    UNIQUE (username),
    FOREIGN KEY (imageId) REFERENCES image (imageId) ON DELETE SET NULL
);

-- MediaDao
CREATE TABLE IF NOT EXISTS media
(
    mediaId     SERIAL PRIMARY KEY,
    type        INT          NOT NULL,
    title       VARCHAR(100) NOT NULL,
    description TEXT,
    image       TEXT,
    length      INT,
    releaseDate DATE,
    seasons     INT,
    country     INT
);

-- ListsDao
CREATE TABLE IF NOT EXISTS mediaList
(
    mediaListId   SERIAL PRIMARY KEY,
    userId        INT  NOT NULL,
    listname      TEXT NOT NULL,
    description   TEXT NOT NULL,
    creationDate  DATE,
    visibility    BOOLEAN,
    collaborative BOOLEAN,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS listElement
(
    mediaId     INT NOT NULL,
    mediaListId INT NOT NULL,
    FOREIGN KEY (mediaId) REFERENCES media (mediaId) ON DELETE CASCADE,
    FOREIGN KEY (mediaListId) REFERENCES medialist (medialistid) ON DELETE CASCADE,
    UNIQUE (mediaId, mediaListId)
);

CREATE TABLE IF NOT EXISTS forkedLists
(
    originalistId INT NOT NULL,
    forkedlistId  INT NOT NULL,
    FOREIGN KEY (originalistId) REFERENCES medialist (medialistid) ON DELETE CASCADE,
    FOREIGN KEY (forkedlistId) REFERENCES medialist (medialistid) ON DELETE CASCADE
);

-- CollaborativeDao
CREATE TABLE IF NOT EXISTS collaborative
(
    collabId       SERIAL PRIMARY KEY,
    listId         INT NOT NULL,
    collaboratorId INT NOT NULL,
    accepted       BOOLEAN,
    FOREIGN KEY (listId) REFERENCES medialist (medialistid) ON DELETE CASCADE,
    FOREIGN KEY (collaboratorId) REFERENCES users (userid) ON DELETE CASCADE,
    UNIQUE (listId, collaboratorId)
);

-- CommentDao
CREATE TABLE IF NOT EXISTS mediacomment
(
    commentId   SERIAL PRIMARY KEY,
    userId      INT NOT NULL,
    mediaId     INT NOT NULL,
    description TEXT,
    date        DATE,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE,
    FOREIGN KEY (mediaId) REFERENCES media (mediaId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS listcomment
(
    commentId   SERIAL PRIMARY KEY,
    userId      INT NOT NULL,
    listId      INT NOT NULL,
    description TEXT,
    date        DATE,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE,
    FOREIGN KEY (listId) REFERENCES medialist (medialistid) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS commentNotifications
(
    notificationId SERIAL PRIMARY KEY,
    commentId INT NOT NULL,
    opened    BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (commentId) REFERENCES listcomment (commentid) ON DELETE CASCADE
);

-- FavoriteDao
CREATE TABLE IF NOT EXISTS favoritemedia
(
    userId  INT NOT NULL,
    mediaId INT NOT NULL,
    FOREIGN KEY (mediaId) REFERENCES media (mediaId) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS favoritelists
(
    userId      INT NOT NULL,
    mediaListId INT NOT NULL,
    FOREIGN KEY (mediaListId) REFERENCES medialist (mediaListId) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE
);

-- GenreDao
CREATE TABLE IF NOT EXISTS genre
(
    genreId SERIAL PRIMARY KEY,
    name    TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS mediaGenre
(
    mediaId INT NOT NULL,
    genreId INT NOT NULL,
    FOREIGN KEY (mediaId) REFERENCES media (mediaid) ON DELETE CASCADE,
    FOREIGN KEY (genreId) REFERENCES genre (genreId) ON DELETE CASCADE
);

-- ModeratorDao
CREATE TABLE IF NOT EXISTS modRequests
(
    requestId SERIAL PRIMARY KEY,
    userId INT,
    date   DATE NOT NULL,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE
);

-- ReportDao
CREATE TABLE IF NOT EXISTS listReport
(
    reportId   SERIAL PRIMARY KEY,
    listId     INT  NOT NULL,
    reporteeId INT  NOT NULL,
    report     TEXT NOT NULL,
    date       DATE NOT NULL,
    FOREIGN KEY (listId) REFERENCES mediaList (mediaListId) ON DELETE CASCADE,
    FOREIGN KEY (reporteeId) REFERENCES users (userId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS listCommentReport
(
    reportId   SERIAL PRIMARY KEY,
    listId     INT  NOT NULL,
    commentId  INT  NOT NULL,
    reporteeId INT  NOT NULL,
    report     TEXT NOT NULL,
    date       DATE NOT NULL,
    FOREIGN KEY (listId) REFERENCES mediaList (mediaListId) ON DELETE CASCADE,
    FOREIGN KEY (commentId) REFERENCES listComment (commentId) ON DELETE CASCADE,
    FOREIGN KEY (reporteeId) REFERENCES users (userId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS mediaCommentReport
(
    reportId   SERIAL PRIMARY KEY,
    mediaId    INT  NOT NULL,
    commentId  INT  NOT NULL,
    reporteeId INT  NOT NULL,
    report     TEXT NOT NULL,
    date       DATE NOT NULL,
    FOREIGN KEY (mediaId) REFERENCES media (mediaId) ON DELETE CASCADE,
    FOREIGN KEY (commentId) REFERENCES mediaComment (commentId) ON DELETE CASCADE,
    FOREIGN KEY (reporteeId) REFERENCES users (userId) ON DELETE CASCADE
);

-- StaffDao
CREATE TABLE IF NOT EXISTS staffMember
(
    staffMemberId SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    description   TEXT,
    image         TEXT
);

CREATE TABLE IF NOT EXISTS director
(
    mediaId       INT NOT NULL,
    staffMemberId INT NOT NULL,
    FOREIGN KEY (mediaId) References media (mediaId) ON DELETE CASCADE,
    FOREIGN KEY (staffMemberId) References staffMember (staffMemberId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS crew
(
    mediaId       INT          NOT NULL,
    staffMemberId INT          NOT NULL,
    characterName VARCHAR(100) NOT NULL,
    FOREIGN KEY (mediaId) References media (mediaId) ON DELETE CASCADE,
    FOREIGN KEY (staffMemberId) References staffMember (staffMemberId) ON DELETE CASCADE
);

-- StudioDao
CREATE TABLE IF NOT EXISTS studio
(
    studioId SERIAL PRIMARY KEY,
    name     TEXT,
    image    TEXT
);
CREATE TABLE IF NOT EXISTS mediaStudio
(
    mediaId  INT NOT NULL,
    studioId INT NOT NULL,
    FOREIGN KEY (mediaId) REFERENCES media (mediaId),
    FOREIGN KEY (studioId) REFERENCES studio (studioId)
);

-- TokenDao
CREATE TABLE IF NOT EXISTS token
(
    userId     INT  NOT NULL,
    type       INT  NOT NULL,
    token      TEXT NOT NULL,
    expiryDate DATE NOT NULL,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE
);

-- WatchDao
CREATE TABLE IF NOT EXISTS towatchmedia
(
    userId    INT NOT NULL,
    mediaId   INT NOT NULL,
    watchDate DATE,
    FOREIGN KEY (mediaId) REFERENCES media (mediaId) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE
);




