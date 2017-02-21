--drop table animal if exists
drop table books;

create table books (
  isbn_13 varchar (13) primary key,
  title varchar (100),
  author varchar (80),
  publish_date date,
  price decimal (6,2),
  content blob
);

insert into books values (
  '1111111111111',          	--id
  'The Adventures of Steve',    --title
  'Russell Barron', 			--author
  SYSDATE,    				    --publishDate
  123.50,   					--price
  EMPTY_BLOB()					--blob
);