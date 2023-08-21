import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
	private GiveFrame giveFrame;
    private GetFrame getFrame;
    private OrderFrame orderFrame;
    private JLabel homeText;
    private JLabel instruction;
    private JLabel purpose;
    private JLabel contact;
    private JButton give;
    private JButton get;
    private JButton order;
    private User user;

    public MainFrame(User user) {
    	
        super("Home Page");
        setSize(500, 400);
        setLayout(null);

        get = new JButton("Get food");
        get.setBounds(110, 330, 100, 40);
        add(get);

        give = new JButton("Give food");
        give.setBounds(210, 330, 100, 40);
        add(give);
        
        order = new JButton("History");
        order.setBounds(310, 330, 100, 40);
        add(order);
        
        homeText = new JLabel("Welcome to share food platform!");
		homeText.setBounds(100, 5, 400, 40);
		homeText.setFont(new Font("Arial", Font.BOLD, 20));
		add(homeText);
        
        instruction = new JLabel("Instruction: Share your extra food with those in need.");
        instruction.setBounds(10, 50, 500, 40);
        add(instruction);

        purpose = new JLabel("Purpose: Reduce food waste and help those in need.");
        purpose.setBounds(10, 100, 500, 40);
        add(purpose);

        contact = new JLabel("Contact us: sharefood@example.com");
        contact.setBounds(10, 150, 500, 40);
        add(contact);
        
        this.user = user;

       	give.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       				setVisible(false);
       				giveFrame = new GiveFrame(user, MainFrame.this);
       				giveFrame.setVisible(true);
        	}
       	});

        get.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       				setVisible(false);
       				getFrame = new GetFrame(user, MainFrame.this);
       				getFrame.setVisible(true);
       		}
       	});

        order.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
//       				orderFrame.createProvidePanel();
       				setVisible(false);
       				orderFrame = new OrderFrame(user, MainFrame.this);
       				orderFrame.setVisible(true);
       		}
       	});
        
        
        
    }
}
