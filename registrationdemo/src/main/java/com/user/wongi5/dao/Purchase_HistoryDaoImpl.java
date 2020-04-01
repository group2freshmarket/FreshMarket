package com.user.wongi5.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Purchase_History getPurchase(String email, String date) {
		String sql = "SELECT * FROM Purchase_History WHERE email=:email AND pur_date=:date";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		params.put("date", date);
		
		return (Purchase_History) jdbcTemplate.queryForObject(sql, params, new ItemMapper());
	}
	
	@Override
	public List<Purchase_History> getAllPurchases(String email) {
		String sql = "SELECT * FROM Purchase_History WHERE email =:email";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		
		return jdbcTemplate.query(sql, params,new RowMapper<Purchase_History>() {
			@Override
			public Purchase_History mapRow(ResultSet rs, int row) throws SQLException {
				Purchase_History p = new Purchase_History();
				p.setOrderId(rs.getInt("orderId"));
				p.setDate(rs.getString("pur_date"));
				p.setEmail(rs.getString("email"));
				p.setTotal_Price(rs.getDouble("total_Price"));
				return p;
			}
		});
	}
	
	private static final class ItemMapper implements RowMapper<Purchase_History>{

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
