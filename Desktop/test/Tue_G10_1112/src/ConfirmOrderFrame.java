import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ConfirmOrderFrame extends JFrame{
	private static final int FRAME_WIDTH = 400; 
	private static final int FRAME_HEIGHT = 300; 
	private static final int FIELD_WIDTH = 10;
	private JPanel itemPanel, quantityPanel, operatePanel, overallPanel; 
	
	private JLabel itemLabel, quantityLabel;//在createItemComp中
	private JComboBox<String> foodCombo;//在createItemComp中
	private JTextField quantityField;//在createItemComp中
	
	private JButton addButton, buyButton; 
	private boolean addcheck = false;
	
	private String detail = "";
	private JTextArea infoArea;
	private JScrollPane scrollPane;
	
	private ProvideList providelist;
	private ArrayList<Food> providedFoods;
	private ArrayList<Food> foodlist = new ArrayList<>();//人拿多少食物
	private User user;
	
    private JPanel buttonLine = new JPanel();
	private GetFrame getframe;
	
	public ConfirmOrderFrame(ProvideList providelist, User user, GetFrame getframe) { 
		this.setTitle("Confirm order");
		this.setSize(this.FRAME_WIDTH, this.FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.providelist = providelist;
        this.user = user;
        this.getframe = getframe;
        this.providedFoods = providelist.getFoods();
        createItemComp();
        createButton();
        createInfoArea();
        createHomeButton();
        createPanel();
	}
	public void createItemComp() {
		itemLabel = new JLabel("Item");
		quantityLabel = new JLabel("Quantity"); 
		foodCombo = new JComboBox();
		quantityField = new JTextField(FIELD_WIDTH);
		for(Food f:this.providelist.getFoods()) {
			foodCombo.addItem(f.getName());
		} 
	}
	public void createButton()  {
		addButton = new JButton("Add"); 
		buyButton = new JButton("Check out");
		addButton.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       			if(quantityField.getText().equals("0")) {
       				JOptionPane.showMessageDialog(null, "This number must above than 0",
       				      "Error!", JOptionPane.ERROR_MESSAGE);
       			}else {
       				try {
		       			int int_quantity = Integer.parseInt(quantityField.getText());
		       			String productName = (String) foodCombo.getSelectedItem();
		       			Food food = new Food(productName,int_quantity);
		       			food.setID(providedFoods.get(foodCombo.getSelectedIndex()).getID());
		       			food.setPID(providelist.getID());
		       			foodlist.add(food);
//		       			System.out.println(foodlist.toString());
		       			//alignment還可再加強，目前排不整齊
		    			String foodInfo =String.format("%-20s%-20d\n", food.getName(), food.getAmount());
		       			infoArea.append(foodInfo);
       				}catch(NumberFormatException e) {
       					JOptionPane.showMessageDialog(ConfirmOrderFrame.this, "食物數量必須是數字",
             				      "Error!", JOptionPane.ERROR_MESSAGE);
       				}
       			}
       			addcheck=true;
       		}
       	});
		buyButton.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       			if(addcheck==true) {
       				try {
       					addcheck=false;
        				user.reserve(foodlist,providelist);
        				int result = JOptionPane.showConfirmDialog(ConfirmOrderFrame.this
        						, "\n預約成功，詳細的食物領取地點請到Order頁面看。請問否返回get頁面"
        						,"food", JOptionPane.OK_OPTION);
           				if (result == JOptionPane.OK_OPTION) {
           					// 用户点击了确认按钮，执行操作
           					ConfirmOrderFrame.this.setVisible(false);
           					getframe.repaint();
           					getframe.setVisible(true);
       					}
        			} catch (Exception e) {
        				int result = JOptionPane.showConfirmDialog(ConfirmOrderFrame.this
        						, e.getMessage()+"\n預約失敗，請回到get頁面重新預約"
        						,"food", JOptionPane.OK_OPTION);
           				if (result == JOptionPane.OK_OPTION) {
           					// 用户点击了确认按钮，执行操作
           					//可以再改成更好的使用者流程(新增delete from chart按鈕)
           					foodlist.removeAll(foodlist);
           					ConfirmOrderFrame.this.setVisible(false);
           					getframe.setVisible(true);
       					}
        			}
       			}
       			else {
       				JOptionPane.showMessageDialog(ConfirmOrderFrame.this, "Add food you want!",
         				      "Error!", JOptionPane.ERROR_MESSAGE);
       			}
           	}
		});
	}
	public void createInfoArea(){
		infoArea = new JTextArea(20,30);
		detail = String.format("%-20s\n%-20s%-20s\n","預約清單:",  "食物名稱", "食物數量");
		for(Food f:foodlist) {
			detail+=String.format("%10s%10d\n", f.getName(), f.getAmount());
		}
		infoArea.append(detail);
		infoArea.setEditable(false);
		scrollPane = new JScrollPane(infoArea);
	}
	public void createHomeButton() {
		JButton homePage = new JButton("Back");
		homePage.setBounds(220, 330, 600, 40);
		buttonLine.add(homePage);
		homePage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				foodlist.removeAll(foodlist);
				ConfirmOrderFrame.this.setVisible(false);
				getframe.setVisible(true);
			}
		});
	}
	public void createPanel() {
		itemPanel = new JPanel(); 
		itemPanel.add(itemLabel); 
		itemPanel.add(foodCombo);
		quantityPanel = new JPanel(); 
		quantityPanel.add(quantityLabel); 
		quantityPanel.add(quantityField);
		operatePanel = new JPanel(new GridLayout(2, 2)); 
		operatePanel.add(itemPanel);
		operatePanel.add(quantityPanel); 
		operatePanel.add(addButton);
		operatePanel.add(buyButton);
		overallPanel = new JPanel(new BorderLayout()); 
		overallPanel.add(operatePanel, BorderLayout.NORTH); 
		overallPanel.add(infoArea, BorderLayout.CENTER);
		overallPanel.add(buttonLine, BorderLayout.SOUTH);
		this.add(overallPanel);
	}
}
