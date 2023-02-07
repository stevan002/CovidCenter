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
    sadrzaj varchar(500) not null,
    datumVremeObjavljivanje datetime not null,
    primary key(id)
);

create table vestoObolelima(
	id bigint auto_increment,
    brojObolelih int not null,
    brojTestiranih int not null,
    brojUkupnoObolelih int not null,
    brojHospitalizovanih int not null,
    brojNaRespiratorima int not null,
    datumObjavljivanja date not null,
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
    foreign key(medicinskoOsobljeId) references korisnici(id) on delete cascade,
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