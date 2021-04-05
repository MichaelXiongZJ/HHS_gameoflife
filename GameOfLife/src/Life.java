import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import processing.core.PApplet;

/**

	Represents a Game Of Life grid.

	Coded by:
	Modified on:

*/

public class Life {

	// Add a 2D array field to represent the Game Of Life grid.
	private boolean [][] grid;
	
	
	/**
	 * Initialized the Game of Life grid to an empty 20x20 grid.
	 */
	public Life() {
		grid = new boolean[20][20];
	}

	
	
	/**
	 * Initializes the Game of Life grid to a 20x20 grid and fills it with data from the file given.
	 * 
	 * @param filename The path to the text file.
	 */
	public Life(String filename) {
		grid = new boolean[20][20];
		this.readData(filename, grid);
	//	System.out.print(this);
	}

	
	
	/**
	 * Runs a single step within the Game of Life by applying the Game of Life rules on
	 * the grid.
	 */
	public void step() {
		boolean [][] temp = grid;
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[0].length; j++) {
				int count = countNeighbors(i, j);
				System.out.print(count+" ");
				if(count == 3) {
					temp[i][j] = true;
				}else if(count <= 1|| count >= 4) {
					temp[i][j] = false;
				}
			}
			System.out.print("\n");
		}
		grid = temp;
		
//		System.out.print(toString());
//
		System.out.println("\n");
	}

	
	/**
	 * Runs n steps within the Game of Life.
	 * @param n The number of steps to run.
	 */
	public void step(int n) {
		for(int a=0; a<n; a++) {
			step();
		}
	}
	
	/**
	 * count the number of neighbors
	 * @param i y coordinate 
	 * @param j x coordinate
	 * @return count the number of neighbors
	 */
	public int countNeighbors(int i, int j) {
		int count = 0;
		for(int a=i-1; a<=i+1; a++) {
			for(int b=j-1; b<=j+1; b++) {
				if((a>=0) && (b>=0) && (a<grid.length) && (b<grid[0].length)) {
					if(grid[a][b]) {
						count++;
					}
				}
			}
		}
		if(grid[i][j]) {
			count--;
		}
		return count;
	}
	
	/**
	 * Formats this Life grid as a String to be printed (one call to this method returns the whole multi-line grid)
	 * 
	 * @return The grid formatted as a String.
	 */
	public String toString() {
		String output = "";
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[0].length; j++) {
				if(grid[i][j]) {
					output += "* ";
				}else {
					output += "- ";
				}
			}
			output += "\n";
		}
		return output;
	}
	
	
	
	/**
	 * (Graphical UI)
	 * Draws the grid on a PApplet.
	 * The specific way the grid is depicted is up to the coder.
	 * 
	 * @param marker The PApplet used for drawing.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 */
	public void draw(PApplet marker, float x, float y, float width, float height) {
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[0].length; j++) {
				if(grid[i][j]) {
					marker.fill(250, 250, 0);;
				}else {
					marker.fill(170);
				}
				float rectWidth = width/grid[0].length;
				float rectHeight = height/grid.length;
				float rectX = x + rectWidth*j;
				float rectY = y + rectHeight*i;
				marker.rect(rectX, rectY, rectWidth, rectHeight);
			}
		}	
	}
	
	
	
	/**
	 * (Graphical UI)
	 * Determines which element of the grid matches with a particular pixel coordinate.
	 * This supports interaction with the grid using mouse clicks in the window.
	 * 
	 * @param p A Point object containing a graphical pixel coordinate.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 * @return A Point object representing a coordinate within the game of life grid.
	 */
	public Point clickToIndex(Point p, float x, float y, float width, float height) {
		int i = (int) ((p.y - y)*grid.length/height);
		int j = (int) ((p.x - y)*grid[0].length/width);
		Point coordinates = new Point(i, j);
		return coordinates;
	}
	
	/**
	 * (Graphical UI)
	 * Toggles a cell in the game of life grid between alive and dead.
	 * This allows the user to modify the grid.
	 * 
	 * @param i The x coordinate of the cell in the grid.
	 * @param j The y coordinate of the cell in the grid.
	 */
	public void toggleCell(int i, int j) {
		grid[i][j] = !grid[i][j];
	}

	

	// Reads in array data from a simple text file containing asterisks (*)
	public void readData (String filename, boolean[][] gameData) {
		File dataFile = new File(filename);

		if (dataFile.exists()) {
			int count = 0;

			FileReader reader = null;
			Scanner in = null;
			try {
				reader = new FileReader(dataFile);
				in = new Scanner(reader);

				while (in.hasNext()) {
					String line = in.nextLine();
					for(int i = 0; i < line.length(); i++)
						if (count < gameData.length && i < gameData[count].length && line.charAt(i)=='*')
							gameData[count][i] = true;

					count++;
				}
			} catch (IOException ex) {
				throw new IllegalArgumentException("Data file " + filename + " cannot be read.");
			} finally {
				if (in != null)
					in.close();
			}

		} else {
			throw new IllegalArgumentException("Data file " + filename + " does not exist.");
		}
	}



	
	
}