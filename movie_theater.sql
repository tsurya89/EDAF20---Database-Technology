set foreign_key_checks = 0;

drop table if exists Person;
drop table if exists Reservation;
drop table if exists Theater;
drop table if exists Movie;
drop table if exists Performance;

set foreign_key_checks = 1;

create table Person (
    username varchar(20),
    name varchar(20),
    address varchar(50),
    phoneNum varchar(20),
    primary key(username)
);

create table Theater (
    name varchar(20),
    totalSeats int,
    primary key(name)
);

create table Movie (
    name varchar(20),
    primary key(name)
);

create table Performance (
    performanceNum int,
    movieName varchar(20),
    theaterName varchar(20),
    performanceDate date,
    freeSeats int,
    primary key(performanceNum),
    foreign key (theaterName) references Theater(name)
);

create table Reservation (
    reservationNum int, 
    username varchar(20),
    movieName varchar(20),
    reservationDate date,
    primary key(reservationNum),
    foreign key (username) references Person(username),
    foreign key (movieName) references Movie(name),
    foreign key (reservationNum) references Performance(performanceNum)
);

-- Inserting movie titles
insert into Movie values ('Black Panther');
insert into Movie values ('Barbie');
insert into Movie values ('Oppenheimer');
insert into Movie values ('Inception');
insert into Movie values ('Up');
insert into Movie values ('Black Widow');
insert into Movie values ('Frozen II');
insert into Movie values ('Hairspray');
insert into Movie values ('A Man Called Ove');
insert into Movie values ('Insidious');

-- Inserting people
INSERT INTO Person (username, name, address, phoneNum) VALUES
('john_doe', 'John Doe', '123 Elm St, Springfield', '555-123-5611'),
('jane_smith', 'Jane Smith', '456 Oak St, Springfield', '555-567-8911'),
('bob_jones', 'Bob Jones', '789 Pine St, Shelbyville', '555-876-7822'),
('alice_williams', 'Alice Williams', '101 Maple St, Capital City', '555-112-3344'),
('charlie_brown', 'Charlie Brown', '202 Birch St, Evergreen', '555-345-5566');

-- Inserting theaters
INSERT INTO Theater (name, totalSeats) VALUES
('AMC Methuen', 200),
('AMC Hawthorn', 500),
('Filmstaden', 100);

-- Inserting performances
INSERT INTO Performance (performanceNum, movieName, theaterName, performanceDate, freeSeats) VALUES
(1, 'Black Panther', 'Filmstaden', '2025-04-10', 100), 
(2, 'Up', 'AMC Methuen', '2025-05-25', 200),
(3, 'Frozen II', 'AMC Hawthorn', '2025-01-05', 500),
(4, 'Insidious', 'AMC Methuen', '2025-10-31', 200),
(5, 'Barbie', 'Filmstaden', '2025-07-16', 100);

-- List all movies that are shown
select movieName from Performance;

-- List dates when a movie is shown
select movieName, performanceDate from Performance;

-- List all data concerning a movie show
select * from Performance;

-- Make a reservation
INSERT INTO Reservation (reservationNum, username, movieName, reservationDate) VALUES
(1, 'john_doe', 'Black Panther', '2025-04-10'),
(2, 'john_doe', 'Frozen II', '2025-01-05');


-- Check that the key constraints work (error checking)
-- INSERT INTO Theater values ('AMC Methuen', 200);
-- INSERT INTO Performance VALUES ('Oppenheimer', 'AMC Lowell', '2025-04-10');