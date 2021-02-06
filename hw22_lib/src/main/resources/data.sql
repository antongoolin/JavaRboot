insert into genre(name)
values('Видения'),('Ода'),('Скетч');

insert into author(name)
values ('Пушкин'),('Державин'), ('Грачевский');

insert into book(name, genre_id)
values ('Руслан и Людмила',1),('Бог',2),('Ералаш',3);

insert into comment(text,comment_author,book_id )
values('Неплохо','user',1),('Прекрасно','user',2),('Удивительно','user',3);

insert into book_authors(book_id, author_id)
values (1,1),(2,2),(3,3);