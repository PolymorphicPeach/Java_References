package scenes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

import sprites.Sprite;

public class Scene extends JPanel{
// private:
	private ArrayList<Sprite> sprites;
	
	
// protected:
	
	// =============================================
	//              paintComponent
	// ---------------------------------------------
	// Will be called in repaint() in the game loop.
	// =============================================
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		sprites.forEach(sprite-> {
			sprite.render(g2d);
		});
	}
	
// public:
	public Scene() {
		// Stop Swing from randomly repainting since this will use a game loop.
		this.setIgnoreRepaint(true);
		
		this.sprites = new ArrayList<>();
		
	}
	
	public void update(long elapsedTime) {
		sprites.forEach(sprite->{
			sprite.update(elapsedTime);
		});
	}
	
	// ===================================================
	//              getPreferredSize
	// ---------------------------------------------------
	// JPanel will have a default size of 0,0. 
	// So, this method will force the JPanel to resize.
	// ===================================================
	@Override 
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}
	
	
	public void add(Sprite s) {
		this.sprites.add(s);
	}
	
	public Sprite getSprite(String name) {
		if(!sprites.isEmpty()) {
			for(Sprite sprite : sprites) {
				if(sprite.getName() == name) {
					return sprite;
				}
			}
		}
		
		if(!sprites.isEmpty()) {
			return sprites.get(0);
		}
		else {
			return null;
		}
	}
	
}
