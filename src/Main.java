import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.LinkedList;

public class Main extends Applet implements Runnable, KeyListener
{
	public static final boolean DEBUG = false;

	private static final long serialVersionUID = -6800175946442857266L;

	// applet details and running vars
	// -------------------------------------------------------------------------------

	private int width = 800;
	private int height = 600;
	private Graphics gg;
	private Image ii;
	private Thread thread = new Thread(this);
	public static Main main;
	public static float zoom = 1.0f;
	public static float minZoom = .5f;
	public static float maxZoom = 1.5f;

	// other vars
	// -------------------------------------------------------------------------------

	private long previousTime = System.currentTimeMillis();
	private LinkedList<Long> times = new LinkedList<Long>();

	private int x = 0;
	private int y = 0;

	private LinkedList<Chunk> chunks = new LinkedList<Chunk>();

	private HashSet<Character> keys = new HashSet<Character>();

	private int stepMultiplier = 1;
	private int moveStep = 1;

	// Overridden methods
	// --------------------------------------------------------------------------------

	@Override
	public void init()
	{
		int x = 0;
		int y = 0;
		int chunkSize = (int) (Chunk.tileSize * Tile.TILE_SIZE * zoom);
		for (int i = 0; i < width / (Chunk.tileSize * Tile.TILE_SIZE * zoom); i++)
		{
			y = 0;
			for (int j = 0; j < height / (Chunk.tileSize * Tile.TILE_SIZE * zoom); j++)
			{
				chunks.add(new Chunk(x, y, i, j));
				Chunk.chunkSize = chunkSize;
				y += chunkSize;
			}
			x += chunkSize;
		}

		if (main == null)
		{
			main = this;
		}

		addKeyListener(this);

		this.setSize(width, height);
		this.setBackground(Color.GRAY);
	}

	@Override
	public void start()
	{
		thread.start();
	}

	@Override
	public void run()
	{
		while (true)
		{
			manageKeyboardUI();
			repaint();

			try
			{
				Thread.sleep(16);
			}
			catch (Exception e)
			{
				// pass
			}
		}
	}

	@Override
	public void update(Graphics g)
	{
		doubleBuffer(g);
	}

	@Override
	public void paint(Graphics g)
	{
		for (Chunk c : chunks)
		{
			if (DEBUG)
			{
				if (c.getX() >= x && c.getX() <= x + width - Chunk.chunkSize)
				{
					if (c.getY() >= y && c.getY() <= y + height - Chunk.chunkSize)
					{
						c.paint(g);
					}
				}
			}
			else
			{
				if (c.getX() >= x - Chunk.chunkSize && c.getX() <= x + width)
				{
					if (c.getY() >= y - Chunk.chunkSize && c.getY() <= y + height)
					{
						c.paint(g);
					}
				}
			}
		}

		if (true)
		{
			g.drawString("Pos: " + x + "," + y, 10, 20);
			g.drawString("Keys: " + keys.toString(), 10, 40);
			g.drawString("FPS: " + calcFPS(), 10, 60);
			g.drawString("Zoom: " + zoom, 10, 80);
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		keys.add(new Character(Character.toLowerCase(e.getKeyChar())));
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		keys.remove(new Character(Character.toLowerCase(e.getKeyChar())));
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
	}

	// Functional methods
	// --------------------------------------------------------------------------

	private void manageKeyboardUI()
	{
		if (isPressed((char) 65535))
			stepMultiplier = 5;
		else
			stepMultiplier = 1;
		if (isPressed('a') || isPressed('A'))
			x -= moveStep * stepMultiplier;
		else if (isPressed('d') || isPressed('D'))
			x += moveStep * stepMultiplier;
		if (isPressed('w') || isPressed('W'))
			y -= moveStep * stepMultiplier;
		else if (isPressed('s') || isPressed('S'))
			y += moveStep * stepMultiplier;
		// manage zoom
		if (isPressed('q') || isPressed('Q'))
		{
			if (zoom < maxZoom)
			{
				zoom += .01;
				zoom = zoom * 100;
				zoom = Math.round(zoom);
				zoom /= 100;
				for (Chunk chunk : chunks)
				{
					Chunk.chunkSize = (int) (Chunk.tileSize * Tile.TILE_SIZE * zoom);
					chunk.update();
				}
			}
		}
		else if (isPressed('e') || isPressed('E'))
		{
			if (zoom > minZoom)
			{
				zoom -= .01;
				zoom = zoom * 100;
				zoom = Math.round(zoom);
				zoom /= 100;
				for (Chunk chunk : chunks)
				{
					Chunk.chunkSize = (int) (Chunk.tileSize * Tile.TILE_SIZE * zoom);
					chunk.update();
				}
			}
		}
		// reset zoom
		if (isPressed('r') || isPressed('R'))
		{
			zoom = 1.0f;
			for (Chunk chunk : chunks)
			{
				Chunk.chunkSize = (int) (Chunk.tileSize * Tile.TILE_SIZE * zoom);
				chunk.update();
			}
		}
	}

	private boolean isPressed(char c)
	{
		for (Character element : keys)
		{
			if (c == element)
			{
				return true;
			}
		}
		return false;
	}

	private void doubleBuffer(Graphics g)
	{
		if (ii == null)
		{
			ii = createImage(this.getWidth(), this.getHeight());
			gg = ii.getGraphics();
		}

		gg.setColor(getBackground());
		gg.fillRect(0, 0, this.getWidth(), this.getHeight());

		gg.setColor(getForeground());
		paint(gg);

		g.drawImage(ii, 0, 0, this);
	}

	public long calcFPS()
	{
		times.add(System.currentTimeMillis() - previousTime);
		if (times.size() > 60)
		{
			times.removeFirst();
		}
		previousTime = System.currentTimeMillis();
		long totalTime = 0;
		for (Long t : times)
		{
			totalTime += t;
		}

		return 1000 / (totalTime / times.size());
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

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
}
