/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */
package main;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements ActionListener, MouseListener, KeyListener, ChangeListener {
	View view;
	Model model;

	boolean keyLeft, keyRight, keyUp, keyDown, keyQuit, keyEdit, keyJump, keyFireball, keySkeleton; //if true, the accorded key is pressed
	int pipeW, pipeH;
	Controller(Model m) {
		model = m;
		pipeH = 240;
		pipeW = 60;
	}

	void setView(View v) {
		view = v;
	}

	void update() {
		if(keyRight) { //move view right and move Wizard right
			if(!keyEdit)
				model.updateWizardForward();
			else
				view.updateScrollPos(10, 0);
		}
		if(keyLeft) { //move view left and move Wizard left
			if(!keyEdit)
				model.updateWizardBackward();
			else
				view.updateScrollPos(-10, 0);
		}
		if(keyUp && keyEdit) { //move view up in edit mode
			view.updateScrollPos(0, -10);
		}
		if(keyDown && keyEdit) { //move view down in edit mode
			view.updateScrollPos(0, 10);
		}
		if(keyQuit){ //quit game
			System.out.println("Exiting with 'Q/Esc' Press. Hope you saved!");
			System.exit(1);
		}
		if(keyJump){ //space bar pressed, jump
			model.wizardJump();
		}
		if((!keyJump && !keyLeft && !keyRight) || keyEdit){//if no movement keys are being pressed or in edit mode
			model.noAnimation();
		}
		if(keyFireball) {
			model.shootFireball();
			keyFireball = false;
		}
	}
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()){
			case "save" -> model.saveSerial();
			case "load" -> model.loadSerial();
			case "reset" -> model.restartWizard();
			case "edit" -> {keyEdit = true;
							view.setIsEditMode(true);}
			case "play" -> {keyEdit = false;
							view.setIsEditMode(false);}
			case "pipe" -> keySkeleton = false;
			case "skeleton" -> keySkeleton = true;
		}
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {
		if(keyEdit) {
			//set X and Y cords in model for mouse click
			model.setXY(e.getX() + view.scrollPosX, e.getY() + view.scrollPosY);
			//Add/Remove pipe in model
			if(!keySkeleton)
				model.addPipe(pipeW, pipeH);
			else
				model.addSkeleton();
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT -> keyRight = true; //continue scroll right
			case KeyEvent.VK_LEFT -> keyLeft = true; // continue scroll left
			case KeyEvent.VK_UP -> keyUp = true;
			case KeyEvent.VK_DOWN -> keyDown = true;
			case KeyEvent.VK_SPACE -> keyJump = true;//space bar actively pressed, activate jump in model
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT -> keyRight = false;
			case KeyEvent.VK_LEFT -> keyLeft = false;
			case KeyEvent.VK_UP -> keyUp = false;
			case KeyEvent.VK_DOWN -> keyDown = false;
			case KeyEvent.VK_SPACE -> keyJump = false;
			case KeyEvent.VK_CONTROL -> keyFireball = true;
		}
//		//if user entered 'e' and now in editing mode
//		if(e.getKeyCode() == KeyEvent.VK_E && keyEdit){
//			System.out.println("Entering edit mode...");
//		}
//		//if user entered 'e' and no long in edit mode
//		else if(e.getKeyCode() == KeyEvent.VK_E){
//			System.out.println("Leaving edit mode...");
//		}
//		if(e.getKeyCode() == KeyEvent.VK_G && keySkeleton){
//			System.out.println("Now in add Skeleton mode...");
//		}
//		//if user entered 'e' and no long in edit mode
//		else if(e.getKeyCode() == KeyEvent.VK_G){
//			System.out.println("Back to add Pipe mode...");
//		}
	}
	public void stateChanged(ChangeEvent e) {

		JSlider source = (JSlider)e.getSource();
		if(source.getName().equals("width"))
			pipeW = source.getValue();
		if(source.getName().equals("height"))
			pipeH = source.getValue();
//		if (!source.getValueIsAdjusting()) {
//			int fps = (int)source.getValue();
//			if (fps == 0) {
//				if (!frozen) stopAnimation();
//			} else {
//				delay = 1000 / fps;
//				timer.setDelay(delay);
//				timer.setInitialDelay(delay * 10);
//				if (frozen) startAnimation();
//			}
//		}
	}

	public void keyTyped(KeyEvent e) {}
}
