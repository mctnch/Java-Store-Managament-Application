package V;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					try {
			            // Set cross-platform Java L&F (also called "Metal")
			        UIManager.setLookAndFeel(
			            UIManager.getCrossPlatformLookAndFeelClassName());
			    } 
			    catch (UnsupportedLookAndFeelException e) {
			       // handle exception
			    }
			    catch (ClassNotFoundException e) {
			       // handle exception
			    }
			    catch (InstantiationException e) {
			       // handle exception
			    }
			    catch (IllegalAccessException e) {
			       // handle exception
			    }
			        
					
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setBounds(100, 100, 700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnNewButton_1 = new JButton("Product");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductFrame f = new ProductFrame();
				  f.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton_1.setBounds(10, 48, 99, 21);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("User");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserFrame f = new UserFrame();
				  f.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(10, 79, 99, 21);
		getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Invoice");
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton_3.setForeground(new Color(0, 0, 0));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				InvoiceFrame f = new InvoiceFrame();
				f.setVisible(true);
			}
			
		});
		btnNewButton_3.setBounds(10, 110, 99, 21);
		getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_1_1 = new JButton("Customer");
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton_1_1.addActionListener(  new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  CustomerFrame f = new CustomerFrame();
			  f.setVisible(true);
			}
		});
		
		btnNewButton_1_1.setBounds(10, 17, 99, 21);
		getContentPane().add(btnNewButton_1_1);

	}
}
