package com.example.euprava.dao.impl;

import com.example.euprava.Models.VestObolelima;
import com.example.euprava.dao.VestObolelimaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VestObolelimaDAOImpl implements VestObolelimaDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private class VestObolelimaRowCallBackHandler implements RowCallbackHandler{
        private Map<Long, VestObolelima> vesti = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            int brojObolelih = rs.getInt(index++);
            int brojTestiranih = rs.getInt(index++);
            int brojUkupnoObolelih = rs.getInt(index++);
            int brojHospitalizovanih = rs.getInt(index++);
            int brojNaRespiratorima = rs.getInt(index++);
            Date datumVremeObjavljivanja = rs.getDate(index++);

            VestObolelima vestObolelima = vesti.get(id);
            if(vestObolelima == null){
                vestObolelima = new VestObolelima(id, brojObolelih, brojTestiranih, brojUkupnoObolelih, brojHospitalizovanih, brojNaRespiratorima, datumVremeObjavljivanja);
                vesti.put(vestObolelima.getId(), vestObolelima);
            }
        }
        public List<VestObolelima> getVesti(){
            return new ArrayList<>(vesti.values());
        }
    }
    @Override
    public VestObolelima findOne(Long id) {
        String sql = "select v.id, v.brojObolelih, v.brojTestiranih, v.brojUkupnoObolelih, v.brojHospitalizovanih" +
                ", v.brojNaRespiratorima, v.datumObjavljivanja " +
                "from vestoObolelima v " +
                "where id=? " +
                "order by id=?";
        VestObolelimaRowCallBackHandler rowCallBackHandler = new VestObolelimaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, id);
        return rowCallBackHandler.getVesti().get(0);
    }

    @Override
    public List<VestObolelima> findAll() {
        String sql = "select v.id, v.brojObolelih, v.brojTestiranih, v.brojUkupnoObolelih, v.brojHospitalizovanih" +
                ", v.brojNaRespiratorima, v.datumObjavljivanja " +
                "from vestoObolelima v " +
                "order by v.id";
        VestObolelimaRowCallBackHandler rowCallBackHandler = new VestObolelimaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getVesti();
    }

    @Override
    public JdbcTemplate count() {
        //prodiskutovati
        String sql = "select sum(brojObolelih) from vestoObolelima";
        VestObolelimaRowCallBackHandler rowCallBackHandler = new VestObolelimaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return jdbcTemplate;
    }

    @Transactional
    @Override
    public int save(VestObolelima vestObolelima) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "insert into vestoObolelima(brojObolelih, brojTestiranih, brojUkupnoObolelih, brojHospitalizovanih, brojNaRespiratorima, datumObjavljivanja) " +
                        "values(?,?,?,?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setInt(index++, vestObolelima.getBrObolelih());
                preparedStatement.setInt(index++, vestObolelima.getBrTestiranih());
                preparedStatement.setInt(index++, vestObolelima.getBrUkupnoObolelih());
                preparedStatement.setInt(index++, vestObolelima.getBrHospitalizovanih());
                preparedStatement.setInt(index++, vestObolelima.getBrNaRespiratorima());
                preparedStatement.setDate(index++, (Date) vestObolelima.getDatumVremeObjavljivanja());

                return preparedStatement;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh?1:0;
    }
    @Transactional
    @Override
    public int update(VestObolelima vestObolelima) {
        String sql = "update vestoObolelima set brojObolelih=?, brojTestiranih=?, brojUkupnoObolelih=?, brojHospitalizovanih=?" +
                ", brojNaRespiratorima=?, datumObjavljivanja=? where id=?";
        boolean uspeh = jdbcTemplate.update(sql, vestObolelima.getBrObolelih(), vestObolelima.getBrTestiranih(), vestObolelima.getBrUkupnoObolelih(), vestObolelima.getBrHospitalizovanih(), vestObolelima.getBrNaRespiratorima(), vestObolelima.getDatumVremeObjavljivanja())==1;
        return uspeh?1:0;
    }
    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "delete from vestoObolelima where id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
