package com.user.wongi5.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.user.wongi5.model.Order_details;

@Repository
public class Order_detailsDaoImpl implements Order_detailsDao{
	
NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate=jdbcTemplate;
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

	@Override
	public Order_details getOrder(int id) {
		String sql = "SELECT * FROM Order_details WHERE detailId=:id";
		
		return (Order_details) jdbcTemplate.queryForObject(sql, getSqlParameterByModelPurchaseO(id), new ItemMapperO());
	}
	
	private SqlParameterSource getSqlParameterByModelPurchaseO(int id) {

		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
		parameterSource.addValue("detailId", id);
		return parameterSource;
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
