DROP DATABASE IF EXISTS SocialBook;
CREATE DATABASE SocialBook;
USE SocialBook;

CREATE TABLE customer(
    id_customer      int(11) NOT NULL AUTO_INCREMENT,
    customer_name    varchar(15),
    customer_surname varchar(15),
    email            varchar(100) NOT NULL,
    customer_pwd     varchar(160)  NOT NULL,
    customer_usr     varchar(16)  NOT NULL,
    c_description    varchar(100) NOT NULL,
    PRIMARY KEY (id_customer),
    UNIQUE KEY (customer_usr),
    UNIQUE KEY (email)
);

CREATE TABLE book(
    ISBN             varchar(13) PRIMARY KEY,
    title            varchar(50)   NOT NULL,
    genre            varchar(16)   NOT NULL,
    price_cent       int(5)        NOT NULL,
    publication_year year          NOT NULL,
    publishing_house varchar(30)   NOT NULL,
    plot             varchar(500)  NOT NULL,
    catalogue        bool          NOT NULL
);

CREATE TABLE author(
    id_author      int(11) AUTO_INCREMENT PRIMARY KEY,
    author_name    varchar(16) NOT NULL,
    author_surname varchar(16) NOT NULL
);

CREATE TABLE authorAssociation(
    id_author int(11),
    ISBN      varchar(13),
    CONSTRAINT fk_aa_a FOREIGN KEY (id_author) REFERENCES author (id_author),
    CONSTRAINT fk_aa_b FOREIGN KEY (ISBN) REFERENCES book (ISBN),
    PRIMARY KEY (id_author, ISBN)
);

CREATE TABLE customerOrder(
    id_order     int(16) AUTO_INCREMENT PRIMARY KEY,
    order_price  decimal(6, 2) NOT NULL,
    invoice_addr varchar(16)   NOT NULL,
    cart         bool          NOT NULL,
    date         date,
    id_customer  int(11)       NOT NULL,
    CONSTRAINT fk_co_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer)
);

CREATE TABLE orderDetail(
    id_order int(16) AUTO_INCREMENT,
    ISBN     varchar(13),
    price    decimal(4, 2) NOT NULL,
    PRIMARY KEY (id_order, ISBN),
    CONSTRAINT fk_od_o FOREIGN KEY (id_order) REFERENCES customerOrder (id_order),
    CONSTRAINT fk_od_b FOREIGN KEY (ISBN) REFERENCES book (ISBN)
);

CREATE TABLE infoPayment(
    card_number char(16) PRIMARY KEY,
    name        varchar(12) NOT NULL,
    surname     varchar(12) NOT NULL,
    exp_date_mm varchar(2)  NOT NULL,
    exp_date_yy varchar(4)  NOT NULL,
    cvv         varchar(3)  NOT NULL,
    id_customer int(11)     NOT NULL,
    CONSTRAINT fk_i_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer)
);

CREATE TABLE bookList(
    id_booklist   int(11) AUTO_INCREMENT PRIMARY KEY,
    booklist_name varchar(30) NOT NULL,
    favorite      bool        NOT NULL
);

CREATE TABLE booklistDetail(
    id_customer int(11) NOT NULL,
    id_booklist int(11) NOT NULL,
    property    bool    NOT NULL,
    PRIMARY KEY (id_customer, id_booklist),
    CONSTRAINT fk_bd_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer),
    CONSTRAINT fk_bd_bl FOREIGN KEY (id_booklist) REFERENCES bookList (id_booklist)
);

CREATE TABLE admin(
    admn_usr varchar(16) PRIMARY KEY,
    admn_pwd varchar(16)    NOT NULL,
    admn_role varchar(16)   NOT NULL
);

CREATE TABLE review(
    id_review   int(11) AUTO_INCREMENT PRIMARY KEY,
    id_customer int(11)     NOT NULL,
    ISBN        varchar(13) NOT NULL,
    review_date date        NOT NULL,
    body        varchar(100),
    vote        int,
    CONSTRAINT fk_r_c FOREIGN KEY (id_customer) REFERENCES customer(id_customer),
    CONSTRAINT fk_r_b FOREIGN KEY (ISBN) REFERENCES book(ISBN)
);

CREATE TABLE customerPhoto(
    id_customer    int(11) PRIMARY KEY,
    customer_photo MEDIUMBLOB,
    CONSTRAINT fk_c_p FOREIGN KEY (id_customer) REFERENCES customer (id_customer)
);


CREATE TABLE bookPhoto(
    ISBN       varchar(13) PRIMARY KEY,
    book_photo MEDIUMBLOB,
    CONSTRAINT fk_b_p FOREIGN KEY (ISBN) REFERENCES book (ISBN)
);

CREATE TABLE ticket(
    id_ticket   int(11) AUTO_INCREMENT PRIMARY KEY,
    id_customer int(11)      NOT NULL,
    admn_usr    varchar(16)  NOT NULL,
    open_date   date         NOT NULL,
    issue       varchar(100) NOT NULL,
    close_date  date,
    status      varchar(20),
    CONSTRAINT fk_t_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer),
    CONSTRAINT fk_t_am FOREIGN KEY (admn_usr) REFERENCES admin (admn_usr)
);

CREATE TABLE message(
    id_message   int(11) AUTO_INCREMENT PRIMARY KEY,
    sender       bool         NOT NULL,
    id_ticket    int(11)      NOT NULL,
    time_stamp   timestamp    NOT NULL,
    message_body varchar(100) NOT NULL,
    CONSTRAINT fk_m_t FOREIGN KEY (id_ticket) REFERENCES ticket (id_ticket)
);

CREATE TABLE follow(
    id_customer int(11) AUTO_INCREMENT PRIMARY KEY,
    id_follower int(11) NOT NULL,
    CONSTRAINT fk_f_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer),
    CONSTRAINT fk_f_f FOREIGN KEY (id_follower) REFERENCES customer (id_customer)
);

CREATE TABLE profiling(
    id_customer     int(11) PRIMARY KEY,
    age_14_18       bool NOT NULL,
    age_19_25       bool NOT NULL,
    age_26_30       bool NOT NULL,
    age_31_40       bool NOT NULL,
    age_41_50       bool NOT NULL,
    age_50_plus     bool NOT NULL,
    underage_sons   bool NOT NULL,
    student         bool NOT NULL,
    unemployed      bool NOT NULL,
    employee        bool NOT NULL,
    worker          bool NOT NULL,
    freelance       bool NOT NULL,
    p_read          bool NOT NULL,
    play_instrument bool NOT NULL,
    sing            bool NOT NULL,
    dance           bool NOT NULL,
    videogame       bool NOT NULL,
    tv_series       bool NOT NULL,
    travels         bool NOT NULL,
    cooking         bool NOT NULL,
    sport           bool NOT NULL,
    volunteering    bool NOT NULL,
    art             bool NOT NULL,
    beauty          bool NOT NULL,
    cartoon         bool NOT NULL,
    drink           bool NOT NULL,
    walk            bool NOT NULL,

    CONSTRAINT fk_p_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer)
);

INSERT INTO customer(customer_name, customer_surname, email, customer_pwd, customer_usr, c_description) VALUES
('Ale', 'Bar', 'ale.bar@gmail.com', SHA1('barbados99'), 'AleBarbados', 'studentessa disperata'),
('Luca', 'Russo', 'luketto.222000@gmail.com', SHA1('pizzamandolino'), 'LukettoFurbetto', 'Voglio disperatamente porre fine alla mia vita alle volte');

INSERT INTO book(ISBN, title, genre, price_cent, publication_year, publishing_house, plot, catalogue) VALUES
('9788869183157', 'Harry Potter e la pietra filosofale', 'Fantasy', 1800, 2018, 'Salani', "Nel giorno del suo undicesimo compleanno, la vita di Harry Potter cambia per sempre. Una lettera, consegnata dal gigantesco e arruffato Rubeus Hagrid, contiene infatti delle notizie sconvolgenti. Harry scopre di non essere un ragazzo come gli altri: è un mago e una straordinaria avventura lo aspetta..", true),
('9788893817035', 'Harry Potter e la camera dei segreti', 'Fantasy', 1699, 2018, 'Salani', "Harry Potter è ormai celebre: durante il primo anno alla Scuola di Magia e Stregoneria di Hogwarts ha sconfitto il terribile Voldemort, vendicando la morte dei suoi genitori e coprendosi di gloria. Ma una spaventosa minaccia incombe sulla scuola: un incantesimo che colpisce i compagni di Harry, uno dopo l'altro, e che sembra legato a un antico mistero racchiuso nella tenebrosa Camera dei Segreti.", true),
('9788869186127', 'Harry Potter e il prigioniero di Azkaban', 'Fantasy', 1850, 2018, 'Salani', "Una terribile minaccia incombe sulla Scuola di Magia e Stregoneria di Hogwarts. Sirius Black, il famigerato assassino, è evaso dalla prigione di Azkaban. È in caccia e la sua preda è proprio a Hogwarts, dove Harry e i suoi amici stanno per cominciare il loro terzo anno. Nonostante la sorveglianza dei Dissennatori la scuola non è più un luogo sicuro, perché al suo interno si nasconde un traditore...", false);
