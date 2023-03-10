package com.example.euprava.dao.impl;

import com.example.euprava.Models.EUloga;
import com.example.euprava.Models.Korisnik;
import com.example.euprava.dao.KorisnikDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;
@Repository
public class KorisnikDAOImpl implements KorisnikDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class KorisnikRowCallBackHandler implements RowCallbackHandler{

        private Map<Long, Korisnik> korisnici = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            //DOVRSITI SVE
            Korisnik korisnik = korisnici.get(0); //umesto 0 staviti id
            if(korisnik == null){
                korisnik = new Korisnik();
                korisnici.put(korisnik.getId(), korisnik);
            }
        }

        public List<Korisnik> getKorisnici(){
            return new ArrayList<>(korisnici.values());
        }
    }
    @Override
    public Korisnik findOne(Long id) {
        String sql =
                "Select kor.id, kor.ime, kor.prezime, kor.email, kor.lozinka, kor.datumRodjenja, kor.jmbg, kor.adresa, kor.brTelefona, kor.datumVremeRegistracije, kor.uloga " +
                "from korisnici kor " +
                "where kor.id = ? " +
                "order by kor.id";

        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, id);

        return rowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public Korisnik findOneByEmail(String email) {

        String sql =
                "Select kor.id, kor.ime, kor.prezime, kor.email, kor.lozinka, kor.datumRodjenja, kor.jmbg, kor.adresa, kor.brTelefona, kor.datumVremeRegistracije, kor.uloga " +
                        "from korisnici kor " +
                        "where kor.email = ? " +
                        "order by kor.id";

        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, email);

        return rowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public Korisnik findOneByEmailAndPassword(String email, String lozinka) {
        String sql =
                "Select kor.id, kor.ime, kor.prezime, kor.email, kor.lozinka, kor.datumRodjenja, kor.jmbg, kor.adresa, kor.brTelefona, kor.datumVremeRegistracije, kor.uloga " +
                        "from korisnici kor " +
                        "where kor.email = ? and kor.lozinka = ? " +
                        "order by kor.id";
        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, email, lozinka);

        if(rowCallBackHandler.getKorisnici().size() == 0) {
            return null;
        }

        return rowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public List<Korisnik> findAll() {
        String sql =
                "Select kor.id, kor.ime, kor.prezime, kor.email, kor.lozinka, kor.datumRodjenja, kor.jmbg, kor.adresa, kor.brTelefona, kor.datumVremeRegistracije, kor.uloga " +
                        "from korisnici kor " +
                        "order by kor.id";

        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);

        return rowCallBackHandler.getKorisnici();
    }
    @Transactional
    @Override
    public int save(Korisnik korisnik) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql ="insert into korisnici (ime, prezime, email, lozinka, datumRodjenja, jmbg, adresa, brTelefona, datumVremeRegistracije, uloga) " +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement= con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                //ODRADITI SETTERE


                return preparedStatement;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh?1:0;
    }
    @Transactional
    @Override
    public int update(Korisnik korisnik) {
        String sql = "update korisnici set ime=?, prezime=?, email=?, lozinka=?, datumRodjenja=?, jmbg=?, adresa=?, brTelefona=?, datumVremeRegistracije=?, uloga=? " +
                "where id=?";
        boolean uspeh = jdbcTemplate.update(sql, korisnik.getIme(), korisnik.getPrezime(), korisnik.getEmail(), korisnik.getLozinka(), korisnik.getDatumRodjenja(), korisnik.getJmbg(), korisnik.getAdresa(), korisnik.getBrTelefona(), korisnik.getDatumVremeRegistracije(), korisnik.getUloga()) == 1;
        return uspeh?1:0;
    }
    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "delete from korisnici where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
