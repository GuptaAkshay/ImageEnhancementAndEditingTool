/**
 * 
 */

/**
 * @author Sinner
 *
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;

@SuppressWarnings({ "unused", "serial" })
public class StandardColorSelector
  extends AbstractColorChooserPanel
  implements ActionListener
{
  private String[] names = { "Black", "Blue", "Cyan", "Dark Gray", "Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "Red", "White", "Yellow" };
  private Color[] colors = { Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow };
  @SuppressWarnings({ "unchecked", "rawtypes" })
private JComboBox<String> selector = new JComboBox(this.names);
  
  public void buildChooser()
  {
    this.selector.setEditable(false);
    this.selector.setSelectedIndex(0);
    this.selector.addActionListener(this);
    add(this.selector);
  }
  
  public void updateChooser() {}
  
  public String getDisplayName()
  {
    return "Standard";
  }
  
  public Icon getSmallDisplayIcon()
  {
    return null;
  }
  
  public Icon getLargeDisplayIcon()
  {
    return null;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    getColorSelectionModel().setSelectedColor(this.colors[this.selector.getSelectedIndex()]);
  }
}
