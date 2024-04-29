package V;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import M.CustomerDB;
import M.CustomerManager;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_id;
	private JTextField textField_name;
	private JTable table;
	private JTextField textField_surname;
	private JTextField textField_phone;
	private ArrayList<CustomerDB> list;

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
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

					CustomerFrame frame = new CustomerFrame();
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
	public CustomerFrame()
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
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(425, 18, 45, 13);
		contentPane.add(lblNewLabel);

		textField_id = new JTextField();
		textField_id.setEditable(false);
		textField_id.setBackground(new Color(247, 228, 9));
		textField_id.setBounds(524, 10, 122, 18);
		contentPane.add(textField_id);
		textField_id.setColumns(10);

		JLabel lblName = new JLabel("name");
		lblName.setBounds(425, 41, 45, 13);
		contentPane.add(lblName);

		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(524, 35, 122, 18);
		contentPane.add(textField_name);

		JLabel lblSurname = new JLabel("surname");
		lblSurname.setBounds(425, 64, 67, 13);
		contentPane.add(lblSurname);

		textField_surname = new JTextField();
		textField_surname.setColumns(10);
		textField_surname.setBounds(524, 58, 122, 18);
		contentPane.add(textField_surname);

		JLabel lblPhone = new JLabel("phone");
		lblPhone.setBounds(425, 87, 45, 13);
		contentPane.add(lblPhone);

		textField_phone = new JTextField();
		textField_phone.setColumns(10);
		textField_phone.setBounds(524, 81, 122, 18);
		contentPane.add(textField_phone);

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(10, 15, 405, 332);
		contentPane.add(scrollPane);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (table.getSelectedRowCount() < 1)
				{
					return;
				}
				int index = table.getSelectedRow();
				int id = Integer.parseInt(table.getValueAt(index, 0).toString());
				String name = table.getValueAt(index, 1).toString();
				String surname = table.getValueAt(index, 2).toString();
				String phone = table.getValueAt(index, 3).toString();
				textField_id.setText("" + id);
				textField_name.setText("" + name);
				textField_surname.setText("" + surname);
				textField_phone.setText(phone);

			}
		});
		scrollPane.setViewportView(table);
		table.setBackground(Color.white);

		JButton btnNewButton = new JButton("SAVE NEW");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				String id = textField_id.getText().trim();
				String name = textField_name.getText().trim();
				String surname = textField_surname.getText().trim();
				String phone = textField_phone.getText().trim();

				StatusAndMessage sm = new StatusAndMessage();
				checkInputCorrection(id,name, surname, phone, sm, ChangeType.SAVE);
				if (sm.Status)
				{
					name = name.toLowerCase();
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					surname = surname.toLowerCase();
					surname = surname.substring(0, 1).toUpperCase() + surname.substring(1);
					CustomerDB x = new CustomerDB(0, name, surname, phone);
					CustomerManager.saveNewCustomer(x);
					textField_id.setText("");
					textField_name.setText("");
					textField_surname.setText("");
					textField_phone.setText("");
					load();
				} else
				{
					JOptionPane.showMessageDialog(new JFrame(), sm.ErrorMessage);
					// ErrorFrame f = new ErrorFrame(sm.ErrorMessage);
					// f.setVisible(true);

				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton.setBounds(423, 110, 84, 21);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("EDIT");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String id = textField_id.getText().trim();
				String name = textField_name.getText().trim();
				String surname = textField_surname.getText().trim();
				String phone = textField_phone.getText().trim();

				StatusAndMessage sm = new StatusAndMessage();
				checkInputCorrection(id,name, surname, phone, sm, ChangeType.EDIT);
				
				
				if (sm.Status)
				{ 
					int int_id = Integer.parseInt(id);
					String message  = "Do you want to edit the customer information below?";
					list = CustomerManager.CheckingOldDataBeforEdit(int_id);
					for (CustomerDB  old :list)
					{
						if(!old.name.equals(name))
						{
							message += String.format("\nchange name from %s -> %s",old.name,name);
						}
						if(!old.surname.equals(surname))
						{
							message += String.format("\nchange surname from %s -> %s",old.surname,surname);
						}
						if(!old.phone.equals(phone))
						{
							message += String.format("\nchange phone number from %s -> %s",old.phone,phone);
						}
					}
					
					int input = JOptionPane.showConfirmDialog(null,message,"EDIT?",JOptionPane.YES_NO_OPTION);
					
					
					if(input == 0)
				    {
						name = name.toLowerCase();
						name = name.substring(0, 1).toUpperCase() + name.substring(1);
						surname = surname.toLowerCase();
						surname = surname.substring(0, 1).toUpperCase() + surname.substring(1);
						CustomerDB x = new CustomerDB(int_id, name, surname, phone);
						CustomerManager.editCustomer(x);
						textField_id.setText("");
						textField_name.setText("");
						textField_surname.setText("");
						textField_phone.setText("");
						load();
				    }
					
				} 
				else
				{
					JOptionPane.showMessageDialog(new JFrame(), sm.ErrorMessage);
					// ErrorFrame f = new ErrorFrame(sm.ErrorMessage);
					// f.setVisible(true);
				}
				
				
			}
		});
		btnNewButton_1.setBounds(423, 141, 84, 21);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("DELETE");
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ 
				String id = textField_id.getText().trim();
				String name = textField_name.getText().trim();
				String surname = textField_surname.getText().trim();
				StatusAndMessage sm = new StatusAndMessage();
				
				if (id.isBlank())
				{
					sm.ErrorMessage = "Please select any rows for delete";
					JOptionPane.showMessageDialog(new JFrame(), sm.ErrorMessage);
				}
				else
				{
					int input = JOptionPane.showConfirmDialog(null, "Are you sure to delete the customer below?\nCustomer : "+name+" "+surname,"DELETE?",JOptionPane.YES_NO_OPTION);
			        // 0=yes, 1=no, 2=cancel
			        //System.out.println(input);
			        if(input == 0)
			        {
						int int_id = Integer.parseInt(id);
						CustomerDB x = new CustomerDB(int_id);
						CustomerManager.deleteCustomer(x);
						textField_id.setText("");
						textField_name.setText("");
						textField_surname.setText("");
						textField_phone.setText("");
						load();
			        }

				} 
					// ErrorFrame f = new ErrorFrame(sm.ErrorMessage);
					// f.setVisible(true);

				
			}
		});
		btnNewButton_1_1.setBounds(423, 172, 84, 21);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_2 = new JButton("REFRESH");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_id.setText("");
				textField_name.setText("");
				textField_surname.setText("");
				textField_phone.setText("");
				load();
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton_2.setBounds(524, 109, 85, 21);
		contentPane.add(btnNewButton_2);

		load();
	}

	public void load()
	{
		list = CustomerManager.getAllCustomer();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("id");
		model.addColumn("name");
		model.addColumn("surname");
		model.addColumn("phone");

		for (CustomerDB c : list)
		{
			model.addRow(new Object[]
			{ c.id, c.name, c.surname, c.phone });
		}
		table.setModel(model);
	}

	public StatusAndMessage checkInputCorrection(String id,String name, String  surname, String phone,	StatusAndMessage sm, ChangeType Type)
	{   
		sm.Status = true;
		sm.ErrorMessage = "";
		list = CustomerManager.getAllCustomer();
		
		if(id.isBlank() && name.isBlank() && surname.isBlank() && phone.isBlank())
		{
			sm.Status = false;
			sm.ErrorMessage = "Please fill data for any actions";
			return sm;
		}
		
		if( !id.isBlank() && Type == ChangeType.SAVE)
		{
			sm.Status = false;
			sm.ErrorMessage = "Existing customer can not save as new";
			return sm;
		}
		
		if (id.isBlank() && Type == ChangeType.EDIT)
		{
			sm.Status = false;
			sm.ErrorMessage = "Can not edit new Customer , please save information as new customer ";
			return sm;
		}
		
		if(name.isBlank())
		{
			sm.Status = false;
			sm.ErrorMessage = "Please enter customer name";
			return sm;
		}
		
		if(surname.isBlank())
		{
			sm.Status = false;
			sm.ErrorMessage = "Please enter customer surname";
			return sm;
		}
		

		if(Type == ChangeType.SAVE)
		{
			
			for (CustomerDB c : list )
			{
				if ( c.name.toUpperCase().equals(name.toUpperCase()) && c.surname.toUpperCase().equals(surname.toUpperCase()) )
				{
					sm.Status = false;
					sm.ErrorMessage = "This customer is already member. If the customer want to change their phone number please press EDIT button";
					return sm;
				}
				
			}
		}
		
		if(Type == ChangeType.EDIT)
		{

			for (CustomerDB c : list )
			{
				if (c.name.toUpperCase().equals(name.toUpperCase()) && c.surname.toUpperCase().equals(surname.toUpperCase()) && c.phone.equals(phone) )
				{
					sm.Status = false;
					sm.ErrorMessage = "Nothing changed for editing";
					return sm;
				}
				
			}
		}
		
		if (phone.isBlank())
		{
			sm.Status = false;
			sm.ErrorMessage = "Please enter customer phone";
			return sm;
		}

		boolean IsMatchPhoneFormat = Pattern.matches("[0]{1}[\\d]{9}", phone);
		
		if (phone.length() != 10 || !IsMatchPhoneFormat)
		{
			sm.Status = false;
			sm.ErrorMessage = "Phone number has incorrect format!";
			return sm;
		}
		
		return sm;
	}
}
