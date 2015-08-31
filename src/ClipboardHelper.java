import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

@SuppressWarnings("unused")
public class ClipboardHelper
{
  public static Image getClipboard()
  {
    Transferable localTransferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
    try
    {
      if ((localTransferable != null) && (localTransferable.isDataFlavorSupported(DataFlavor.imageFlavor))) {
        return (Image)localTransferable.getTransferData(DataFlavor.imageFlavor);
      }
    }
    catch (UnsupportedFlavorException localUnsupportedFlavorException) {}catch (IOException localIOException) {}
    return null;
  }
  
  public static void setClipboard(Image paramImage)
  {
    ClipboardHelper.ImageSelection localImageSelection = new ClipboardHelper.ImageSelection(paramImage);
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(localImageSelection, null);
  }
  
  public static class ImageSelection
    implements Transferable
  {
    private Image image;
    
    public ImageSelection(Image paramImage)
    {
      this.image = paramImage;
    }
    
    public DataFlavor[] getTransferDataFlavors()
    {
      return new DataFlavor[] { DataFlavor.imageFlavor };
    }
    
    public boolean isDataFlavorSupported(DataFlavor paramDataFlavor)
    {
      return DataFlavor.imageFlavor.equals(paramDataFlavor);
    }
    
    public Object getTransferData(DataFlavor paramDataFlavor)
      throws UnsupportedFlavorException, IOException
    {
      if (!DataFlavor.imageFlavor.equals(paramDataFlavor)) {
        throw new UnsupportedFlavorException(paramDataFlavor);
      }
      return this.image;
    }
  }
}
