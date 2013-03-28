# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table Classes (
  id                        varchar(255) not null,
  name                      varchar(255),
  expdate                   timestamp,
  schoolid                  varchar(255),
  teacherid                 varchar(255),
  level                     varchar(255),
  constraint pk_Classes primary key (id))
;

create table ClassPupil (
  classid                   varchar(255) not null,
  indid                     varchar(255) not null,
  constraint uq_ClassPupil_1 unique (classid,indid))
;

create table questionsets (
  id                        varchar(255) not null,
  active                    boolean,
  constraint pk_questionsets primary key (id))
;

create table servers (
  id                        varchar(255) not null,
  location                  varchar(255),
  constraint pk_servers primary key (id))
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
  class                     varchar(255),
  constraint ck_users_gender check (gender in ('MALE','FEMALE','OTHER')),
  constraint ck_users_type check (type in ('ADMINISTRATOR','ORGANIZER','INDEPENDENT','PUPIL','TEACHER','AUTHOR','ANON')),
  constraint pk_users primary key (id))
;

create sequence Classes_seq;

create sequence ClassPupil_seq;

create sequence questionsets_seq;

create sequence servers_seq;

create sequence users_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists Classes;

drop table if exists ClassPupil;

drop table if exists questionsets;

drop table if exists servers;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists Classes_seq;

drop sequence if exists ClassPupil_seq;

drop sequence if exists questionsets_seq;

drop sequence if exists servers_seq;

drop sequence if exists users_seq;

