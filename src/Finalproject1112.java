import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Finalproject1112/* extends JFrame*/{
//	private JPanel panelCont = new JPanel();
//	private HomePanel home;
//	private GetPanel get;
//	private GivePanel give;
//	private HistoryPanel history;
//	private JButton homeB = new JButton("home");
//	private JButton getB = new JButton("get");
//	private JButton giveB = new JButton("give");
//	private JButton historyB = new JButton("history");
//	private CardLayout c1 = new CardLayout();
//	
//	public Finalproject1112(User user) {
//		homeB.setBounds(10,150,100,40);
//		homeB.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				c1.show(panelCont, "1");
//			}
//		});
//		getB.setBounds(10,150,100,40);
//		getB.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				c1.show(panelCont, "2");
//			}
//		});
//		giveB.setBounds(10,150,100,40);
//		giveB.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				c1.show(panelCont, "3");
//			}
//		});
//		historyB.setBounds(10,150,100,40);
//		historyB.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				c1.show(panelCont, "4");
//			}
//		});
//		panelCont.setLayout(c1);
//		
//		home = new HomePanel(user, getB, giveB, historyB);
//		get = new GetPanel(user);
//		give = new GivePanel(user);
//		history = new HistoryPanel(user);
////		home.add(getB, giveB, historyB);
//		get.add(homeB, giveB, historyB);
//		give.add(homeB, getB, historyB);
//		history.add(homeB, getB, giveB);
//		
//		panelCont.add(home, "1");
//		panelCont.add(get, "2");
//		panelCont.add(give, "3");
//		panelCont.add(history, "4");
//		c1.show(panelCont, "1");
//		
//		this.add(panelCont);
//		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		this.pack();
//		this.setVisible(true);
//	}
    public static void main(String[] args) {
				String server = "jdbc:mysql://localhost:3306/";
				String database = "leftover_system"; // change to your own database
				String url = server + database + "?useSSL=false";
				String username = "root"; // change to your own user name
				String password = ""; // change to your own password　　　
				
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				LoginFrame logIn = new LoginFrame();
				logIn.setVisible(true);
				logIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
