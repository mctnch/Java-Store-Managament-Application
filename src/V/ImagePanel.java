package V;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
	public ImagePanel()
	{
		
	}
	Image img;
	public ImagePanel(Image img)
	{
		this.img = img;
		
	}
	
	public void paint(Graphics g)
	{
		
		if(img != null) 
		{
			g.drawImage(img,0,0,this.getWidth(), this.getHeight(), 0, 0, img.getWidth(this), img.getHeight(this), this);
			
		}
		else
		{
			g.fillRect(0,0,getWidth(),getHeight());
		}
	}
	
	public void setImage(Image img)
	{
		this.img = img;
		repaint();
	}
	
	public Image getImage()
	{
		return  img;
	}
	
}