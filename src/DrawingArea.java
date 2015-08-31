/**
 * 
 */

/**
 * @author Sinner
 *
 */
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JWindow;

@SuppressWarnings({ "serial", "unused" })
public class DrawingArea
  extends JPanel
{
  private DrawingArea.MyMouseListener theListener = new DrawingArea.MyMouseListener();
  private Point start = new Point();
  private Point end = new Point();
  private Run parent;
  private Shape current;
  private int x1 = 0;
  private int x2 = 0;
  private int y1 = 0;
  private int y2 = 0;
  private int w = 0;
  private int h = 0;
  private int stroke = 1;
  private float alpha = 1.0F;
  private JWindow size = new JWindow();
  private JLabel coordinates = new JLabel();
  private boolean done = true;
  private BufferedImage free;
  private BufferedImage overlayImage;
  
  public DrawingArea(Run paramPaint)
  {
    this.parent = paramPaint;
    addMouseListener(this.theListener);
    addMouseMotionListener(this.theListener);
    this.size.setSize(50, 20);
    this.size.setAlwaysOnTop(true);
    Container localContainer = this.size.getContentPane();
    localContainer.add(this.coordinates);
  }
  
  public Dimension getPreferredSize()
  {
    BufferedImage localBufferedImage = ImageStack.getImage();
    Dimension localDimension = super.getPreferredSize();
    if (localBufferedImage != null) {
      localDimension = new Dimension(localBufferedImage.getWidth(), localBufferedImage.getHeight());
    }
    return localDimension;
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    super.paintComponent(paramGraphics);
    localGraphics2D.setComposite(AlphaComposite.getInstance(3, this.alpha));
    setBackground(this.parent.getBack());
    if (this.free != null) {
      localGraphics2D.drawImage(this.free, 0, 0, null);
    } else if (ImageStack.getImage() != null) {
      localGraphics2D.drawImage(ImageStack.getImage(), 0, 0, null);
    }
    if (this.overlayImage != null) {
      localGraphics2D.drawImage(this.overlayImage, (int)this.end.getX(), (int)this.end.getY(), null);
    }
    if ((this.parent.getCrop()) && (!this.done))
    {
      localGraphics2D.setColor(Color.BLACK);
      localGraphics2D.drawRect(this.x1, this.y1, this.w, this.h);
      localGraphics2D.setColor(Color.WHITE);
      float[] arrayOfFloat = { 5.0F, 5.0F };
      BasicStroke localBasicStroke = new BasicStroke(1.0F, 0, 2, 10.0F, arrayOfFloat, 0.0F);
      localGraphics2D.setStroke(localBasicStroke);
      localGraphics2D.drawRect(this.x1, this.y1, this.w, this.h);
    }
    if (this.current != null) {
      this.current.draw(paramGraphics);
    }
    localGraphics2D.dispose();
  }
  
  public void setStroke(int paramInt)
  {
    this.stroke = paramInt;
  }
  
  public void setAlpha(float paramFloat)
  {
    this.alpha = paramFloat;
    repaint();
  }  

  public BufferedImage createImage()
  {
    Dimension localDimension = getSize();
    Rectangle localRectangle = new Rectangle(0, 0, localDimension.width, localDimension.height);
    BufferedImage localBufferedImage2 = ImageStack.getImage();
    BufferedImage localBufferedImage1;
    if (localBufferedImage2 != null) {
      localBufferedImage1 = new BufferedImage(localRectangle.width, localRectangle.height, localBufferedImage2.getType());
    } else {
      localBufferedImage1 = new BufferedImage(localRectangle.width, localRectangle.height, 1);
    }
    Graphics2D localGraphics2D = localBufferedImage1.createGraphics();
    paint(localGraphics2D);
    localGraphics2D.dispose();
    return localBufferedImage1;
  }
  
  public void setOverlayImage(BufferedImage paramBufferedImage)
  {
    this.overlayImage = paramBufferedImage;
  }
  
  private void setParameters()
  {
    this.x1 = this.start.x;
    this.y1 = this.start.y;
    this.x2 = this.end.x;
    this.y2 = this.end.y;
    int i = 0;
    if (this.x1 > this.x2)
    {
      i = this.x1;
      this.x1 = this.x2;
      this.x2 = i;
    }
    if (this.y1 > this.y2)
    {
      i = this.y1;
      this.y1 = this.y2;
      this.y2 = i;
    }
    this.w = (this.x2 - this.x1);
    this.h = (this.y2 - this.y1);
  }
  
  private void setCursorLabel()
  {
    Point localPoint = MouseInfo.getPointerInfo().getLocation();
    int i = (int)localPoint.getX();
    int j = (int)localPoint.getY();
    this.size.setLocation(i, j - 20);
    this.coordinates.setText(this.w - 2 + ", " + (this.h - 2));
  }
  
  class MyMouseListener
    extends MouseAdapter
  {
    BufferedImage in;
    
    MyMouseListener() {}
    
    public void mouseMoved(MouseEvent paramMouseEvent)
    {
      DrawingArea.this.end = paramMouseEvent.getPoint();
      DrawingArea.this.setParameters();
      if (DrawingArea.this.overlayImage != null)
      {
        DrawingArea.this.setCursor(new Cursor(1));
        DrawingArea.this.repaint();
      }
    }
    
    public void mousePressed(MouseEvent paramMouseEvent)
    {
      if ((!DrawingArea.this.parent.getTransparent()) && (!DrawingArea.this.parent.getFloodFill()) && (!DrawingArea.this.parent.getReplace()))
      {
        DrawingArea.this.done = false;
        DrawingArea.this.start = paramMouseEvent.getPoint();
        if (DrawingArea.this.parent.getCrop())
        {
          DrawingArea.this.setCursorLabel();
          DrawingArea.this.size.setVisible(true);
        }
      }
    }
    
    public void mouseDragged(MouseEvent paramMouseEvent)
    {
      DrawingArea.this.end = paramMouseEvent.getPoint();
      DrawingArea.this.setParameters();
      if ((!DrawingArea.this.parent.getTransparent()) && (!DrawingArea.this.parent.getFloodFill()) && (!DrawingArea.this.parent.getReplace())) {
        if (DrawingArea.this.parent.getCrop())
        {
          DrawingArea.this.setCursorLabel();
          DrawingArea.this.repaint();
        }
        else
        {
          JRadioButton localJRadioButton = DrawingArea.this.parent.getSelected();
          String str = localJRadioButton.getText();
          Color localColor = DrawingArea.this.parent.getFore();
          if (str.equals("Draw Free"))
          {
            DrawingArea.this.current = new Shape(DrawingArea.this.start.x, DrawingArea.this.start.y, DrawingArea.this.end.x, DrawingArea.this.end.y, str, localColor, DrawingArea.this.stroke);
            DrawingArea.this.repaint();
            DrawingArea.this.free = DrawingArea.this.createImage();
            DrawingArea.this.start = DrawingArea.this.end;
          }
          else if (str.equals("Draw Line"))
          {
            DrawingArea.this.current = new Shape(DrawingArea.this.start.x, DrawingArea.this.start.y, DrawingArea.this.end.x, DrawingArea.this.end.y, str, localColor, DrawingArea.this.stroke);
          }
          else
          {
            DrawingArea.this.current = new Shape(DrawingArea.this.x1, DrawingArea.this.y1, DrawingArea.this.w, DrawingArea.this.h, str, localColor, DrawingArea.this.stroke);
          }
          DrawingArea.this.repaint();
        }
      }
    }
    
    public void mouseReleased(MouseEvent paramMouseEvent)
    {
      DrawingArea.this.end = paramMouseEvent.getPoint();
      this.in = ImageStack.getImage();
      BufferedImage localBufferedImage1 = DrawingArea.this.createImage();
      if (DrawingArea.this.parent.getFloodFill())
      {
        floodFill();
      }
      else if (DrawingArea.this.parent.getReplace())
      {
        replace();
      }
      /*else if (DrawingArea.this.parent.getTransparent())
      {
        if ((DrawingArea.this.end.x < this.in.getWidth()) && (DrawingArea.this.end.y < this.in.getHeight()))
        {
          Color localColor = new Color(this.in.getRGB(DrawingArea.this.end.x, DrawingArea.this.end.y));
          BufferedImage localBufferedImage3 = new BufferedImage(this.in.getWidth(), this.in.getHeight(), 2);
          Graphics2D localGraphics2D = localBufferedImage3.createGraphics();
          localGraphics2D.drawImage(TransparencyUtil.makeColorTransparent(this.in, localColor), 0, 0, null);
          localGraphics2D.dispose();
          ImageStack.addImage(localBufferedImage3);
          DrawingArea.this.parent.setTransparent(false);
        }
      }*/
      else if (DrawingArea.this.parent.getCrop())
      {
        DrawingArea.this.size.setVisible(false);
        
        localBufferedImage1 = localBufferedImage1.getSubimage(DrawingArea.this.x1 + 1, DrawingArea.this.y1 + 1, DrawingArea.this.w - 1, DrawingArea.this.h - 1);
        
        int i = JOptionPane.showConfirmDialog(DrawingArea.this.parent, "Width = " + (DrawingArea.this.w - 2) + "\nHeight = " + (DrawingArea.this.h - 2), "Crop", 2);
        if (i == 0) {
          ImageStack.addImage(localBufferedImage1);
        }
        DrawingArea.this.parent.setCrop(false);
      }
      else
      {
        BufferedImage localBufferedImage2 = ImageStack.getImage();
        if ((DrawingArea.this.parent.getConstrain()) && (localBufferedImage2 != null)) {
          localBufferedImage1 = localBufferedImage1.getSubimage(0, 0, localBufferedImage2.getWidth(), localBufferedImage2.getHeight());
        }
        ImageStack.addImage(localBufferedImage1);
        DrawingArea.this.current = null;
      }
      DrawingArea.this.setCursor(new Cursor(0));
      DrawingArea.this.repaint();
      DrawingArea.this.revalidate();
      DrawingArea.this.free = null;
      DrawingArea.this.overlayImage = null;
      DrawingArea.this.done = true;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void floodFill()
    {
      ArrayDeque localArrayDeque = new ArrayDeque();
      BufferedImage localBufferedImage = new BufferedImage(this.in.getWidth(), this.in.getHeight(), this.in.getType());
      Graphics2D localGraphics2D = localBufferedImage.createGraphics();
      localGraphics2D.drawImage(this.in, 0, 0, null);
      localGraphics2D.dispose();
      if ((DrawingArea.this.end.x < localBufferedImage.getWidth()) && (DrawingArea.this.end.y < localBufferedImage.getHeight()))
      {
        DrawingArea.this.setCursor(new Cursor(3));
        Color localColor1 = new Color(this.in.getRGB(DrawingArea.this.end.x, DrawingArea.this.end.y));
        if (!localColor1.equals(DrawingArea.this.parent.getFore())) {
          localArrayDeque.add(DrawingArea.this.end);
        }
        while (!localArrayDeque.isEmpty())
        {
          Point localPoint1 = (Point)localArrayDeque.remove();
          localBufferedImage.setRGB(localPoint1.x, localPoint1.y, DrawingArea.this.parent.getFore().getRGB());
          for (int i = localPoint1.x - 1; i < localPoint1.x + 2; i++) {
            for (int j = localPoint1.y - 1; j < localPoint1.y + 2; j++) {
              if ((i == localPoint1.x) || (j == localPoint1.y)) {
                if ((i > -1) && (j > -1) && (i < localBufferedImage.getWidth()) && (j < localBufferedImage.getHeight()))
                {
                  Point localPoint2 = new Point(i, j);
                  Color localColor2 = new Color(localBufferedImage.getRGB(localPoint2.x, localPoint2.y));
                  if (!localColor2.equals(DrawingArea.this.parent.getFore())) {
                    if (!localArrayDeque.contains(localPoint2)) {
                      localArrayDeque.add(localPoint2);
                    }
                  }
                }
              }
            }
          }
        }
      }
      ImageStack.addImage(localBufferedImage);
      DrawingArea.this.parent.setFloodFill(false);
    }
    
    private void replace()
    {
      Point localPoint = null;
      Color localColor1 = null;
      BufferedImage localBufferedImage = new BufferedImage(this.in.getWidth(), this.in.getHeight(), this.in.getType());
      Graphics2D localGraphics2D = localBufferedImage.createGraphics();
      localGraphics2D.drawImage(this.in, 0, 0, null);
      localGraphics2D.dispose();
      if ((DrawingArea.this.end.x < localBufferedImage.getWidth()) && (DrawingArea.this.end.y < localBufferedImage.getHeight()))
      {
        localPoint = new Point(DrawingArea.this.end.x, DrawingArea.this.end.y);
        localColor1 = new Color(this.in.getRGB(DrawingArea.this.end.x, DrawingArea.this.end.y));
        for (int i = 0; i < localBufferedImage.getWidth(); i++) {
          for (int j = 0; j < localBufferedImage.getHeight(); j++)
          {
            localPoint.setLocation(i, j);
            Color localColor2 = new Color(this.in.getRGB(localPoint.x, localPoint.y));
            if (localColor2.equals(localColor1)) {
              localBufferedImage.setRGB(localPoint.x, localPoint.y, DrawingArea.this.parent.getFore().getRGB());
            }
          }
        }
        ImageStack.addImage(localBufferedImage);
        DrawingArea.this.parent.setReplace(false);
      }
    }
  }


}
