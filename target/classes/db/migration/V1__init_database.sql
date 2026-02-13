create sequence if not exists  category_seq increment by 1 start with 1;
create sequence if not exists  product_seq increment by 1 start with 1;

create table if not exists category (
    id integer primary key default nextval('category_seq'),
    description varchar(255),
    name varchar(255)
);

create table if not exists product (
    id integer primary key default nextval('product_seq'),
    description varchar(255),
    name varchar(255),
    available_quantity integer not null,
    price numeric(38, 2),
    category_id integer
);