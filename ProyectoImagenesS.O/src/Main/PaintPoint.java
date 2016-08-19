/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
import java.awt.Color;
import java.awt.Stroke;


public class PaintPoint
{
	// Set the brush (stroke) and color
	public Color color;
        public int simbol;
		
	// Set x, y values for mouseDragged
	public int x;
	public int y;
	public boolean bool;
		
        public PaintPoint(){
        }
        
	// Parameters that store mouse location of mouseDragged, current color, and current brush size
	public PaintPoint(Color color,int simbol, int x, int y, boolean bool)
	{
		this.color = color;
		this.x = x;
		this.y = y;
		this.bool = bool;
                this.simbol = simbol;
	}

}