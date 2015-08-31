/**
 * 
 */

/**
 * @author Sinner
 *
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.rtf.RTFEditorKit;

@SuppressWarnings({ "serial", "unused" })
public class Run
  extends JFrame
  implements ItemListener, ActionListener
{
  DrawingArea canvas = new DrawingArea(this);
  private boolean crop = false;
  private boolean transparent = false;
  private boolean floodFill = false;
  private boolean replace = false;
  private JScrollPane scrollPane = new JScrollPane(this.canvas);
  private Menus menus = new Menus(this);
  private JPanel topControls = new JPanel();
  private JPanel bottomControls = new JPanel();
  private JRadioButton selected = new JRadioButton();
  private JRadioButton[] optionsArray = new JRadioButton[6];
  private JCheckBox constrain = new JCheckBox("Constrain",true);
  private JButton backButton;
  private JButton foreButton;
  private Color backColor = Color.WHITE;
  private Color foreColor = Color.BLACK;
  private JButton undo;
  private JButton clear;
  private Container c;
  private JColorChooser chooser = new JColorChooser();
  private StandardColorSelector customTab = new StandardColorSelector();
  private float alpha;
  private JFrame help;
  private File file;
  
  public Run()
  {
    setTitle("Image Enhancement and Editing Tool");
    setExtendedState(JFrame.MAXIMIZED_BOTH); 
    setMinimumSize(new Dimension(650,500));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(3);
    this.c = getContentPane();
    this.c.setLayout(new BorderLayout());
    initComponents();
    setJMenuBar(this.menus.getMenuBar());
    this.c.add(this.topControls, "North");
    this.c.add(this.scrollPane, "Center");
    this.c.add(this.bottomControls, "South");
    initHelp();
    setVisible(true);
  }
  
  private void initComponents()
  {
    this.backButton = new JButton("Background");
    this.foreButton = new JButton("Foreground");
    this.undo = new JButton("Undo");
    this.clear = new JButton("Clear");
    ButtonGroup localButtonGroup = new ButtonGroup();
    
    String[] arrayOfString = { "Draw Free", "Draw Rect", "Fill Rect", "Draw Oval", "Fill Oval", "Draw Line" };
    this.topControls.add(this.constrain);
    for (int i = 0; i < this.optionsArray.length; i++)
    {
      this.optionsArray[i] = new JRadioButton(arrayOfString[i]);
      this.topControls.add(this.optionsArray[i]);
      this.optionsArray[i].addItemListener(this);
      localButtonGroup.add(this.optionsArray[i]);
    }
    this.optionsArray[0].setSelected(true);
    this.selected = this.optionsArray[0];
    this.bottomControls.setLayout(new GridLayout(1, 4));
    this.bottomControls.add(this.foreButton);
    this.foreButton.addActionListener(this);
    this.bottomControls.add(this.backButton);
    this.backButton.addActionListener(this);
    this.bottomControls.add(this.undo);
    this.undo.addActionListener(this);
    this.bottomControls.add(this.clear);
    this.clear.addActionListener(this);
    this.chooser.addChooserPanel(this.customTab);
  }
  
  private void initHelp()
  {
    RTFEditorKit localRTFEditorKit = new RTFEditorKit();
    JEditorPane localJEditorPane = new JEditorPane();
    JScrollPane localScrollPane = new JScrollPane();
    localJEditorPane.setEditorKit(localRTFEditorKit);
    localScrollPane.setViewportView(localJEditorPane);
    localJEditorPane.setBackground(Color.white);
    localJEditorPane.setEditable(false);
    String str = "help.rtf";
    ClassLoader localClassLoader = getClass().getClassLoader();
    try
    {
      localJEditorPane.setPage(localClassLoader.getResource(str));
    }
    catch (IOException localIOException) {}
    this.help = new JFrame("Help");
    this.help.setSize(300, 600);
    this.help.setResizable(false);
    this.help.setLocationRelativeTo(null);
    Container localContainer = this.help.getContentPane();
    localContainer.setLayout(new BorderLayout());
    localContainer.add(localScrollPane, "Center");
  }
  
  public void itemStateChanged(ItemEvent paramItemEvent)
  {
    for (int i = 0; i < this.optionsArray.length; i++) {
      if (this.optionsArray[i].isSelected()) {
        this.selected = this.optionsArray[i];
      }
    }
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Object localObject1;
    if (paramActionEvent.getSource() == this.backButton)
    {
      localObject1 = JColorChooser.createDialog(this, "Color Chooser", true, this.chooser, new ActionListener()
      {
        public void actionPerformed(ActionEvent paramAnonymousActionEvent)
        {
          Run.this.backColor = Run.this.chooser.getColor();
          
          Run.this.backColor = new Color(Run.this.backColor.getRed(), Run.this.backColor.getGreen(), Run.this.backColor.getBlue(), 255);
        }
      }, null);
      

      ((JDialog)localObject1).setVisible(true);
      this.canvas.repaint();
    }
    
    else if (paramActionEvent.getSource() == this.foreButton)
    {
      localObject1 = JColorChooser.createDialog(this, "Color Chooser", true, this.chooser, new ActionListener()
      {
        public void actionPerformed(ActionEvent paramAnonymousActionEvent)
        {
          Run.this.foreColor = Run.this.chooser.getColor();
        }
      }, null);
      

      ((JDialog)localObject1).setVisible(true);
      this.canvas.repaint();
    }
    
    else if (paramActionEvent.getSource() == this.undo)
    {
      ImageStack.removeImage();
      this.canvas.repaint();
    }
    
    else
    {
      Object localObject2;
      if (paramActionEvent.getSource() == this.menus.openMenuItem)
      {
        try
        {
          this.file = FileHelper.open();
          localObject1 = ImageIO.read(this.file);
          if (localObject1 == null)
          {
            localObject2 = "This program can only open image files, like png or jpg";
            JOptionPane.showMessageDialog(null, localObject2, "Error", 0);
          }
          else
          {
            ImageStack.addImage((BufferedImage)localObject1);
            this.canvas.repaint();
            this.canvas.revalidate();
          }
        }
        catch (IOException localIOException1)
        {
          localObject2 = "could not open file";
          JOptionPane.showMessageDialog(null, localObject2, "IOException", 0);
        }
      }
      
      else if (paramActionEvent.getSource() == this.menus.saveAsMenuItem)
      {
        if (ImageStack.getImage() != null) {
          this.file = FileHelper.saveAs(ImageStack.getImage());
        }
      }
      
      else if (paramActionEvent.getSource() == this.menus.saveMenuItem)
      {
        if (ImageStack.getImage() != null) {
          if (this.file != null) {
            FileHelper.save(this.file, ImageStack.getImage());
          } else {
            this.file = FileHelper.saveAs(ImageStack.getImage());
          }
        }
      }
      
      else if (paramActionEvent.getSource() == this.menus.copyMenuItem)
      {
        if (ImageStack.getImage() != null) {
          ClipboardHelper.setClipboard(ImageStack.getImage());
        }
      }
      
      else
      {
        BufferedImage localBufferedImage1;
        if (paramActionEvent.getSource() == this.menus.pasteMenuItem)
        {
          localBufferedImage1 = (BufferedImage)ClipboardHelper.getClipboard();
          ImageStack.addImage(localBufferedImage1);
          this.canvas.repaint();
          this.canvas.revalidate();
        }
        
        else
        {
          Object localObject3;
          if (paramActionEvent.getSource() == this.menus.fadeMenuItem)
          {
            localBufferedImage1 = ImageStack.getImage();
            if (localBufferedImage1 != null)
            {
              localObject2 = new FadeDialog(this);
              ((FadeDialog)localObject2).setVisible(true);
              if (this.alpha < 1.0F)
              {
                localObject3 = FadedImage.fadeImage(ImageStack.getImage(), this.alpha);
                ImageStack.addImage((BufferedImage)localObject3);
              }
              this.canvas.setAlpha(1.0F);
              this.canvas.repaint();
            }
          }                  
          
          else if (paramActionEvent.getSource() == this.menus.resizeMenuItem)
          {
            localBufferedImage1 = ImageStack.getImage();
            if (localBufferedImage1 != null)
            {
              ImageStack.addImage(ImageResize.resizeImage(localBufferedImage1));
              this.canvas.repaint();
              this.canvas.revalidate();
            }
          }
          
          else if (paramActionEvent.getSource() == this.menus.scaleMenuItem)
          {
            localBufferedImage1 = ImageStack.getImage();
            if (localBufferedImage1 != null)
            {
              ImageStack.addImage(ScaledImage.getScaledImage(localBufferedImage1));
              this.canvas.repaint();
              this.canvas.revalidate();
            }
          }
          
          else
          {
            int j;
            if (paramActionEvent.getSource() == this.menus.cropMenuItem)
            {
              localBufferedImage1 = ImageStack.getImage();
              if (localBufferedImage1 != null)
              {
                j = JOptionPane.showConfirmDialog(this, "Use the mouse to select a portion of the drawing area to crop", "Crop", 2);
                if (j == 0)
                {
                  this.crop = true;
                  this.canvas.setCursor(new Cursor(1));
                }
              }
            }
            
            else if (paramActionEvent.getSource() == this.menus.flipVertiMenuItem)
            {
              localBufferedImage1 = ImageStack.getImage();
              if (localBufferedImage1 != null)
              {
            	  ImageStack.addImage(FlipVeri.flipImageVertically(localBufferedImage1));
                  this.canvas.repaint();
                  this.canvas.revalidate();
              }
            }
            
            else if (paramActionEvent.getSource() == this.menus.flipHoriMenuItem)
            {
              localBufferedImage1 = ImageStack.getImage();
              if (localBufferedImage1 != null)
              {
            	  ImageStack.addImage(FlipHori.flipImageHorizontally(localBufferedImage1));
                  this.canvas.repaint();
                  this.canvas.revalidate();
              }
            }
            
            else if (paramActionEvent.getSource() == this.menus.sharpMenuItem)
            {
              localBufferedImage1 = ImageStack.getImage();
              if (localBufferedImage1 != null)
              {
            	  ImageStack.addImage(Blur.sharpenImage(localBufferedImage1));
                  this.canvas.repaint();
                  this.canvas.revalidate();
              }
            }
            
            else if (paramActionEvent.getSource() == this.menus.rotateMenuItem)
            {
              localBufferedImage1 = ImageStack.getImage();
              if (localBufferedImage1 != null)
              {
            	  ImageStack.addImage(ImageRotator.rotateMyImage(localBufferedImage1));
                  this.canvas.repaint();
                  this.canvas.revalidate();
              }
            }
            
            else if (paramActionEvent.getSource() == this.menus.conAndBriMenuItem)
            {
              localBufferedImage1 = ImageStack.getImage();
              if (localBufferedImage1 != null)
              {
            	  ImageStack.addImage(ContrastAndBrightness.contrastBrightness(localBufferedImage1));
                  this.canvas.repaint();
                  this.canvas.revalidate();
              }
            }
                        
            else if (paramActionEvent.getSource() == this.menus.superimposeMenuItem)
            {
              try
              {
                localBufferedImage1 = ImageIO.read(FileHelper.open());
                this.canvas.setOverlayImage(localBufferedImage1);
                this.canvas.repaint();
                this.canvas.revalidate();
              }
              catch (IOException localIOException2)
              {
                String str = "could not open file";
                JOptionPane.showMessageDialog(null, str, "IOException", 0);
              }
            }
            
            else if (paramActionEvent.getSource() == this.menus.guassianMenuItem)
            {
              localBufferedImage1 = ImageStack.getImage();
              if (localBufferedImage1 != null)
              {
            	  ImageStack.addImage(Blur.gausBlur(localBufferedImage1));
                  this.canvas.repaint();
                  this.canvas.revalidate();
              }
            }
            else if (paramActionEvent.getSource() == this.menus.histoMenuItem)
            {
              localBufferedImage1 = ImageStack.getImage();
              if (localBufferedImage1 != null)
              {
            	 new RGBFrame(localBufferedImage1);                  
              }
            }
            
            else if (paramActionEvent.getSource() == this.menus.histoEQMenuItem)
            {
              localBufferedImage1 = ImageStack.getImage();
              if (localBufferedImage1 != null)
              {
            	  ImageStack.addImage(EqualiseHistogram.histogramEqualization(localBufferedImage1));
                  this.canvas.repaint();
                  this.canvas.revalidate();                  
              }
            }
            
            else if (paramActionEvent.getSource() == this.menus.erodeMenuItem)
            {
              localBufferedImage1 = ImageStack.getImage();
              if (localBufferedImage1 != null)
              {
            	  ImageStack.addImage(ErodeDilate.erodeImage(localBufferedImage1));
                  this.canvas.repaint();
                  this.canvas.revalidate();
              }
            }
            
            else if (paramActionEvent.getSource() == this.menus.dilateMenuItem)
            {
              localBufferedImage1 = ImageStack.getImage();
              if (localBufferedImage1 != null)
              {
            	  ImageStack.addImage(ErodeDilate.dilateImage(localBufferedImage1));
                  this.canvas.repaint();
                  this.canvas.revalidate();
              }
            }
            
            else
            {
              int k;
              if (paramActionEvent.getSource() == this.menus.widthMenuItem)
              {
                int i = 1;
                k = 0;
                while (k == 0)
                {
                  localObject3 = JOptionPane.showInputDialog("Choose a width. The default is 1", "1");
                  if (localObject3 != null) {
                    try
                    {
                      i = Integer.parseInt((String)localObject3);
                      if (i > 0)
                      {
                        k = 1;
                        this.canvas.setStroke(i);
                      }
                    }
                    catch (NumberFormatException localNumberFormatException) {}
                  } else {
                    k = 1;
                  }
                }
              }
              
              else
              {
                BufferedImage localBufferedImage2;
                if (paramActionEvent.getSource() == this.menus.floodFillMenuItem)
                {
                  localBufferedImage2 = ImageStack.getImage();
                  if (localBufferedImage2 != null)
                  {
                    k = JOptionPane.showConfirmDialog(this, "Click on the area to fill.", "Flood Fill", 2);
                    if (k == 0)
                    {
                      this.floodFill = true;
                      this.canvas.setCursor(new Cursor(12));
                    }
                  }
                }
                else if (paramActionEvent.getSource() == this.menus.replaceMenuItem)
                {
                  localBufferedImage2 = ImageStack.getImage();
                  if (localBufferedImage2 != null)
                  {
                    k = JOptionPane.showConfirmDialog(this, "Click on the color to replace.", "Replace Color", 2);
                    if (k == 0)
                    {
                      this.replace = true;
                      this.canvas.setCursor(new Cursor(12));
                    }
                  }
                }
                
                else if (paramActionEvent.getSource() == this.menus.helpMenuItem)
                {
                  this.help.setVisible(true);
                }
                
                else if (paramActionEvent.getSource() == this.menus.aboutMenuItem)
                {
                  JOptionPane.showMessageDialog(this, "This is Fourth Year Project", "About", 1);
                }
                
                else
                {
                  ImageStack.clear();
                  this.canvas.repaint();
                }
                
              }
              
            }
            
          }
          
        }
        
      }
      
    }
    
  }
  
  public void setAlpha(float paramFloat)
  {
    this.alpha = paramFloat;
  }  
  
  public boolean getTransparent()
  {
    return this.transparent;
  }
  
  public void setTransparent(boolean paramBoolean)
  {
    this.transparent = paramBoolean;
  }
  
  public boolean getFloodFill()
  {
    return this.floodFill;
  }
  
  public void setFloodFill(boolean paramBoolean)
  {
    this.floodFill = paramBoolean;
  }
  
  public boolean getReplace()
  {
    return this.replace;
  }
  
  public void setReplace(boolean paramBoolean)
  {
    this.replace = paramBoolean;
  }
  
  public boolean getCrop()
  {
    return this.crop;
  }
  
  public void setCrop(boolean paramBoolean)
  {
    this.crop = paramBoolean;
  }
  
  public boolean getConstrain()
  {
    return this.constrain.isSelected();
  }
  
  public JRadioButton getSelected()
  {
    return this.selected;
  }
  
  public Color getBack()
  {
    return this.backColor;
  }
  
  public Color getFore()
  {
    return this.foreColor;
  }
  public static void main(String args[] ){
	  try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		    new Run();
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
  }
}
