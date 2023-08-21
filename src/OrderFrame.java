import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

class OrderFrame extends JFrame {
	private User user;
	private ArrayList<JPanel> titlePanel = new ArrayList<>();
	private ArrayList<JPanel> buttonLine = new ArrayList<>();
	private JScrollPane scrollPane;//point to different scrollPane add to different panel
	private JButton homePage = new JButton();
	private MainFrame mainframe;
	private JPanel panelCont = new JPanel();//hold pages
	private CardLayout c1 = new CardLayout();
	private ArrayList<ProvideList> u_pl = new ArrayList<>();
	private ArrayList<ReserveList> u_reserve = new ArrayList<>();
	private ArrayList<ReceiveList> u_receive = new ArrayList<>();
	private JPanel reservePanel, receivePanel, providePanel;
	private JPanel reservePanelBig = new JPanel();//many reserve in scrollPanel+top+bottom
	private JPanel receivePanelBig = new JPanel();//many receive in scrollPanel+top+bottom
	private JPanel providePanelBig = new JPanel();//many provide in scrollPanel+top+bottom
	
    public OrderFrame(User user, MainFrame home) {
        super("OrderFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        this.user = user;
        this.mainframe = home;
        createTopPanel();
        createReservePanel();
		createReceivePanel();
        createProvidePanel();
        createHomeButton();
        addAllPanel();
		this.add(panelCont);
        setVisible(true);
    }
    public void createTopPanel() {
    	for(int i=0; i<3; i++) {
	    	//主畫面 一個combo box, default = reserve
	    	JPanel title = new JPanel();
	        title.setLayout(new BoxLayout(title, BoxLayout.X_AXIS)); 
	        
	        JLabel list = new JLabel("   List:");
			list.setBounds(10, 20, 100, 40);
			title.add(list);
			
			String[] showlist = {"Reserve list", "Receive list", "Provide list"};
		    JComboBox<String> comboBox = new JComboBox<String>(showlist);
		    comboBox.setBounds(120, 20, 50, 40);
		    title.add(comboBox);
		    
		    JButton change = new JButton("Change");
			change.setBounds(180, 20, 80, 40);
			title.add(change);
			
			change.addActionListener(new ActionListener() {
	 	       public void actionPerformed(ActionEvent event) {
	 	        		if(comboBox.getSelectedItem().toString().equals("Receive list")) {
	 	        			c1.show(panelCont, "3");
	 	        			comboBox.setSelectedIndex(1);
	 	        		}else if(comboBox.getSelectedItem().toString().equals("Provide list")){
	 	        			c1.show(panelCont, "2");
	 	        			comboBox.setSelectedIndex(2);
	 	        		}else if(comboBox.getSelectedItem().toString().equals("Reserve list")) {
	 	        			c1.show(panelCont, "1");
	 	        			comboBox.setSelectedIndex(0);
	 	        		}
	 	        	}
	 	    });
			
			JLabel ID = new JLabel("ID:"+user.getID()+"   ");
			ID.setBounds(450, 10, 40, 20);
			title.add(ID);
			titlePanel.add(title);
    	}
    }
    public void createReservePanel() {
    	//small panel : reserve
    	reservePanel = new JPanel();
    	u_reserve = user.getReserves();
    	if(u_reserve.size() == 0) {
//    		JOptionPane.showMessageDialog(OrderFrame.this, "No reserve now!","Text",
//    	   		JOptionPane.INFORMATION_MESSAGE);
    	 }else {
         	System.out.println("有reserve");
    	    for(ReserveList reserve : u_reserve) {
	    	    JPanel panel = new JPanel();
	    	    JButton cancel = new JButton("Cancel");
	    	    panel.add(reserve.createPanel());
	    	    panel.add(cancel);
	    	    cancel.addActionListener(new ActionListener() {
	    	    	public void actionPerformed(ActionEvent event) {
	    	    	   user.deleteList(reserve);
	    	    	   reservePanel.remove(panel);
	    	    	   reservePanel.updateUI();
	    	    	   scrollPane.updateUI();
    	    	   }
    	    	});
	    	    JButton receive = new JButton("已領取食物");
	    	    panel.add(receive);
	    	    receive.addActionListener(new ActionListener() {
	    	    	public void actionPerformed(ActionEvent event) {
		    	    	   user.receive(reserve);
		    	    	   reservePanel.remove(panel);
		    	    	   reservePanel.updateUI();
		    	    	   receivePanel.updateUI();
		    	    	   scrollPane.updateUI();
	    	    	   }
	    	    	});
	    	    reservePanel.add(panel);
    	    }
    	 }
    }
    public void createReceivePanel() {
    	//small panel : receive
    	receivePanel = new JPanel();
        u_receive = user.getReceives();
        if(u_receive.size() == 0) {
//        	JOptionPane.showMessageDialog(OrderFrame.this, "No reserve now!","Text",
//   					JOptionPane.INFORMATION_MESSAGE);
        }else {
        	System.out.println("有receive");
        	for(ReceiveList receive : u_receive) {
        		JPanel panel = new JPanel();
        		panel.add(receive.createPanel());
        		receivePanel.add(panel);
        	}
        }
    }
    public void createProvidePanel() {
    	//small panel : provide
    	providePanel = new JPanel();
        u_pl = user.getProvides();
        if(u_pl.size() == 0) {
//        	JOptionPane.showMessageDialog(OrderFrame.this, "You never provide before!","Text",
//   					JOptionPane.INFORMATION_MESSAGE);
        }else {
        	System.out.println("有provie");
        	for(ProvideList pl : u_pl) {
        		JPanel panel = new JPanel();
        		JButton cancel = new JButton("Delete");
        		panel.add(pl.createPanel());
        		panel.add(cancel);
        		cancel.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent event) {
        				user.deleteList(pl);
        				providePanel.remove(panel);
        				providePanel.updateUI();
        				scrollPane.updateUI();
//        				JOptionPane.showMessageDialog(OrderFrame.this, "You have cancelled the food you provide!"
//								,"Cancel",JOptionPane.INFORMATION_MESSAGE);
        			}
        		});
        		providePanel.add(panel);
        	}
        }
    }
    public void createHomeButton() {
    	for(int i=0; i<3; i++) {
    		JPanel home = new JPanel();
			homePage = new JButton("Home");
			homePage.setBounds(220, 330, 600, 40);
			home.add(homePage);
			homePage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					OrderFrame.this.setVisible(false);
					mainframe.setVisible(true);
				}
			});
			buttonLine.add(home);
    	}
	}
    public void addAllPanel() {
    	reservePanelBig.setLayout(new BorderLayout());
    	reservePanel.setLayout(new BoxLayout(reservePanel, BoxLayout.Y_AXIS));
    	scrollPane = new JScrollPane(reservePanel);
    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	reservePanelBig.add(titlePanel.get(0), BorderLayout.NORTH);
    	reservePanelBig.add(scrollPane, BorderLayout.CENTER);
    	reservePanelBig.add(buttonLine.get(0), BorderLayout.SOUTH);
    	
    	receivePanelBig.setLayout(new BorderLayout());
    	receivePanel.setLayout(new BoxLayout(receivePanel, BoxLayout.Y_AXIS));
    	scrollPane = new JScrollPane(receivePanel);
    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	receivePanelBig.add(titlePanel.get(1), BorderLayout.NORTH);
    	receivePanelBig.add(scrollPane, BorderLayout.CENTER);
    	receivePanelBig.add(buttonLine.get(1), BorderLayout.SOUTH);
    	
    	providePanelBig.setLayout(new BorderLayout());
    	providePanel.setLayout(new BoxLayout(providePanel, BoxLayout.Y_AXIS));
    	scrollPane = new JScrollPane(providePanel);
    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	providePanelBig.add(titlePanel.get(2), BorderLayout.NORTH);
    	providePanelBig.add(scrollPane, BorderLayout.CENTER);
    	providePanelBig.add(buttonLine.get(2), BorderLayout.SOUTH);
    	
    	panelCont.setLayout(c1);
    	
    	panelCont.add(reservePanelBig, "1");
    	panelCont.add(providePanelBig, "2");
    	panelCont.add(receivePanelBig, "3");
    	c1.show(panelCont, "1");
    }
}
//	private ArrayList<JPanel> panels = new ArrayList<>();
//	private ArrayList<JPanel> bigPanels = new ArrayList<>();
//        createPanels();
//    public void createPanels() {
//	    panels.add(new JPanel());
//		panels.add(new JPanel());
//		panels.add(new JPanel());
//    	for(int i=0; i<3; i++) {
//	    	ArrayList<List> u_l = user.getLists(i);
//	    	if(u_l.size() == 0) {
//	    		
//	    	}else {
//	    	    for(List l : u_l) {
//		    	    JPanel panel = new JPanel();
//		    	    JButton cancel = new JButton("Cancel");
//		    	    panel.add(l.createPanel());
//		    	    panel.add(cancel);
//		    	    cancel.addActionListener(new ActionListener() {
//		    	    	public void actionPerformed(ActionEvent event) {
//		    	    	   user.deleteList(l);
//		    	    	   panels.get(0).remove(panel);//if panel is not in panels.get(i), nothing would happen
//		    	    	   panels.get(1).remove(panel);
//		    	    	   panels.get(2).remove(panel);
//		    	    	   panels.get(0).updateUI();
//		    	    	   panels.get(1).updateUI();
//		    	    	   panels.get(2).updateUI();
//		    	    	   scrollPane.updateUI();
//		    	    	}
//	    	    	});
//					if(l instanceof ReserveList){
//						JButton take = new JButton("拿到食物了");
//						panel.add(take);
//						take.addActionListener(new ActionListener() {
//							public void actionPerformed(ActionEvent event) {
//								user.receive();
//								panels.get(0).updateUI();
//								panels.get(1).updateUI();
//								panels.get(2).updateUI();
//								scrollPane.updateUI();
//							}
//						});
//		    	    panels.get(i).add(panel);
//	    	    }
//	    	}
//    	}
//    }
//    public void addAllPanel() {
//    	panelCont.setLayout(c1);
//    	for(int i=0; i<3; i++) {
//    		bigPanels.add(new JPanel());
//	        bigPanels.get(i).setLayout(new BorderLayout());
//	        panels.get(i).setLayout(new BoxLayout(panels.get(i), BoxLayout.Y_AXIS));
//	        scrollPane = new JScrollPane(panels.get(i));
//	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//	        bigPanels.get(i).add(titlePanel.get(i), BorderLayout.NORTH);
//	        bigPanels.get(i).add(scrollPane, BorderLayout.CENTER);
//	        bigPanels.get(i).add(buttonLine.get(i), BorderLayout.SOUTH);
//	        panelCont.add(bigPanels.get(i), String.format("%d", i));
//    	}
//		c1.show(panelCont, "0");
//    }