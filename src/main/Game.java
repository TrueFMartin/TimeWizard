/*
    Franklin True Martin
    11/1/22
    Side Scroller
 */

package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

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
		fillMenu();

		this.setVisible(true);
		//JOptionPane.showInternalOptionDialog(view.getRootPane(), "Welecome. Controls are on top. Move backwards to reverse time. Don't die.",
		//		"Welcome", 1, 3, null, null, 1);
	}


	public void run() {
		model.loadSerial();
		while(true) {
			controller.update();//game updates when keys pressed
			model.update(); //update model methods that don't require key press
			view.repaint(); //indirectly calls view.paintComponent

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
	private void fillMenu(){
		JMenuBar menu = new JMenuBar();

		Border raisedbevel = BorderFactory.createRaisedBevelBorder();

		JButton saveButton = new JButton("Save");
			saveButton.setActionCommand("save");
			saveButton.addActionListener(controller);
			saveButton.setFocusable(false);
			menu.add(saveButton);

		JButton loadButton = new JButton("Load from Save");
			loadButton.setActionCommand("load");
			loadButton.addActionListener(controller);
			loadButton.setFocusable(false);
			menu.add(loadButton);

		JButton resetButton = new JButton("Reset Wizard");
			resetButton.setActionCommand("reset");
			resetButton.addActionListener(controller);
			resetButton.setFocusable(false);
			menu.add(resetButton);

		JMenu modeMenu = new JMenu("Mode");
			ButtonGroup modeGroup = new ButtonGroup();
			JRadioButtonMenuItem playModeRB = new JRadioButtonMenuItem("Play mode");
			playModeRB.setSelected(true);
			playModeRB.setActionCommand("play");
			playModeRB.addActionListener(controller);
			modeGroup.add(playModeRB);


			JRadioButtonMenuItem editModeRB = new JRadioButtonMenuItem("Edit mode");
			editModeRB.setSelected(false);
			editModeRB.setActionCommand("edit");
			editModeRB.addActionListener(controller);
			modeGroup.add(editModeRB);

			modeMenu.add(playModeRB);
			modeMenu.add(editModeRB);
			modeMenu.setBorder(BorderFactory.createBevelBorder(4));
			menu.add(modeMenu);

		JMenu mapMenu = new JMenu("Editor options");
			ButtonGroup mapGroup = new ButtonGroup();
			JRadioButtonMenuItem pipeModeRB = new JRadioButtonMenuItem("Add pipes");
			pipeModeRB.setSelected(true);
			pipeModeRB.setActionCommand("pipe");
			pipeModeRB.addActionListener(controller);
			mapGroup.add(pipeModeRB);

			JRadioButtonMenuItem skeletonModeRB = new JRadioButtonMenuItem("Add skeletons");
			skeletonModeRB.setSelected(false);
			skeletonModeRB.setActionCommand("skeleton");
			skeletonModeRB.addActionListener(controller);
			mapGroup.add(skeletonModeRB);

		JSlider widthSlider = new JSlider(JSlider.HORIZONTAL,
				20, 240, 60);
			widthSlider.setName("width");
			widthSlider.addChangeListener(controller);

			widthSlider.setMajorTickSpacing(30);
			widthSlider.setMinorTickSpacing(15);
			widthSlider.setPaintTicks(true);
			widthSlider.setPaintLabels(true);
			JLabel widthLabel = new JLabel("Pipe Width");

		JSlider heightSlider = new JSlider(JSlider.HORIZONTAL,
				0, 240, 240);
			heightSlider.setName("height");
			heightSlider.addChangeListener(controller);

			heightSlider.setMajorTickSpacing(60);
			heightSlider.setMinorTickSpacing(30);
			heightSlider.setPaintTicks(true);
			heightSlider.setPaintLabels(true);
			JLabel heightLabel = new JLabel("Pipe Height");

		mapMenu.add(pipeModeRB);
		mapMenu.add(skeletonModeRB);
		mapMenu.add(widthLabel);
		mapMenu.add(widthSlider);
		mapMenu.add(heightLabel);
		mapMenu.add(heightSlider);
		menu.add(mapMenu);

		JMenu controlMenu = new JMenu("Controls");
			JMenuItem m1 = new JMenuItem("Movment: Arrowkeys");
			JMenuItem m2 = new JMenuItem("Jump: Spacebar");
			JMenuItem m3 = new JMenuItem("Fireball: Ctrl");
			JMenuItem m4 = new JMenuItem("Add/Remove: Mouse");
			controlMenu.add(m1);
			controlMenu.add(m2);
			controlMenu.add(m3);
			controlMenu.add(m4);

		menu.add(controlMenu);

		this.setJMenuBar(menu);
	}

	public static void main(String[] args) {
		Game g = new Game();
		g.run();
	}
}
