package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import scenes.Scene;
import sprites.Sprite;

public class Game {
// private:
	private Scene scene;
	private Sprite player;
	private Thread gameLoop;
	private boolean isRunning;
	
	@SuppressWarnings("serial")
	private void addKeyBindings() {
		
		this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0 , false), "W pressed");
		this.scene.getActionMap().put("W pressed", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				player.UP = true;
				player.setIdleState(false);
				
				System.out.println("W pressed");
			}
		});
		
		this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "W released");
		this.scene.getActionMap().put("W released", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				player.UP = false;
				player.setIdleState(true);
				
				System.out.println("W released");
			}
		});
		
		this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0 , false), "S pressed");
		this.scene.getActionMap().put("S pressed", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				player.DOWN = true;
				player.setIdleState(false);
				
				System.out.println("S pressed");
			}
		});
		
		this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "S released");
		this.scene.getActionMap().put("S released", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				player.DOWN = false;
				player.setIdleState(true);
				
				System.out.println("S released");
			}
		});
		
		this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0 , false), "A pressed");
		this.scene.getActionMap().put("A pressed", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				player.LEFT = true;
				player.setIdleState(false);
				
				System.out.println("A pressed");
			}
		});
		
		this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "A released");
		this.scene.getActionMap().put("A released", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				player.LEFT = false;
				player.setIdleState(true);
				
				System.out.println("A released");
			}
		});
		
		this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0 , false), "D pressed");
		this.scene.getActionMap().put("D pressed", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				player.RIGHT = true;
				player.setIdleState(false);
				
				System.out.println("D pressed");
			}
		});
		
		this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "D released");
		this.scene.getActionMap().put("D released", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				player.RIGHT = false;
				player.setIdleState(true);
				
				System.out.println("D released");
			}
		});
	}
	
	// ========================================
	//                showUI()
	// ----------------------------------------
	// - Creates Swing UI
	// - Instantiate sprites, scene, etc
	// - Start game loop
	// ========================================
	private void showUI() {
		
		// Set up JFrame
		JFrame frame = new JFrame("Trying this");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		
		// Set up Scene and Sprite
		scene = new Scene();
		player = new Sprite("Sprites/Alchemist_idle.png", "Sprites/Alchemist_walk.png", 20, 20, "Alchemist");
		
		// Activate the keybindings
		addKeyBindings();
		scene.add(player);
		
		scene.add(new Sprite("Sprites/Fisherman_idle.png", "Sprites/Fisherman_walk.png", 100, 100, "Fisherman"));
		scene.add(new Sprite("Sprites/Merchant_idle.png", "Sprites/Merchant_walk.png", 150, 50, "Merchant"));
		
		
		// Set up game loop
		setupGameLoop();
		frame.add(scene, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		
		
		
		JPanel buttonPanel = new JPanel();
		
		JButton button1 = new JButton("Control Alchemist");
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Controlling Alchemist");
				
				player = scene.getSprite("Alchemist");
			}
			
		});
		
		JButton button2 = new JButton("Control Fisherman");
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Controlling Fisherman");
				player = scene.getSprite("Fisherman");
			}
			
		});
		
		JButton button3 = new JButton("Control Merchant");
		button3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Controlling Merchant");
				player = scene.getSprite("Merchant");
			}
			
		});
		
		buttonPanel.add(button1);
		buttonPanel.add(button2);
		buttonPanel.add(button3);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		
		isRunning = true;
		gameLoop.start();
	}
	
	
	// ============================================================
	//                      setupGameLoop()
	// ------------------------------------------------------------
	// - Creates the game loop
	// - Must be encapsulated in a Thread
	// - Timer must generate a separate thread to not block the UI
	//
	// ** Variable time-step **
	//
	// ============================================================
	private void setupGameLoop() {

		// Start a new thread
		gameLoop = new Thread(()-> {
			int FPS = 60;
			long timeBetweenUpdates = 1000000000/FPS;
			int maxUpdatesBetweenRender = 1;
			
			long lastUpdateTime = System.nanoTime();
			long currentTime = System.currentTimeMillis();
			
			while(isRunning) {
				long now = System.nanoTime();
				long elapsedTime = System.currentTimeMillis() - currentTime;
				currentTime += elapsedTime;
				int updateCount = 0;
				
				
				while(now - lastUpdateTime >= timeBetweenUpdates && updateCount < maxUpdatesBetweenRender) {
					this.scene.update(elapsedTime);
					lastUpdateTime += timeBetweenUpdates;
					++updateCount;
				}
				
				if(now - lastUpdateTime >= timeBetweenUpdates) {
					lastUpdateTime = now - timeBetweenUpdates;
				}
				
				this.scene.repaint();
				long lastRenderTime = now;
				
				while(now - lastRenderTime < timeBetweenUpdates && now - lastUpdateTime < timeBetweenUpdates) {
					Thread.yield();
					now = System.nanoTime();
				}
			}
			
		});
	}
	
// public:
	
	// =============== Main ====================
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Game::new);
	}
	// =========================================
	
	public Game() {
		showUI();
	}
}
