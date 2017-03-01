package coding.fun.maze;

import java.io.File;
import java.io.IOException;

import coding.fun.maze.RecursiveSolver.TileType;

public class Main {

	public static void main(String[] args) throws IOException {
		File imageFile = new File("src/main/resources/tiny.png");
		File solvedMazeImage = new File("solved.png");

		MazeImageHandler imageHandler = new MazeImageHandler();

		boolean[][] array = imageHandler.loadMaze(imageFile);

		MazeSolver solver = new RecursiveSolver(array);
		solver.solve();

		imageHandler.writeOutputMaze(solver.getSolvedMaze(), solvedMazeImage);

		System.out.println("done");
	}

	private static void printArray(boolean[][] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				System.out.print("[" + array[i][j] + "]");
			}
			System.out.println();
		}
	}

	private static void printArray(TileType[][] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				System.out.print("[" + array[i][j] + "]");
			}
			System.out.println();
		}
	}

}
