DROP DATABASE IF EXISTS SocialBook;
CREATE DATABASE IF NOT EXISTS SocialBook;
USE SocialBook;

CREATE TABLE customer(

    id_customer      int(11) NOT NULL AUTO_INCREMENT,
    customer_name    varchar(15),
    customer_surname varchar(15),
    email            varchar(100) NOT NULL,
    customer_pwd     varchar(16)  NOT NULL,
    customer_usr     varchar(16)  NOT NULL,
    PRIMARY KEY (id_customer),
    UNIQUE KEY (customer_usr),
    UNIQUE KEY (email)

);

CREATE TABLE book(

    ISBN             varchar(13) PRIMARY KEY,
    title            varchar(30)   NOT NULL,
    genre            varchar(16)   NOT NULL,
    price            decimal(4, 2) NOT NULL,
    publication_year date          NOT NULL,
    publishing_house varchar(30)   NOT NULL,
    plot             varchar(100)  NOT NULL
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
    id_customer  int(11) NOT NULL,
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
    id_customer int(11) NOT NULL,
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
    property    bool NOT NULL,
    PRIMARY KEY (id_customer, id_booklist),
    CONSTRAINT fk_bd_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer),
    CONSTRAINT fk_bd_bl FOREIGN KEY (id_booklist) REFERENCES bookList (id_booklist)
);

CREATE TABLE admin(
    admn_usr varchar(16) PRIMARY KEY,
    admn_pwd varchar(16) NOT NULL,
    admn_role varchar(16) NOT NULL
);

CREATE TABLE review(

    id_review   int(11) AUTO_INCREMENT PRIMARY KEY,
    id_customer int(11) NOT NULL,
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
    id_customer int(11) NOT NULL,
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
    id_ticket    int(11) NOT NULL,
    time_stamp   timestamp    NOT NULL,
    message_body varchar(100) NOT NULL,
    CONSTRAINT fk_m_t FOREIGN KEY (id_ticket) REFERENCES ticket (id_ticket)
);

CREATE TABLE profiling(

    id_customer int(11) PRIMARY KEY,
    age_14_18 bool NOT NULL,
    age_19_25 bool NOT NULL,
    age_26_30 bool NOT NULL,
    age_31_40 bool NOT NULL,
    age_41_50 bool NOT NULL,
    age_50_plus bool NOT NULL,
    underage_sons bool NOT NULL,
    student bool NOT NULL,
    unemployed bool NOT NULL,
    employee bool NOT NULL,
    worker bool NOT NULL,
    freelance bool NOT NULL,
);