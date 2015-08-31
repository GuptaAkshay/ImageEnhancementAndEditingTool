

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ErodeDilate {
	
   public static BufferedImage erodeImage(BufferedImage paraBufferedImage) {	
	     
	   System.loadLibrary( Core.NATIVE_LIBRARY_NAME );    
         Mat source = img2Mat(paraBufferedImage);
         Mat destination = source;

         int erosion_size = 3;        
         
         Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*erosion_size + 1, 2*erosion_size+1));
         Imgproc.erode(source, destination, element);
         BufferedImage newImage = Mat2img(destination);                               
         
         return newImage;
   }
   public static BufferedImage dilateImage(BufferedImage paraBufferedImage) {	
       
	   System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
       Mat source = img2Mat(paraBufferedImage);
       Mat destination = source;
      
       int dilation_size = 3;
       
       Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*dilation_size + 1, 2*dilation_size+1));
       Imgproc.dilate(source, destination, element1);
       BufferedImage newImage = Mat2img(destination);                               
       
       return newImage;
 }
   public static Mat img2Mat(BufferedImage in)
   {
	   BufferedImage image = in;
	   byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	   Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	   mat.put(0, 0, data);
          return mat;
    } 
   public static BufferedImage Mat2img(Mat in)
   {
	   Mat mat = in;
	   byte[] data = new byte[mat.rows()*mat.cols()*(int)(mat.elemSize())];
	   mat.get(0, 0, data);
	   if (mat.channels() == 3) {
	    for (int i = 0; i < data.length; i += 3) {
	     byte temp = data[i];
	     data[i] = data[i + 2];
	     data[i + 2] = temp;
	    }
	   }
	   BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), BufferedImage.TYPE_3BYTE_BGR);
	   image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
	   return image;
   }
   
}