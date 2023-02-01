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
public class KorisnikDAOImpl implements KorisnikDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private class KorisnikRowCallBackHandler implements RowCallbackHandler {
        private Map<Long, Korisnik> korisnici = new LinkedHashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            String ime = rs.getString(index++);
            String prezime = rs.getString(index++);
            String email = rs.getString(index++);
            String lozinka = rs.getString(index++);
            Date datumRodjenja = rs.getDate(index++);
            String jmbg = rs.getString(index++);
            String adresa = rs.getString(index++);
            String brTelefona = rs.getString(index++);
            LocalDateTime datVrRegistracije = rs.getTimestamp(index++).toLocalDateTime();
            String cellValue = rs.getString(index++);
            EUloga tip = EUloga.valueOf(cellValue); //chatGPT
            Korisnik korisnik = korisnici.get(id);
            if(korisnik != null){
                korisnik = new Korisnik(id, email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brTelefona, datVrRegistracije, tip);
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
                "select k.id, k.ime, k.prezime, k.email, k.lozinka, k.datumRodjenja, k.jmbg, k.adresa, k.brTelefona, k.datVrRegistracije, k.tip from korisnik k " +
                "where k.id = ? " +
                "order by k.id";
        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, id);

        return rowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public Korisnik findOne(String email) {
        String sql =
                "select k.id, k.ime, k.prezime, k.email, k.lozinka, k.datumRodjenja, k.jmbg, k.adresa, k.brTelefona, k.datVrRegistracije, k.tip from korisnik k " +
                        "where k.email = ? " +
                        "order by k.id";
        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, email);

        return rowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public Korisnik findOne(String email, String lozinka) {
        String sql =
                "select k.id, k.ime, k.prezime, k.email, k.lozinka, k.datumRodjenja, k.jmbg, k.adresa, k.brTelefona, k.datVrRegistracije, k.tip from korisnik k " +
                        "where k.email = ? AND " +
                        "k.lozinka = ? " +
                        "order by k.id";
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
                "select k.id, k.ime, k.prezime, k.email, k.lozinka, k.datumRodjenja, k.jmbg, k.adresa, k.brTelefona, k.datVrRegistracije, k.tip from korisnik k " +
                    "order by k.id = ?";
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
                String sql = "INSERT INTO korisnik (ime, prezime, email, lozinka, datumRodjenja, jmbg, adresa, brTelefona, datVrRegistracije, tip) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, korisnik.getIme());
                preparedStatement.setString(index++, korisnik.getPrezime());
                preparedStatement.setString(index++, korisnik.getEmail());
                preparedStatement.setString(index++, korisnik.getLozinka());
                preparedStatement.setDate(index++, korisnik.getDatumRodjenja());
                preparedStatement.setString(index++, korisnik.getJmbg());
                preparedStatement.setString(index++, korisnik.getAdresa());
                preparedStatement.setString(index++, korisnik.getBrTelefona());
                preparedStatement.setString(index++, String.valueOf(korisnik.getDatumVremeRegistracije()));
                preparedStatement.setString(index++, String.valueOf(korisnik.getUloga()));
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
        String sql = "UPDATE korisnik SET ime = ?, prezime = ?, email = ?, lozinka = ?, datumRodjenja = ?, jmbg = ?, adresa = ?, brTelefona = ?, datVrRegistracije = ?, tip = ? WHERE id = ?";
        boolean uspeh =jdbcTemplate.update(sql, korisnik.getIme(), korisnik.getPrezime(), korisnik.getEmail(), korisnik.getLozinka(), korisnik.getDatumRodjenja(), korisnik.getJmbg(), korisnik.getAdresa(), korisnik.getBrTelefona(), korisnik.getDatumVremeRegistracije(), korisnik.getUloga()) == 1;

        return uspeh?1:0;
    }
    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM korisnik WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Korisnik> find(String email, String ime, String prezime, String adresa) {
        return null;
    }
}
