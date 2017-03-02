package coding.fun.maze;

import java.io.File;
import java.io.IOException;

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

}
