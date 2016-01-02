import java.applet.Applet;
import java.awt.Graphics;

public class Chunk
{
	public static final boolean DEBUG = false;

	public static final int tileSize = 8;
	public static int chunkSize = 0;

	private int x = 0;
	private int y = 0;
	private int chunkX = 0;
	private int chunkY = 0;

	private Tile[][] tiles = new Tile[tileSize][tileSize];

	// Constructors
	// -----------------------------------------------------------

	public Chunk(int x, int y, Tile[][] tiles)
	{
		setX(x);
		setY(y);
		setTiles(tiles);
		for (int r = 0; r < tileSize; r++)
		{
			for (int c = 0; c < tileSize; c++)
			{
				tiles[r][c].setX(x + r * (int) (Tile.TILE_SIZE * Main.zoom));
				tiles.clone()[r][c].setY(y + c * (int) (Tile.TILE_SIZE * Main.zoom));
			}
		}
	}

	public Chunk(int x, int y, int chunkX, int chunkY)
	{
		if (DEBUG)
			System.out.println("initializing chunk");
		setChunkX(chunkX);
		setChunkY(chunkY);
		setX(x);
		setY(y);
		for (int r = 0; r < tileSize; r++)
		{
			for (int c = 0; c < tileSize; c++)
			{
				tiles[r][c] = new Tile();
				tiles[r][c].setX(x + r * (int) (Tile.TILE_SIZE * Main.zoom));
				tiles.clone()[r][c].setY(y + c * (int) (Tile.TILE_SIZE * Main.zoom));
				if (DEBUG)
					System.out.println(r + "," + c + " | " + tiles[r][c].getX() + "," + tiles[r][c].getY());
			}
		}
	}

	public Chunk()
	{
		if (DEBUG)
			System.out.println("initializing chunk");
		for (int r = 0; r < tileSize; r++)
		{
			for (int c = 0; c < tileSize; c++)
			{
				tiles[r][c] = new Tile();
				tiles[r][c].setX(x + r * (int) (Tile.TILE_SIZE * Main.zoom));
				tiles.clone()[r][c].setY(y + c * (int) (Tile.TILE_SIZE * Main.zoom));
				if (DEBUG)
					System.out.println(r + "," + c + " | " + tiles[r][c].getX() + "," + tiles[r][c].getY());
			}
		}
	}

	// Functional methods
	// -------------------------------------------------

	public void update()
	{
		x = chunkX * chunkSize;
		y = chunkY * chunkSize;
		for (int r = 0; r < tileSize; r++)
		{
			for (int c = 0; c < tileSize; c++)
			{
				tiles[r][c].setX(x + r * (int) (Tile.TILE_SIZE * Main.zoom));
				tiles.clone()[r][c].setY(y + c * (int) (Tile.TILE_SIZE * Main.zoom));
			}
		}
	}

	public void paint(Graphics g)
	{
		for (Tile[] tRow : tiles)
		{
			for (Tile t : tRow)
			{
				t.paint(this, g);
			}
		}
	}

	// Getters and Setters
	// -----------------------------------------

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

	public Tile[][] getTiles()
	{
		Tile[][] newTiles = new Tile[tileSize][tileSize];
		for (int r = 0; r < tileSize; r++)
		{
			for (int c = 0; c < tileSize; c++)
			{
				newTiles[r][c] = new Tile(tiles[r][c]);
			}
		}
		return newTiles;
	}

	public void setTiles(Tile[][] tiles)
	{
		Tile[][] newTiles = new Tile[tileSize][tileSize];
		for (int r = 0; r < tileSize; r++)
		{
			for (int c = 0; c < tileSize; c++)
			{
				newTiles[r][c] = new Tile(tiles[r][c]);
			}
		}
		this.tiles = newTiles;
	}

	public int getChunkX()
	{
		return chunkX;
	}

	public void setChunkX(int chunkX)
	{
		this.chunkX = chunkX;
	}

	public int getChunkY()
	{
		return chunkY;
	}

	public void setChunkY(int chunkY)
	{
		this.chunkY = chunkY;
	}
}
