INSERT INTO author (name,email,phone, birthdate) VALUES ('Julio Ramon Riveiro', 'jriveiro@gmail.com', '994567123', '1929-01-30T11:00');

INSERT INTO blog (name, description, url, status, author_id) VALUES('Blog de JRR', 'Literatura peruana', 'https://blogprueba.com', 'inactivo', 1);

INSERT INTO post (title, postdate, status, content, blog_id) VALUES('Post de JRR', '1929-01-30T11:00', 'borrador', 'contenido de prueba', 1);

INSERT INTO comment (commentdate, name, estado, post_id) VALUES('1929-01-30T11:00', 'Comentario de prueba', 'estado de prueba', 1);