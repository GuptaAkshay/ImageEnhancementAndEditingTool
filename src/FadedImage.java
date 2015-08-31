import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FadedImage
{
  public static BufferedImage fadeImage(BufferedImage paramBufferedImage, float paramFloat)
  {
    BufferedImage localBufferedImage = new BufferedImage(paramBufferedImage.getWidth(), paramBufferedImage.getHeight(), 2);
    Graphics2D localGraphics2D = localBufferedImage.createGraphics();
    localGraphics2D.setComposite(AlphaComposite.getInstance(3, paramFloat));
    localGraphics2D.drawImage(paramBufferedImage, 0, 0, null);
    localGraphics2D.dispose();
    return localBufferedImage;
  }
}
