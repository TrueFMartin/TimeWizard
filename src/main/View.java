/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */
package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.InputStream;

class View extends JPanel {
	BufferedImage groundImage;
	BufferedImage background1, background2, background3, background4; //background is 4 layered images,
	BufferedImage textBox1, textBox2;
	BufferedImage wizardFrame, healthBars;

	Model model;

	int scrollPos; //left/right position scrolled by user
	JInternalFrame frame;

	public InputStream returnImage(String imageLocation){
		//System.out.println(View.class.getClassLoader());
		return View.class.getClassLoader().getResourceAsStream(imageLocation);
	}
	View(Controller c, Model m) {

		model = m;
		c.setView(this);

		try {	//Loads ground and all background images

			this.background1 = ImageIO.read(returnImage("resources/background/background1.png"));
			this.background2 = ImageIO.read(returnImage("resources/background/background2.png"));
			this.background3 = ImageIO.read(returnImage("resources/background/background3.png"));
			this.background4 = ImageIO.read(returnImage("resources/background/background4.png"));
			this.groundImage = ImageIO.read(returnImage("resources/background/ground.png"));
			this.textBox1 = ImageIO.read(returnImage("resources/background/controlText1.png"));
			this.textBox2 = ImageIO.read(returnImage("resources/background/controlText2.png"));
			this.wizardFrame = ImageIO.read(returnImage("resources/frame/WizardFramed.png"));
			healthBars = ImageIO.read(returnImage("resources/frame/HealthFrame.png"));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
//		frame = new JInternalFrame("test");
//
//		this.add(frame);
	}

	public void paintComponent(Graphics g) {
		scrollPos = model.wizard.x - 200; //Keeps the screen "centered" 200 pixels to the left of wizard

		//Adds 4 layers of background
		g.drawImage(background1, 0, 0, 960,816, null);
		g.drawImage(background2, 0, 0, 960,816, null);
		g.drawImage(background3, 0, 0, 960,816, null);
		g.drawImage(background4, 0, 0, 960,816, null);
		

		for(int i = 0; i < 100; i++){
			//draws ground that won't move with view
			g.drawImage(groundImage, 300*i - scrollPos, 300, 300, 345, null);
		}
		//draw all sprite objects
		for (Sprite sprite : model.sprites) {
			sprite.draw(g, scrollPos);
		}
		//draw text help boxes on top of other images 
		g.drawImage(textBox1, 0, 0, 250,100, null);
		g.drawImage(textBox2, 250, 0, 250,100, null);
		g.drawImage(wizardFrame, 0, 120, 83,80,null);
		g.drawImage(healthBars,80,140, 150, 40, null);
		g.setColor(new Color(255, 0, 60));
		g.fillRect(82,146,145 * model.wizard.health / Wizard.HEALTH_MAX, 7);
		g.setColor(new Color(50, 20, 255));
		g.fillRect(82, 167, 145 * model.wizard.mana / Wizard.MANA_MAX, 7);
	}
}
