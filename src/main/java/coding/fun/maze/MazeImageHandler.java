package coding.fun.maze;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class MazeImageHandler {

	public boolean[][] loadMaze(File file) throws IOException {
		BufferedImage img = ImageIO.read(file);
		return convertToBooleanArray(img);
	}

	public TileType[][] loadMazeTileType(File file) throws IOException {
		BufferedImage img = ImageIO.read(file);
		return convertToTileTypeArray(img);
	}

	private boolean[][] convertToBooleanArray(BufferedImage img) {
		boolean[][] array = new boolean[img.getHeight()][img.getWidth()];

		for(int row = 0; row < array.length; row++) {
			for(int col = 0; col < array[row].length; col++) {
				int pixel = img.getRGB(col, row);
				switch (pixel) {
					case -16777216:
						// Black
						array[row][col] = false;
						break;
					case -1:
						// White
						array[row][col] = true;
						break;
					default:
						throw new IllegalArgumentException("This is a strange pixel (" + row + ";" + col + "): " + pixel);
				}
			}
		}

		return array;
	}

	private TileType[][] convertToTileTypeArray(BufferedImage img) {
		TileType[][] array = new TileType[img.getHeight()][img.getWidth()];

		for(int row = 0; row < array.length; row++) {
			for(int col = 0; col < array[row].length; col++) {
				int pixel = img.getRGB(col, row);
				switch (pixel) {
					case -16777216:
						// Black
						array[row][col] = TileType.WALL;
						break;
					case -1:
						// White
						array[row][col] = TileType.FREE;
						break;
					default:
						throw new IllegalArgumentException("This is a strange pixel (" + row + ";" + col + "): " + pixel);
				}
			}
		}

		return array;
	}

	public void writeOutputMaze(TileType[][] solvedMaze, File outputfile) throws IOException {
		BufferedImage outputImg = new BufferedImage(solvedMaze[0].length, solvedMaze.length, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < outputImg.getWidth(); x++) {
			for (int y = 0; y < outputImg.getHeight(); y++) {
				setMatchingColour(solvedMaze, outputImg, x, y);
			}
		}
		ImageIO.write(outputImg, "png", outputfile);
	}

	private void setMatchingColour(TileType[][] solvedMaze, BufferedImage outputImg, int x, int y) {
		switch (solvedMaze[y][x]) {
			case FREE:
				outputImg.setRGB(x, y, Color.white.getRGB());
				break;
			case WALL:
				outputImg.setRGB(x, y, Color.black.getRGB());
				break;
			case VISITED:
				outputImg.setRGB(x, y, Color.red.getRGB());
				break;
			default:
				throw new IllegalArgumentException("WTF?");
		}
	}

	public void writeMaze(boolean[][] maze, File outputfile) throws IOException {
		BufferedImage outputImg = new BufferedImage(maze[0].length, maze.length, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < outputImg.getWidth(); x++) {
			for (int y = 0; y < outputImg.getHeight(); y++) {
				setMatchingColour(maze, outputImg, x, y);
			}
		}
		ImageIO.write(outputImg, "png", outputfile);
	}

	private void setMatchingColour(boolean[][] maze, BufferedImage outputImg, int x, int y) {
		if (maze[y][x]) {
			outputImg.setRGB(x, y, Color.white.getRGB());
		}
		else {
			outputImg.setRGB(x, y, Color.black.getRGB());
		}
	}

	public void writeSolutionForNodes(boolean[][] maze, File output, List<? extends Node> solutionNodes) throws IOException {
		BufferedImage outputImg = new BufferedImage(maze[0].length, maze.length, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < outputImg.getWidth(); x++) {
			for (int y = 0; y < outputImg.getHeight(); y++) {
				setMatchingColour(maze, outputImg, x, y);
			}
		}

		Node currentNode;
		Node previousNode = solutionNodes.get(0);
		for (Node n : solutionNodes) {
			currentNode = n;
			Position pos = currentNode.getPosition();
			Position previousPos = previousNode.getPosition();

			if (maze.length == 401) {
				System.out.println("Now coloring " + pos.toString());
			}
			outputImg.setRGB(pos.getX(), pos.getY(), Color.RED.getRGB());

			fillBetweenNodes(outputImg, pos, previousPos);

			previousNode = currentNode;
		}

		ImageIO.write(outputImg, "png", output);

	}

	private void fillBetweenNodes(BufferedImage outputImg, Position pos, Position previousPos) {
		// fill in between nodes
		int startX;
		int endX;
		if (previousPos.getX() < pos.getX()) {
			startX = previousPos.getX();
			endX = pos.getX();
		}
		else {
			startX = pos.getX();
			endX = previousPos.getX();
		}

		int startY;
		int endY;
		if (previousPos.getY() < pos.getY()) {
			startY = previousPos.getY();
			endY = pos.getY();
		}
		else {
			startY = pos.getY();
			endY = previousPos.getY();
		}

		for (int x = startX; x < endX; x++) {
			outputImg.setRGB(x, startY, Color.RED.getRGB());
		}
		for (int y = startY; y < endY; y++) {
			outputImg.setRGB(startX, y, Color.RED.getRGB());
		}
	}

}
