import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile
{
	private static final boolean DEBUG = true;
	public static final int TILE_SIZE = 32;
	
	private Image texture;
	public int x;
	public int y;

	public Tile()
	{
		loadTexture();
	}

	public Tile(Tile t)
	{
		loadTexture();
		setX(t.getX());
		setY(t.getY());
	}

	public Tile(int x, int y)
	{
		loadTexture();
		setX(x);
		setY(y);
	}
	
	public Tile(int x, int y, Image texture)
	{
		setTexture(texture);
		setX(x);
		setY(y);
	}

	public void loadTexture()
	{
		try
		{
			texture  = ImageIO.read(new File("../res/null.png"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update()
	{

	}

	public void paint(Chunk c, Graphics g)
	{
		if (DEBUG && c.getX() == x && c.getY() == y)
		{
			g.setColor(Color.RED);
			g.fillRect(x - Main.main.getX(), y - Main.main.getY(), (int) (TILE_SIZE * Main.scale), (int) (TILE_SIZE * Main.scale));
		}
		else
		{
			g.setColor(Color.BLUE);
			if(texture != null)
			{
				g.drawImage(texture, x - Main.main.getX(), y - Main.main.getY(), (int) (TILE_SIZE * Main.scale), (int) (TILE_SIZE * Main.scale),
						null);
			}
			else
			{
				g.drawRect(x - Main.main.getX(), y - Main.main.getY(), (int) (TILE_SIZE * Main.scale), (int) (TILE_SIZE * Main.scale));
			}
		}
	}

	// Getters and Setters
	// ------------------------------------------------

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Image getTexture()
	{
		return texture;
	}

	public void setTexture(Image texture)
	{
		this.texture = texture;
	}
}
