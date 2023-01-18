package sprites;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;



@SuppressWarnings("serial")
public class Sprite extends JLabel {
// private:
	
	enum Facing{
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
	
	Facing currentFacing;
	
	String name;
	private int x;
	private int y;
	private float speed;
	
	int animationSpeed;
	long animationTimer;
	
	private BufferedImage idleSpriteSheet;
	private BufferedImage movementSpriteSheet;
	private ArrayList<ArrayList<BufferedImage>> spriteFramesIdle;
	private ArrayList<ArrayList<BufferedImage>> spriteFramesMovement;
	
	private boolean idleState;
	
	int animationFrame;
	BufferedImage currentFrame;

	
// public:
	public boolean UP;
	public boolean DOWN;
	public boolean LEFT;
	public boolean RIGHT;
	
	public Sprite(String spriteSheetIdle, String spriteSheetMovement, int startingX, int startingY, String name) {
		
		try {
			idleSpriteSheet = ImageIO.read(new File(spriteSheetIdle));
			movementSpriteSheet = ImageIO.read(new File(spriteSheetMovement));
			
			spriteFramesIdle = new ArrayList<ArrayList<BufferedImage>>();
			spriteFramesMovement = new ArrayList<ArrayList<BufferedImage>>();
			
			for(int i = 0; i < 4; ++i) {
				spriteFramesIdle.add(new ArrayList<BufferedImage>());
				spriteFramesMovement.add(new ArrayList<BufferedImage>());
				
				for(int j = 0; j < 4; ++j) {
					spriteFramesIdle.get(i).add(idleSpriteSheet.getSubimage(j * 32, i * 48, 32, 48));
					spriteFramesMovement.get(i).add(movementSpriteSheet.getSubimage(j * 32, i * 48, 32, 48));
				}
			}
		}
		catch(IOException e) {
			System.out.println("Error" + e);
		}
		
		x = startingX;
		y = startingY;
		
		speed = 0.25f;
		idleState = true;
		
		// 0 - 3
		animationFrame = 0;
		
		animationSpeed = 100;
		animationTimer = 0;
		
		UP = false;
		DOWN = false;
		currentFacing = Facing.DOWN;
		LEFT = false;
		RIGHT = false;
		
		this.name = name;
		
	}
	
	public void update(long elapsedTime) {
		
		// ========== Handling Animations ============
		animationTimer += elapsedTime;
		
		if(animationTimer >= 100) {
			++animationFrame;
			animationTimer = 0;
		}
		
		if(animationFrame > 3) {
			animationFrame = 0;
		}
		// ===========================================
		
		if(UP) {
			currentFacing = Facing.UP;
		}
		else if(DOWN) {
			currentFacing = Facing.DOWN;
		}
		else if(LEFT) {
			currentFacing = Facing.LEFT;
		}
		else if(RIGHT) {
			currentFacing = Facing.RIGHT;
		}
		
		
		if(idleState) {
			
			switch(currentFacing){
			case UP:
				currentFrame = spriteFramesIdle.get(3).get(animationFrame);
				break;
				
			case DOWN:
				currentFrame = spriteFramesIdle.get(0).get(animationFrame);
				break;
				
			case LEFT:
				currentFrame = spriteFramesIdle.get(1).get(animationFrame);
				break;
				
			case RIGHT:
				currentFrame = spriteFramesIdle.get(2).get(animationFrame);
				break;
				
			}
			
		}
		else {
			
			switch(currentFacing){
			case UP:
				y -= speed * elapsedTime;
				currentFrame = spriteFramesMovement.get(3).get(animationFrame);
				break;
				
			case DOWN:
				y += speed * elapsedTime;
				currentFrame = spriteFramesMovement.get(0).get(animationFrame);
				break;
				
			case LEFT:
				x -= speed * elapsedTime;
				currentFrame = spriteFramesMovement.get(1).get(animationFrame);
				break;
				
			case RIGHT:
				x += speed * elapsedTime;
				currentFrame = spriteFramesMovement.get(2).get(animationFrame);
				break;
				
			}
		}
		
		
	}
	
	
	
	public void render(Graphics2D g2d) {
		
		g2d.drawImage(currentFrame, x, y, null);
		
		//BufferedImage spriteFrame = idleSpriteSheet.getSubimage(animationFrame * 32, 0, 32, 48);
		
		//g2d.drawImage(spriteFrame, this.x, this.y, null);
	}
	
	public void setIdleState(boolean s) {
		idleState = s;
	}
	
	public String getName() {
		return name;
	}
	
}
