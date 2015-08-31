


import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

public class FlipHori {
	
	public static BufferedImage flipImageHorizontally(BufferedImage paraBufferedImage){
		 int w = paraBufferedImage.getWidth();
	     int h = paraBufferedImage.getHeight();
	     BufferedImage dimg = new BufferedImage(w, h, paraBufferedImage.getType());
	     Graphics2D g = dimg.createGraphics();	       
	     g.drawImage(paraBufferedImage, 0, 0, w, h, w, 0, 0, h, null);
	     g.dispose();
	     return dimg;
	}
}
