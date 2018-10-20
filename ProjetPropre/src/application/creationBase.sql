drop table if exists AMIS;

drop table if exists CLASSEMENT;

drop table if exists INVITATION;

drop table if exists JEU;

drop table if exists ENVOYER;

drop table if exists MESSAGE;

drop table if exists PARTICIPER;

drop table if exists PARTIE;

drop table if exists USER;

/*==============================================================*/
/* Table: AMIS                                                  */
/*==============================================================*/
create table AMIS
(
   PSEUDOUSER           varchar(20) not null,
   USE_PSEUDOUSER       varchar(20) not null,
   ACCEPTATIONAMIS      tinyint(1),
   DATEDEMANDEAMIS      datetime,
   primary key (PSEUDOUSER, USE_PSEUDOUSER)
);

/*==============================================================*/
/* Table: CLASSEMENT                                            */
/*==============================================================*/
create table CLASSEMENT
(
   IDPARTIE             Integer(8) not null,
   PSEUDOUSER           varchar(20) not null,
   NBPOINTCLASSEMENT    Integer(255),
   primary key (IDPARTIE, PSEUDOUSER)
);

/*==============================================================*/
/* Table: INVITATION                                            */
/*==============================================================*/
create table INVITATION
(
   PSEUDOUSER           varchar(20) not null,
   USE_PSEUDOUSER       varchar(20) not null,
   IDJEU                Integer(8) not null,
   DATEINVITATION       datetime,
   primary key (PSEUDOUSER, USE_PSEUDOUSER, IDJEU)
);

/*==============================================================*/
/* Table: JEU                                                   */
/*==============================================================*/
create table JEU
(
   IDJEU                Integer(8) not null,
   NOMJEU               varchar(20),
   DESCRIPTIONJEU       varchar(300),
   TYPEJEU              varchar(10),
   FICHIERJEU           LONGBLOB,
   ETATJEU              tinyint(1),
   primary key (IDJEU)
);

/*==============================================================*/
/* Table: MESSAGE                                               */
/*==============================================================*/
create table MESSAGE
(
   IDMSG				Integer(8) not null,
   CONTENUMESSAGE       varchar(150),
   DATEMESSAGE          datetime,
   primary key (IDMSG)
);

/*==============================================================*/
/* Table: ENVOYER                                               */
/*==============================================================*/
create table ENVOYER
(
   PSEUDOUSER           varchar(20) not null,
   USE_PSEUDOUSER       varchar(20) not null,
   IDMSG				Integer(8) not null,
   LECTUREMESSAGE       tinyint(1),
   primary key (PSEUDOUSER, USE_PSEUDOUSER, IDMSG)
);

/*==============================================================*/
/* Table: PARTICIPER                                            */
/*==============================================================*/
create table PARTICIPER
(
   PSEUDOUSER           varchar(20) not null,
   USE_PSEUDOUSER       varchar(20) not null,
   NUMPARTIE            Integer(8) not null,
   DATEPARTICIPATION    datetime,
   primary key (PSEUDOUSER, USE_PSEUDOUSER, DATEPARTICIPATION)
);

/*==============================================================*/
/* Table: PARTIE                                                */
/*==============================================================*/
create table PARTIE
(
   IDPARTIE             Integer(8) not null,
   IDJEU                Integer(8) not null,
   NUMPARTIE 			Integer not null,
   DATEPARTIE           date,
   NUMETAPEPARTIE       Integer(100),
   ACTIONJOUEURPARTIE   varchar(100),
   JOUEURACTIONPARTIE   varchar(20),
   ETATPARTIE           Integer(8),
   VAINQUEURPARTIE      varchar(20),
   primary key (IDPARTIE)
);

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table USER
(
   PSEUDOUSER           varchar(20) not null,
   MDPUSER              varchar(32),
   EMAILUSER            varchar(50),
   ROLEUSER             char(1),
   ETATUSER             tinyint(1),
   ICONE                LONGBLOB,
   primary key (PSEUDOUSER)
);

alter table AMIS add constraint FK_AMIS foreign key (USE_PSEUDOUSER)
      references USER (PSEUDOUSER) on delete restrict on update restrict;

alter table AMIS add constraint FK_AMIS2 foreign key (PSEUDOUSER)
      references USER (PSEUDOUSER) on delete restrict on update restrict;

alter table CLASSEMENT add constraint FK_CLASSEMENT foreign key (PSEUDOUSER)
      references USER (PSEUDOUSER) on delete restrict on update restrict;

alter table CLASSEMENT add constraint FK_CLASSEMENT2 foreign key (IDPARTIE)
      references PARTIE (IDPARTIE) on delete restrict on update restrict;

alter table INVITATION add constraint FK_INVITATION foreign key (IDJEU)
      references JEU (IDJEU) on delete restrict on update restrict;

alter table INVITATION add constraint FK_INVITATION2 foreign key (PSEUDOUSER)
      references USER (PSEUDOUSER) on delete restrict on update restrict;

alter table INVITATION add constraint FK_INVITATION3 foreign key (USE_PSEUDOUSER)
      references USER (PSEUDOUSER) on delete restrict on update restrict;

alter table ENVOYER add constraint FK_MESSAGE2 foreign key (PSEUDOUSER)
      references USER (PSEUDOUSER) on delete restrict on update restrict;

alter table ENVOYER add constraint FK_MESSAGE3 foreign key (IDMSG)
      references MESSAGE (IDMSG) on delete restrict on update restrict;

alter table PARTICIPER add constraint FK_PARTICIPER foreign key (USE_PSEUDOUSER)
      references USER (PSEUDOUSER) on delete restrict on update restrict;

alter table PARTICIPER add constraint FK_PARTICIPER4 foreign key (PSEUDOUSER)
      references USER (PSEUDOUSER) on delete restrict on update restrict;

alter table PARTIE add constraint FK_APPARTIENT foreign key (IDJEU)
      references JEU (IDJEU) on delete restrict on update restrict;

/*alter table PARTIE add constraint FK_PARTICIPER3 foreign key (PSEUDOUSER, USE_PSEUDOUSER)
      references PARTICIPER (PSEUDOUSER, USE_PSEUDOUSER) on delete restrict on update restrict;
*/
INSERT INTO `USER`(`PSEUDOUSER`, `MDPUSER`, `EMAILUSER`, `ROLEUSER`, `ETATUSER`) VALUES ('iuto','mdp1','123@gmail.com','A',0);
INSERT INTO `USER`(`PSEUDOUSER`, `MDPUSER`, `EMAILUSER`, `ROLEUSER`, `ETATUSER`) VALUES ('iute','mdp2','456@gmail.com','J',0);
INSERT INTO `USER`(`PSEUDOUSER`, `MDPUSER`, `EMAILUSER`, `ROLEUSER`, `ETATUSER`) VALUES ('Donald','mdp3','789@gmail.com','J',0);
INSERT INTO `USER`(`PSEUDOUSER`, `MDPUSER`, `EMAILUSER`, `ROLEUSER`, `ETATUSER`) VALUES ('Picsou','mdp4','666@gmail.com','J',0);

INSERT INTO `PARTICIPER`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `NUMPARTIE` ,`DATEPARTICIPATION`) VALUES ('iuto','iute',1,'2018-01-02 11:30:00');
INSERT INTO `PARTICIPER`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `NUMPARTIE` ,`DATEPARTICIPATION`) VALUES ('iute','Donald',2,'2018-01-02 11:31:00');
INSERT INTO `PARTICIPER`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `NUMPARTIE` ,`DATEPARTICIPATION`) VALUES ('iuto','Picsou',3,'2018-01-02 11:32:00');
INSERT INTO `PARTICIPER`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `NUMPARTIE` ,`DATEPARTICIPATION`) VALUES ('iute','iuto',4,'2018-01-02 11:32:30');
INSERT INTO `PARTICIPER`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `NUMPARTIE` ,`DATEPARTICIPATION`) VALUES ('iuto','iute',5,'2018-01-02 11:30:20');

INSERT INTO `JEU`(`IDJEU`, `NOMJEU`, `DESCRIPTIONJEU`, `TYPEJEU`, `FICHIERJEU`, `ETATJEU`) VALUES (1,'Puissance 4','Faire des ligne de 4','point',NULL,1);
INSERT INTO `JEU`(`IDJEU`, `NOMJEU`, `DESCRIPTIONJEU`, `TYPEJEU`, `FICHIERJEU`, `ETATJEU`) VALUES (2,'Dame','Un JEU de dame','touratour',NULL,1);
INSERT INTO `JEU`(`IDJEU`, `NOMJEU`, `DESCRIPTIONJEU`, `TYPEJEU`, `FICHIERJEU`, `ETATJEU`) VALUES (3,'Scrabble','Faite ressortir votre vocabulaire','touratour',NULL,0);


INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (1,2,1,'2018-03-28',1,'A1B6','iuto',0,NULL);    -- le pion en A1 de iuto se déplace en B6
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (2,2,1,'2018-03-28',2,'E8C7','iute',0,NULL);
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (3,2,1,'2018-03-28',3,'I3E4','iuto',0,NULL);
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (4,2,1,'2018-03-28',4,'C2I3','iute',-1,'iuto'); -- iuto a gagner a la fin du tour de iute

INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (5,1,2,'2018-03-28',1,'1','iute',0,NULL);    -- Le joueur iute vien de metre un pion sur la colone 1
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (6,1,2,'2018-03-28',2,'2','Donald',0,NULL);
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (7,1,2,'2018-03-28',3,'3','iute',0,NULL);
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (8,1,2,'2018-03-28',4,'4','Donald',-1,'Donald'); -- Donald a gagner pendant son tour

INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (9,3,3,'2018-03-28',1,'HC08Bonjour','iuto',0,NULL); -- iuto vient de placer le mot Bonjour Horizontalement a partir de C 8
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (10,3,3,'2018-03-28',2,'VE12Voiture','Picsou',0,NULL);
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (11,3,3,'2018-03-28',3,'VB01Dauphin','iuto',0,NULL);
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (12,3,3,'2018-03-28',4,'HC07Scrable','Picsou',-2,'Picsou'); -- iuto a abandonner picsou gagne

INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (13,1,4,'2018-03-28',1,'A1B6','iute',0,NULL);
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (14,1,4,'2018-03-28',2,'E8C7','iuto',0,NULL);
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (15,1,4,'2018-03-28',3,'I3E4','iute',0,NULL);
INSERT INTO `PARTIE`(`IDPARTIE`, `IDJEU`, `NUMPARTIE`, `DATEPARTIE`, `NUMETAPEPARTIE`, `ACTIONJOUEURPARTIE`, `JOUEURACTIONPARTIE`, `ETATPARTIE`, `VAINQUEURPARTIE`) VALUES (16,1,4,'2018-03-28',4,'C2I3','iuto',-1,'iuto');-- iuto a gagner a la fin de son tour contre iute au puissance 4


INSERT INTO `AMIS`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `ACCEPTATIONAMIS`, `DATEDEMANDEAMIS`) VALUES ('iuto','iute',1,'2018-01-02 11:30:00'); -- iuto est AMIS avec iute depuis le 2 janvier 2018 a 11H 30
INSERT INTO `AMIS`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `ACCEPTATIONAMIS`, `DATEDEMANDEAMIS`) VALUES ('iuto','Donald',1,'2017-01-02 11:30:00');
INSERT INTO `AMIS`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `ACCEPTATIONAMIS`, `DATEDEMANDEAMIS`) VALUES ('iuto','Picsou',0,NULL); -- iuto a demander Picsou en AMIS mais ce dernier pas encore accepter la demande
INSERT INTO `AMIS`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `ACCEPTATIONAMIS`, `DATEDEMANDEAMIS`) VALUES ('Donald','Picsou',1,'1947-12-01 11:30:00');

INSERT INTO `MESSAGE`(`IDMSG`, `CONTENUMESSAGE`, `DATEMESSAGE`) VALUES (1,'Je vous invite a faire une partie de dame','2018-03-28 11:30:00'); -- Un message écrit a 11:30 le 28 mars 2018
INSERT INTO `MESSAGE`(`IDMSG`, `CONTENUMESSAGE`, `DATEMESSAGE`) VALUES (2,'Super invite moi sur une partie !','2018-03-28 11:45:00');
INSERT INTO `MESSAGE`(`IDMSG`, `CONTENUMESSAGE`, `DATEMESSAGE`) VALUES (3,'Message non lu','2018-03-28 11:30:00');

INSERT INTO `ENVOYER`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `IDMSG`, `LECTUREMESSAGE`) VALUES ('iuto','iute',1,1); -- Le joueur iuto a envoyer le message 1 a iute. iute a lu le message
INSERT INTO `ENVOYER`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `IDMSG`, `LECTUREMESSAGE`) VALUES ('iuto','Donald',1,0); -- Le joueur iuto a envoyer le message 1 a Donald. Donald n'a pas lu le message
INSERT INTO `ENVOYER`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `IDMSG`, `LECTUREMESSAGE`) VALUES ('iute','iuto',2,1);
INSERT INTO `ENVOYER`(`PSEUDOUSER`, `USE_PSEUDOUSER`, `IDMSG`, `LECTUREMESSAGE`) VALUES ('Picsou','iuto',3,0);

-- La liste des partie en cour de l’utilisateur iuto :
SELECT DISTINCT numPartie
FROM PARTICIPER NATURAL JOIN PARTIE NATURAL JOIN USER
WHERE PSEUDOUSER='iuto' OR USE_PSEUDOUSER='iuto' AND etatUser=1;

-- La liste des message non lus de l’utilisateur iuto:
SELECT contenuMessage,dateMessage
FROM MESSAGE NATURAL JOIN ENVOYER
WHERE USE_PSEUDOUSER='iuto' AND lectureMessage=0;

-- Le nombre de partie gagnée par le joueur iuto contre le joueur iute au jeu puissance 4:
SELECT COUNT(vainqueurPartie) nbVictoireIuto
FROM PARTIE NATURAL JOIN PARTICIPER NATURAL JOIN JEU
WHERE vainqueurPartie='iuto' AND (PSEUDOUSER='iute' OR USE_PSEUDOUSER='iute') AND NOMJEU='Puissance 4';