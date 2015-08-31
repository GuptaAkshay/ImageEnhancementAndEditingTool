/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import static com.googlecode.javacv.cpp.opencv_core.cvSize;

import com.googlecode.javacv.cpp.opencv_imgproc;

import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

/**
 *
 * @author Akshay
 */
public class Blur {

    public static BufferedImage gausBlur(BufferedImage paraBufferedImage){	
    		
    	IplImage iplImg = IplImage.createFrom(paraBufferedImage);
    	double gamma=Double.parseDouble(JOptionPane.showInputDialog("Enter the amount of bluring \n Limit: 1 to 10 "));
    	if(gamma<=10){
	    	gamma = gamma/2;    	    	
	    	opencv_imgproc.GaussianBlur(iplImg,iplImg, cvSize(3,3), gamma, gamma, 1);	    	  	          
    	}
    	else{
    		JOptionPane.showMessageDialog(null, "No Changes Made");
    	}
    	BufferedImage bufferImg = iplImg.getBufferedImage();
    	return bufferImg;
    }
   public static BufferedImage sharpenImage(BufferedImage paraBufferedImage){	
	
	   IplImage iplImg = IplImage.createFrom(paraBufferedImage);
	   CvMat image = iplImg.asCvMat();		 
	   CvMat result= image.clone();          
	   double gamma=Double.parseDouble(JOptionPane.showInputDialog("Enter the amount of sharpness \n Limit: 1 to 50 ")); 
	   gamma = 100*gamma;
		          //kernel size 13
	   opencv_imgproc.GaussianBlur(result, image,cvSize(9,9),gamma,gamma,1);    
	   opencv_core.cvAddWeighted(result, 1.5/*(alpha)*/, image, -0.5/*(beta)*/, 1, image);         //alpha and beta should vary from -2 to 2
	   IplImage iplImg2 = image.asIplImage();
	   BufferedImage bufferImg = iplImg2.getBufferedImage();  
	   return bufferImg;    
    }



}
