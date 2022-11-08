/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */
package main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.InputStream;

class View extends JPanel {
	public static final int HB_X = 80;
	public static final int HB_Y = 40;
	BufferedImage groundImage;
	BufferedImage background1, background2, background3, background4; //background is 4 layered images,
	BufferedImage textBox1, textBox2;
	BufferedImage wizardFrame, healthBars;

	Model model;

	int scrollPosX; //left/right position scrolled by user
	int scrollPosY;
	private boolean isEditMode;


	View(Controller c, Model m) {

		model = m;
		c.setView(this);
		isEditMode = false;

		try {	//Loads ground and all background images

			this.background1 = ImageIO.read(returnImage("resources/background/background1.png"));
			this.background2 = ImageIO.read(returnImage("resources/background/background2.png"));
			this.background3 = ImageIO.read(returnImage("resources/background/background3.png"));
			this.background4 = ImageIO.read(returnImage("resources/background/background4.png"));
			this.groundImage = ImageIO.read(returnImage("resources/background/ground.png"));
			this.wizardFrame = ImageIO.read(returnImage("resources/frame/WizardFramed.png"));
			healthBars = ImageIO.read(returnImage("resources/frame/HealthFrame.png"));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}


	}

	protected void setIsEditMode(boolean isEditMode){
		this.isEditMode = isEditMode;
	}
	protected void updateScrollPos(int x, int y){
		scrollPosX += x;
		scrollPosY += y;
	}

	public void paintComponent(Graphics g) {
		if(!isEditMode) {
			scrollPosX = model.wizard.x - 200;
			scrollPosY = model.wizard.y < 300 ? model.wizard.y - 300 : 0;
		}
		//Adds 4 layers of background
		g.drawImage(background1, 0, 0, 960,816, null);
		g.drawImage(background2, 0, 0, 960,816, null);
		g.drawImage(background3, 0, 0, 960,816, null);
		g.drawImage(background4, 0, 0, 960,816, null);
		

		for(int i = 0; i < 100; i++){
			//draws ground that won't move with view
			g.drawImage(groundImage, 300*i - scrollPosX,  300 - scrollPosY, 300, 345, null);
		}
		//draw all sprite objects
		for (Sprite sprite : model.sprites) {
			sprite.draw(g, scrollPosX, scrollPosY);
		}

//		---------------Draw wizard headshot and status bars, HB is healthbar------------------
		g.drawImage(wizardFrame, HB_X - 80, HB_Y - 20, 83, 80,null);
		g.drawImage(healthBars, HB_X, HB_Y, 150, 40, null);
		g.setColor(new Color(255, 0, 60));
		g.fillRect(HB_X + 2,HB_Y + 6,145 * model.wizard.health / Wizard.HEALTH_MAX, 7);
		g.setColor(new Color(50, 20, 255));
		g.fillRect(HB_X + 2, HB_Y + 27, 145 * model.wizard.mana / Wizard.MANA_MAX, 7);
	}

	private void editorDraw(Graphics g){

	}
	private void playDraw(Graphics g){
		scrollPosX = model.wizard.x - 200; //Keeps the screen "centered" 200 pixels to the left of wizard

		scrollPosY = model.wizard.y < 300? 300 - model.wizard.y : 0;
	}


	public InputStream returnImage(String imageLocation){
		//System.out.println(View.class.getClassLoader());
		return View.class.getClassLoader().getResourceAsStream(imageLocation);
	}
}
class r {
	public static void drawRotatedShape(final Graphics2D g2, final Shape shape,
										final double angle,
										final float x, final float y) {

		final AffineTransform saved = g2.getTransform();
		final AffineTransform rotate = AffineTransform.getRotateInstance(
				angle, x, y);
		g2.transform(rotate);
		g2.draw(shape);
		g2.setTransform(saved);
	}
}
