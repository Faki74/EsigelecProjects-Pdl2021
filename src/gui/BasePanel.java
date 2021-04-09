package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BasePanel extends JPanel {
	public static Dimension maxDim=new Dimension(1280,720),minDim=new Dimension(800,512),normalDim=new Dimension(900,577);
	protected static String imagesDir = "/images/",imagePath="esigBase2.png";
	protected BufferedImage backImg;

	{
		try {
			backImg=ImageIO.read(this.getClass().getResource(imagesDir+imagePath));
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * Create the panel.
	 */
	public BasePanel() {
		setSize(new Dimension(886,540));
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//super.paintComponents(g);
		//repaint();
		//revalidate();
		g.drawImage(backImg,0,0,getWidth(),getHeight(),null);
		//repaint();
	}
	
}
