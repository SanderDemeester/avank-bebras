--
-- Script that generates the tables of the database for the Avank implementation of the Bebras application.
-- Make sure you run this script as the same database user that the application will user, otherwise you'll have permission problems
--

CREATE TABLE grades (
    name text NOT NULL,
    lowerbound integer NOT NULL,
    upperbound integer NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE users (
    id text NOT NULL,
    name text NOT NULL,
    gender text NOT NULL,
    birthdate date NOT NULL,
    email text UNIQUE,
    registrationdate date NOT NULL,
    preflanguage text NOT NULL,
    password text NOT NULL,
    hash text NOT NULL,
    type text NOT NULL,
    telephone character varying(256),
    address character varying(256),
    comment text,
    class integer,
    reset_token text,
    blockeduntil date,
    PRIMARY KEY (id)
);

CREATE TABLE schools (
    id SERIAL NOT NULL,
    name text NOT NULL,
    address text NOT NULL,
    orig text REFERENCES users,
    PRIMARY KEY (id)
);

CREATE TABLE classes (
    id SERIAL NOT NULL,
    name text NOT NULL,
    expdate date NOT NULL,
    schoolid integer NOT NULL REFERENCES schools,
    teacherid text NOT NULL REFERENCES users,
    level text NOT NULL REFERENCES grades,
    PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT users_class_fk FOREIGN KEY (class) REFERENCES classes;

CREATE TABLE servers (
    id text NOT NULL,
    location text NOT NULL,
    ftpuri text NOT NULL,
    ftpport text NOT NULL,
    ftpuser text NOT NULL,
    ftppass text NOT NULL,
    ftppath text,
    is_http_secured boolean NOT NULL,
    http_username text,
    http_password text,
    PRIMARY KEY (id)
);

CREATE TABLE difficulties (
    name text NOT NULL,
    rank integer NOT NULL UNIQUE,
    cpoints integer NOT NULL,
    wpoints integer NOT NULL,
    npoints integer NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE faq (
    id SERIAL NOT NULL,
    name text,
    content text,
    language text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE links (
    title text NOT NULL,
    url text NOT NULL,
    PRIMARY KEY (title)
);

CREATE TABLE questions (
    id SERIAL NOT NULL,
    officialid text UNIQUE,
    serverid text NOT NULL REFERENCES servers,
    active boolean NOT NULL,
    author text REFERENCES users,
    PRIMARY KEY (id)
);

CREATE TABLE contests (
    id text NOT NULL,
    name text NOT NULL,
    type text NOT NULL,
    active boolean NOT NULL,
    starttime timestamp without time zone,
    endtime timestamp without time zone,
    creator text NOT NULL REFERENCES users,
    duration integer NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE questionsets (
    id SERIAL NOT NULL,
    level text NOT NULL REFERENCES grades,
    contid text NOT NULL REFERENCES contests,
    active boolean NOT NULL,
    name text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE anonanswer (
    id SERIAL NOT NULL,
    questionid integer NOT NULL REFERENCES questions,
    answer text NOT NULL,
    correct boolean NOT NULL,
    language text NOT NULL,
    questionsetid integer NOT NULL REFERENCES questionsets,
    PRIMARY KEY (id)
);

CREATE TABLE classpupil (
    classid integer NOT NULL REFERENCES classes,
    indid text NOT NULL REFERENCES users,
    PRIMARY KEY (classid, indid)
);


CREATE TABLE competitionresponsible (
    userid text NOT NULL REFERENCES users,
    contid text NOT NULL REFERENCES contests,
    PRIMARY KEY (userid,contid)
);


CREATE TABLE competitionscore (
    qsid integer NOT NULL REFERENCES questionsets,
    uid text NOT NULL REFERENCES users,
    score integer NOT NULL,
    PRIMARY KEY (qsid,uid)
);

CREATE TABLE contestclasses (
    classid integer NOT NULL REFERENCES classes,
    contestid text NOT NULL REFERENCES contests,
    PRIMARY KEY (classid,contestid)
);

CREATE TABLE helpteachers (
    teacherid text NOT NULL REFERENCES users,
    classid integer NOT NULL REFERENCES classes,
    PRIMARY KEY (teacherid, classid)
);

CREATE TABLE pupilanswers (
    indid text NOT NULL REFERENCES users,
    qid integer NOT NULL REFERENCES questions,
    questionsetid integer NOT NULL REFERENCES questionsets,
    answer text NOT NULL,
    correct boolean NOT NULL,
    language text NOT NULL,
    PRIMARY KEY (indid,qid,questionsetid)
);

CREATE TABLE questionsetquestions (
    qid integer NOT NULL REFERENCES questions,
    qsid integer NOT NULL REFERENCES questionsets,
    difficulty text NOT NULL REFERENCES difficulties,
    PRIMARY KEY (qid,qsid)
);
