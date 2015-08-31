/**
 * 
 */

/**
 * @author Sinner
 *
 */
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class FadeDialog
  extends JDialog
  implements ChangeListener, PropertyChangeListener
{
  private final JOptionPane optionPane;
  private String ok = "OK";
  private String cancel = "Cancel";
  private JSlider slider = new JSlider(0, 100, 100);
  private Run parent;
  
  public FadeDialog(Run paramPaint)
  {
    super(paramPaint, "Fade", true);
    this.parent = paramPaint;
    Object[] arrayOfObject1 = { "Adjust the slider to fade the image.", this.slider };
    Object[] arrayOfObject2 = { this.ok, this.cancel };
    this.optionPane = new JOptionPane(arrayOfObject1, 3, 2, null, arrayOfObject2, arrayOfObject2[0]);
    setContentPane(this.optionPane);
    this.slider.addChangeListener(this);
    this.optionPane.addPropertyChangeListener(this);
    pack();
    setLocationRelativeTo(paramPaint);
  }
  
  public void stateChanged(ChangeEvent paramChangeEvent)
  {
    float f = this.slider.getValue() / 100.0F;
    this.parent.setAlpha(f);
    this.parent.canvas.setAlpha(f);
    this.parent.canvas.repaint();
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent)
  {
    String str = paramPropertyChangeEvent.getPropertyName();
    if ((isVisible()) && (paramPropertyChangeEvent.getSource() == this.optionPane) && (("value".equals(str)) || ("inputValue".equals(str))))
    {
      Object localObject = this.optionPane.getValue();
      if (localObject == JOptionPane.UNINITIALIZED_VALUE) {
        return;
      }
      this.optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
      if (localObject.equals(this.ok))
      {
        dispose();
      }
      else
      {
        this.parent.setAlpha(1.0F);
        dispose();
      }
    }
  }
}
