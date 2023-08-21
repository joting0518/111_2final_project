//import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProvideList extends List{
	String description;
	DataBaseManager dbManager = new DataBaseManager();
	public ProvideList(ArrayList<Food> foods, String generalLocation, String detailLocation, String description, int userID) {
		super(foods, generalLocation, detailLocation, userID);
		this.description = description;
		dbManager.createL(this);
		setID(dbManager.getListID(this));
		for(Food f:getFoods()) {
			System.out.println("adding foods to food sheet");
			f.setPID(getID());
			dbManager.createFood(f);
			f.setID(dbManager.getFoodID(f));
		}
	}
	public ProvideList(ArrayList<Food> foods, String generalLocation, String detailLocation, String description, int userID, String downLoad) {
		super(foods, generalLocation, detailLocation, userID);
		this.description = description;
		setID(dbManager.getListID(this));
	}
	public String getDescribe() {
		return description;
	}
	public void foodTaken(ArrayList<Food> foods) throws Exception{
		ArrayList<Food> pfoods = getFoods();
		for(int i=0; i<foods.size(); i++) {
			for(int j=0; j<pfoods.size(); j++)
				if(pfoods.get(j).getID() == foods.get(i).getID()) {
					if(pfoods.get(j).getAmount()>=foods.get(i).getAmount()) {
						pfoods.get(j).deduce(foods.get(i).getAmount());
						pfoods.get(j).setRsID(foods.get(i).getRSID());
						break;
					}else {
						throw new Exception(String.format("您預約的%s數量大於目前剩餘數量", pfoods.get(j).getName()));
					}
				}
		}
	}
	public void foodBack(ArrayList<Food> foods) {
		ArrayList<Food> pfoods = getFoods();
		for(int i=0; i<foods.size(); i++) {
			for(int j=0; j<pfoods.size(); j++)
				if(pfoods.get(j).getID() == foods.get(i).getID()) {
					pfoods.get(j).add(foods.get(i).getAmount());
					pfoods.get(j).setRsID(-1);
					break;
				}
		}
		dbManager.createL(this);
	}
	public JPanel createPanel() {//再修改
		JPanel all = new JPanel();
		JPanel left = super.createPanel();;
		JPanel right = new JPanel();
		JLabel L1 = new JLabel(String.format("Place: %s", getGLocation()));
		JLabel L2 = new JLabel(String.format("Description: %s", getDLocation()));

		right.add(L1);
		right.add(L2);
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		all.add(left);
		all.add(right);
		all.setLayout(new BoxLayout(all, BoxLayout.X_AXIS));//再修改
    	System.out.println("createProvidePanel");
		return all;
	}
}

//	public JPanel createGiveP() {
//		JPanel all = createGiveP();
//		Component[] components = all.getComponents();
//		for (Component c : components) {
//			if (c instanceof JButton) {
//				all.remove(c);
//				break; // stop iterating once we remove the button
//			}
//		}
//		JButton delete = new JButton("delete");//button 在Frame再設，因為histiryFrame、getFrame的按鈕長不一樣
//		delete.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
//		JButton edit = new JButton("edit");
//		edit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
//		all.add(delete);
//		all.add(edit);
//		return all;
//	}