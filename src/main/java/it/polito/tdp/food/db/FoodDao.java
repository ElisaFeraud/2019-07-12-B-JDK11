package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Collegamento;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	public void getVertici(Map<Integer,Food> idMap, int porzione) {
		String sql = "SELECT DISTINCT f.food_code, f.display_name "
				+ "FROM `portion` p, food f "
				+ "WHERE p.food_code=f.food_code "
				+ "GROUP BY f.food_code, f.display_name "
				+ "HAVING COUNT(p.portion_id)>=? "
				+ "ORDER BY f.display_name ASC ";
				
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, porzione);
	
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if(!idMap.containsKey(res.getInt("f.food_code"))) {
						Food food = new Food(res.getInt("f.food_code"),res.getString("f.display_name"));
					   idMap.put(res.getInt("f.food_code"), food);
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
		

		} catch (SQLException e) {
			e.printStackTrace();
	
		}

	}
	
	public List<Collegamento> getArchi(Map<Integer,Food> idMap, int porzioni){
		String sql = "SELECT DISTINCT p1.food_code, p2.food_code, AVG(p1.saturated_fats) AS grassi1, AVG(p2.saturated_fats) AS grassi2 "
				+ "FROM `portion` p1, `portion` p2 "
				+ "WHERE p1.food_code>p2.food_code  "
				+ "GROUP BY p1.food_code, p2.food_code "
				+ "HAVING (AVG(p1.saturated_fats)-AVG(p2.saturated_fats))<>0 AND COUNT(p1.portion_id)>=? AND COUNT(p2.portion_id)>=? ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1,porzioni);
			st.setInt(2,porzioni);
			List<Collegamento> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if(idMap.containsKey(res.getInt("p1.food_code")) && idMap.containsKey(res.getInt("p2.food_code"))) {
						Food food1 = idMap.get(res.getInt("p1.food_code"));
						Food food2 = idMap.get(res.getInt("p2.food_code"));
						Double grassi1 = res.getDouble("grassi1");
						Double grassi2 = res.getDouble("grassi2");
						Collegamento collegamento = new Collegamento(food1,food2,grassi1,grassi2);
						list.add(collegamento);
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
}
