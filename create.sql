create sequence hibernate_sequence start with 1 increment by 1
create table tbl_office (id bigint not null, code varchar(255) check (code>=12), inactive boolean, name varchar(29), provider integer, primary key (id))
alter table tbl_office add constraint UK_6qn23urdnm21m82j5usi8s243 unique (code)
alter table tbl_office add constraint UK_7joqhqo58yjr95nfem7x3fix8 unique (name)
create sequence hibernate_sequence start with 1 increment by 1
create table tbl_office (id bigint not null, code varchar(255) check (code>=12), inactive boolean, name varchar(29), provider integer, primary key (id))
alter table tbl_office add constraint UK_6qn23urdnm21m82j5usi8s243 unique (code)
alter table tbl_office add constraint UK_7joqhqo58yjr95nfem7x3fix8 unique (name)
