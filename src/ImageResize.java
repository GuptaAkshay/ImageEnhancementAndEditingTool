

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("unused")
public class ImageResize  {
 
	  public static BufferedImage resizeImage(BufferedImage paraBufferedImage){
		  int w=Integer.parseInt(JOptionPane.showInputDialog("Width"));
		  int h= Integer.parseInt(JOptionPane.showInputDialog("Height"));
		  
		  BufferedImage dimg = new BufferedImage(w,h,paraBufferedImage.getType());
	      Graphics2D g = dimg.createGraphics();
	      if(w!=0 || h != 0){
	    	  g.drawImage(paraBufferedImage,0,0,w,h,null);
	      }
	      else{
	    	  JOptionPane.showMessageDialog(null, "No Changes Made");
	      }	 
	      return dimg;
	  }
	  	   
}