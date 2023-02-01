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

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProizvodjacVakcinaDAOImpl implements ProizvodjacVakcinaDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private class ProizvodjacVakcinaRowMapper implements RowMapper<ProizvodjacVakcina> {
        private Map<Long, ProizvodjacVakcina> proizvodjaciVakcina = new LinkedHashMap<>();

        @Override
        public ProizvodjacVakcina mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            String proizvodjac = rs.getString(index++);
            String drzavaProizvodnje = rs.getString(index++);

            ProizvodjacVakcina proizvodjacVakcina = new ProizvodjacVakcina(id, proizvodjac, drzavaProizvodnje);
            return proizvodjacVakcina;
        }
    }

    @Override
    public ProizvodjacVakcina findOne(Long id) {
        String sql =
                "select p.id, p.prozivodjac, p.drzavaProizvodnje from proizvodjacVakcina p " +
                        "where p.id = ? ";
        return jdbcTemplate.queryForObject(sql, new ProizvodjacVakcinaRowMapper(), id);
    }

    @Override
    public List<ProizvodjacVakcina> findAll() {
        String sql =
                "select p.id, p.prozivodjac, p.drzavaProizvodnje from proizvodjacVakcina p " +
                        "order by p.id";
        return jdbcTemplate.query(sql, new ProizvodjacVakcinaRowMapper());
    }

    @Override
    public List<ProizvodjacVakcina> find(String proizvodjac) {
        proizvodjac = "%" + proizvodjac + "%";
        String sql = "select id, prozivodjac, drzavaProizvodnje from proizvodjacVakcina where prozivodjac like ?";
        return jdbcTemplate.query(sql, new ProizvodjacVakcinaRowMapper(), proizvodjac);
    }


    @Override
    public int save(ProizvodjacVakcina proizvodjacVakcina) {
        String sql = "insert into proizvodjacVakcina (prozivodjac, drzavaProizvodnje) values (?, ?)";
        return jdbcTemplate.update(sql, proizvodjacVakcina.getProizvodjac(), proizvodjacVakcina.getDrzavaProizvodnje());
    }

    @Override
    public int update(ProizvodjacVakcina proizvodjacVakcina) {
        String sql = "update proizvodjacVakcina set prozivodjac = ?, drzavaProizvodnje = ? where id = ?";
        return jdbcTemplate.update(sql, proizvodjacVakcina.getProizvodjac(), proizvodjacVakcina.getDrzavaProizvodnje(), proizvodjacVakcina.getId());
    }

    @Override
    public int delete(Long id) {
        String sql = "delete from proizvodjacVakcina where id=?";
        return jdbcTemplate.update(sql, id);
    }
}
