/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */
package main;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements ActionListener, MouseListener, KeyListener
{
	View view;
	Model model;

	boolean keyLeft, keyRight, keyQuit, keySave, keyLoad, keyRestart, keyEdit, keyJump, keyFireball, keySkeleton; //if true, the accorded key is pressed

	Controller(Model m) {
		model = m;
	}

	void setView(View v) {
		view = v;
	}

	void update() {
		if(keyRight) { //move view right and move Wizard right
			model.updateWizardForward();
		}
		if(keyLeft) { //move view left and move Wizard left
			model.updateWizardBackward();
		}
		if(keyQuit){ //quit game
			System.out.println("Exiting with 'Q/Esc' Press. Hope you saved!");
			System.exit(1);
		}
		if(keySave) { //user releases 's' key
			model.marshal(); //marshals model to json
			keySave = false; //'s' key is no longer considered pressed/released
		}
		if(keyLoad){ //user releases 'l' key
			model.unmarshal(); //unmarshals json file to model
			keyLoad = false; //consider 'l' key released
		}
		if(keyJump){ //space bar pressed, jump
			model.wizardJump();
		}
		if(!keyJump && !keyLeft && !keyRight) { //if no movement keys are being pressed
			model.noAnimation();
		}
		if(keyFireball) {
			model.shootFireball();
			keyFireball = false;
		}
		if(keyRestart){
			model.restartWizard();
			keyRestart = false;
		}

	}
	public void actionPerformed(ActionEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {
		if(keyEdit) {
			//set X and Y cords in model for mouse click
			model.setXY(e.getX() + view.scrollPos, e.getY());
			//Add/Remove pipe in model
			if(!keySkeleton)
				model.addPipe();
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
			case KeyEvent.VK_SPACE -> keyJump = true;//space bar actively pressed, activate jump in model
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT -> keyRight = false;
			case KeyEvent.VK_LEFT -> keyLeft = false;
			case KeyEvent.VK_Q, KeyEvent.VK_ESCAPE -> keyQuit = true; //if user presses q/esc, exit program
			case KeyEvent.VK_S -> keySave = true;
			case KeyEvent.VK_L -> keyLoad = true;
			case KeyEvent.VK_E -> keyEdit = !keyEdit; //changes edit-mode state
			case KeyEvent.VK_SPACE -> keyJump = false;
			case KeyEvent.VK_CONTROL -> keyFireball = true;
			case KeyEvent.VK_G -> keySkeleton = !keySkeleton;
			case KeyEvent.VK_R -> keyRestart = true;
		}
		//if user entered 'e' and now in editing mode
		if(e.getKeyCode() == KeyEvent.VK_E && keyEdit){
			System.out.println("Entering edit mode...");
		}
		//if user entered 'e' and no long in edit mode
		else if(e.getKeyCode() == KeyEvent.VK_E){
			System.out.println("Leaving edit mode...");
		}
		if(e.getKeyCode() == KeyEvent.VK_G && keySkeleton){
			System.out.println("Now in add Skeleton mode...");
		}
		//if user entered 'e' and no long in edit mode
		else if(e.getKeyCode() == KeyEvent.VK_G){
			System.out.println("Back to add Pipe mode...");
		}
	}

	public void keyTyped(KeyEvent e) {}
}
