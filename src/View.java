/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.InputStream;
import java.awt.Graphics;

class View extends JPanel {
	BufferedImage groundImage;
	BufferedImage background1, background2, background3, background4; //background is 4 layered images,
	BufferedImage textBox1, textBox2;

	Model model;

	int scrollPos; //left/right position scrolled by user


	public InputStream returnImage(String imageLocation){
		return View.class.getClassLoader().getResourceAsStream(imageLocation);
	}
	View(Controller c, Model m) {

		model = m;
		c.setView(this);
		try {	//Loads ground and all background images

			this.background1 = ImageIO.read(returnImage("background/background1.png"));
			this.background2 = ImageIO.read(returnImage("background/background2.png"));
			this.background3 = ImageIO.read(returnImage("background/background3.png"));
			this.background4 = ImageIO.read(returnImage("background/background4.png"));
			this.groundImage = ImageIO.read(returnImage("background/ground.png"));
			this.textBox1 = ImageIO.read(returnImage("background/controlText1.png"));
			this.textBox2 = ImageIO.read(returnImage("background/controlText2.png"));

		}
		catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
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
	}
}
