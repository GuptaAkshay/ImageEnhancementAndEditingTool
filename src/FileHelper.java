import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileHelper
{
  public static File open()
  {
    JFileChooser localJFileChooser = new JFileChooser();
    File localFile = null;
    int i = localJFileChooser.showOpenDialog(null);
    if (i == 0) {
      localFile = localJFileChooser.getSelectedFile();
    }
    return localFile;
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
public static File saveAs(BufferedImage paramBufferedImage)
  {
    JFileChooser localJFileChooser = new JFileChooser();
    File localFile = null;
    List localList = Arrays.asList(ImageIO.getWriterFileSuffixes());
    int i = localJFileChooser.showSaveDialog(null);
    if (i == 0)
    {
      localFile = localJFileChooser.getSelectedFile();
      if (!localFile.exists())
      {
        if (hasSuffix(localFile)) {}
        saveIt(paramBufferedImage, localFile, localList);
      }
      else if (localFile.exists())
      {
        int j = JOptionPane.showConfirmDialog(null, "File exists do you want to save anyway?");
        if (j == 0)
        {
          if (hasSuffix(localFile)) {}
          saveIt(paramBufferedImage, localFile, localList);
        }
        else if (j == 1)
        {
          JOptionPane.showMessageDialog(null, "The file was not saved.");
        }
      }
    }
    else if (i == 1)
    {
      localJFileChooser.setVisible(false);
    }
    return localFile;
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
public static void save(File paramFile, BufferedImage paramBufferedImage)
  {
    List localList = Arrays.asList(ImageIO.getWriterFileSuffixes());
    if (paramFile != null) {
      saveIt(paramBufferedImage, paramFile, localList);
    } else {
      saveAs(paramBufferedImage);
    }
  }
  
  private static boolean hasSuffix(File paramFile)
  {
    boolean bool = true;
    String str1 = paramFile.getPath();
    if (str1 == null) {
      return false;
    }
    int i = str1.lastIndexOf(".");
    if (i == -1)
    {
      String str2 = "file suffix was not specified";
      JOptionPane.showMessageDialog(null, str2, "Error", 0);
      bool = false;
    }
    return bool;
  }
  
  private static void saveIt(BufferedImage paramBufferedImage, File paramFile, List<String> paramList)
  {
    String str1 = paramFile.getPath();
    int i = str1.lastIndexOf(".");
    String str2 = str1.substring(i + 1);
    if (paramList.contains(str2))
    {
      if ((paramBufferedImage.getType() == 2) && ((str2.equals("jpg")) || (str2.equals("jpeg"))))
      {
        String str3 = "this image contains transparency. you cannot save it as jpg or jpeg.";
        JOptionPane.showMessageDialog(null, str3, "Error", 0);
      }
      else
      {
        try
        {
          ImageIO.write(paramBufferedImage, str2, new File(str1));
        }
        catch (IOException localIOException) {}
      }
    }
    else
    {
      String str4 = "unknown writer file suffix (" + str2 + ")";
      JOptionPane.showMessageDialog(null, str4, "Error", 0);
    }
  }
}
