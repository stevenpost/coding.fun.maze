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

}
