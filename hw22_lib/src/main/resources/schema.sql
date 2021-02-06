create table genre(
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table author(
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table book(
    id bigserial,
    name varchar(255),
    genre_id bigint references genre(id),
    primary key (id)
);

create table comment(
    id bigserial,
    text varchar(255),
    comment_author varchar(255),
    book_id bigint references book(id),
    primary key (id)
);

create table book_authors(
    book_id bigint references book(id) on delete cascade,
    author_id bigint references author(id) on delete cascade,
    primary key (book_id, author_id)
);