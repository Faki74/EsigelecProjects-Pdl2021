package gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class GifLoader extends JFrame{
	private int a;
	public static void main(String[] args) {
		new GifLoader();
	}
	/**
	 * @param a
	 */
	public GifLoader() {
		Icon img=new ImageIcon(this.getClass().getResource("loading1.gif"));
		   JLabel lb=new JLabel(img);
		   lb.setSize(100,100);
		   this.getContentPane().add(lb);
		   setSize(150,150);
		   setVisible(true);
	}
}
