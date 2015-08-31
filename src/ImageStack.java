/**
 * ImageStack Class maintains stack a of images which used to for reverting changes.
 */

/**
 * @author Sinner
 *
 */
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageStack
{
  @SuppressWarnings({ "unchecked", "rawtypes" })
private static ArrayList<BufferedImage> images = new ArrayList();
  
  public static BufferedImage getImage()
  {
    int i = images.size();
    if (i > 0) {
      return (BufferedImage)images.get(i - 1);
    }
    return null;
  }
  
  public static void addImage(BufferedImage paramBufferedImage)
  {
    if (images.size() > 14) {
      images.remove(0);
    }
    images.add(paramBufferedImage);
  }
  
  public static void removeImage()
  {
    int i = images.size();
    if (i > 0) {
      images.remove(i - 1);
    }
  }
  
  public static void clear()
  {
    images.clear();
  }
}
