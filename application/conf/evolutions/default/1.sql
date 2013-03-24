# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table users (
  id                        varchar(255) not null,
  name                      varchar(255),
  birthdate                 timestamp,
  registration_date         timestamp,
  pref_language             varchar(255),
  password                  varchar(255),
  hash                      varchar(255),
  telephone                 varchar(255),
  address                   varchar(255),
  email                     varchar(255),
  gender                    varchar(6),
  type                      varchar(13),
  active                    boolean,
  constraint ck_users_gender check (gender in ('MALE','FEMALE')),
  constraint ck_users_type check (type in ('ADMINISTRATOR','ORGANIZER','INDEPENDENT','PUPIL','TEACHER','AUTHOR','ANON')),
  constraint pk_users primary key (id))
;

create table users (
  id                        varchar(255) not null,
  name                      varchar(255),
  birthdate                 timestamp,
  registration_date         timestamp,
  pref_language             varchar(255),
  password                  varchar(255),
  hash                      varchar(255),
  telephone                 varchar(255),
  address                   varchar(255),
  email                     varchar(255),
  gender                    varchar(6),
  type                      varchar(13),
  active                    boolean,
  constraint ck_users_gender check (gender in ('MALE','FEMALE')),
  constraint ck_users_type check (type in ('ADMINISTRATOR','ORGANIZER','INDEPENDENT','PUPIL','TEACHER','AUTHOR','ANON')),
  constraint pk_users primary key (id))
;

create table users (
  id                        varchar(255) not null,
  name                      varchar(255),
  birthdate                 timestamp,
  registration_date         timestamp,
  pref_language             varchar(255),
  password                  varchar(255),
  hash                      varchar(255),
  telephone                 varchar(255),
  address                   varchar(255),
  email                     varchar(255),
  gender                    varchar(6),
  type                      varchar(13),
  active                    boolean,
  constraint ck_users_gender check (gender in ('MALE','FEMALE')),
  constraint ck_users_type check (type in ('ADMINISTRATOR','ORGANIZER','INDEPENDENT','PUPIL','TEACHER','AUTHOR','ANON')),
  constraint pk_users primary key (id))
;

create table users (
  id                        varchar(255) not null,
  name                      varchar(255),
  birthdate                 timestamp,
  registration_date         timestamp,
  pref_language             varchar(255),
  password                  varchar(255),
  hash                      varchar(255),
  telephone                 varchar(255),
  address                   varchar(255),
  email                     varchar(255),
  gender                    varchar(6),
  type                      varchar(13),
  active                    boolean,
  constraint ck_users_gender check (gender in ('MALE','FEMALE')),
  constraint ck_users_type check (type in ('ADMINISTRATOR','ORGANIZER','INDEPENDENT','PUPIL','TEACHER','AUTHOR','ANON')),
  constraint pk_users primary key (id))
;

create table server (
  name                      varchar(255) not null,
  base_url                  varchar(255),
  path                      varchar(255),
  constraint pk_server primary key (name))
;

create table users (
  id                        varchar(255) not null,
  name                      varchar(255),
  birthdate                 timestamp,
  registration_date         timestamp,
  pref_language             varchar(255),
  password                  varchar(255),
  hash                      varchar(255),
  telephone                 varchar(255),
  address                   varchar(255),
  email                     varchar(255),
  gender                    varchar(6),
  type                      varchar(13),
  active                    boolean,
  constraint ck_users_gender check (gender in ('MALE','FEMALE')),
  constraint ck_users_type check (type in ('ADMINISTRATOR','ORGANIZER','INDEPENDENT','PUPIL','TEACHER','AUTHOR','ANON')),
  constraint pk_users primary key (id))
;

create table users (
  id                        varchar(255) not null,
  name                      varchar(255),
  birthdate                 timestamp,
  registration_date         timestamp,
  pref_language             varchar(255),
  password                  varchar(255),
  hash                      varchar(255),
  telephone                 varchar(255),
  address                   varchar(255),
  email                     varchar(255),
  gender                    varchar(6),
  type                      varchar(13),
  active                    boolean,
  constraint ck_users_gender check (gender in ('MALE','FEMALE')),
  constraint ck_users_type check (type in ('ADMINISTRATOR','ORGANIZER','INDEPENDENT','PUPIL','TEACHER','AUTHOR','ANON')),
  constraint pk_users primary key (id))
;

create sequence users_seq;

create sequence users_seq;

create sequence users_seq;

create sequence users_seq;

create sequence server_seq;

create sequence users_seq;

create sequence users_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists users;

drop table if exists users;

drop table if exists users;

drop table if exists users;

drop table if exists server;

drop table if exists users;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists users_seq;

drop sequence if exists users_seq;

drop sequence if exists users_seq;

drop sequence if exists users_seq;

drop sequence if exists server_seq;

drop sequence if exists users_seq;

drop sequence if exists users_seq;

