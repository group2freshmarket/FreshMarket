package com.user.wongi5.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.user.wongi5.model.Order_details;
import com.user.wongi5.model.Purchase_History;

public class ProcessDaoImpl implements ProcessDao {
	
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
	
	private SqlParameterSource getSqlParameterByModelOrder(Order_details o)
	{
		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
		if(o!=null)
		{
			parameterSource.addValue("itemId", o.getItemId());
			parameterSource.addValue("orderId", o.getOrderId());
			parameterSource.addValue("itemQty", o.getItemQty());
			parameterSource.addValue("itemPrice", o.getItemPrice());
		}
		return parameterSource;
	}
	
	@Override
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

	private SqlParameterSource getSqlParameterByModelPurchaseP(int id) {

		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
		parameterSource.addValue("orderId", id);
		return parameterSource;
	}
	
	@Override
	public Purchase_History getPurchase(int id) {
		String sql = "SELECT * FROM Purchase_History WHERE orderId=:id";
		
		return (Purchase_History) jdbcTemplate.queryForObject(sql, getSqlParameterByModelPurchaseP(id), new ItemMapperP());
	}

	@Override
	public boolean addOrder_details(Order_details o) {
		boolean res = true;
		try {
			String sql = "INSERT INTO Order_details(itemId, orderId, itemQty, itemPrice) VALUES(:itemId, :orderId, :itemQty, :itemPrice)";
			
			jdbcTemplate.update(sql, getSqlParameterByModelOrder(o));
			
		} catch (Exception e) {
			res = false;
			System.out.println(e.getMessage());
		}
		return res;
	}

	private SqlParameterSource getSqlParameterByModelPurchaseO(int id) {

		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
		parameterSource.addValue("detailId", id);
		return parameterSource;
	}
	
	@Override
	public Order_details getOrder(int id) {
		String sql = "SELECT * FROM Order_details WHERE detailId=:id";
		
		return (Order_details) jdbcTemplate.queryForObject(sql, getSqlParameterByModelPurchaseO(id), new ItemMapperO());
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
	
	private static final class ItemMapperO implements RowMapper<Order_details>{

		public Order_details mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			Order_details o = new Order_details();
			o.setDetailId(rs.getInt("detailId"));
			o.setItemId(rs.getInt("itemId"));
			o.setOrderId(rs.getInt("orderId"));
			o.setItemQty(rs.getInt("itemQty"));
			o.setItemPrice(rs.getDouble("itemPrice"));
			return o;
		}
		
	}

}
