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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            LocalDateTime datumVremeObjavljivanja = rs.getTimestamp(index++).toLocalDateTime();

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
        String sql = "select v.id, v.brojObolelih, v.brojTestiranih, get_total_infected(v.id), v.brojHospitalizovanih" +
                ", v.brojNaRespiratorima, v.datumObjavljivanja " +
                "from vestoObolelima v " +
                "where v.id=? " +
                "order by v.id";
        VestObolelimaRowCallBackHandler rowCallBackHandler = new VestObolelimaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, id);
        if(rowCallBackHandler.getVesti().size() == 0){
            return null;
        }
        return rowCallBackHandler.getVesti().get(0);
    }

    @Override
    public VestObolelima findLastInserted() {
        String sql = "select v.id, v.brojObolelih, v.brojTestiranih, get_total_infected(v.id), v.brojHospitalizovanih" +
                ", v.brojNaRespiratorima, v.datumObjavljivanja " +
                "from vestoObolelima v " +
                "order by v.datumObjavljivanja desc " +
                "limit 1";

        VestObolelimaRowCallBackHandler rowCallBackHandler = new VestObolelimaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);

        List<VestObolelima> vesti = rowCallBackHandler.getVesti();
        if (!vesti.isEmpty()) {
            return vesti.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<VestObolelima> findAll() {
        String sql = "select v.id, v.brojObolelih, v.brojTestiranih, get_total_infected(v.id), v.brojHospitalizovanih" +
                ", v.brojNaRespiratorima, v.datumObjavljivanja " +
                "from vestoObolelima v " +
                "order by v.id";
        VestObolelimaRowCallBackHandler rowCallBackHandler = new VestObolelimaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);
        return rowCallBackHandler.getVesti();
    }

    @Transactional
    @Override
    public int save(VestObolelima vestObolelima) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "insert into vestoObolelima(brojObolelih, brojTestiranih, brojHospitalizovanih, brojNaRespiratorima, datumObjavljivanja) " +
                        "values(?,?,?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setInt(index++, vestObolelima.getBrObolelih());
                preparedStatement.setInt(index++, vestObolelima.getBrTestiranih());
                preparedStatement.setInt(index++, vestObolelima.getBrHospitalizovanih());
                preparedStatement.setInt(index++, vestObolelima.getBrNaRespiratorima());
                Timestamp timestamp = Timestamp.valueOf(vestObolelima.getDatumVremeObjavljivanja());
                preparedStatement.setString(index++, timestamp.toString());

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
        String sql = "update vestoObolelima set brojObolelih=?, brojTestiranih=?, brojHospitalizovanih=?" +
                ", brojNaRespiratorima=?, datumObjavljivanja=? where id=?";

        boolean uspeh = jdbcTemplate.update(sql, vestObolelima.getBrObolelih(), vestObolelima.getBrTestiranih(),
                vestObolelima.getBrHospitalizovanih(), vestObolelima.getBrNaRespiratorima(),
                Timestamp.valueOf(vestObolelima.getDatumVremeObjavljivanja()).toString(), vestObolelima.getId())==1;
        return uspeh?1:0;
    }
    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "delete from vestoObolelima where id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
