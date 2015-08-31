/** Contrast And Brightness Class is used Change Contrast and Brightness of Images
 * No change values for contrast is 1 and for brightness is 0 
 * contrast value is should be in between 0.1 and 2.0 
 * To increase contrast value should be greater than 1
 * To decrease contrast value should be less than 1
 
 * contrast value is should be in between -20 and +20
 * To increase brightness value should be greater than 0
 * To decrease brightness value should be greater than 0
 */

/**
 * @author Sinner
 *
 */

import com.googlecode.javacv.cpp.opencv_core.*;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;


public class ContrastAndBrightness {
	
    // con(contrast) should be between 0.1 to 2
	//bri(brightness) should be between -20 to 20
    public static BufferedImage contrastBrightness(BufferedImage paraBufferedImage) {
    	BufferedImage bufferImg=paraBufferedImage;
    	IplImage iplImg = IplImage.createFrom(paraBufferedImage);
    	CvMat cvmatImage = iplImg.asCvMat(); 
        //Scanner in = new Scanner(System.in);
        String con = JOptionPane.showInputDialog("Enter the value to adjust Contrast \n "
        		+ "Note: \n To increase enter value > 1 "
        		+ "\n To decrease value < 1 "
        		+ "\n For no change enter 1 "
        		+ "\n Limit: 0.1 to 2.0");        
    	
        double contrastValue= Double.parseDouble(con);                                                        
       String bri = JOptionPane.showInputDialog("Enter the value to adjust Brightness \n "
        		+ "Note :\n To increase enter +ve value "
        		+ "\n To decrease enter -ve value "
        		+ "\n For no change enter 0 "
        		+ "\n Limit: -20 to +20 ");
        double brightnessValue= Double.parseDouble(bri);
        brightnessValue = 2*brightnessValue;
        
		// Loop over rows (i), columns (j), and colors (c).
		for (int i = 0; i < cvmatImage.rows(); i++) {
			for (int j = 0; j < cvmatImage.cols(); j++) {
				for (int c = 0; c < 3; c++) {               //c is for color channels BRG(blue red green)
					double scaled = cvmatImage.get(i, j, c) * contrastValue + brightnessValue;
                                        if (scaled > 255){
                                            scaled= 255;
                                            cvmatImage.put(i, j, c, scaled);
                                        }else if(scaled <0){
                                            scaled= 0;
                                            cvmatImage.put(i, j, c, scaled);
                                        }else{                                            
                                            cvmatImage.put(i, j, c, scaled);
                                        }
				}
			}
		}	
		iplImg = cvmatImage.asIplImage();
		bufferImg = iplImg.getBufferedImage();
        return bufferImg;
	}            
}
