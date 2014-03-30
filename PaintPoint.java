import java.awt.Color;
import java.awt.Stroke;


public class PaintPoint
{
	// Set the brush (stroke) and color
	public Color color;
	public Stroke stroke;
		
	// Set x, y values for mouseDragged
	public int x;
	public int y;
	public boolean bool;
		
	// Parameters that store mouse location of mouseDragged, current color, and current brush size
	public PaintPoint(Color color, Stroke stroke, int x, int y, boolean bool)
	{
		this.color = color;
		this.stroke = stroke;
		this.x = x;
		this.y = y;
		this.bool = bool;
	}

}