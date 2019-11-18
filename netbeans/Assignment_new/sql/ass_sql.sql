/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Administrator
 * Created: Mar 30, 2019
 */

create table "SQC".CONSUMPTION
(
        CONSUMPTION_ID INTEGER NOT NULL,
	USERID INTEGER not null,
	FOODID INTEGER not null,
	CON_DATE DATE not null,
	QUANTITY_FOOD DOUBLE,
	primary key (CONSUMPTION_ID)
);
create table "SQC".CREDENTIAL
(
        CREDENTIAL_ID INTEGER NOT NULL,
	USERID INTEGER not null,
	USERNAME VARCHAR(12) not null,
	PASSWORD VARCHAR(48) not null,
	SIGNDATE DATE not null,
        PRIMARY KEY (CREDENTIAL_ID)
);
create table "SQC".FOOD
(
	FOODID INTEGER not null primary key,
	FOODNAME VARCHAR(32) not null,
	FOOD_CATEGORY VARCHAR(32) not null,
	TOTAL_CALORIE DOUBLE not null,
	SERVINGUNIT VARCHAR(12) not null,
	TOTAL_SERVING DOUBLE not null,
	FAT DOUBLE not null
);
create table "SQC".REPORT
(
        REPORT_ID integer not null,
	USERID INTEGER not null,
	REPORT_DATE DATE not null,
	BURNED_CALORIES DOUBLE,
	STEPS_PER_MILE INTEGER,
	CALORIES_CONSUMED DOUBLE,
	CALORIES_GOAL DOUBLE,
	primary key (REPORT_ID)
);
create table "SQC".USERS
(
	USERID INTEGER not null primary key,
	FIRSTNAME VARCHAR(8) not null,
	SURNAME VARCHAR(8) not null,
	E_MAIL VARCHAR(8),
	DOB DATE not null,
	HEIGHT DOUBLE not null,
	WEIGHT DOUBLE not null,
	GENDER VARCHAR(2) not null,
	ADDRESS VARCHAR(24),
	POSTCODE VARCHAR(24),
	ACTLEVEL INTEGER not null,
	STEPS DOUBLE not null
);
ALTER TABLE CONSUMPTION ADD CONSTRAINT FK_userid foreign key (userid) references Users (userid) on delete cascade;
ALTER TABLE CONSUMPTION ADD CONSTRAINT FK_FOODid foreign key (foodid) references Food (foodid) on delete cascade;
ALTER TABLE CREDENTIAL ADD CONSTRAINT FK_userid0 foreign key (userid) references Users(userid) on delete cascade;
ALTER TABLE REPORT ADD CONSTRAINT FK_userid1 foreign key (userid) references Users(userid) on delete cascade;

insert into food values (1,'Apple cider','drink',87,'cup',0.75,0);
insert into food values (2,'Bacon pork','meat',36,'slice',1,3);
insert into food values (3,'Chicken McNuggets','meat',306,'pcs',6,18);
insert into food values (4,'Duck skin removed','meat',187,'oz',3,10);
insert into food values (5,'Egg roll with shrimp','meat',167,'roo',1,7);
insert into food values (6,'Fruit ice','fruit',124,'cup',0.5,0);
insert into food values (7,'Ground lamb','meat',235,'oz',3,17);
insert into food values (8,'Heart beef','meat',149,'oz',3,5);
insert into food values (9,'Ice milk','drink',111,'cup',0.5,2);
insert into food values (10,'Jellies','snack',48,'Tbsp',1,0);
insert into food values (11,'Lemon juice','drink',3,'Tbsp',1,0);
insert into food values (12,'Mango diced','fruit',54,'cup',0.5,0);
insert into food values (13,'Mushroom raw','vegetable',9,'cup',0.5,0);
insert into food values (14,'Nuts bread','bread',158,'slice',1,6);
insert into food values (15,'Okra','vegetable',34,'cup',0.5,0);
insert into food values (16,'Pancake homemade plain','bread',66,'each',1,3);
insert into food values (17,'Potato salad German','vegetable',94,'cup',0.5,4);
insert into food values (18,'Raspberries fresh','fruit',30,'cup',0.5,0);
insert into food values (19,'Strawberries fresh','fruit',23,'cup',0.5,0);
insert into food values (20,'Turkey skin removed','meat',140,'oz',3,3);
insert into Users values (1,'Gou','Yi',' ','1995-03-03',1.70,170,'M',' ',' ',1,5000);
insert into Users values (2,'Hang','Zhao',' ','1995-03-13',1.72,140,'M',' ',' ',2,5300);
insert into Users values (3,'K','Jun',' ','1995-05-03',1.80,190,'M',' ',' ',3,5500);
insert into Users values (4,'Gary','Gou',' ','1995-07-03',1.76,130,'M',' ',' ',4,4000);
insert into Users values (5,'Ximiy','Chen',' ','1995-10-21',1.65,100,'FM',' ',' ',2,8000);
insert into REPORT values(1,1,'2019-03-29',4000,25,8000,10000);
insert into REPORT values(2,2,'2019-03-29',2000,23,6000,11000);
insert into REPORT values(3,3,'2019-03-29',3000,21,7000,12000);
insert into REPORT values(4,4,'2019-03-29',5000,27,9000,13000);
insert into REPORT values(5,5,'2019-03-29',1000,28,5500,14000);
insert into credential values(1,1,'gui2550','3ba874b26d424e9edbae72c41ebc4cfc','2019-03-28');
insert into credential values(2,2,'guia2550','7b34839147daa8f74f9eecf79dcf7c7f','2019-03-29');
insert into credential values(3,3,'guib2550','c8837b23ff8aaa8a2dde915473ce0991','2019-03-28');
insert into credential values(4,4,'guic2550','8ddee4d88143cd88fa3e84dc3f78491b','2019-03-28');
insert into credential values(5,5,'guid2550','a845d4fa668e5965b39026b488f602d6','2019-03-29');
insert into consumption values(1,1,1,'2019-03-27',5);
insert into consumption values(2,1,2,'2019-03-27',4);
insert into consumption values(3,1,3,'2019-03-27',2);
insert into consumption values(4,2,15,'2019-03-27',1);
insert into consumption values(5,2,13,'2019-03-27',2);