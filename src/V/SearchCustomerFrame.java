package V;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import M.CustomerDB;
import M.CustomerManager;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchCustomerFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_search;
	private JTable table;
	private ArrayList<CustomerDB> list;
	private JButton btn_select;
	SelectCustomerI xSelectCustomerI;
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
					SearchCustomerFrame frame = new SearchCustomerFrame();
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
	public SearchCustomerFrame()
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 79, 416, 174);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		textField_search = new JTextField();
		textField_search.setBounds(10, 45, 153, 19);
		contentPane.add(textField_search);
		textField_search.setColumns(10);
		
		JButton btn_search = new JButton("Search");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = textField_search.getText();
				if(s.isEmpty())
				{
					load();
				}
				else
				{
					search();
				}
				
			}
		});
		btn_search.setBounds(173, 44, 85, 21);
		contentPane.add(btn_search);
		
		btn_select = new JButton("Select");
		btn_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRowCount() == 0)
				{
					JOptionPane.showMessageDialog(SearchCustomerFrame.this,"Please select a row.");
					return;
				}
				
				if(xSelectCustomerI != null)
				{
					if(list != null)
					{
						xSelectCustomerI.select(list.get(table.getSelectedRow()));
						
						setVisible(false);
					}
				}
			}
		});
		btn_select.setBounds(341, 44, 85, 21);
		contentPane.add(btn_select);
		
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
	
	public void search()
	{
		list = CustomerManager.searchCustomer(textField_search.getText());
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
	
	public void setSelectCustomerI(SelectCustomerI x)
	{
		xSelectCustomerI = x;
	}

}

interface SelectCustomerI  //design pattern
{
	public void select(CustomerDB x);
	
}
