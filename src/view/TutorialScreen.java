package view;

//imports
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.MainController;

import javax.swing.JButton;

public class TutorialScreen extends JPanel implements ActionListener, MouseMotionListener {
	private static JLabel instructions;
	private static MainController c; // the controller operating the game
	private JButton gameStart;
	private JButton titleScreen;
	private static Window window;
	private BufferedImage fishImage;
	private BufferedImage foodImage;
	private BufferedImage bgImage1;
	private BufferedImage bgImage2;
	private BufferedImage trashImage;
	private final int mapHeight = 592;
	private final static int mapLength = 5728;
	private final static int windowHeight = 592;
	private final static int windowLength = 2000;
	private static int[] foodxLocation;
	private static int[] foodyLocation;
	private static int[] trashxLocation;
	private static int[] trashyLocation;
	private static int fishxLocation;
	private static int fishyLocation;
	private static int numFood = 10;
	private static int numTrash = 25;
	private static boolean dispFood = true;
	private static boolean dispTrash = false;
	static Timer timer;
	private static int j = 20;
	private static int bg1xpos;
	private static int bg2xpos;
	private static String mode;
	private static String text;
	private static boolean pauseMovement;
	private int tick;

	// constructor
	public TutorialScreen() {
		// layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// layered pane
		JLayeredPane layeredPane = new JLayeredPane(); // create
		layeredPane.setPreferredSize(new Dimension(windowLength, windowHeight)); // resize
		layeredPane.setBorder(BorderFactory.createTitledBorder("Tutorial")); // TODO:
																				// Remove
		// layeredPane.addMouseMotionListener(this); // add mouse listener

		// add Mouse Motion
		addMouseMotionListener(this);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(Box.createRigidArea(new Dimension(50, 50)));
		add(layeredPane);
		add(createControlPanel());
		instructions = new JLabel("Use mouse to move the fish. Eat food, don't eat trash");
		add(instructions);

		// create buffered images
		// Create and load the duke icon.
		try {
			fishImage = ImageIO.read(new File(c.getFishURL()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create and load food icon
		try {
			foodImage = ImageIO.read(new File(c.getFoodURL()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create and load trash icon
		try {
			trashImage = ImageIO.read(new File(c.getTrashURL()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create and load the background image.hjtreuessaw3i7754
		try {
			bgImage1 = ImageIO.read(new File(c.getBgURL()));
			bgImage2 = bgImage1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// create map locations
		bg1xpos = 0;
		bg2xpos = mapLength;

		// generate food locations
		foodxLocation = new int[numFood];
		foodyLocation = new int[numFood];
		for (int i = 0; i < numFood; i++) {
			foodxLocation[i] = (int) (Math.random() * windowLength);
			foodyLocation[i] = (int) (Math.random() * windowHeight);
		}

		// generate trash locations
		trashxLocation = new int[numTrash];
		trashyLocation = new int[numTrash];
		for (int i = 0; i < numTrash; i++) {
			trashxLocation[i] = (int) (Math.random() * windowLength);
			trashyLocation[i] = (int) (Math.random() * windowHeight);
		}

	}

	private JButton createButton(String label, String actionCommand) {
		JButton b = new JButton(label);
		b.setActionCommand(actionCommand);
		return b;
	}

	private JPanel createControlPanel() {
		gameStart = createButton("Start Game", "goToGame");
		gameStart.addActionListener(this);
		titleScreen = createButton("Return to Title Screen", "goToTitle");
		titleScreen.addActionListener(this);

		JPanel controls = new JPanel();
		controls.add(gameStart);
		controls.add(titleScreen);
		return controls;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		fishxLocation = e.getX();
		fishyLocation = e.getY();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		MainController c = new MainController(true);
		if (cmd == "goToGame") {
			window.stopAndRemoveTimer(timer);
			c.startGame();
			// TODO: need action to open game
		} else if (cmd == "goToTitle") {
			window.stopAndRemoveTimer(timer);
			c.showTitleScreen();
			
		}
	}

	private static void createAndShowGUI(Window frame) {
		// content
		JComponent newContentPane = new TutorialScreen();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// set tutorial screen
		c.setTutorialScreen((TutorialScreen) newContentPane);

		// display
		frame.pack();
		frame.setVisible(true);

		System.out.println("disp"); // TODO: remove
		// create Timer
		// create Swing timer with actionListener
		timer = new Timer(40, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// paintImmediat();
				// i+=400;
				update();
				newContentPane.repaint();
				newContentPane.revalidate();
				System.out.println("paint tut");
				// c.getGamePlayScreen().updateFishPosition();
			}
		});
		frame.addTimer(timer);;
		timer.start();

	}

	public static void activateTutorial(MainController c, Window w) {
		TutorialScreen.c = c;
		TutorialScreen.window = w;
		createAndShowGUI(w);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// disp map
		bg1xpos -= j;
		bg2xpos -= j;
		//System.out.println(bg1xpos); //TODO: remove
		g.drawImage(bgImage1, bg1xpos, 50, this);
		g.drawImage(bgImage2, bg2xpos, 50, this);

		// disp objects
		if (dispFood) {
			for (int i = 0; i < numFood; i++) {
				foodxLocation[i] -= j;
				g.drawImage(foodImage, foodxLocation[i], foodyLocation[i] + 50, this);
				// System.out.println((foodxLocation[i]-j)+" "+
				// foodyLocation[i]);
				// g.drawImage(foodImage, 1000, 250, this);
			}
		}
		if (dispTrash) {
			for (int i = 0; i < numTrash; i++) {
				trashxLocation[i] -= j;
				g.drawImage(trashImage, trashxLocation[i], trashyLocation[i] + 50, this);
			}
		}

		// disp fish
		g.drawImage(fishImage, fishxLocation, fishyLocation, this);

		// display instructions

	}

	public static void update() {
		// push items to back end of screen
		System.out.println("update");
		for (int i = 0; i < numFood; i++) {
			if (foodxLocation[i] <= 0) {
				foodxLocation[i] = windowLength + 100;
				foodyLocation[i] = (int) (Math.random() * windowHeight);
			}
		}

		for (int i = 0; i < numTrash; i++) {
			if (trashxLocation[i] <= 0) {
				trashxLocation[i] = windowLength + 100;
				trashyLocation[i] = (int) (Math.random() * windowHeight);
			}
		}

		// relocateMap
		if (bg1xpos < bg2xpos && bg1xpos < (-mapLength)) {
			bg1xpos = bg2xpos + (mapLength);
		} else if (bg1xpos > bg2xpos && bg2xpos < (-mapLength)) {
			bg2xpos = bg1xpos + (mapLength);
		}

		// update mode
		if (mode == "instructions") {
			pauseMovement = true;
		} else if (mode == "moveFish") {
			text = "Use mouse to move fish!";
			dispFood = false;
			dispTrash = false;
			pauseMovement = false;
		} else if (mode == "getFood") {
			text = "Move fish to food to eat fish";
			dispFood = true;
			dispTrash = false;
			pauseMovement = false;
		} else if (mode == "avoidTrash") {
			text = "Avoid the trash as you are swimming";
			dispFood = true;
			dispTrash = true;
			pauseMovement = false;
		} else if (mode == "accumulateTrash") {
			text = "Trash accumulates in the game, keep avoiding trash!";
			dispFood = false;
			dispTrash = false;
			pauseMovement = false;
		} else if (mode == "collectTrash") {
			text = "You hit trash";
			dispFood = false;
			dispTrash = false;
			pauseMovement = false;
		}

		// runTutorial();
	}

	public static void runTutorial() {
		mode = "moveFish";
		mode = "getFood";
		mode = "avoidTrash";
		mode = "accumlateTrash";
		mode = "collectTrash";
		mode = "dispInstuctions";

	}

}
