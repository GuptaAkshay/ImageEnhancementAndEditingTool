/**
 * 
 */

/**
 * @author Sinner
 *
 */

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class ImageRotator {
	
    public static BufferedImage rotateMyImage(BufferedImage paraBufferedImage) {
    	double angleOfRotation = Double.parseDouble(JOptionPane.showInputDialog("Enter the angle to rotate \n (in Degrees)"));
        int w = paraBufferedImage.getWidth();
        int h = paraBufferedImage.getHeight();
        BufferedImage dimg =new BufferedImage(w, h, paraBufferedImage.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); 
        g.rotate(Math.toRadians(angleOfRotation), w/2, h/2);
 
        g.drawImage(paraBufferedImage, null, 0, 0);
        return dimg;
    }

}