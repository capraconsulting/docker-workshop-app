CREATE TABLE COUNTER (
	id bigserial primary key,
	counter bigint not null
);


insert into COUNTER (counter) values (1);