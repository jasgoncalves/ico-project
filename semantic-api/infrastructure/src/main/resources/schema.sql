drop table IF EXISTS query;

create table query (
                       id uuid not null default random_uuid(),
                       name varchar(255),
                       query_parameters clob,
                       primary key (id));