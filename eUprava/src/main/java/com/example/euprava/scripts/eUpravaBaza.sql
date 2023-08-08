DROP SCHEMA IF EXISTS euprava;
CREATE SCHEMA euprava DEFAULT CHARACTER SET utf8;
USE euprava;

create table korisnici(
	id bigint auto_increment,
    email varchar(80) not null,
    lozinka varchar(50) not null,
    ime varchar(50) not null,
    prezime varchar(50) not null,
    datumRodjenja date not null,
    jmbg varchar(13) not null,
    adresa varchar(50) not null,
    brTelefona varchar(20) not null,
    datumVremeRegistracije datetime not null,
    uloga enum("Medicinsko_osoblje", "Pacijent", "Administrator")  not null,
    PRIMARY KEY(id)
);

create table proizvodjacVakcina(
	id bigint auto_increment,
    proizvodjac varchar(50) not null,
    drzavaProizvodnje varchar(30) not null,
    PRIMARY KEY(id)
);

create table vakcina(
	id bigint auto_increment,
    ime varchar(30) not null,
    kolicina int not null,
    proizvodjacId bigint not null,
    FOREIGN KEY(proizvodjacId) REFERENCES proizvodjacVakcina(id)
		ON DELETE CASCADE,
    PRIMARY KEY(id)
);

create table vest(
	id bigint auto_increment,
    naziv varchar(50) not null,
    sadrzaj varchar(500) not null,
    datumVremeObjavljivanje datetime not null,
    primary key(id)
);

create table vestoObolelima(
	id bigint auto_increment,
    brojObolelih int not null,
    brojTestiranih int not null,
    brojHospitalizovanih int not null,
    brojNaRespiratorima int not null,
    datumObjavljivanja datetime not null,
    primary key(id)
);

create table kartonPacijenta(
	id bigint auto_increment,
    doza int not null,
    datumVremeDobijanjaDoze datetime not null,
    pacijentId bigint not null,
    FOREIGN KEY(pacijentId) REFERENCES korisnici(id)
		ON DELETE CASCADE,
    PRIMARY KEY(id)
);

create table nabavkaVakcine (
	id bigint auto_increment,
    kolicinaVakcina int not null,
    razlogNabavke varchar(100),
    datumKreiranjaZahteva datetime not null,
    medicinskoOsoboljeId bigint not null,
    razlogOdbijanjaRevizijeNabavke varchar(100),
    statusNabavke varchar(50),
    foreign key(medicinskoOsoboljeId) references korisnici(id) on delete cascade,
    vakcinaId bigint not null,
    foreign key(vakcinaId) references Vakcina(id) on delete cascade,
    primary key(id)
    
);

create table prijavaZaVakcinu(
	id bigint auto_increment,
    datumVremePrijave datetime not null,
    pacijentId bigint not null,
    foreign key(pacijentId) references korisnici(id) on delete cascade,
    vakcinaId bigint not null,
    foreign key(vakcinaId) references Vakcina(id) on delete cascade,
    primary key(id)
);

insert into korisnici(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brTelefona, datumVremeRegistracije, uloga)
 values("stevan@gmail.com","lozinka", "Stevan", "Stankovic", "2002-09-23", "1234567891234", "Safarikova 31", "061123123", now(), "Pacijent");
insert into korisnici(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brTelefona, datumVremeRegistracije, uloga)
 values("milica@gmail.com","lozinka", "Milica", "Milic", "2002-09-23", "1234567891234", "Hajduk Stanka 31", "061123123", now(), "Medicinsko_osoblje");
insert into korisnici(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brTelefona, datumVremeRegistracije, uloga)
 values("mihajlo@gmail.com","lozinka", "Mihajlo", "Mandic", "2002-09-23", "1234567891234", "Hajduk Stanka 31", "061123123", now(), "Pacijent");

insert into proizvodjacVakcina(proizvodjac, drzavaProizvodnje) values ("Ime Proizvodjaca", "Srbija");
insert into proizvodjacVakcina(proizvodjac, drzavaProizvodnje) values ("Ime Proizvodjaca 2", "Engleska");

insert into vakcina(ime, kolicina, proizvodjacId) values("Fajzer", 10, 1);
insert into vakcina(ime, kolicina, proizvodjacId) values("Ruska", 10, 2);

insert into kartonPacijenta(doza, datumVremeDobijanjaDoze, pacijentId) values (1, now(), 1);
insert into kartonPacijenta(doza, datumVremeDobijanjaDoze, pacijentId) values (1, now(), 3);

insert into nabavkaVakcine(kolicinaVakcina, razlogNabavke, datumKreiranjaZahteva, medicinskoOsoboljeId, razlogOdbijanjaRevizijeNabavke, statusNabavke, vakcinaId)
 values(5, "Nema dovoljno vakcina", now(), 2, null, "Odobreno", 1);
insert into nabavkaVakcine(kolicinaVakcina, razlogNabavke, datumKreiranjaZahteva, medicinskoOsoboljeId, razlogOdbijanjaRevizijeNabavke, statusNabavke, vakcinaId)
 values(5, "Nema dovoljno vakcina", now(), 2, null, "Odobreno", 2);
 
insert into prijavaZaVakcinu(datumVremePrijave, pacijentId, vakcinaId) values (now(), 1, 1);
insert into prijavaZaVakcinu(datumVremePrijave, pacijentId, vakcinaId) values (now(), 3, 2);

CREATE FUNCTION get_total_infected(vest_id INT) RETURNS INT
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE ukupno_zarazenih INT;
    SELECT SUM(brojObolelih) INTO ukupno_zarazenih FROM vestoObolelima WHERE datumObjavljivanja <= (select datumObjavljivanja from vestoObolelima
                                                                                    where id = vest_id);
    RETURN ukupno_zarazenih;
END;


