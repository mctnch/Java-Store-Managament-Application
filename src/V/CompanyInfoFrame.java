package V;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import M.CompanyInfoDB;
import M.CompanyInfoManager;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CompanyInfoFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_company_name;
	private JTextField textField_phone;
	private JTextField textField_email;
	CompanyInfoDB xCompanyInfoDB;
	private JLabel lblNewLabel_1;
	private JTextField textField_alley;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField textField_sub_district;
	private JLabel lblNewLabel_4;
	private JTextField textField_district;
	private JLabel lblNewLabel_5;
	private JTextField textField_province;
	private JLabel lblNewLabel_6;
	private JTextField textField_postal_code;
	private JTextField textField_address_no;

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
					CompanyInfoFrame frame = new CompanyInfoFrame();
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
	public CompanyInfoFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField_company_name = new JTextField();
		textField_company_name.setBounds(181, 46, 236, 19);
		contentPane.add(textField_company_name);
		textField_company_name.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Company Name");
		lblNewLabel.setBounds(31, 49, 94, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblAdress = new JLabel("Adress");
		lblAdress.setBounds(31, 90, 57, 13);
		contentPane.add(lblAdress);
		
		textField_address_no = new JTextField();
		textField_address_no.setColumns(10);
		textField_address_no.setBounds(181, 87, 84, 19);
		contentPane.add(textField_address_no);
		
		JLabel lblNewLabel_1_1 = new JLabel("Phone");
		lblNewLabel_1_1.setBounds(31, 182, 94, 13);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Email");
		lblNewLabel_1_2.setBounds(31, 211, 57, 13);
		contentPane.add(lblNewLabel_1_2);
		
		textField_phone = new JTextField();
		textField_phone.setColumns(10);
		textField_phone.setBounds(181, 179, 236, 19);
		contentPane.add(textField_phone);
		
		textField_email = new JTextField();
		textField_email.setColumns(10);
		textField_email.setBounds(181, 208, 236, 19);
		contentPane.add(textField_email);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompanyInfoDB cc = new CompanyInfoDB();
				cc.company_name = textField_company_name.getText().trim();
				cc.address_no = textField_address_no.getText().trim();
				cc.alley = textField_alley.getText().trim();
				cc.sub_district = textField_sub_district.getText().trim();
				cc.district = textField_district.getText().trim();
				cc.province = textField_province.getText().trim();
				cc.postal_code = Integer.parseInt(textField_postal_code.getText().trim());
				cc.phone = textField_phone.getText().trim();
				cc.email = textField_email.getText().trim();
				CompanyInfoManager.editCompanyInfo(cc);
				loadData();
				JOptionPane.showMessageDialog(CompanyInfoFrame.this, "Edit company information complete.");
			}
		});
		btnNewButton.setBounds(180, 250, 85, 21);
		contentPane.add(btnNewButton);
		
		lblNewLabel_1 = new JLabel("Address No.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_1.setBounds(121, 90, 57, 13);
		contentPane.add(lblNewLabel_1);
		
		textField_alley = new JTextField();
		textField_alley.setColumns(10);
		textField_alley.setBounds(333, 87, 84, 19);
		contentPane.add(textField_alley);
		
		lblNewLabel_2 = new JLabel("Alley");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_2.setBounds(275, 90, 57, 13);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Sub-District");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_3.setBounds(121, 119, 57, 13);
		contentPane.add(lblNewLabel_3);
		
		textField_sub_district = new JTextField();
		textField_sub_district.setColumns(10);
		textField_sub_district.setBounds(181, 116, 84, 19);
		contentPane.add(textField_sub_district);
		
		lblNewLabel_4 = new JLabel("District");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_4.setBounds(275, 119, 57, 13);
		contentPane.add(lblNewLabel_4);
		
		textField_district = new JTextField();
		textField_district.setColumns(10);
		textField_district.setBounds(333, 116, 84, 19);
		contentPane.add(textField_district);
		
		lblNewLabel_5 = new JLabel("Province");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_5.setBounds(121, 145, 57, 13);
		contentPane.add(lblNewLabel_5);
		
		textField_province = new JTextField();
		textField_province.setColumns(10);
		textField_province.setBounds(181, 142, 84, 19);
		contentPane.add(textField_province);
		
		lblNewLabel_6 = new JLabel("Postal Code");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_6.setBounds(275, 145, 57, 13);
		contentPane.add(lblNewLabel_6);
		
		textField_postal_code = new JTextField();
		textField_postal_code.setColumns(10);
		textField_postal_code.setBounds(333, 142, 84, 19);
		contentPane.add(textField_postal_code);
		
		loadData();
	}
	
	public void loadData()
	{
		xCompanyInfoDB = CompanyInfoManager.getCompanyInfo();
		textField_company_name.setText(xCompanyInfoDB.company_name);
		textField_address_no.setText(xCompanyInfoDB.address_no);
		textField_alley.setText(xCompanyInfoDB.alley);
		textField_sub_district.setText(xCompanyInfoDB.sub_district);
		textField_district.setText(xCompanyInfoDB.district);
		textField_province.setText(xCompanyInfoDB.province);
		textField_postal_code.setText(""+xCompanyInfoDB.postal_code);
		textField_phone.setText(xCompanyInfoDB.phone);
		textField_email.setText(xCompanyInfoDB.email);
	}
}
