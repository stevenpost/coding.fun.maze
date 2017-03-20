package coding.fun.maze;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import ar.com.hjg.pngj.FilterType;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineHelper;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.pixels.PixelsWriterDefault;

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

	public void writeSolutionForNodes(File input, File output, List<? extends Node> solutionNodes) throws IOException {
		BufferedImage inputImg = ImageIO.read(input);
		int height = inputImg.getHeight();
		int width = inputImg.getWidth();


		try (OutputStream os = new BufferedOutputStream(new FileOutputStream(output))) {
			PngWriter png = new PngWriter(os, new ImageInfo(width, height, 8, false));
			((PixelsWriterDefault) png.getPixelsWriter()).setFilterType(FilterType.FILTER_AVERAGE);
			png.setCompLevel(6);

			BufferedImage intermediateImage = copyMazeImage(inputImg, height, width);
			drawSolution(solutionNodes, intermediateImage);
			writeMazeImage(intermediateImage, height, width, png);

			png.end();
		}
	}

	private BufferedImage copyMazeImage(BufferedImage inputImg, int height, int width) {
		BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		final WritableRaster raster = inputImg.getRaster();

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if (isPassable(x, y, raster)) {
					outputImg.setRGB(x, y, Color.WHITE.getRGB());
				}
				else {
					outputImg.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}

		return outputImg;
	}

	private boolean isPassable(int x, int y, final WritableRaster raster) {
		Object o = raster.getDataElements(x, y, null);
		if (o instanceof byte[]) {
			byte color = ((byte[])o)[0];
			if (color == 0) {
				return false;
			}
			return true;
		}

		throw new IllegalArgumentException("This wasn't an array as expected");
	}

	private void writeMazeImage(BufferedImage inputImg, int height, int width, PngWriter png) {
		ImageLineInt iline1 = new ImageLineInt(png.imgInfo);
		ImageLineInt iline = iline1;
		final WritableRaster raster = inputImg.getRaster();
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				final int color = getColorForSolutionImage(x, y, raster);
				ImageLineHelper.setPixelRGB8(iline1, x, color);
			}
			png.writeRow(iline, y);
		}
	}

	private int getColorForSolutionImage(int x, int y, final WritableRaster raster) {
		Object o = raster.getDataElements(x, y, null);
		if (o instanceof int[]) {
			return ((int[])o)[0];
		}

		throw new IllegalArgumentException("This wasn't an array as expected" + o.getClass());
	}

	private void drawSolution(List<? extends Node> solutionNodes, BufferedImage outputImg) {
		Node currentNode;
		Node previousNode = solutionNodes.get(0);
		for (Node n : solutionNodes) {
			currentNode = n;
			Position pos = currentNode.getPosition();
			Position previousPos = previousNode.getPosition();

			outputImg.setRGB(pos.getX(), pos.getY(), Color.RED.getRGB());

			fillBetweenNodes(outputImg, pos, previousPos);

			previousNode = currentNode;
		}
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
