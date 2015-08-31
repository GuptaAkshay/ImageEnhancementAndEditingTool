/**
 * 
 */

/**
 * @author Sinner
 *
 */
import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

@SuppressWarnings("serial")
public class SplashScreen extends JWindow {
  private int duration;
  public SplashScreen(int d) {
    duration = d;
  }

  // A simple little method to show a title screen in the center
  // of the screen for the amount of time given in the constructor
  public void showSplash() {
    JPanel content = (JPanel)getContentPane();
    content.setBackground(Color.darkGray);

    // Set the window's bounds, centering the window
    int width = 600;
    int height =250;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width-width)/2;
    int y = (screen.height-height)/2;
    setBounds(x,y,width,height);

    // Build the splash screen
    JLabel label = new JLabel(new ImageIcon("src/splash.png"));
    JLabel copyrt = new JLabel
      ("Version 1.0", JLabel.CENTER);
    copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
    content.add(label, BorderLayout.CENTER);
    content.add(copyrt, BorderLayout.SOUTH);
    Color oraRed = new Color(156, 20, 20, 255 );
    content.setBorder(BorderFactory.createLineBorder(oraRed,10));

    // Display it
    setVisible(true);

    // Wait a little while, maybe while loading resources
    try { Thread.sleep(duration); } catch (Exception e) {}

    setVisible(false);
  }

  public void showSplashAndExit() {
    showSplash();    
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

  public static void main(String[] args) {
    // Throw a nice little title page up on the screen first
    SplashScreen splash = new SplashScreen(5000);
    // Normally, we'd call splash.showSplash() and get on with the program.
    // But, since this is only a test...
    splash.showSplashAndExit();
  }
}