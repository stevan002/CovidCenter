package com.example.euprava.dao.impl;

import com.example.euprava.Models.ProizvodjacVakcina;
import com.example.euprava.Models.Vakcina;
import com.example.euprava.dao.ProizvodjacVakcinaDAO;
import com.example.euprava.dao.VakcinaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Repository
public class VakcinaDAOImpl implements VakcinaDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProizvodjacVakcinaDAO proizvodjacVakcinaDAO;

    private class VakcinaRowCallBackHandler implements RowCallbackHandler{
        private Map<Long, Vakcina> vakcine = new LinkedHashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            String ime = rs.getString(index++);
            int kolicina = rs.getInt(index++);
            Long proizvodjacId= rs.getLong(index++);

            ProizvodjacVakcina proizvodjacVakcina = proizvodjacVakcinaDAO.findOne(proizvodjacId);

            Vakcina vakcina = vakcine.get(id);
            if(vakcina == null){
                vakcina = new Vakcina(id, ime, kolicina, proizvodjacVakcina);
                vakcine.put(vakcina.getId(), vakcina);
            }
        }
        private List<Vakcina> getVakcine(){
            return new ArrayList<>(vakcine.values());
        }
    }
    @Override
    public Vakcina findOne(Long id) {
        String sql = "select vak.id, vak.ime, vak.kolicina, vak.proizvodjacId from vakcina vak " +
                "where vak.id=? " +
                "order by vak.id";

        VakcinaRowCallBackHandler rowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, id);

        if(rowCallBackHandler.getVakcine().size() == 0){
            return null;
        }

        return rowCallBackHandler.getVakcine().get(0);
    }

    @Override
    public List<Vakcina> search(String pretraga) {
        String sql = "select v.id, v.ime, v.kolicina, v.proizvodjacId from vakcina v " +
                "join proizvodjacVakcina p on v.proizvodjacId = p.id " +
                "where v.ime = ? or v.kolicina = ? or p.proizvodjac = ? or p.drzavaProizvodnje = ? " +
                "order by v.id ";
        VakcinaRowCallBackHandler rowCallBackHandler = new VakcinaRowCallBackHandler();
        String temp = "%" + pretraga + "%";
        jdbcTemplate.query(sql, rowCallBackHandler, temp, temp, temp, temp);
        return rowCallBackHandler.getVakcine();
    }

    @Override
    public List<Vakcina> findAll() {
        String sql = "select v.id, v.ime, v.kolicina, v.proizvodjacId from vakcina v " +
                "order by v.id";
        VakcinaRowCallBackHandler rowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);

        if(rowCallBackHandler.getVakcine().size() == 0){
            return null;
        }

        return rowCallBackHandler.getVakcine();
    }
    @Transactional
    @Override
    public int save(Vakcina vakcina) {
        PreparedStatementCreator statementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "insert into vakcina(ime, kolicina, proizvodjacId) values (?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, vakcina.getIme());
                preparedStatement.setInt(index++, vakcina.getKolicina());
                preparedStatement.setLong(index++, vakcina.getProizvodjac().getId());

                return preparedStatement;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(statementCreator, keyHolder)==1;
        return uspeh?1:0;
    }
    @Transactional
    @Override
    public int update(Vakcina vakcina) {
        String sql = "update vakcina set ime=?, kolicina=?, proizvodjacId=? where id=?";
        boolean uspeh = jdbcTemplate.update(sql,vakcina.getIme(), vakcina.getKolicina(), vakcina.getProizvodjac().getId(), vakcina.getId()) == 1;
        return uspeh?1:0;
    }

    @Override
    public int delete(Long id) {
        String sql = "delete from vakcina where id=?";
        return jdbcTemplate.update(sql, id);
    }
}
