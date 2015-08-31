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

public class ScaledImage
{
  public static BufferedImage getScaledImage(BufferedImage paramBufferedImage)
  {
    int i = paramBufferedImage.getWidth();
    int j = paramBufferedImage.getHeight();
    int k = 100;
    int m = 0;
    while (m == 0)
    {
      String str = JOptionPane.showInputDialog("Choose a scale. The defaut is 100%", "100");
      if (str != null) {
        try
        {
          k = Integer.parseInt(str);
          if (k > 0)
          {
            m = 1;
            double d = k / 100.0D;
            i = (int)(i * d);
            j = (int)(j * d);
          }
        }
        catch (NumberFormatException localNumberFormatException) {}
      } else {
        m = 1;
      }
    }
    int n = paramBufferedImage.getTransparency() == 1 ? 1 : 2;
    
    BufferedImage localBufferedImage = new BufferedImage(i, j, n);
    Graphics2D localGraphics2D = localBufferedImage.createGraphics();
    localGraphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    
    localGraphics2D.drawImage(paramBufferedImage, 0, 0, i, j, null);
    localGraphics2D.dispose();
    return localBufferedImage;
  }
}
