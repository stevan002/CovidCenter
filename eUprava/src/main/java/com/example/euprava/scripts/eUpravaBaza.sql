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
    datumVremeRegistracije datetime,
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
    sadrzaj varchar(1000) not null,
    datumVremeObjavljivanje datetime not null,
    primary key(id)
);

create table vestoObolelima(
	id bigint auto_increment,
    brObolelih int not null,
    brTestiranih int not null,
    brUkupnoObolelih int not null,
    brHospitalizovanih int not null,
    brNaRespiratorima int not null,
    datumVremeObjavljivanja datetime not null,
    primary key(id)
);

create table kartonPacijenta(
	id bigint auto_increment,
    doza varchar(50) not null,
    datumVremeDobijanjaDoze datetime not null,
    pacijentId bigint not null,
    FOREIGN KEY(pacijentId) REFERENCES Korisnik(id)
		ON DELETE CASCADE,
    PRIMARY KEY(id)
    
);