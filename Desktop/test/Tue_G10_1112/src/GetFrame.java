import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class GetFrame extends JFrame {
	//need to add database
	private User user;
	private DataBaseManager dbManager = new DataBaseManager();
	private ArrayList<ProvideList> pl = new ArrayList<>();
    private JPanel jp = new JPanel();//many small provide panel
    private JScrollPane scrollPane;
    private JPanel buttonLine = new JPanel();
	private JButton homePage = new JButton();
	private MainFrame mainframe;
    
    public GetFrame(User user, MainFrame mainframe) {
        super("Get");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLayout(new BorderLayout());
        this.user = user;
        this.mainframe = mainframe;
        pl = dbManager.getAllP();
        //small panel
        if(pl.size() == 0) {
        	JLabel nofood = new JLabel("No food now!");
        	nofood.setBounds(70, 100, 400, 40);
        	nofood.setFont(new Font("Arial", Font.BOLD, 50));
    		add(nofood);
        }else {
        	for(ProvideList PL: pl) {
//        		if (PL != null) {
	        		JPanel panel = new JPanel();
	        		JButton take = new JButton("Take");
	        		panel.add(PL.createPanel());
	        		panel.add(take);
	        		take.addActionListener(new ActionListener() {
	        			public void actionPerformed(ActionEvent event) {
	        				ConfirmOrderFrame cof = new ConfirmOrderFrame(PL,user, GetFrame.this);	
	        				setVisible(false);
	        				cof.setVisible(true);
	        			}
	        		});
	        		jp.add(panel);
//        		}
        	}
        }
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(jp);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        createHomeButton();
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonLine, BorderLayout.SOUTH);
    }
    public void createHomeButton() {
		homePage = new JButton("Home");
		homePage.setBounds(220, 330, 600, 40);
		buttonLine.add(homePage);
		homePage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				GetFrame.this.setVisible(false);
				mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainframe.setVisible(true);
			}
		});
	}
}