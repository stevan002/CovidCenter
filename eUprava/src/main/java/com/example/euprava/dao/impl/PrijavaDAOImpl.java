package com.example.euprava.dao.impl;

import com.example.euprava.Models.Pacijent;
import com.example.euprava.Models.Prijava;
import com.example.euprava.Models.Vakcina;
import com.example.euprava.dao.PacijentDAO;
import com.example.euprava.dao.PrijavaDAO;
import com.example.euprava.dao.VakcinaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PrijavaDAOImpl implements PrijavaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PacijentDAO pacijentDAO;
    @Autowired
    private VakcinaDAO vakcinaDAO;

    private class PrijavaRowCallBackHandler implements RowCallbackHandler{
        private Map<Long, Prijava> prijavaMap = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            LocalDateTime datumVremePrijave = rs.getTimestamp(index++).toLocalDateTime();
            Long pacijentId = rs.getLong(index++);
            Long vakcinaId = rs.getLong(index++);

            Pacijent pacijent = pacijentDAO.findOne(pacijentId);
            Vakcina vakcina = vakcinaDAO.findOne(vakcinaId);

            Prijava prijava = prijavaMap.get(id);
            if(prijava == null){
                prijava = new Prijava(id, datumVremePrijave.minusHours(2), pacijent, vakcina);
                prijavaMap.put(prijava.getId(), prijava);
            }
        }

        public List<Prijava> getPrijavaMap() {
            return new ArrayList<>(prijavaMap.values());
        }
    }

    @Override
    public List<Prijava> findAll() {
        String sql = "select p.id, p.datumVremePrijave, p.pacijentId, p.vakcinaId " +
                "from prijavaZaVakcinu p " +
                "order by p.id";
        PrijavaRowCallBackHandler prijavaRowCallBackHandler = new PrijavaRowCallBackHandler();
        jdbcTemplate.query(sql, prijavaRowCallBackHandler);

        if(prijavaRowCallBackHandler.getPrijavaMap().size() == 0){
            return null;
        }
        return prijavaRowCallBackHandler.getPrijavaMap();
    }

    @Override
    public List<Prijava> search(String pretraga) {
        String sql = "select p.id, p.datumVremePrijave, p.pacijentId, p.vakcinaId " +
                "from prijavaZaVakcinu p " +
                "join korisnici k on p.pacijentId = k.id " +
                "where k.ime like ? or k.prezime like ? or k.jmbg like ? " +
                "order by p.id";
        PrijavaRowCallBackHandler prijavaRowCallBackHandler = new PrijavaRowCallBackHandler();
        String query = "%" + pretraga + "";
        jdbcTemplate.query(sql, prijavaRowCallBackHandler, query, query, query);

        return prijavaRowCallBackHandler.getPrijavaMap();
    }

    @Override
    public Prijava findOne(Long id) {
        String sql = "select p.id, p.datumVremePrijave, p.pacijentId, p.vakcinaId " +
                "from prijavaZaVakcinu p " +
                "where p.id = ? " +
                "order by p.id";
        PrijavaRowCallBackHandler prijavaRowCallBackHandler = new PrijavaRowCallBackHandler();
        jdbcTemplate.query(sql, prijavaRowCallBackHandler, id);

        if(prijavaRowCallBackHandler.getPrijavaMap().size() == 0){
            return null;
        }
        return prijavaRowCallBackHandler.getPrijavaMap().get(0);
    }

    @Override
    public int save(Prijava prijava) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "insert into prijavaZaVakcinu(datumVremePrijave, pacijentId, vakcinaId) values(?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                Timestamp timestamp = Timestamp.valueOf(prijava.getVremeDobijanjaDoze());
                preparedStatement.setString(index++, timestamp.toString());
                preparedStatement.setLong(index++, prijava.getPacijentId());
                preparedStatement.setLong(index++, prijava.getVakcinaId());

                return preparedStatement;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean success = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return success ? 1 : 0;
    }

    @Override
    public int update(Prijava prijava) {
        String sql = "update prijavaZaVakcinu set datumVremePrijave = ?, pacijentId = ?, vakcinaId = ? where id = ?";
        boolean success = jdbcTemplate.update(sql,
                Timestamp.valueOf(prijava.getVremeDobijanjaDoze()).toString(),
                        prijava.getPacijentId(),
                        prijava.getVakcinaId(),
                        prijava.getId()) == 1;
        return success ? 1 : 0;
    }

    @Override
    public void deleteByPacijent(Long pacijentId, Long id) {
        String sql = "delete from prijavaZaVakcinu where pacijentId = ? and id <> ?";
        jdbcTemplate.update(sql, pacijentId, id);
    }

    @Override
    public int delete(Long id) {
        String sql = "delete from prijavaZaVakcinu where id = ?";
        return jdbcTemplate.update(sql, id);
    }


}
