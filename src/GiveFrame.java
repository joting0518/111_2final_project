import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class GiveFrame extends JFrame {
	private JPanel provideInstruction, provideInput, provideInputOther, foodsInstruction, foodsInput, submitP, preview, finishP, buttonLine, all;
	private JScrollPane scrollPane;
	private JButton submit, finish;
	private JButton homePage;
	private JTextField detailAddress, description, foodNameInput, foodNumberInput;
	private JTextArea outputArea;
	private MainFrame mainframe;
	private Food food;
	private ArrayList<Food> foodList = new ArrayList<>();
	private User user;
	private JComboBox<String> comboBox;
	
	public void createProvideInput() {
		JLabel location = new JLabel("Where are you?");
		location.setBounds(10, 50, 500, 40);
		provideInstruction.add(location);
		
		JLabel big = new JLabel("離你最近的大樓");
		big.setBounds(10, 150, 500, 40);
		provideInput.add(big);
	    String[] buildings = {"請選擇", "綜和院館", "大智樓", "大仁樓", "大勇樓", "集英樓", "新聞館", "扇形廣場"
	    		, "資訊大樓", "電算中心", "行政大樓", "果夫樓", "四維堂", "志希樓", "研究大樓", "商學院"
	    		, "中正圖書館", "學思樓", "逸仙樓", "莊敬一舍", "莊敬二舍", "莊敬三舍", "莊敬九舍", "井塘樓"
	    		, "達賢圖書館", "莊敬一舍", "傳播學院", "道藩樓", "百年樓", "季陶樓", "國際大樓", "藝文中心大禮堂"
	    		, "藝文中心", "自強十舍", "自強九舍", "自強五、六舍", "自強七、八舍", "自強七、八舍", "自強一、二、三舍"
	    		, "山下其他", "山上其他", "其他"};
	    comboBox = new JComboBox<String>(buildings);
	    comboBox.setBounds(10, 100, 100, 40);
	    comboBox.setSelectedIndex(0);
	    provideInput.add(comboBox);
	    
	    JLabel small = new JLabel("詳細的地點敘述");
	    small.setBounds(10, 150, 500, 40);
		provideInput.add(small);
	    detailAddress = new JTextField(10);
	    detailAddress.setBounds(110, 100, 300, 40);
	    provideInput.add(detailAddress);
	    
	    JLabel other = new JLabel("備註");
	    other.setBounds(10, 150, 500, 40);
	    provideInputOther.add(other);
	    description = new JTextField(10);
		//description.addFocusListener(new JTextFieldHintListener(description,"input food detail here"));
		//https://blog.csdn.net/yanjingtp/article/details/79282365
		description.setBounds(10, 250, 300, 40);
		provideInputOther.add(description);
	}
	public void createFoodsInput() {
		JLabel instruction = new JLabel(String.format("請輸入你想要提供的食物"));
		instruction.setBounds(10, 150, 500, 40);
		foodsInstruction.add(instruction);
		
		JPanel foodsBox = new JPanel();
		foodsBox.setLayout(new FlowLayout());
		JLabel foodName = new JLabel("食物名稱");
		foodName.setBounds(10, 150, 500, 40);
		foodsBox.add(foodName);
		
		foodNameInput = new JTextField(10);
		foodNameInput.setBounds(10, 200, 500, 40);
		foodsBox.add(foodNameInput);
		
		JPanel foodsBox2 = new JPanel();
		foodsBox2.setLayout(new FlowLayout());
		JLabel foodNumber = new JLabel("食物數量");
		foodNumber.setBounds(10, 150, 500, 40);
		foodsBox2.add(foodNumber);
		
		foodNumberInput = new JTextField(10);
		foodNumberInput.setBounds(110, 200, 500, 40);
		foodsBox2.add(foodNumberInput);
		foodsInput.add(foodsBox);
		foodsInput.add(foodsBox2);
		
		submit = new JButton("Add Food");
		submit.setBounds(310,200,600,40);
		submit.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       			try {
       				if(foodNameInput.getText().isEmpty() || foodNumberInput.getText().isEmpty()) {
           				JOptionPane.showMessageDialog(GiveFrame.this, "You should input food name and food number.","Error",
               					JOptionPane.ERROR_MESSAGE);
           			}else{
           				String foodName = foodNameInput.getText();
    					int foodNum = Integer.parseInt(foodNumberInput.getText());
    					outputArea.append(String.format("%10s%10d\n", foodName, foodNum));
    					food = new Food(foodName,foodNum);//不更新dataBase
    					foodList.add(food);
    					foodNameInput.setText("");
    					foodNumberInput.setText("");
           			}
       			}catch(NumberFormatException e) {
       				JOptionPane.showMessageDialog(GiveFrame.this, "food number isn't number.","Error",
           					JOptionPane.ERROR_MESSAGE);
       			}
       		}
       	});
		submitP.add(submit);
	}
	public void createPreviewFinish() {
		outputArea = new JTextArea(10, 20);
		scrollPane = new JScrollPane(outputArea);
		scrollPane.setBounds(10,400,400,200);
		preview.add(scrollPane);
		
		finish = new JButton("Finish");
		finish.setBounds(10,400,600,40);
		finishP.add(finish);
		
		finish.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       			if(((String)comboBox.getSelectedItem()).equals("請選擇") || detailAddress.getText().isEmpty()) {
       				JOptionPane.showMessageDialog(GiveFrame.this, "You haven't selected general location or detail location is empty.","error",
           					JOptionPane.ERROR_MESSAGE);
   				}else {
   					user.provide(foodList, comboBox.getSelectedItem().toString(), detailAddress.getText(), description.getText());
   					foodList.removeAll(foodList);
   					outputArea.updateUI();
   					detailAddress.setText("");
   					description.setText("");
   					foodNameInput.setText("");
   					foodNumberInput.setText("");
   					int result = JOptionPane.showConfirmDialog(GiveFrame.this, "Provide success.\nYou can view your provide history on history page.\nDo you want to go back to homepage?"
						,"success", JOptionPane.OK_OPTION);
   					if (result == JOptionPane.OK_OPTION) {
   						// 用户点击了确认按钮，执行操作
   						// 目前回首頁再回到give輸入欄位不會清空
   						GiveFrame.this.setVisible(false);
   						mainframe.setVisible(true);	
   					}
       			}
       		}
       	});
	}
	public void createHomeButton() {
		homePage = new JButton("Home");
		homePage.setBounds(220, 330, 600, 40);
		buttonLine.add(homePage);
		homePage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				GiveFrame.this.setVisible(false);
				mainframe.setVisible(true);
			}
		});
	}
	public void createLayout() {
		provideInput.setBounds(0, 0, 470, 125);
		foodsInput.setBounds(0, 225, 470, 100);
		preview.setBounds(0, 350, 470, 100);
		finishP.setBounds(0, 350, 470, 50);
		buttonLine.setBounds(0, 350, 470, 50);
		all.add(Box.createVerticalStrut(5));
		all.add(provideInstruction);
		all.add(provideInput);
		all.add(provideInputOther);
		all.add(foodsInstruction);
		all.add(foodsInput);
		all.add(submitP);
		all.add(preview);
		all.add(finishP);
		all.add(buttonLine);
		all.setLayout(new BoxLayout(all, BoxLayout.Y_AXIS));
		this.add(all);
	}
    public GiveFrame(User user, MainFrame home) {
        super("Give");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        this.user = user;
        this.mainframe = home;
        provideInstruction = new JPanel();
        provideInput = new JPanel();
        provideInputOther = new JPanel();
        foodsInstruction = new JPanel();
        foodsInput = new JPanel();
        submitP = new JPanel();
        preview = new JPanel();
        finishP = new JPanel();
        buttonLine = new JPanel();
        all = new JPanel();
        createProvideInput();
        createFoodsInput();
        createPreviewFinish();
        createHomeButton();
        createLayout();
    }
}
