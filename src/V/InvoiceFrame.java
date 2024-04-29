package V;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import M.CompanyInfoDB;
import M.CompanyInfoManager;
import M.CustomerDB;
import M.ProductDB;
import M.ProductManager;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class InvoiceFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JPanel panel;
	public JTable table;
	public JScrollPane scrollPane ;
	private String CompanyInfo; 
	CustomerDB xCustomerDB;
	ProductDB xProductDB;
	DefaultTableModel model = new DefaultTableModel();
	
	private ArrayList<InvoiceDetail> detailList = new ArrayList<InvoiceDetail> ();

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
				
				try
				{
					InvoiceFrame frame = new InvoiceFrame();
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
	public InvoiceFrame()
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
		
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

				panel.setBounds(10, 60, getWidth()-35, getHeight()-110);
			}
		});
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 41, getWidth()-35, getHeight()-110);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lbl_invoice = new JLabel("INVOICE");
		lbl_invoice.setBounds(262, 23, 68, 13);
		panel.add(lbl_invoice);
		
		JLabel lbl_details = new JLabel("Details");
		lbl_details.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbl_details.setBounds(34, 190, 45, 13);
		panel.add(lbl_details);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 213, 546, 302);
		panel.add(scrollPane);
		
		model.addColumn("no.");
		model.addColumn("image");
		model.addColumn("product_name");
		model.addColumn("price_per_unit");
		model.addColumn("quantity");
		model.addColumn("total_price");
		
		table = new JTable(model);
		table.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    String columnName = table.getColumnName(column);
                    if (columnName == "quantity")
                    {
                    	InvoiceDetail id = detailList.get(row);
                    	id.qty = Integer.parseInt(table.getValueAt(row, column).toString());
                    	id.total_price = id.price_per_unit *id.qty;
                    	detailList.set(row, id);
                    	load();
                    }
                    else
                    {
                    	load();
                    }
                    
                }
            }
        });
	
		scrollPane.setViewportView(table);
		
		JLabel lbl_customer_info = new JLabel("");
		lbl_customer_info.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbl_customer_info.setBounds(119, 155, 252, 13);
		panel.add(lbl_customer_info);
		setLocationRelativeTo(null);

		contentPane.setLayout(null);
		
		JButton btn_select_customer = new JButton("Select Customer");
		btn_select_customer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchCustomerFrame f = new SearchCustomerFrame();
				f.setSelectCustomerI(new SelectCustomerI()
				{
					
					@Override
					public void select(CustomerDB x)
					{
						// TODO Auto-generated method stub
						xCustomerDB = x;
						String s = String.format("%s %s (%s) (id : %d)", x.name,x.surname,x.phone,x.id);
						
						lbl_customer_info.setText(s);
					}
				});
					
				f.setVisible(true);
			}
		});
		btn_select_customer.setBounds(10, 10, 173, 21);
		contentPane.add(btn_select_customer);
		
		JButton btn_select_product = new JButton("Select Product");
		btn_select_product.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchProductFrame f = new SearchProductFrame();
				f.setSelectProductI(new SelectProductI()
				{
					
					@Override
					public void select(ProductDB x)
					{
						// TODO Auto-generated method stub
						xProductDB = x;
						//String s = String.format("%s %s (%s) (id : %d)", x.name,x.surname,x.phone,x.id);
						
						setDetail(xProductDB);
					}
				});
					
				f.setVisible(true);
			}
		});
		btn_select_product.setBounds(193, 10, 173, 21);
		contentPane.add(btn_select_product);
		
		JButton btn_save = new JButton("Save");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_save.setBounds(415, 10, 85, 21);
		contentPane.add(btn_save);
		
		JButton btn_print = new JButton("Print");
		btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setPrintable(new InvoicePrint(InvoiceFrame.this));
				boolean doPrint = job.printDialog();
				if (doPrint)
				{
					try {
						job.print();
					}
					catch (PrinterException e1) {
						
					}
				}
			}
		});
		btn_print.setBounds(510, 10, 85, 21);
		contentPane.add(btn_print);
		
		CompanyInfoDB x = CompanyInfoManager.getCompanyInfo();
		CompanyInfo = String.format("%s %s  %s Alley, %s, %s, %s %d ",x.company_name,x.address_no,x.alley,x.sub_district,x.district,x.province,x.postal_code);
		
		CompanyInfoDB x2 = CompanyInfoManager.getCompanyInfo();
		String CompanyInfo2 = String.format("Phone : %s Email : %s ",x.phone,x.email);
		
		JLabel textArea_company_info = new JLabel();
		textArea_company_info.setFont(new Font("Monospaced", Font.BOLD, 13));
		textArea_company_info.setBounds(34, 78, 921, 22);
		panel.add(textArea_company_info);
		textArea_company_info.setText(CompanyInfo);
		

		JLabel textArea_company_info_2 = new JLabel("");
		textArea_company_info_2.setFont(new Font("Monospaced", Font.BOLD, 13));
		textArea_company_info_2.setBounds(34, 110, 480, 13);
		panel.add(textArea_company_info_2);
		textArea_company_info_2.setText(CompanyInfo2);
		
		
		JLabel textArea_date = new JLabel();
		textArea_date.setBounds(34, 46, 296, 22);
		panel.add(textArea_date);
		textArea_date.setText("Date : " +new SimpleDateFormat().format(Calendar.getInstance().getTime()).toString());
		
		JLabel lbl_customer = new JLabel("BILL TO");
		lbl_customer.setFont(new Font("Tahoma", Font.BOLD, 10));
		lbl_customer.setBounds(34, 155, 78, 13);
		panel.add(lbl_customer);
		
		
		
		
	}
	
	public void setDetail(ProductDB xProduct)
	{
		InvoiceDetail x = new InvoiceDetail();
		x.no = detailList.size()+1 ;
		x.productName = xProduct.product_name;
		x.price_per_unit = xProduct.price_per_unit;
		x.qty = 1 ;
		x.total_price = x.price_per_unit *x.qty;
		x.product = xProduct;
		
		detailList.add(x);
		load();
	}

	
	public void load()
	{
		//list = ProductManager.getAllProduct();
		model.setNumRows(0);

		for (InvoiceDetail c : detailList)
		{
			model.addRow(new Object[]
			{ c.no, c.product.product_image,c.productName, c.price_per_unit, c.qty,c.total_price});
		}
		table.setModel(model);
		
		table.getColumn("image").setCellRenderer(new InvoiceDetailTableRenderer());
		
		for (int i=0; i< table.getRowCount(); i++)
		{
			table.setRowHeight(i,120);
		}
	}
}


class InvoiceDetail // private class;
{
	public int  no;
	public String productName;
	public int qty;
	public double price_per_unit;
	public double total_price;
	
	public ProductDB product;
}

class InvoiceDetailTableRenderer extends DefaultTableCellRenderer implements TableCellRenderer
{
	public Component getTableCellRendererComponent
				(JTable table, Object value , boolean isSelected, boolean hasFocus , int row ,int col)
	{
		ImageComponent i = new ImageComponent();
		i.image = (BufferedImage) value;
		return i;
	}
}

class ImageComponent extends JComponent
{
	public BufferedImage image;
	
	public void paint(Graphics g)
	{
		if (image != null)
		{
			g.drawImage(image,0,0,120,120,0,0,image.getWidth(),image.getHeight(),this);
		}
	}
}

class InvoicePrint implements Printable
{
	InvoiceFrame xframe;
	public InvoicePrint(InvoiceFrame frame)
	{
		xframe = frame;
	}
	@Override
	public int  print(Graphics g, PageFormat pf, int page) throws PrinterException
	{
		if(page > 0 )
		{
			return NO_SUCH_PAGE;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(),pf.getImageableY());
		
		for(int i=0 ; i < xframe.panel.getComponentCount() ; i++)
		{
			Component c = xframe.panel.getComponent(i);
			if(c instanceof JLabel)
			{
				JLabel l = (JLabel)c;
				g2d.setFont(l.getFont());
				g2d.drawString(l.getText(),
				(int)(l.getLocation().getX())
				,(int)(l.getLocation().getY()+l.getHeight()));
				
			}
		}
		int x =10 ;
		int y = (int)(xframe.scrollPane.getLocation().getY())+40;
		for(int i =0 ; i < xframe.table.getColumnCount(); i++)
		{
			g2d.setFont(xframe.table.getFont());
			g2d.drawString(xframe.table.getColumnName(i),
			x,
			y);
		    x += xframe.table.getColumn(xframe.table.getColumnName(i)).getWidth();	
		}
		
		
		y += 40;
		
		for (int j=0 ;j < xframe.table.getRowCount(); j++)
		{
			x =10 ;
			for(int i =0 ; i < xframe.table.getColumnCount(); i++)
			{
				if (xframe.table.getColumnName(i).equals("image"))
				{
					BufferedImage image = (BufferedImage)xframe.table.getValueAt(j,i);
					if( image != null)
					{
						g.drawImage(image,x,y-20,x+120,y+120-20,0,0,image.getWidth(),image.getHeight(),null);
					}
				}
				else
				{
					g2d.setFont(xframe.table.getFont());
					g2d.drawString(""+xframe.table.getValueAt(j,i),x,y);
				    	
				
				}
				x += xframe.table.getColumn(xframe.table.getColumnName(i)).getWidth();
			}
			y += xframe.table.getRowHeight(j);
		}
		
		
		return PAGE_EXISTS;
		
	}
	
}
