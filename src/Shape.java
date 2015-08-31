import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Shape
{
  private int x;
  private int y;
  private int w;
  private int h;
  private String shape;
  private Color color;
  private int stroke;
  
  public Shape(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString, Color paramColor, int paramInt5)
  {
    this.x = paramInt1;
    this.y = paramInt2;
    this.w = paramInt3;
    this.h = paramInt4;
    this.shape = paramString;
    this.color = paramColor;
    this.stroke = paramInt5;
  }
  
  public void draw(Graphics paramGraphics)
  {
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    


    localGraphics2D.setColor(this.color);
    localGraphics2D.setStroke(new BasicStroke(this.stroke));
    if (this.shape.equals("Draw Free")) {
      localGraphics2D.drawLine(this.x, this.y, this.w, this.h);
    }
    if (this.shape.equals("Draw Rect")) {
      localGraphics2D.drawRect(this.x, this.y, this.w, this.h);
    } else if (this.shape.equals("Fill Rect")) {
      localGraphics2D.fillRect(this.x, this.y, this.w, this.h);
    } else if (this.shape.equals("Draw Oval")) {
      localGraphics2D.drawOval(this.x, this.y, this.w, this.h);
    } else if (this.shape.equals("Fill Oval")) {
      localGraphics2D.fillOval(this.x, this.y, this.w, this.h);
    } else if (this.shape.equals("Draw Line")) {
      localGraphics2D.drawLine(this.x, this.y, this.w, this.h);
    }
  }
}
