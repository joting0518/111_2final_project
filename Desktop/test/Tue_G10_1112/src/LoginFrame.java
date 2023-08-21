import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {
	private JLabel homeText;
    private JLabel id;
    private JLabel password;
    private JButton register, login;
    private JTextField idInput;
    private JTextField passwordInput;
    private MainFrame mainframe;
    private UserManager userManager = new UserManager();
    private JPanel title, input, submit;

	public LoginFrame(){
		super("Log in");
		setSize(500, 400);
		createTitle();
		createInput();
		createSubmit();
		createLayout();
	}
	public void createTitle() {
		title = new JPanel();
		homeText = new JLabel("Welcome to share food platform!");
		homeText.setBounds(100, 5, 400, 40);
		homeText.setFont(new Font("Arial", Font.BOLD, 20));
		title.add(homeText);
	}
	public void createInput() {
		input = new JPanel();
		id = new JLabel("Your id:");
		id.setBounds(10, 50, 60, 40);
		input.add(id);

		final int FIELD_WIDTH = 10;
		idInput = new JTextField(FIELD_WIDTH);
		idInput.setBounds(60, 50, 100, 40);
		input.add(idInput);
		
		password = new JLabel("Your password:");
		password.setBounds(10, 100, 100, 40);
		input.add(password);
		
		passwordInput = new JTextField(FIELD_WIDTH);
		passwordInput.setBounds(110, 100, 100, 40);
		input.add(passwordInput);
	}
	public void createSubmit() {
		submit = new JPanel();
		register = new JButton("Register");
		register.setBounds(10,150,100,40);
		submit.add(register);
		
		register.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       			try{
       				userManager.register(idInput.getText(),passwordInput.getText());
       				JOptionPane.showMessageDialog(LoginFrame.this, "Register success.\nPlease login now","login",
           					JOptionPane.INFORMATION_MESSAGE);
       				idInput.setText("");
       				passwordInput.setText("");
       			}catch(Exception e){
       				JOptionPane.showMessageDialog(LoginFrame.this, e.getMessage(),"error",
           					JOptionPane.ERROR_MESSAGE);
       			}
        	}
       	});
		
		login = new JButton("Login");
		login.setBounds(10,150,100,40);
		submit.add(login);
		
		login.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       			try{
       				User user = userManager.logIn(idInput.getText(),passwordInput.getText());
       				setVisible(false);
       				mainframe = new MainFrame(user);
       		        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           			mainframe.setVisible(true);
       			}catch(Exception e){
//       				JOptionPane.showMessageDialog(LoginFrame.this, e.getStackTrace(),"login",
//           					JOptionPane.ERROR_MESSAGE);
       				e.printStackTrace();
       			}
        	}
       	});
	}
	public void createLayout() {
		input.setLayout(new FlowLayout());
		submit.setLayout(new FlowLayout());
		this.add(title);
		this.add(input);
		this.add(submit);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
	}
}
