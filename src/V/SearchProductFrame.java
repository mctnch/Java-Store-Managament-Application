package V;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import M.CustomerDB;
import M.ProductDB;
import M.ProductManager;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

public class SearchProductFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_search;
	private JTable table;
	private ArrayList<ProductDB> list;
	SelectProductI xSelectProductI;

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
					SearchProductFrame frame = new SearchProductFrame();
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
	public SearchProductFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField_search = new JTextField();
		textField_search.setColumns(10);
		textField_search.setBounds(10, 45, 153, 19);
		contentPane.add(textField_search);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 79, 416, 174);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
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
		
		JButton btn_select = new JButton("Select");
		btn_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRowCount() == 0)
				{
					JOptionPane.showMessageDialog(SearchProductFrame.this,"Please select a row.");
					return;
				}
				
				if(xSelectProductI != null)
				{
					if(list != null)
					{
						xSelectProductI.select(list.get(table.getSelectedRow()));
						
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
		list = ProductManager.getAllProduct();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("product_id");
		model.addColumn("product_name");
		model.addColumn("price_per_unit");
		model.addColumn("product_description");

		for (ProductDB c : list)
		{
			model.addRow(new Object[]
			{ c.product_id, c.product_name, c.price_per_unit, c.product_description});
		}
		table.setModel(model);
	}
	
	public void search()
	{
		list = ProductManager.searchProduct(textField_search.getText());
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("product_id");
		model.addColumn("product_name");
		model.addColumn("price_per_unit");
		model.addColumn("product_description");

		for (ProductDB c : list)
		{
			model.addRow(new Object[]
			{ c.product_id, c.product_name, c.price_per_unit, c.product_description});
		}
		table.setModel(model);
	}
	
	public void setSelectProductI(SelectProductI x)
	{
		xSelectProductI = x;
	}

}

interface SelectProductI  //design pattern
{
	public void select(ProductDB x);
	
}