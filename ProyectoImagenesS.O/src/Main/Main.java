/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;



import javax.swing.JFrame;


public class Main extends JFrame
{
	//Constructor
	Main()
	{
		// Label of program
		super("Simple Paint Program");
		
		// Create paint panel and start as a thread
		PaintPanel paintPanel = new PaintPanel();
		Thread paintThread = new Thread(paintPanel);
		paintThread.start();
		add(paintPanel);
	}
	
	public static void main(String[] args)
	{
		// Create frame
		Main frame = new Main();
		
		// Set frame size and display frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(850, 600);
		frame.setLocation(400, 100);
		frame.setVisible(true);		
                
                
	}
}

