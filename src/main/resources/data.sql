INSERT INTO author (name,email,phone, birthdate) VALUES ('Jaime Zegarra', 'jzegarra@gmail.com', '994567123', '1929-01-30T11:00');
INSERT INTO blog (name, description, url, status, author_id) VALUES('Blog de JZ', 'Temas varios', 'https://blogprueba.com', 'inactivo', 1);
INSERT INTO post (title, postdate, status, content, blog_id) VALUES('Post de JZ', '1980-01-20T09:00', 'borrador', 'contenido de prueba', 1);
INSERT INTO comment (commentdate, name, estado, post_id) VALUES('1990-06-10T13:00', 'Comentario de JZ', 'estado de prueba', 1);

INSERT INTO author (name,email,phone, birthdate) VALUES ('Ana Perez', 'aperez@gmail.com', '994567123', '2006-02-13T14:00');

INSERT INTO author (name,email,phone, birthdate) VALUES ('Juan Tovar', 'jtovar@gmail.com', '994567123', '1990-02-13T14:00');
INSERT INTO blog (name, description, url, status, author_id) VALUES('Blog de JT', 'Temas de politica', 'https://blogprueba.com', 'inactivo', 3);
INSERT INTO post (title, postdate, status, content, blog_id) VALUES('Post de JT', '1995-04-25T16:00', 'borrador', 'contenido de prueba', 2);
INSERT INTO comment (commentdate, name, estado, post_id) VALUES('2000-03-15T11:00', 'Comentario de JZ', 'estado de prueba', 2);
