# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table server (
  name                      varchar(255) not null,
  base_url                  varchar(255),
  path                      varchar(255),
  constraint pk_server primary key (name))
;

create sequence server_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists server;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists server_seq;

