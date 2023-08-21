//import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ReserveList extends List{
	Date reserveTime;
	DataBaseManager dbManager = new DataBaseManager();
	public ReserveList(ArrayList<Food> food, String generalLocation, String detailLocation, int user) {
		super(food, generalLocation, detailLocation, user);
//		reserveTime = new Date(0, 0, 0, 0, 30, 0);
		dbManager.createL(this);
		setID(dbManager.getListID(this));
	}
	public ReserveList(ArrayList<Food> food, String generalLocation, String detailLocation, int user, String rs) {
		super(food, generalLocation, detailLocation, user);
		setID(dbManager.getListID(this));
	}
	public Date getRTime() {
		return reserveTime;
	}
	public JPanel createPanel() {//再修改
		JPanel all = new JPanel();
		JPanel left = super.createPanel();;
		JPanel right = new JPanel();
		JLabel L1 = new JLabel(String.format("取餐地點: 大樓： %s 詳細地點： %s", getGLocation(), getDLocation()));

		right.add(L1);
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		all.add(left);
		all.add(right);
		all.setLayout(new BoxLayout(all, BoxLayout.X_AXIS));//再修改
    	System.out.println("createProvidePanel");
		return all;
	}
}
