package coding.fun.maze;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MazeImageHandler {

	public boolean[][] loadMaze(File file) throws IOException {
		BufferedImage img = ImageIO.read(file);
		return convertToBooleanArray(img);
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

	public void writeOutputMaze(TileType[][] solvedMaze, File outputfile) throws IOException {
		BufferedImage outputImg = new BufferedImage(solvedMaze[0].length, solvedMaze.length, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < outputImg.getWidth(); x++) {
			for (int y = 0; y < outputImg.getHeight(); y++) {
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
		}
		ImageIO.write(outputImg, "png", outputfile);
	}

	public void writeMaze(boolean[][] maze, File outputfile) throws IOException {
		BufferedImage outputImg = new BufferedImage(maze[0].length, maze.length, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < outputImg.getWidth(); x++) {
			for (int y = 0; y < outputImg.getHeight(); y++) {
				if (maze[y][x]) {
					outputImg.setRGB(x, y, Color.white.getRGB());
				}
				else {
					outputImg.setRGB(x, y, Color.black.getRGB());
				}
			}
		}
		ImageIO.write(outputImg, "png", outputfile);
	}

	public void writeSolutionForNodes(boolean[][] maze, File output, Node startNode) throws IOException {
		BufferedImage outputImg = new BufferedImage(maze[0].length, maze.length, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < outputImg.getWidth(); x++) {
			for (int y = 0; y < outputImg.getHeight(); y++) {
				if (maze[y][x]) {
					outputImg.setRGB(x, y, Color.white.getRGB());
				}
				else {
					outputImg.setRGB(x, y, Color.black.getRGB());
				}
			}
		}

		Node currentNode = startNode;
		Node previousNode = startNode;
		while (currentNode != null) {
			Position pos = currentNode.getPosition();
			Position previousPos = previousNode.getPosition();
			outputImg.setRGB(pos.getX(), pos.getY(), Color.RED.getRGB());

			fillBetweenNodes(outputImg, pos, previousPos);

			previousNode = currentNode;
			currentNode = getNextNode(currentNode);
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

	private Node getNextNode(Node currentNode) {
		Node nextNode;

		// down
		nextNode = currentNode.getLinkDown();
		if (nextNode != null) {
			currentNode.unlinkDown();
			return nextNode;
		}

		// right
		nextNode = currentNode.getLinkRight();
		if (nextNode != null) {
			currentNode.unlinkRight();
			return nextNode;
		}

		// left
		nextNode = currentNode.getLinkLeft();
		currentNode.unlinkLeft();
		if (nextNode != null) {
			return nextNode;
		}

		// up
		nextNode = currentNode.getLinkUp();
		currentNode.unlinkUp();
		if (nextNode != null) {
			return nextNode;
		}

		return null;
	}

}
