/**
 * 
 */

/**
 * @author Sinner
 *
 */
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FlipVeri {	
		
	public static BufferedImage flipImageVertically(BufferedImage paramBufferedImage){		 
		        int w = paramBufferedImage.getWidth();
		        int h = paramBufferedImage.getHeight();
		        BufferedImage dimg = new BufferedImage(w, h, paramBufferedImage.getType());
		        Graphics2D g = dimg.createGraphics();
		        g.drawImage(paramBufferedImage, 0, 0, w, h, 0, h, w, 0, null);
		        g.dispose();
		        return dimg;		    
	}
}