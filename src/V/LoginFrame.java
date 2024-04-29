package V;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import M.UserManager;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;

public class LoginFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_username;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//double width = screenSize.getWidth();
		//double height = screenSize.getHeight();
		
		setTitle("Shoo Shop Login");
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 500, 300);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField_username = new JTextField();
		textField_username.setBounds(207, 76, 162, 19);
		contentPane.add(textField_username);
		textField_username.setColumns(10);
		
		passwordField = new JPasswordField();
		//passwordField.setEchoChar('•');
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					check();
				}
			}
		});
		passwordField.setBounds(207, 111, 162, 19);
		contentPane.add(passwordField);
		
		JLabel Ibl_username = new JLabel("Username");
		Ibl_username.setBounds(121, 79, 65, 13);
		contentPane.add(Ibl_username);
		
		JLabel lbl_password = new JLabel("Password");
		lbl_password.setBounds(121, 114, 65, 13);
		contentPane.add(lbl_password);
		
		JButton btn_login = new JButton("Login");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check();
				
			}
		});
		btn_login.setBounds(147, 162, 85, 21);
		contentPane.add(btn_login);
		
		JButton btn_exit = new JButton("Exit");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btn_exit.setBounds(252, 162, 85, 21);
		contentPane.add(btn_exit);
		
		JCheckBox chckbx_show_password = new JCheckBox("Show Password");
		chckbx_show_password.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbx_show_password.isSelected())
				{
					passwordField.setEchoChar((char)0);
				}
				else
				{
					passwordField.setEchoChar('•');
				}
				
			}
		});
		chckbx_show_password.setBounds(207, 135, 130, 21);
		contentPane.add(chckbx_show_password);
	}
	
	public void check()
	{
		if(UserManager.checkLogin(textField_username.getText().toLowerCase(),new String(passwordField.getPassword())))
		{
			MainFrame f = new MainFrame();
			f.setVisible(true);
			//LoginFrame.this.dispatchEvent(new WindowEvent(LoginFrame.this, WindowEvent.WINDOW_CLOSING));
			LoginFrame.this.setVisible(false);
		}
		else
		{
			JOptionPane.showMessageDialog(LoginFrame.this,"username or password are incorrect");
		}
	}
}
