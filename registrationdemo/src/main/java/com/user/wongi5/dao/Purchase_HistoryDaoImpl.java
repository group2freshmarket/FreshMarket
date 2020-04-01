package com.user.wongi5.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.user.wongi5.model.Purchase_History;

@Repository
public class Purchase_HistoryDaoImpl implements Purchase_HistoryDao {
	
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate=jdbcTemplate;
	}

	private SqlParameterSource getSqlParameterByModelPurchase(Purchase_History p)
	{
		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
		if(p!=null)
		{
			parameterSource.addValue("orderId", p.getOrderId());
			parameterSource.addValue("pur_date", p.getDate());
			parameterSource.addValue("email", p.getEmail());
			parameterSource.addValue("total_Price", p.getTotal_Price());
		}
		return parameterSource;
	}
	
	public boolean addPurchase(Purchase_History p) {
		boolean res = true;
		try {
			String sql = "INSERT INTO Purchase_History(pur_date, email, total_Price) VALUES(:pur_date, :email, :total_Price)";
			
			jdbcTemplate.update(sql, getSqlParameterByModelPurchase(p));
			
		} catch (Exception e) {
			res = false;
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	@Override
	public Purchase_History getPurchase(int id) {
		String sql = "SELECT * FROM Purchase_History WHERE orderId=:id";
		
		return (Purchase_History) jdbcTemplate.queryForObject(sql, getSqlParameterByModelPurchaseP(id), new ItemMapperP());
	}
	
	private SqlParameterSource getSqlParameterByModelPurchaseP(int id) {

		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
		parameterSource.addValue("orderId", id);
		return parameterSource;
	}
	
	private static final class ItemMapperP implements RowMapper<Purchase_History>{

		public Purchase_History mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			Purchase_History p = new Purchase_History();
			p.setOrderId(rs.getInt("orderId"));
			p.setDate(rs.getString("pur_date"));
			p.setEmail(rs.getString("email"));
			p.setTotal_Price(rs.getDouble("total_Price"));
			return p;
		}
		
	}

}
