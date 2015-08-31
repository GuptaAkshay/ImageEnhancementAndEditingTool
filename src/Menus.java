/**
 * 
 */

/**
 * @author Sinner
 *
 */
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

final class Menus
{
  private JMenuBar menuBar = new JMenuBar();
  
  private JMenu fileMenu = new JMenu("File");
  
  private JMenu editMenu = new JMenu("Edit");
  
  private JMenu imageMenu = new JMenu("Image");
  
  
  private JMenu filterMenu = new JMenu("Filter");  
  private JMenu noiseMenu = new JMenu("Noise");
  private JMenu otherMenu = new JMenu("Others");
  
  private JMenu drawMenu = new JMenu("Draw");  
  private JMenu infoMenu = new JMenu("Info");
  
  JMenuItem openMenuItem = new JMenuItem("Open");
  JMenuItem saveAsMenuItem = new JMenuItem("Save As");
  JMenuItem saveMenuItem = new JMenuItem("Save");  
  JMenuItem copyMenuItem = new JMenuItem("Copy");
  JMenuItem pasteMenuItem = new JMenuItem("Paste");
  
  JMenuItem fadeMenuItem = new JMenuItem("Fade");
  JMenuItem resizeMenuItem = new JMenuItem("Resize");
  JMenuItem scaleMenuItem = new JMenuItem("Zoom");
  JMenuItem cropMenuItem = new JMenuItem("Crop");
  JMenuItem flipVertiMenuItem = new JMenuItem("Flip Vertical");
  JMenuItem flipHoriMenuItem = new JMenuItem("Flip Horizontal");
  JMenuItem sharpMenuItem = new JMenuItem("Sharpen");  
  JMenuItem rotateMenuItem = new JMenuItem("Rotate");
  JMenuItem conAndBriMenuItem = new JMenuItem("Contrast And Brightness");
  JMenuItem superimposeMenuItem = new JMenuItem("Superimpose");
  
  JMenuItem guassianMenuItem = new JMenuItem("Gaussian");
  JMenuItem histoMenuItem = new JMenuItem("Histogram");
  JMenuItem erodeMenuItem = new JMenuItem("Erosion");
  JMenuItem dilateMenuItem = new JMenuItem("Dilation");
  JMenuItem histoEQMenuItem = new JMenuItem("Histogram Equalise");
  
  JMenuItem widthMenuItem = new JMenuItem("Width");
  JMenuItem floodFillMenuItem = new JMenuItem("Flood Fill");
  JMenuItem replaceMenuItem = new JMenuItem("Replace Color");
  JMenuItem helpMenuItem = new JMenuItem("Help");
  JMenuItem aboutMenuItem = new JMenuItem("About");
  
  Menus(ActionListener paramActionListener)
  {
    int i = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
    this.copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(67, i));
    this.pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(86, i));
    setListener(paramActionListener);
    
    this.fileMenu.add(this.openMenuItem);
    this.fileMenu.add(this.saveAsMenuItem);
    this.fileMenu.add(this.saveMenuItem);
    this.editMenu.add(this.copyMenuItem);
    this.editMenu.add(this.pasteMenuItem);
        
    
    this.imageMenu.add(this.cropMenuItem);
    this.imageMenu.add(this.resizeMenuItem);
    this.imageMenu.add(this.scaleMenuItem);
    this.imageMenu.add(this.rotateMenuItem);
    this.imageMenu.add(this.sharpMenuItem);
    this.imageMenu.add(this.fadeMenuItem); 
    this.imageMenu.add(this.flipHoriMenuItem);
    this.imageMenu.add(this.flipVertiMenuItem);   
    this.imageMenu.add(this.conAndBriMenuItem);       
    this.imageMenu.add(this.superimposeMenuItem);
    
    this.filterMenu.add(this.noiseMenu);
    this.filterMenu.add(this.otherMenu);
    this.filterMenu.add(this.histoMenuItem);
    this.filterMenu.add(this.histoEQMenuItem);
    this.noiseMenu.add(this.guassianMenuItem);
    this.otherMenu.add(this.erodeMenuItem);
    this.otherMenu.add(this.dilateMenuItem);
    
    this.drawMenu.add(this.widthMenuItem);
    this.drawMenu.add(this.floodFillMenuItem);
    this.drawMenu.add(this.replaceMenuItem);
    
    this.infoMenu.add(this.helpMenuItem);
    this.infoMenu.add(this.aboutMenuItem);
    
    this.menuBar.add(this.fileMenu);
    this.menuBar.add(this.editMenu);
    this.menuBar.add(this.imageMenu);
    this.menuBar.add(this.filterMenu);
    this.menuBar.add(this.drawMenu);
    
    this.menuBar.add(Box.createHorizontalGlue());
    
    this.menuBar.add(this.infoMenu);
  }
  
  private void setListener(ActionListener paramActionListener)
  {
    this.openMenuItem.addActionListener(paramActionListener);
    this.saveAsMenuItem.addActionListener(paramActionListener);
    this.saveMenuItem.addActionListener(paramActionListener);
    this.copyMenuItem.addActionListener(paramActionListener);
    this.pasteMenuItem.addActionListener(paramActionListener);
    
    this.fadeMenuItem.addActionListener(paramActionListener);
    this.resizeMenuItem.addActionListener(paramActionListener);
    this.scaleMenuItem.addActionListener(paramActionListener);
    this.cropMenuItem.addActionListener(paramActionListener);
    this.flipVertiMenuItem.addActionListener(paramActionListener);
    this.flipHoriMenuItem.addActionListener(paramActionListener);
    this.sharpMenuItem.addActionListener(paramActionListener);
    this.rotateMenuItem.addActionListener(paramActionListener);
    this.conAndBriMenuItem.addActionListener(paramActionListener);
    this.superimposeMenuItem.addActionListener(paramActionListener);
    
    
    this.guassianMenuItem.addActionListener(paramActionListener);
    this.histoMenuItem.addActionListener(paramActionListener);
    this.histoEQMenuItem.addActionListener(paramActionListener);
    this.erodeMenuItem.addActionListener(paramActionListener);
    this.dilateMenuItem.addActionListener(paramActionListener);
    
    this.widthMenuItem.addActionListener(paramActionListener);
    this.floodFillMenuItem.addActionListener(paramActionListener);
    this.replaceMenuItem.addActionListener(paramActionListener);
    this.helpMenuItem.addActionListener(paramActionListener);
    this.aboutMenuItem.addActionListener(paramActionListener);
  }
  
  public JMenuBar getMenuBar()
  {
    return this.menuBar;
  }
}

