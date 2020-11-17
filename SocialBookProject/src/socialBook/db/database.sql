CREATE DATABASE IF NOT EXISTS 'socialBook';
USE 'socialBook';

DROP TABLE IF EXISTS
    booklist;
DROP TABLE IF EXISTS
    carrello;
DROP TABLE IF EXISTS
    libro_categoria;
DROP TABLE IF EXISTS
    categoria;
DROP TABLE IF EXISTS
    libro;
DROP TABLE IF EXISTS
    login;
DROP TABLE IF EXISTS
    utente;



CREATE TABLE utente (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  passwordhash char(40) NOT NULL,
  nome varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  admin tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (username),
  UNIQUE KEY (email)
);

CREATE TABLE categoria (
  id int(11) NOT NULL AUTO_INCREMENT,
  nome varchar(45) NOT NULL,
  descrizione mediumtext NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE libro (
  id int(11) NOT NULL AUTO_INCREMENT,
  nome varchar(45) NOT NULL,
  descrizione longtext NOT NULL,
  prezzo decimal(5,2) NOT NULL,
  PRIMARY KEY (id),
  KEY (prezzo),
  FULLTEXT KEY (nome),
  FULLTEXT KEY (nome,descrizione)
);

CREATE TABLE libro_categoria (
  idlibro int(11) NOT NULL,
  idcategoria int(11) NOT NULL,
  PRIMARY KEY (idlibro, idcategoria),
  CONSTRAINT FOREIGN KEY (idlibro) REFERENCES libro (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FOREIGN KEY (idcategoria) REFERENCES categoria (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE login (
  id char(36) NOT NULL,
  idutente int(11) NOT NULL,
  token char(36) NOT NULL,
  time timestamp NOT NULL,
  PRIMARY KEY (id),
  KEY (idutente),
  CONSTRAINT FOREIGN KEY (idutente) REFERENCES utente (id)
);

CREATE TABLE booklist (

    id int AUTO_INCREMENT primary key ,
    nome varchar(20) not null ,
    id_libro int NOT NULL,
    id_utente int NOT NULL,
    creatore bool,
    constraint foreign key(id_utente) references utente(id),
    constraint foreign key(id_libro) references libro(id)
);

