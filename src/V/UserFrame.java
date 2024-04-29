package V;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import M.CustomerDB;
import M.CustomerManager;
import M.UserDB;
import M.UserManager;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_id;
	private JTextField textField_username;
	private JTable table;
	private JPasswordField passwordField_2;
	private JPasswordField passwordField_1;
	private ArrayList<UserDB> list;
	private JComboBox comboBox ;

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
					UserFrame frame = new UserFrame();
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
	public UserFrame()
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
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 571, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 273, 230);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRowCount() < 1)
				{
					return;
				}
				int index = table.getSelectedRow();
				int id = Integer.parseInt(table.getValueAt(index, 0).toString());
				String username = table.getValueAt(index, 1).toString();
				String usertype = table.getValueAt(index, 2).toString();
				textField_id.setText("" + id);
				textField_username.setText("" + username);
				comboBox.getModel().setSelectedItem(usertype);
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btn_save = new JButton("SAVE NEW");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id = textField_id.getText().trim();
				String username = textField_username.getText().trim();
				String password = new String(passwordField_1.getPassword());
				String re_password = new String(passwordField_2.getPassword());
				String usertype = comboBox.getSelectedItem().toString();
				if(password.equals(re_password))
				{
					if(verifyDuplicateUsername(username))
					{
						username = username.toLowerCase();
						UserDB x = new UserDB(0, username, password, usertype);
						UserManager.saveNewUser(x);
						textField_id.setText("");
						textField_username.setText("");
						passwordField_1.setText("");
						passwordField_2.setText("");
						comboBox.getModel().setSelectedItem("user");
						load();
					}
					else
					{
						JOptionPane.showMessageDialog(UserFrame.this,"Duplicate user name");
					}
					
					
				}
				else
				{
					JOptionPane.showMessageDialog(UserFrame.this, "Passwords are not the same!!");
				}

				
			}
		});
		btn_save.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btn_save.setBounds(303, 266, 84, 21);
		contentPane.add(btn_save);
		
		JLabel lbl_id = new JLabel("id");
		lbl_id.setBounds(303, 24, 45, 13);
		contentPane.add(lbl_id);
		
		JLabel lblUserName = new JLabel("username");
		lblUserName.setBounds(303, 47, 67, 13);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(303, 70, 83, 13);
		contentPane.add(lblPassword);
		
		JLabel lblUsertype = new JLabel("user type");
		lblUsertype.setBounds(303, 176, 83, 13);
		contentPane.add(lblUsertype);
		
		textField_id = new JTextField();
		textField_id.setEditable(false);
		textField_id.setColumns(10);
		textField_id.setBackground(new Color(247, 228, 9));
		textField_id.setBounds(395, 15, 122, 18);
		contentPane.add(textField_id);
		
		textField_username = new JTextField();
		textField_username.setColumns(10);
		textField_username.setBounds(395, 41, 122, 18);
		contentPane.add(textField_username);
		
		JButton btn_delete = new JButton("DELETE");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id = textField_id.getText().trim();
				StatusAndMessage sm = new StatusAndMessage();
				
				if (id.isBlank())
				{
					sm.ErrorMessage = "Please select any rows for delete";
					JOptionPane.showMessageDialog(new JFrame(), sm.ErrorMessage);
				}
				else
				{
					int input = JOptionPane.showConfirmDialog(null, "Are you sure to delete this user?","DELETE?",JOptionPane.YES_NO_OPTION);
			        // 0=yes, 1=no, 2=cancel
			        //System.out.println(input);
			        if(input == 0)
			        {
						int int_id = Integer.parseInt(id);
						UserDB x = new UserDB(int_id);
						UserManager.deleteUser(x);
						textField_id.setText("");
						textField_username.setText("");
						passwordField_1.setText("");
						passwordField_2.setText("");
						comboBox.getModel().setSelectedItem("user");
						load();
			        }

				} 
			}
		});
		btn_delete.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btn_delete.setBounds(303, 297, 84, 21);
		contentPane.add(btn_delete);
		
		JLabel lblNewLabel = new JLabel("re-password");
		lblNewLabel.setBounds(303, 122, 85, 13);
		contentPane.add(lblNewLabel);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(395, 67, 122, 19);
		contentPane.add(passwordField_1);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(395, 119, 122, 19);
		contentPane.add(passwordField_2);
		
		JCheckBox chckbx_show_password_1 = new JCheckBox("Show Password");
		chckbx_show_password_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbx_show_password_1.isSelected())
				{
					passwordField_1.setEchoChar((char)0);
				}
				else
				{
					passwordField_1.setEchoChar('•');
				}
			}
		});
		chckbx_show_password_1.setBounds(395, 92, 122, 21);
		contentPane.add(chckbx_show_password_1);
		
		JCheckBox chckbx_show_password_2 = new JCheckBox("Show Password");
		chckbx_show_password_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbx_show_password_2.isSelected())
				{
					passwordField_2.setEchoChar((char)0);
				}
				else
				{
					passwordField_2.setEchoChar('•');
				}
			}
		});
		chckbx_show_password_2.setBounds(395, 144, 122, 21);
		contentPane.add(chckbx_show_password_2);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"admin", "user"}));
		comboBox.getModel().setSelectedItem("user");
		comboBox.setBounds(393, 172, 88, 21);
		
		contentPane.add(comboBox);
		
		load();
	}
	
	public void load()
	{
		list = UserManager.getAllUser();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("user_id");
		model.addColumn("username");
		model.addColumn("user_type");

		for (UserDB c : list)
		{
			model.addRow(new Object[]
			{ c.id, c.username, c.usertype});
		}
		table.setModel(model);
	}
	
	public boolean verifyDuplicateUsername(String username)
	{
		for(UserDB u : list)
		{
			if(u.username.toLowerCase().equals(username.toLowerCase()))
			{
				return false;
			}
		}
		return true;
	}
}
