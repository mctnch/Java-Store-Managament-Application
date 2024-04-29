package V;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import M.ProductManager;
import M.CustomerDB;
import M.CustomerManager;
import M.ProductDB;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JTextField textField_id;
	private JTextField textField_name;
	private JTextField textField_price_per_unit;
	private JTextField textField_description;
	private ImagePanel imagePanel;
	private ArrayList<ProductDB> list = new ArrayList<ProductDB> ();
	private JTable table;

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
					ProductFrame frame = new ProductFrame();
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
	public ProductFrame()
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
		setBounds(100, 100, 750, 513);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblProductId = new JLabel("PRODUCT ID");
		lblProductId.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblProductId.setBounds(447, 32, 101, 13);
		getContentPane().add(lblProductId);
		
		textField_id = new JTextField();
		textField_id.setEditable(false);
		textField_id.setColumns(10);
		textField_id.setBackground(new Color(247, 228, 9));
		textField_id.setBounds(545, 32, 160, 18);
		getContentPane().add(textField_id);
		
		JLabel lblProductName = new JLabel("PRODUCT NAME");
		lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblProductName.setBounds(447, 57, 101, 13);
		getContentPane().add(lblProductName);
		
		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(545, 57, 160, 18);
		getContentPane().add(textField_name);
		
		JLabel lblPricePerUnit = new JLabel("PRICE PER UNIT");
		lblPricePerUnit.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPricePerUnit.setBounds(447, 80, 101, 13);
		getContentPane().add(lblPricePerUnit);
		
		textField_price_per_unit = new JTextField();
		textField_price_per_unit.setColumns(10);
		textField_price_per_unit.setBounds(545, 80, 160, 18);
		getContentPane().add(textField_price_per_unit);
		
		JLabel lblProductDesc = new JLabel("PRODUCT DESC");
		lblProductDesc.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblProductDesc.setBounds(447, 103, 101, 13);
		getContentPane().add(lblProductDesc);
		
		textField_description = new JTextField();
		textField_description.setColumns(10);
		textField_description.setBounds(545, 103, 160, 18);
		getContentPane().add(textField_description);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 28, 405, 421);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(table.getSelectedRowCount()<1)
				{
					return ;
				}
				int index = table.getSelectedRow();
				BufferedImage img = list.get(index).product_image;
				if (img != null)
				{
					imagePanel.setImage(img);
				}
				else
				{
					imagePanel.setImage(null);
				}
				int id = Integer.parseInt(table.getValueAt(index, 0).toString());
				String product_name = table.getValueAt(index, 1).toString();
				String price_per_unit = table.getValueAt(index, 2).toString();
				String product_description = table.getValueAt(index, 3).toString();
				textField_id.setText("" + id);
				textField_name.setText("" + product_name);
				textField_price_per_unit.setText("" + price_per_unit);
				textField_description.setText("" + product_description);
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btn_save = new JButton("SAVE NEW");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!textField_price_per_unit.getText().trim().matches("[+-]?\\d+(\\.\\d+)?"))
				{
					JOptionPane.showMessageDialog(ProductFrame.this,"Number only Please!!");
					textField_price_per_unit.requestFocus();
					textField_price_per_unit.selectAll();
				}
				else
				{
					ProductDB x = new ProductDB();
					x.product_id = 0 ;
					x.product_name = textField_name.getText().trim();
					x.price_per_unit = Double.parseDouble(textField_price_per_unit.getText().trim());
					x.product_description = textField_description.getText().trim();
					x.product_image = (BufferedImage)imagePanel.getImage();
					ProductManager.saveNewProduct(x);
					textField_id.setText("");
					textField_name.setText("");
					textField_price_per_unit.setText("");
					textField_description.setText("");
					imagePanel.setImage(null);
					load();
					
				}
				
				
				
			}
		});
		btn_save.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btn_save.setBounds(447, 361, 84, 21);
		getContentPane().add(btn_save);
		
		JButton btn_edit = new JButton("EDIT");
		btn_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!textField_price_per_unit.getText().trim().matches("[+-]?\\d+(\\.\\d+)?"))
				{
					JOptionPane.showMessageDialog(ProductFrame.this,"Number only Please!!");
					textField_price_per_unit.requestFocus();
					textField_price_per_unit.selectAll();
				}
				else
				{
					ProductDB x = new ProductDB();
					x.product_id = Integer.parseInt(textField_id.getText().trim()); ;
					x.product_name = textField_name.getText().trim();
					x.price_per_unit = Double.parseDouble(textField_price_per_unit.getText().trim());
					x.product_description = textField_description.getText().trim();
					x.product_image = (BufferedImage)imagePanel.getImage();
					ProductManager.editProduct(x);
					textField_id.setText("");
					textField_name.setText("");
					textField_price_per_unit.setText("");
					textField_description.setText("");
					imagePanel.setImage(null);
					load();
					
				}
			}
		});
		btn_edit.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btn_edit.setBounds(447, 392, 84, 21);
		getContentPane().add(btn_edit);
		
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
					int input = JOptionPane.showConfirmDialog(null, "Are you sure to delete?","DELETE?",JOptionPane.YES_NO_OPTION);
			        // 0=yes, 1=no, 2=cancel
			        //System.out.println(input);
			        if(input == 0)
			        {
						int int_id = Integer.parseInt(id);
						ProductDB x = new ProductDB(int_id);
						ProductManager.deleteProduct(x);
						textField_id.setText("");
						textField_name.setText("");
						textField_price_per_unit.setText("");
						textField_description.setText("");
						imagePanel.setImage(null);
						load();
			        }

				} 
			}
		});
		btn_delete.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btn_delete.setBounds(447, 423, 84, 21);
		getContentPane().add(btn_delete);
		
		JButton btn_refresh = new JButton("REFRESH");
		btn_refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textField_id.setText("");
				textField_name.setText("");
				textField_price_per_unit.setText("");
				textField_description.setText("");
				imagePanel.setImage(null);
				load();
			}
		});
		btn_refresh.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btn_refresh.setBounds(620, 423, 85, 21);
		getContentPane().add(btn_refresh);
		
		imagePanel = new ImagePanel((Image) null);
		imagePanel.setBounds(469, 152, 219, 196);
		getContentPane().add(imagePanel);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
				fileChooser.setFileFilter(filter);
				int result = fileChooser.showOpenDialog(ProductFrame.this);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
				    try
					{
						BufferedImage bimg = ImageIO.read(selectedFile);
						imagePanel.setImage(bimg);
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(451, 126, 74, 18);
		getContentPane().add(btnNewButton);
		
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
}
