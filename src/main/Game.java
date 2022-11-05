/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */

package main;

import javax.swing.*;
import java.awt.Toolkit;

public class Game extends JFrame
{
	View view;
	Model model;
	Controller controller;
	final static int SCREEN_W = 816;
	final static int SCREEN_H = 816;


	public Game() {

		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
		this.setTitle("Time Reversing Wizard -- In Progress");
		this.setSize(SCREEN_W, SCREEN_H);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		//JOptionPane.showInternalOptionDialog(view.getRootPane(), "Welecome. Controls are on top. Move backwards to reverse time. Don't die.",
		//		"Welcome", 1, 3, null, null, 1);
	}


	public void run() {
		model.loadSerial();
		while(true) {
			controller.update();//game updates when keys pressed
			view.repaint(); //indirectly calls view.paintComponent
			model.update(); //update model methods that don't require key press

			Toolkit.getDefaultToolkit().sync(); //updates screen
			if(this.getWidth() > 900)
				this.setSize(900, this.getHeight());
			//go to sleep for 25 milisec
			try {
				Thread.sleep(35);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void main(String[] args) {
		Game g = new Game();
		g.run();
	}
}
