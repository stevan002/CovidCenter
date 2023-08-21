package com.example.euprava.dao.impl;

import com.example.euprava.Models.Korisnik;
import com.example.euprava.Models.Pacijent;
import com.example.euprava.Services.VakcinaService;
import com.example.euprava.dao.KorisnikDAO;
import com.example.euprava.dao.PacijentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PacijentDAOImpl implements PacijentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private KorisnikDAO korisnikDAO;

    private class RowCallbackHandler implements org.springframework.jdbc.core.RowCallbackHandler {

        private final Map<Long, Pacijent> pacijenti = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            Integer doza = rs.getInt(index++);
            Timestamp datumVremeDobijanjaDoze = rs.getTimestamp(index++);
            LocalDateTime poslednjaDoza = null;
            if(datumVremeDobijanjaDoze != null){
                poslednjaDoza = datumVremeDobijanjaDoze.toLocalDateTime().minusHours(2);
            }

            Pacijent pacijent = pacijenti.get(id);
            Korisnik korisnik = korisnikDAO.findOne(id);

            if(pacijent == null){
                pacijent = new Pacijent(id, doza, poslednjaDoza, korisnik);
                pacijenti.put(pacijent.getKorisnikId(), pacijent);
            }
        }

        public List<Pacijent> getPacijenti() {
            return new ArrayList<>(pacijenti.values());
        }
    }

    @Override
    public Pacijent findOne(Long id) {
        String sql = "select p.pacijentId, p.doza, p.datumVremeDobijanjaDoze " +
                "from kartonPacijenta p " +
                "where p.pacijentId = ? " +
                "order by p.pacijentId";
        RowCallbackHandler rowCallbackHandler = new RowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, id);

        if(rowCallbackHandler.getPacijenti().size() == 0){
            return null;
        }

        return rowCallbackHandler.getPacijenti().get(0);
    }

    @Override
    public List<Pacijent> listAll() {
        String sql = "select p.pacijentId, p.doza, p.datumVremeDobijanjaDoze " +
                "from kartonPacijenta p " +
                "order by p.pacijentId";
        RowCallbackHandler rowCallbackHandler = new RowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);

        if(rowCallbackHandler.getPacijenti().size() == 0){
            return null;
        }

        return rowCallbackHandler.getPacijenti();
    }

    @Transactional
    @Override
    public int save(Pacijent pacijent) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "insert into kartonPacijenta(pacijentId, doza, datumVremeDobijanjaDoze) values (?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setLong(index++, pacijent.getKorisnikId());
                preparedStatement.setInt(index++, pacijent.getDoze());
                if(pacijent.getVremePrimanjaDoze() != null){
                    Timestamp timestamp = Timestamp.valueOf(pacijent.getVremePrimanjaDoze());
                    preparedStatement.setString(index++, timestamp.toString());
                }else{
                    preparedStatement.setString(index++, null);
                }

                return preparedStatement;
            }
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        boolean succesfull = jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder) == 1;
        return succesfull ? 1 : 0;
    }

    @Transactional
    @Override
    public int update(Pacijent pacijent) {
        String sql = "update kartonPacijenta set doza=?, datumVremeDobijanjaDoze=? where pacijentId=?";
        boolean succesfull = jdbcTemplate.update(sql,pacijent.getDoze(), pacijent.getVremePrimanjaDoze(), pacijent.getKorisnikId()) == 1;
        return succesfull ? 1 : 0;
    }

    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "delete from kartonPacijenta where pacijentId=?";
        return jdbcTemplate.update(sql, id);
    }
}
