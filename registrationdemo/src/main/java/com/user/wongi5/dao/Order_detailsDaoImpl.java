package com.user.wongi5.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.user.wongi5.model.Order_details;
import com.user.wongi5.model.Purchase_History;

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
	public List<Order_details> getAllDetails(int id) {
		String sql = "SELECT * FROM Order_details WHERE orderId =:orderId";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", id);
		
		return jdbcTemplate.query(sql, params,new RowMapper<Order_details>() {
			@Override
			public Order_details mapRow(ResultSet rs, int row) throws SQLException {
				Order_details o = new Order_details();
				o.setDetailId(rs.getInt("detailId"));
				o.setItemId(rs.getInt("itemId"));
				o.setOrderId(rs.getInt("orderId"));
				o.setItemQty(rs.getInt("itemQty"));
				o.setItemPrice(rs.getDouble("itemPrice"));
				return o;
			}
		});
	}

}
