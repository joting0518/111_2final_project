import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBaseManager {
	private String server = "jdbc:mysql://localhost:3306/";
	private String database = "leftover_system"; // change to your own database
	private String url = server + database + "?useSSL=false";
	private String username = "root"; // change to your own user name
	private String password = ""; // change to your own password
	private Connection conn;
	public DataBaseManager() {
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("DB Connectd");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void createFood(Food food) {
		try {
			String query = String.format("INSERT INTO `food` (ProvideID,Amount,Name)"
					+ " VALUES (%d,%d,'%s');"
					,food.getPID(),food.getAmount(),food.getName());
			Statement stat = conn.createStatement();
			stat.executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public int getFoodID(Food food) {
		String selectQuery = String.format("SELECT FoodID FROM `food` WHERE Name = '%s' AND Amount = %d AND ProvideID = %d", food.getName(), food.getAmount(), food.getPID());   
		int foodID = -1;
		try {
	        Statement stat = conn.createStatement();
			if (stat.execute(selectQuery)) {
				ResultSet result = stat.getResultSet();
				if (result.next()) {
					foodID = result.getInt(1);
				}
				result.close();
			}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		System.out.println("foodID= "+foodID);
	    return foodID;
	}
	
	public void createL(List l) {
		String insertQuery = "";
	    if (l instanceof ReserveList) {
	        ReserveList reserve = (ReserveList) l;
	        insertQuery = String.format("INSERT INTO reserve (UserID,General_Location,Detail_Location) VALUES (%d,'%s','%s')",
	        		reserve.getUserID(), reserve.getGLocation(), reserve.getDLocation());
	        
	    } else if (l instanceof ProvideList) {
	        ProvideList provide = (ProvideList) l;
	        insertQuery = String.format("INSERT INTO provide (UserID,General_Location,Detail_Location,Description) VALUES (%s,'%s','%s','%s')",
	               Integer.toString(provide.getUserID()), provide.getGLocation(),provide.getDLocation(),provide.getDescribe());
	    } else if (l instanceof ReceiveList) {
	        ReceiveList receive = (ReceiveList) l;
	        insertQuery = String.format("INSERT INTO receive(UserID,General_Location,Detail_Location) VALUES (%d,'%s','%s')",
	               receive.getUserID(), receive.getGLocation(),receive.getDLocation()); 
	    }
	    try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	public void deleteL(List l) {
		ArrayList<String> deleteQuery = new ArrayList<>();
		if (l instanceof ReserveList) {
			ReserveList reserve = (ReserveList) l;
			int ID = reserve.getID();
			for(int i=0; i<l.getFoods().size(); i++) {
				deleteQuery.add(String.format("UPDATE FROM `food` SET ReserveID = NULL, Amount = Amount+%d WHERE `ReserveID` = %d", l.getFoods().get(0).getAmount(), ID));
			}
			deleteQuery.add(String.format("DELETE FROM `reserve` WHERE `ReserveID` = %s",ID));
	        
		}else if (l instanceof ProvideList) {
			ProvideList provide = (ProvideList) l;
			int ID = provide.getID();
			ArrayList<Food> providedFood = provide.getFoods();
			for(int i=0; i<providedFood.size(); i++) {
				System.out.println("to be delete reserve: "+providedFood.get(i).getRSID());
				try {
		            Statement statement = conn.createStatement();
		            statement.executeUpdate(String.format("DELETE FROM `reserve` WHERE `ReserveID` = %d",providedFood.get(i).getRSID()));
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
			}
	        deleteQuery.add(String.format("DELETE FROM `provide` WHERE `ProvideID` = %s",ID));
		}else if (l instanceof ReceiveList) {
			ReceiveList receive = (ReceiveList) l;
			int ID = receive.getID();
	        deleteQuery.add(String.format("DELETE FROM `receive` WHERE `ReceiveID` = %s",ID));
		}
		try {
            Statement statement = conn.createStatement();
            for(String s:deleteQuery) {
            	statement.executeUpdate(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	public int getListID(List l) {//可能取錯-->解法：叫使用者在備註輸入時間
		String query = "";
		int ID = -1;
		if (l instanceof ProvideList) {
			ProvideList provide = (ProvideList)l;
			System.out.println(provide.getUserID()+" "+provide.getDescribe());
			query = String.format("SELECT provideID FROM `provide` WHERE UserID = %d AND Description = '%s'"
					,provide.getUserID(), provide.getDescribe());
		}
		else if (l instanceof ReceiveList) {
			ReceiveList receive = (ReceiveList)l;
			query = String.format("SELECT ReceiveID FROM `receive` WHERE UserID = %d AND TIMESTAMPDIFF(MINUTE, Time, NOW()) <= 1", receive.getUserID());
		}
		else if (l instanceof ReserveList) {
			ReserveList reserve = (ReserveList)l;
			query = String.format("SELECT ReserveID FROM `reserve` WHERE UserID = %d AND TIMESTAMPDIFF(MINUTE, Time, NOW()) <= 1", reserve.getUserID());
		}
		
		try {
			Statement stat = conn.createStatement();
			if (stat.execute(query)) {
				ResultSet result = stat.getResultSet();
				
				if (result.next()) {
					ID = result.getInt(1);
				}
				result.close();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		System.out.println("listID= "+ID);
		return ID;
	}

	public ArrayList<Food> getFoods (int ID, String type){
		String query = "";
		ArrayList <Food> foods = new ArrayList<Food>();
		if (type.equals("p")) {
			query = String.format("SELECT * FROM `food` WHERE provideID = %d", ID);
		}
		else if (type.equals("rc")) {
			query = String.format("SELECT * FROM `food` WHERE receiveID = %d", ID);
		}
		else if (type.equals("rs")) {
			query = String.format("SELECT * FROM `food` WHERE reserveID = %d", ID);
		}
		
		try {
			Statement stat = conn.createStatement();
			if (stat.execute(query)) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					Food f = new Food (result.getString("Name"),result.getInt("Amount"), result.getInt("FoodID"), result.getInt("ProvideID"), result.getInt("ReserveID"), result.getInt("ReceiveID"));
					foods.add(f);
					System.out.println(f.getID()+" "+f.getName()+" "+f.getAmount());
				}
				result.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return foods;
	}

	public ArrayList<ProvideList> getAllP() {
		String query = "SELECT * FROM `provide`";
		ArrayList <ProvideList> pl = new ArrayList<ProvideList>();
		try {
			Statement stat = conn.createStatement();
			if (stat.execute(query)) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					ProvideList p = new ProvideList(getFoods(result.getInt(1),"p"), result.getString("General_Location"),result.getString("Detail_Location"),result.getString("Description"),result.getInt("UserID"),"");
					pl.add(p);
				}
				result.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return pl;
	}
	public ProvideList getPRsed(ReserveList rs) {
		String query1 = String.format("SELECT ProvideID FROM `food` WHERE ReserveID = %d", rs.getID());
		ProvideList provider = null;
		try {
			Statement stat = conn.createStatement();
			if (stat.execute(query1)) {	
				ResultSet result1 = stat.getResultSet();
				int provideID = result1.getInt("ProvideID");
				String query2 = String.format("SELECT * FROM `provide` WHERE ProvideID = %d", provideID);
				if (stat.execute(query2)) {
					ResultSet result2 = stat.getResultSet();
					provider = new ProvideList(getFoods(result2.getInt(1),"p"),result2.getString(3),result2.getString(4),result2.getString(6),result2.getInt(2));
					result2.close();
				}
				result1.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return provider;
	}
	public void createUser(int ID, String password) {
		try {
			Statement stat = conn.createStatement();				
			String query = String.format("INSERT INTO `User` (UserID,Password)"
					+ " VALUES (%s,'%s');"
					, Integer.toString(ID), password);
			stat.executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public User getUser(int ID) {
		User user = null;
		try {
			String query = String.format("SELECT * FROM `User` WHERE UserID=%s", ID);
			Statement stat = conn.createStatement();
			if(stat.execute(query)) {
				ResultSet result = stat.getResultSet();
				if (result.next()) {
//	                System.out.println(result.getInt(1) + " " + result.getString(2));
	                user = new User(result.getInt(1), result.getString(2), get_user_providelist(ID), get_user_receivelist(ID), get_user_reservelist(ID));
	            }
				result.close();
			}
			stat.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	public ArrayList<ProvideList> get_user_providelist(int user_id){
		ArrayList<ProvideList> pl = new ArrayList<ProvideList> ();
		String query = String.format("SELECT * FROM `provide` WHERE UserID = %d", user_id);
		try {
			Statement stat = conn.createStatement();
			if (stat.execute(query)) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					ProvideList p = new ProvideList(getFoods(result.getInt(1),"p"),result.getString("General_Location"),result.getString("Detail_Location"),result.getString("Description"),user_id,"down");
					pl.add(p);
				}
				result.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return pl;
	}
	
	public ArrayList<ReserveList> get_user_reservelist(int user_id){
		ArrayList<ReserveList> rsl = new ArrayList<ReserveList> ();
		String query = String.format("SELECT * FROM `reserve` WHERE UserID = %d", user_id);
		try {
			Statement stat = conn.createStatement();
			if (stat.execute(query)) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					ReserveList rs = new ReserveList(getFoods(result.getInt(1),"rs"),result.getString(3),result.getString(4),user_id, "rs");
					rsl.add(rs);
				}
				result.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rsl;
	}
	
	public ArrayList<ReceiveList> get_user_receivelist(int user_id){
		ArrayList<ReceiveList> rcl = new ArrayList<ReceiveList> ();
		String query = String.format("SELECT * FROM `receive` WHERE UserID = %d", user_id);
		try {
			Statement stat = conn.createStatement();
			if (stat.execute(query)) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					ReceiveList rc = new ReceiveList(getFoods(result.getInt(1),"rc"),result.getString(3),result.getString(4),user_id,"rc");
					rcl.add(rc);
				}
				result.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rcl;
	}
	public void updatePF(ArrayList<Food> rsfoods, int rsID, String type) {
		if(type.equals("rs")) {
			for(Food f:rsfoods) {
				System.out.println(f.getID()+" "+f.getName()+" "+f.getAmount()+" "+f.getPID()+" "+f.getRSID()+" "+f.getRCID());
				String query = String.format("UPDATE `food` SET Amount=%d , ReserveID=%d WHERE FoodID=%d", f.getAmount(), f.getRSID(), f.getID());
				try {
					Statement stat = conn.createStatement();
					stat.executeUpdate(query);
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}else if(type.equals("rc")) {
			for(Food f:rsfoods) {
				System.out.println(f.getID()+" "+f.getName()+" "+f.getAmount()+" "+f.getPID()+" "+f.getRSID()+" "+f.getRCID());
				String query = String.format("UPDATE `food` SET ReceiveID=%d WHERE FoodID=%d", f.getRCID(), f.getID());
				try {
					Statement stat = conn.createStatement();
					stat.executeUpdate(query);
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
