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
            Long id = rs.getLong(index++);
            String ime = rs.getString(index++);
            String prezime = rs.getString(index++);
            String email = rs.getString(index++);
            String lozinka = rs.getString(index++);
            Date datumRodjenja = rs.getDate(index++);
            String jmbg = rs.getString(index++);
            String adresa = rs.getString(index++);
            String brTelefona = rs.getString(index++);
            LocalDateTime datumVremeRegistracije = rs.getTimestamp(index++).toLocalDateTime(); //chatGPT
            String enumString = rs.getString(index++);
            EUloga uloga = EUloga.valueOf(enumString); //chatGPT

            Korisnik korisnik = korisnici.get(id);
            if(korisnik == null){
                korisnik = new Korisnik(id, email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brTelefona, datumVremeRegistracije, uloga);
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

        if(rowCallBackHandler.getKorisnici().size() == 0) {
            return null;
        }

        return rowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public Korisnik findOneByEmail(String email) {

        String sql =
                "select kor.id, kor.ime, kor.prezime, kor.email, kor.lozinka, kor.datumRodjenja, kor.jmbg, kor.adresa, kor.brTelefona, kor.datumVremeRegistracije, kor.uloga " +
                        "from korisnici kor " +
                        "where kor.email = ? " +
                        "order by kor.id";

        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, email);

        if(rowCallBackHandler.getKorisnici().size() == 0) {
            return null;
        }

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
                //PROVERITI
                preparedStatement.setString(index++, korisnik.getEmail());
                preparedStatement.setString(index++, korisnik.getLozinka());
                preparedStatement.setString(index++, korisnik.getIme());
                preparedStatement.setString(index++, korisnik.getPrezime());
                preparedStatement.setDate(index++, korisnik.getDatumRodjenja());
                preparedStatement.setString(index++, korisnik.getJmbg());
                preparedStatement.setString(index++, korisnik.getAdresa());
                preparedStatement.setString(index++, korisnik.getBrTelefona());
                Timestamp timestamp = Timestamp.valueOf(korisnik.getDatumVremeRegistracije());
                preparedStatement.setTimestamp(index++, timestamp); //chatGPT
                preparedStatement.setString(index++, korisnik.getUloga().name()); //chatGPT


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
        String sql = "update korisnici set email=?, lozinka=?, ime=?, prezime=?, datumRodjenja=?, jmbg=?, adresa=?, brTelefona=?, datumVremeRegistracije=?, uloga=? " +
                "where id=?";
        boolean uspeh = jdbcTemplate.update(sql, korisnik.getIme(), korisnik.getPrezime(), korisnik.getEmail(), korisnik.getLozinka(), korisnik.getDatumRodjenja().toString(), korisnik.getJmbg(), korisnik.getAdresa(), korisnik.getBrTelefona(), Timestamp.valueOf(korisnik.getDatumVremeRegistracije()).toString(), korisnik.getUloga().toString(), korisnik.getId()) == 1;
        return uspeh?1:0;
    }
    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "delete from korisnici where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
