/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuBar;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class PaintPanel extends JPanel implements Runnable 
{	
	// Array of buttons for color and brush size's, and clear button
        private JButton[] simbolsB;
	private JButton[] colorB; 
	private JButton[] brushB;
	private JButton clearB;
		
	// Button panel used to hold other panels, and panels that hold the buttons
	private JPanel buttonsP;
	private JPanel colorP;
	//private JPanel brushP;
	private JPanel clearP;
        private JPanel shapesP;
	
	// Button size's
	private int buttonLen = 300;
	private int buttonHeight = 170;
	
	// Current color (blue) and stroke size (thin)
	private Color currColor = new Color(0, 0, 255);
	private Stroke currBrush = new BasicStroke(1.0f);
        private int currSimbol = 0;
        
        //Bottons
        JButton load,save;
        
        JFileChooser fChooser;
       
	
	// Stores the colors
	private HashMap<Integer, Color> colors = new HashMap<Integer, Color>();
	
	// List of color descriptions 
	private List<String> colorList = new ArrayList<String>();
	
	// Stores the brush strokes
	private HashMap<Integer, Stroke> brushes = new HashMap<Integer, Stroke>();

	// Stores the points drawn
	private ArrayList<PaintPoint> points = new ArrayList<PaintPoint>();
	
        // Simbols
        HashMap simbols = new HashMap();
	// Constructor
	public PaintPanel()
	{
                
                fChooser = new JFileChooser();
		// Set mouse listener so that mouse drags can be recorded
		addMouseMotionListener(new MouseDragHandler());
		addMouseListener(new MouseReleaseHandler());
                addMouseListener(new MouseClickedHandler());

		// Set panel background to black
		setBackground(Color.WHITE);
		
		// Initialize panels
		buttonsP = new JPanel();
		colorP = new JPanel();
		//brushP = new JPanel();
		clearP = new JPanel();
                shapesP = new JPanel();
		
		// Place the button panel at the bottom of window
		setLayout(new BorderLayout());
		add(buttonsP, BorderLayout.PAGE_END);
		
		// Put the other panels into button panel
		buttonsP.setLayout(new GridLayout(4,0));
                buttonsP.add(shapesP);
		buttonsP.add(colorP);
		//buttonsP.add(brushP);
		buttonsP.add(clearP);
                

		// Initialize button array's
		colorB = new JButton[5];	
		brushB = new JButton[5];
                simbolsB = new JButton[9];
		
		// Initialize colors and put them into the HashMap and add color description to ArrayList
		Color blue = new Color(0,0,255);
		Color green = new Color(0,255,0);
		Color cyan = new Color(0,255,255);
		Color yellow = new Color(255,255,0);
		Color red = new Color(255,0,0);
		
		colors.put(0, blue);
		colors.put(1, green);
		colors.put(2, cyan);
		colors.put(3, yellow);
		colors.put(4, red);
		
		colorList.add("blue");
		colorList.add("green");
		colorList.add("cyan");
		colorList.add("yellow");
		colorList.add("red");
                
                //Simbolos characters
                simbols.put(0, 
                    "---"+"|  |"+"---"
                );
                simbols.put(1, 
                    ">->->"
                );
                
                simbols.put(2, 
                    "---*"
                );
                simbols.put(3, 
                    "^U^"
                );
                simbols.put(4, 
                    "^_^"
                );
                simbols.put(5, 
                    "^Y^"
                );
                simbols.put(6, 
                    "->K<-"
                );
                simbols.put(7,
                    "(-_-)"
                );
                
                simbols.put(8, 
                    "*"
                );
                
                

		
		// Initialize strokes and store them into HashMap
		Stroke brush1 = new BasicStroke(1.0f);
		Stroke brush3 = new BasicStroke(3.0f);
		Stroke brush5 = new BasicStroke(5.0f);
		Stroke brush7 = new BasicStroke(7.0f);
		Stroke brush9 = new BasicStroke(9.0f);

		brushes.put(0, brush1);
		brushes.put(1, brush3);
		brushes.put(2, brush5);
		brushes.put(3, brush7);
		brushes.put(4, brush9);
		
		
		// Add color buttons to panel
		for(int i = 0; i < colorB.length; i++)
		{
			// Set the colors as buttons
			colorB[i] = new JButton();
			
			// Set color description on button 
			colorB[i].setText(colorList.get(i));
			
			// Set button's text color
			colorB[i].setForeground(Color.GRAY);

			// Set button's background color
			colorB[i].setBackground(colors.get(i));
			
			// When clicked on sends to the ButtonHandler
			colorB[i].addActionListener(new ButtonHandler());

			// Add color buttons to JPanel
			colorP.add(colorB[i]);
		}
		
                
                
                for(int i = 0; i < simbols.size(); i++)
		{
			
			simbolsB[i] = new JButton(""+simbols.get(i).toString()+"");
			
			// When clicked on sends to the ButtonHandler
			simbolsB[i].addActionListener(new ButtonHandler());
			
			// Add brush size buttons to JPanel
			shapesP.add(simbolsB[i]);
		}
                
		
		
		// Add clear button at the bottom of panel
		clearB = new JButton("Clear");
		clearB.addActionListener(new ButtonHandler());
		clearB.setSize(buttonLen, buttonHeight);
		clearP.add(clearB);
                
                // Add save button at at the botton of panel
                save = new JButton("Save");
                save.addActionListener(new ButtonHandler());
                save.setSize(buttonLen,buttonHeight);
                clearP.add(save);
                
                // Add load button at at the botton of panel
                load = new JButton("Load");
                load.addActionListener(new ButtonHandler());
                load.setSize(buttonLen,buttonHeight);
                clearP.add(load);
                
	}
	
        @Override
	public void paint(Graphics graphics)
	{
		// Sets the points drawn to graphics
		Graphics2D g2d = (Graphics2D)graphics;
		super.paint(g2d);
		
		// Draws all the points collected from mouseDragged events
		for(int i = 0; i < (points.size()-1); i++)
		{
			g2d.setColor(points.get(i).color);
			if(points.get(i).bool == false)
			{
                                //System.out.println(points.get(i).simbol);
				/*graphics.drawLine
				(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);*/
                                graphics.drawString((String)simbols.get(points.get(i).simbol),points.get(i).x,points.get(i).y);
			}
		}
	}
	
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			// If a color button is pressed
			if(event.getSource() == colorB[0])
			{
				currColor = colors.get(0);
			}
			
			if(event.getSource() == colorB[1])
			{
				currColor = colors.get(1);
			}
			
			if(event.getSource() == colorB[2])
			{
				currColor = colors.get(2);
			}
			
			if(event.getSource() == colorB[3])
			{
				currColor = colors.get(3);
			}
			
			if(event.getSource() == colorB[4])
			{
				currColor = colors.get(4);
			}
			
			
			// If a brush button is pressed
			if(event.getSource() == brushB[0])
			{
				currBrush = brushes.get(0);
			}
			
			if(event.getSource() == brushB[1])
			{
				currBrush = brushes.get(1);
			}
			
			if(event.getSource() == brushB[2])
			{
				currBrush = brushes.get(2);
			}
			
			if(event.getSource() == brushB[3])
			{
				currBrush = brushes.get(4);
			}
			
			if(event.getSource() == brushB[4])
			{
				currBrush = brushes.get(4);
			}
                        
                        // If a simbol button is pressed
                        if(event.getSource() == simbolsB[0]){
                                currSimbol = 0;
                        }
                        if(event.getSource() == simbolsB[1]){
                                currSimbol = 1;
                        }
                        if(event.getSource() == simbolsB[2]){
                                currSimbol = 2;
                        }
                        if(event.getSource() == simbolsB[3]){
                                currSimbol = 3;
                        }
                        if(event.getSource() == simbolsB[4]){
                                currSimbol = 4;
                        }
                        if(event.getSource() == simbolsB[5]){
                                currSimbol = 5;
                        }
                        if(event.getSource() == simbolsB[6]){
                                currSimbol = 6;
                        }
                        if(event.getSource() == simbolsB[7]){
                                currSimbol = 7;
                        }
                        if(event.getSource() == simbolsB[8]){
                                currSimbol = 8;
                        }
                        
                        
                        if(event.getSource() == save){
                                int result = fChooser.showSaveDialog(new Frame());
                                if(result == JFileChooser.APPROVE_OPTION){
                                    File createdFile = fChooser.getSelectedFile();
                                    Gson json = new Gson();
                                    String data = json.toJson(points);
                                    
                                    try {
                                        FileWriter writer = new FileWriter(createdFile);
                                        writer.write(data);
                                        writer.flush();
                                        writer.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(PaintPanel.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                }
                        }
                        
                        if(event.getSource() == load){
                                String arch = null;
                                int result = fChooser.showOpenDialog(new Frame());
                                if(result == JFileChooser.APPROVE_OPTION){
                                    File selectedFile = fChooser.getSelectedFile();
                                    try {
                                        arch = geTextFile(selectedFile);
                                    } catch (IOException ex) {
                                        Logger.getLogger(PaintPanel.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    Gson json = new Gson();
                                    JsonElement son = new JsonParser().parse(arch);
                                    JsonArray array = son.getAsJsonArray();
                                    
                                    points.clear();
                                    for(JsonElement json2: array){
                                        PaintPoint point = new PaintPoint();
                                        point = json.fromJson(json2, PaintPoint.class);
                                        
                                        points.add(point);
                                    }
                                }
                        }
			
                        // If clear button pressed
			if(event.getSource() == clearB)
			{
				points = new ArrayList<PaintPoint>();
			}
		}
		
	}
	
	private class MouseReleaseHandler extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e)
		{
			// Records mouse dragged events
			points.add(new PaintPoint(currColor,currSimbol, e.getX(), e.getY(), false));	
		}
                
               
	}
        
        private class MouseClickedHandler extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			// Records mouse dragged events
			points.add(new PaintPoint(currColor,currSimbol, e.getX(), e.getY(), false));	
		}
	}

	private class MouseDragHandler extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e)
		{
			// Records mouse dragged events
			points.add(new PaintPoint(currColor,currSimbol, e.getX(), e.getY(), false));
		}
	}
        
        public String geTextFile(File file) throws FileNotFoundException, IOException{
            String txt = "";
            FileInputStream fi = new FileInputStream(file);
            DataInputStream di = new DataInputStream(fi);
            int count = fi.available();
            byte[] bs = new byte[count];
            di.read(bs);
            for(int i=0;i<count;i++){
                char c = (char)bs[i];
                 txt+=c;
            }
            return txt;
        }
    
    
        public String getLineFile(File file) throws FileNotFoundException, IOException{
            String txt = "";
            BufferedReader bf = new BufferedReader(new FileReader(file));
            txt = bf.readLine();
            return txt;
        }

        @Override
	public void run() 
	{
		while(true)
		{
			// Repaint drawing in drawing panel not whole frame
			repaint(0, 0, 850, 450);
			
			try 
			{
				// Thread sleeps
				Thread.sleep(100);
			} 
			catch (InterruptedException e) 
			{
				
			}
		}
	}
}

