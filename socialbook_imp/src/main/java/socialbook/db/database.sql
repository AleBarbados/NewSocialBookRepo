DROP DATABASE IF EXISTS SocialBook;
CREATE DATABASE SocialBook;
USE SocialBook;

CREATE TABLE customer(
    id_customer      int AUTO_INCREMENT,
    customer_name    varchar(15) NOT NULL,
    customer_surname varchar(15) NOT NULL,
    email            varchar(50) NOT NULL,
    customer_pwd     varchar(100) NOT NULL,
    customer_usr     varchar(16) NOT NULL,
    c_description    varchar(150) NOT NULL,
    image            varchar(80),
    UNIQUE KEY (customer_usr),
    UNIQUE KEY (email),
    PRIMARY KEY (id_customer)
);

CREATE TABLE book(
    ISBN             varchar(13),
    title            varchar(50) NOT NULL,
    genre            varchar(30) NOT NULL,
    price_cent       int NOT NULL,
    publication_year year NOT NULL,
    publishing_house varchar(30) NOT NULL,
    plot             varchar(500) NOT NULL,
    catalogue        bool NOT NULL,
    image            varchar(20) NOT NULL,
    FULLTEXT KEY(title),
    FULLTEXT KEY(title, genre),
    PRIMARY KEY(ISBN)
);

CREATE TABLE author(
    id_author      int AUTO_INCREMENT,
    author_name    varchar(16) NOT NULL,
    author_surname varchar(16) NOT NULL,
    PRIMARY KEY(id_author)
);

CREATE TABLE authorAssociation(
    id_author int,
    ISBN      varchar(13),
    CONSTRAINT fk_aa_a FOREIGN KEY (id_author) REFERENCES author (id_author)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    CONSTRAINT fk_aa_b FOREIGN KEY (ISBN) REFERENCES book (ISBN)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    PRIMARY KEY (id_author, ISBN)
);

CREATE TABLE customerOrder(
    id_order     int AUTO_INCREMENT,
    order_price  decimal(6,2) NOT NULL,
    invoice_addr varchar(16) ,
    cart         bool NOT NULL,
    order_date   date,
    id_customer  int NOT NULL,
    CONSTRAINT fk_co_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    PRIMARY KEY(id_order)
);

CREATE TABLE orderDetail(
    id_order int AUTO_INCREMENT,
    ISBN     varchar(13),
    CONSTRAINT fk_od_o FOREIGN KEY (id_order) REFERENCES customerOrder (id_order)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    CONSTRAINT fk_od_b FOREIGN KEY (ISBN) REFERENCES book (ISBN)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    PRIMARY KEY (id_order, ISBN)
);

CREATE TABLE infoPayment(
    card_number 	char(16),
    payment_name    varchar(12) NOT NULL,
    payment_surname varchar(12) NOT NULL,
    exp_date_mm 	varchar(2) NOT NULL,
    exp_date_yy 	varchar(4) NOT NULL,
    cvv         	varchar(3) NOT NULL,
    id_customer 	int NOT NULL,
    CONSTRAINT fk_i_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    PRIMARY KEY(card_number)
);

CREATE TABLE booklist(
    id_booklist   int AUTO_INCREMENT,
    booklist_name varchar(30) NOT NULL,
    favorite      bool NOT NULL,
    image         varchar(80),
	PRIMARY KEY(id_booklist)
);

CREATE TABLE booklistdetail(
    id_customer int NOT NULL,
    id_booklist int NOT NULL,
    property    bool NOT NULL,
    CONSTRAINT fk_bd_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    CONSTRAINT fk_bd_bl FOREIGN KEY (id_booklist) REFERENCES booklist (id_booklist)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    PRIMARY KEY (id_customer, id_booklist)
);

CREATE TABLE booklistassociation(
    id_booklist  int NOT NULL,
    id_book varchar(13) NOT NULL,
    CONSTRAINT fk_ba_bl FOREIGN KEY (id_booklist) REFERENCES booklist (id_booklist)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    CONSTRAINT fk_ba_b FOREIGN KEY (id_book) REFERENCES book (ISBN)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    PRIMARY KEY (id_booklist, id_book)
);

CREATE TABLE admin(
    admn_usr 	varchar(16),
    admn_pwd 	varchar(100) NOT NULL,
    admn_role 	varchar(16) NOT NULL,
    PRIMARY KEY(admn_usr)
);

CREATE TABLE review(
    id_review   int AUTO_INCREMENT,
    id_customer int NOT NULL,
    ISBN        varchar(13) NOT NULL,
    review_date date NOT NULL,
    body        varchar(300),
    vote        varchar(4),
    CONSTRAINT fk_r_c FOREIGN KEY (id_customer) REFERENCES customer(id_customer)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    CONSTRAINT fk_r_b FOREIGN KEY (ISBN) REFERENCES book(ISBN)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    PRIMARY KEY(id_review)
);

CREATE TABLE ticket(
    id_ticket   	int AUTO_INCREMENT,
    id_customer 	int ,
    admn_usr    	varchar(16),
    open_date   	date NOT NULL,
    issue       	varchar(100) NOT NULL,
    close_date  	date,
    t_status   varchar(20) NOT NULL,
    destination varchar(20),
    CONSTRAINT fk_t_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    CONSTRAINT fk_t_am FOREIGN KEY (admn_usr) REFERENCES admin (admn_usr)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    PRIMARY KEY(id_ticket)
);

CREATE TABLE message(
    id_message   int AUTO_INCREMENT,
    sender       bool NOT NULL,
    id_ticket    int NOT NULL,
    time_stamp   timestamp NOT NULL,
    message_body varchar(100) NOT NULL,
    CONSTRAINT fk_m_t FOREIGN KEY (id_ticket) REFERENCES ticket (id_ticket)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    PRIMARY KEY(id_message)
);

CREATE TABLE follow(
    id_customer int AUTO_INCREMENT,
    id_follower int NOT NULL,
    CONSTRAINT fk_f_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    CONSTRAINT fk_f_f FOREIGN KEY (id_follower) REFERENCES customer (id_customer)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
	PRIMARY KEY(id_customer)
);

CREATE TABLE profiling(
    id_customer             int,
    age_14_18               bool NOT NULL,
    age_19_25               bool NOT NULL,
    age_26_30               bool NOT NULL,
    age_31_40               bool NOT NULL,
    age_41_50               bool NOT NULL,
    age_50_plus             bool NOT NULL,
    underage_sons           bool NOT NULL,
    student                 bool NOT NULL,
    unemployed              bool NOT NULL,
    employee                bool NOT NULL,
    worker                  bool NOT NULL,
    freelance               bool NOT NULL,
    p_read                  bool NOT NULL,
    play_instrument         bool NOT NULL,
    sing                    bool NOT NULL,
    dance                   bool NOT NULL,
    videogame               bool NOT NULL,
    tv_series               bool NOT NULL,
    travels                 bool NOT NULL,
    cooking                 bool NOT NULL,
    sport                   bool NOT NULL,
    volunteering            bool NOT NULL,
    art                     bool NOT NULL,
    beauty                  bool NOT NULL,
    cartoon                 bool NOT NULL,
    walk                    bool NOT NULL,
    no_books                bool NOT NULL,
    books_1_3               bool NOT NULL,
    books_3_5               bool NOT NULL,
    books_5_plus            bool NOT NULL,
    crime_novel             bool NOT NULL,
    soap_drama              bool NOT NULL,
    romance_novel           bool NOT NULL,
    historical_novel        bool NOT NULL,
    comics                  bool NOT NULL,
    sci_fi                  bool NOT NULL,
    fantasy                 bool NOT NULL,
    adventure               bool NOT NULL,
    buildunsgroman          bool NOT NULL,
    horror                  bool NOT NULL,
    poems                   bool NOT NULL,
    nonfiction              bool NOT NULL,
    psychological           bool NOT NULL,
    motivational            bool NOT NULL,
    timely_novel            bool NOT NULL,
    satirist                bool NOT NULL,
    science                 bool NOT NULL,
    classic                 bool NOT NULL,
    page_plus_400           bool NOT NULL,
    page_less_400           bool NOT NULL,
    choice_author           bool NOT NULL,
    choice_publishing_house bool NOT NULL,
    choice_prize            bool NOT NULL,
    choice_genre            bool NOT NULL,
    choice_review           bool NOT NULL,
    choice_school           bool NOT NULL,
    choice_plot             bool NOT NULL,
    choice_cover            bool NOT NULL,
    choice_first_impact     bool NOT NULL,
    choice_advice           bool NOT NULL,
    CONSTRAINT fk_p_c FOREIGN KEY (id_customer) REFERENCES customer (id_customer)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
	PRIMARY KEY(id_customer)
);

INSERT INTO customer(customer_name, customer_surname, email, customer_pwd, customer_usr, c_description, image) VALUES
('Ale', 'Bar', 'ale.bar@gmail.com', SHA1('barbados99'), 'AleBarbados', 'studentessa disperata', 'c1.jpg'),
('Luca', 'Russo', 'luketto.222000@gmail.com', SHA1('pizzamandolino'), 'LukettoFurbetto', 'Voglio disperatamente porre fine alla mia vita alle volte', 'c2.jpg'),
('Jeka', 'Proietto', 'angpro99@gmail.com', SHA1('vivaicarlini'), 'Jeka', 'beh che dire, follettini e follettine', 'c3.jpg');


INSERT INTO book(ISBN, title, genre, price_cent, publication_year, publishing_house, plot, catalogue, image) VALUES
('9788869183157', 'Harry Potter e la pietra filosofale', 'Fantasy', 1800, 2018, 'Salani', 'Nel giorno del suo undicesimo compleanno, la vita di Harry Potter cambia per sempre. Una lettera, consegnata dal gigantesco e arruffato Rubeus Hagrid, contiene infatti delle notizie sconvolgenti. Harry scopre di non essere un ragazzo come gli altri: è un mago e una straordinaria avventura lo aspetta..', true, 'b1.jpg'),
('9788893817035', 'Harry Potter e la camera dei segreti', 'Fantasy', 1699, 2018, 'Salani', 'Harry Potter è ormai celebre: durante il primo anno alla Scuola di Magia e Stregoneria di Hogwarts ha sconfitto il terribile Voldemort, vendicando la morte dei suoi genitori e coprendosi di gloria. Ma una spaventosa minaccia incombe sulla scuola: un incantesimo che colpisce i compagni di Harry, uno dopo l''altro, e che sembra legato a un antico mistero racchiuso nella tenebrosa Camera dei Segreti.', true, 'b2.jpg'),
('9788869186127', 'Harry Potter e il prigioniero di Azkaban', 'Fantasy', 1850, 2018, 'Salani', 'Una terribile minaccia incombe sulla Scuola di Magia e Stregoneria di Hogwarts. Sirius Black, il famigerato assassino, è evaso dalla prigione di Azkaban. È in caccia e la sua preda è proprio a Hogwarts, dove Harry e i suoi amici stanno per cominciare il loro terzo anno. Nonostante la sorveglianza dei Dissennatori la scuola non è più un luogo sicuro, perché al suo interno si nasconde un traditore...', false, 'b3.jpg'),
('9788807888960', 'Storie di ordinaria follia', 'Narrativa contemporanea', 1045, 2017, 'Feltrinelli', 'La biografia di Bukowski include due tentativi di lavorare come impiegato ', true, 'b8.jpg'),
('9788807892905', 'La casa degli spiriti', 'Narrativa contemporanea', 1140, 2019, 'Feltrinelli','Una saga familiare del nostro secolo in cui si rispecchiano la storia e il destino di tutto un popolo, quello cileno, nei racconti delle donne di una importante e stravagante famiglia. ', true, 'b9.jpg'),
('9788807880896', 'Seta', 'Narrativa contemporanea', 712, 2013,'Feltrinelli', 'La Francia, i viaggi per mare, il profumo dei gelsi a Lavilledieu, i treni a vapore, la voce di Hélène. Hervé Joncour continuò a raccontare la sua vita, come mai, nella sua vita, aveva fatto."', false, 'b10.jpg'),
('9788807900495', 'La coscienza di Zeno', 'Narrativa classica', 950, 2014, 'Feltrinelli', '"La coscienza di Zeno" (1923), uno dei capolavori della letteratura europea del Novecento, è la tragicomica vicenda di un "inetto a vivere ', true, 'b11.jpg'),
('9788807902505', 'Il canto di Natale', 'Narrativa classica', 712, 2016, 'Feltrinelli', 'Scrooge è talmente cattivo e avaro da rifiutare anche il calore del Natale, per lui solo una perdita di tempo e di soldi. ', false, 'b12.jpg'),
('9788807900150', 'Alice nel paese delle Meraviglie', 'Narrativa classica', 760, 2013, 'Feltrinelli', 'Un luogo unico al mondo. Una terra colma di meraviglia, mistero e pericolo. Si dice che per sopravvivere si debba essere matti come un cappellaio... Visitate il Paese delle Meraviglie, seguendo Alice e il Coniglio Bianco. ', false, 'b13.jpg'),
('9788807900303', 'The Dubliners', 'Narrativa classica', 855, 2013, 'Feltrinelli', 'Quindici racconti di vita quotidiana degli abitanti di Dublino durante i primi anni del ventesimo secolo', true, 'b14.jpg'),
('9788807901997', 'Il piacere', 'Narrativa classica', 3420, 2015, 'Feltrinelli', 'Andrea Sperelli, giovane aristocratico di origini abruzzesi, aspetta con ansia l''ex amante Elena Muti nella sua casa romana a Palazzo Zuccari. Durante l''attesa torna con la memoria alla scena del loro addio, avvenuto quasi due anni prima, nel marzo 1885, su una carrozza in via Nomentana.', false, 'b15.jpg'),
('9788804707035', 'Io, robot', 'Fantascienza classica', 1330, 2018, 'Feltrinelli', 'Gloria Weston ha otto anni e vuole un mondo di bene al suo robot Robbie. Ci gioca a nascondino, si fa portare a cavalcioni, lo costringe a far finta di essere un aereo, gli racconta le stesse favole milioni di volte e lui la ascolta con robotica pazienza.', true, 'b16.jpg'),
('9788842820185', 'Sogni di robot', 'Fantascienza classica', 799, 2014, 'Feltrinelli', 'Se l''uomo è fatto della stessa sostanza dei sogni, che cosa succede quando i robot cominciano a sognare? Succede che robot inquieti, troppo umani, lottano e fuggono, amano e si ribellano; e tentano di evadere dalla cattività meccanica imposta loro da padroni in carne e ossa e governata dalle Tre leggi della robotica.', true, 'b17.jpg');

INSERT INTO author(id_author, author_name, author_surname) VALUES
(1, 'Joanne Kathleen', 'Rowling'),
(2, 'Charles', 'Bukowski'),
(3, 'Isabel', 'Allende'),
(4, 'Alessandro', 'Baricco'),
(5, 'Italo', 'Svevo'),
(6, 'Charles', 'Dickens'),
(7, 'Lewis','Carrol'),
(8,'James', 'Joice'),
(9, 'Gabriele', 'D''Annunzio'),
(10, 'Isaac', 'Asimov');

INSERT INTO authorAssociation (id_author, ISBN) VALUES
(1, '9788869183157'),
(1, '9788893817035'),
(1, '9788869186127'),
(2, '9788807888960'),
(3, '9788807892905'),
(4, '9788807880896'),
(5, '9788807900495'),
(6,'9788807902505'),
(7, '9788807900150'),
(8, '9788807900303'),
(9, '9788807901997'),
(10, '9788804707035'),
(10, '9788842820185');

INSERT INTO booklist (booklist_name, favorite, image) VALUES
('Super Incredibile', 0, 'c1.jpg'),
('Esilarante Cavoletto', 0, 'c2.jpg'),
('WOW', 0, 'c1.jpg'),
('Preferiti', 1, ''),
('Preferiti', 1, ''),
('Preferiti', 1, '');

INSERT INTO booklistdetail (id_customer, id_booklist, property) VALUES
(1, 1, 0),
(1, 2, 0),
(2, 3, 0),
(1, 4, 0),
(2, 6, 0),
(2, 5, 0);

INSERT INTO booklistassociation (id_booklist, id_book) VALUES
(1, '9788869183157'),
(1, '9788842820185'),
(2, '9788869183157'), 
(2, '9788807901997'),
(3, '9788807902505');

INSERT INTO review(id_customer, ISBN, review_date, body, vote) VALUES
(1, '9788869183157', '2021-01-22', '-', '5'),
(2, '9788807902505', '2020-04-20', 'Questa storia è ok.', '3'),
(3, '9788869183157', '2018-01-14', 'Beh che dire', '1');

INSERT INTO admin(admn_usr, admn_pwd, admn_role) VALUES
('username', 'password', 'CUSTOMER_MANAGER'),
('usr', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'CUSTOMER_MANAGER');

INSERT INTO ticket(id_ticket, id_customer,  open_date, issue, t_status, destination) VALUES
(1, 1, '2018-01-14', 'aiuto, sito del cazzo', 'OPEN', 'CUSTOMER_MANAGER');

INSERT INTO customer(customer_name, customer_surname, email, customer_pwd, customer_usr, c_description) VALUES
('Utente', 'Fittizio', 'nonpuoesistere@gmail.hhi', SHA1('nonpuoiaccedere'), 'UtenteFittizio', 'utente fittizio');

INSERT INTO follow(id_customer, id_follower) VALUES
(2,1);