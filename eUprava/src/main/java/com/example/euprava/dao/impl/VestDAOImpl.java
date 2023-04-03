package com.example.euprava.dao.impl;

import com.example.euprava.Models.Vest;
import com.example.euprava.dao.VestDAO;
import org.hibernate.dialect.temptable.StandardTemporaryTableExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VestDAOImpl implements VestDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private class VestRowCallBackHandler implements RowCallbackHandler{
        private Map<Long, Vest> vesti = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            String naziv = rs.getString(index++);
            String sadrzaj = rs.getString(index++);
            Date datumVremeObjavljivanje = rs.getDate(index++);
            Vest vest = vesti.get(id);
            if(vest == null){
                vest = new Vest(id, naziv, sadrzaj, datumVremeObjavljivanje);
                vesti.put(vest.getId(), vest);
            }
        }
        public List<Vest> getVesti(){
            return new ArrayList<>(vesti.values());
        }
    }
    @Override
    public Vest findOne(Long id) {
        String sql = "select v.id, v.naziv, v.sadrzaj, v.datumVremeObjavljivanje from vest v " +
                "where id=? " +
                "order by id=?";

        VestRowCallBackHandler rowCallBackHandler = new VestRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, id);
        return rowCallBackHandler.getVesti().get(0);
    }

    @Override
    public List<Vest> findAll() {
        String sql = "select v.id, v.naziv, v.sadrzaj, v.datumVremeObjavljivanje from vest v " +
                "order by v.id";
        VestRowCallBackHandler rowCallBackHandler = new VestRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);

        return rowCallBackHandler.getVesti();
    }
    @Transactional
    @Override
    public int save(Vest vest) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "insert into vest(naziv, sadrzaj, datumVremeObjavljivanje) " +
                        "values(?, ?, ?)";

                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, vest.getNaziv());
                preparedStatement.setString(index++, vest.getSadrzaj());
                preparedStatement.setDate(index++, (Date) vest.getDatumVremeObjavljivanja());

                return preparedStatement;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh?1:0;
    }
    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "delete from vest where id = ?";
        return jdbcTemplate.update(sql, id);
    }
    @Transactional
    @Override
    public int update(Vest vest) {
        String sql = "update vest set naziv=?, sadrzaj=?, datumVremeObjavljivanje=? " +
                "where id=?";
        boolean uspeh = jdbcTemplate.update(sql, vest.getNaziv(), vest.getSadrzaj(), vest.getDatumVremeObjavljivanja())==1;
        return uspeh?1:0;
    }
}
