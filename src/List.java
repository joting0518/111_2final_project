import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
//import java.sql.Date;

public abstract class List{
	int userID;
	int ID = 0;
	ArrayList<Food> foods;
	String generalLocation;
	String detailLocation;
	Date time;
	JPanel left;
	DataBaseManager dbManager;
	public List(ArrayList<Food> foods, String generalLocation, String detailLocation, int userID) {
		this.foods = foods;
		this.generalLocation = generalLocation;
		this.detailLocation = detailLocation;
		//fetch current time;
		this.userID = userID;
//		createPanel();
	}
	public int getUserID() {
		return userID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public ArrayList<Food> getFoods(){
		return foods;
	}
	public Date getDate() {
		return time;
	}
	public String getGLocation() {
		return generalLocation;
	}
	public String getDLocation() {
		return detailLocation;
	}
	public JPanel getPanel() {
		return left;
	}
	public JPanel createPanel() {//再修改
		left = new JPanel();//要傳到google上
		JLabel foodsLabel = new JLabel("Foods");
		JTextArea foodList = new JTextArea();
		String detail = String.format("%10s%10s\n", "Food Name", "Amount");
		for(Food f:foods) {
			detail+=String.format("%10s%10d\n", f.getName(), f.getAmount());
		}
		foodList.append(detail);
		foodList.setEditable(false);
		left.add(foodsLabel);
		left.add(foodList);
		//右邊的區塊Provide Reserve Receive 長不一樣，override來修
    	System.out.println("createListLeft");
		return left;
	}
}