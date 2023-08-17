package com.example.euprava.dao.impl;

import com.example.euprava.Models.ProizvodjacVakcina;
import com.example.euprava.dao.ProizvodjacVakcinaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.processing.Generated;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProizvodjacVakcinaDAOImpl implements ProizvodjacVakcinaDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class ProizvodjacVakcinaRowCallBackHandler implements RowCallbackHandler{
        private Map<Long, ProizvodjacVakcina> proizvodjaciVakcina = new LinkedHashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            String proizvodjac = rs.getString(index++);
            String drzavaProizvodnje = rs.getString(index++);

            ProizvodjacVakcina proizvodjacVakcina = proizvodjaciVakcina.get(id);
            if(proizvodjacVakcina == null){
                proizvodjacVakcina = new ProizvodjacVakcina(id, proizvodjac, drzavaProizvodnje);
                proizvodjaciVakcina.put(proizvodjacVakcina.getId(), proizvodjacVakcina);
            }
        }

        public List<ProizvodjacVakcina> getProizvodjaciVakcina(){
            return new ArrayList<>(proizvodjaciVakcina.values());
        }
    }

    @Override
    public ProizvodjacVakcina findOne(Long id) {
        String sql = "select pro.id, pro.proizvodjac, pro.drzavaProizvodnje from proizvodjacVakcina pro " +
                "where pro.id = ? " +
                "order by pro.id";

        ProizvodjacVakcinaRowCallBackHandler rowCallBackHandler = new ProizvodjacVakcinaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, id);

        if(rowCallBackHandler.getProizvodjaciVakcina().size() == 0){
            return null;
        }

        return rowCallBackHandler.getProizvodjaciVakcina().get(0);
    }

    @Override
    public List<ProizvodjacVakcina> findAll() {
        String sql = "select pro.id, pro.proizvodjac, pro.drzavaProizvodnje from proizvodjacVakcina pro " +
                "order by pro.id";

        ProizvodjacVakcinaRowCallBackHandler rowCallBackHandler = new ProizvodjacVakcinaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);

        if(rowCallBackHandler.getProizvodjaciVakcina().size() == 0){
            return null;
        }

        return rowCallBackHandler.getProizvodjaciVakcina();
    }
    @Transactional
    @Override
    public int save(ProizvodjacVakcina proizvodjacVakcina) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "insert into proizvodjacVakcina (proizvodjac, drzavaProizvodnje) " +
                        "values(?, ?)";

                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                //PROVERITI
                preparedStatement.setString(index++, proizvodjacVakcina.getProizvodjac());
                preparedStatement.setString(index++, proizvodjacVakcina.getDrzavaProizvodnje());

                return preparedStatement;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh?1:0;
    }

    @Transactional
    @Override
    public int update(ProizvodjacVakcina proizvodjacVakcina) {
        String sql = "update proizvodjacVakcina set proizvodjac=?, drzavaProizvodnje=? " +
                "where id=?";
        boolean uspeh = jdbcTemplate.update(sql, proizvodjacVakcina.getProizvodjac(), proizvodjacVakcina.getDrzavaProizvodnje(), proizvodjacVakcina.getId())==1;
        return uspeh?1:0;
    }

    @Override
    public int delete(Long id) {
        String sql = "delete from proizvodjacVakcina where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
