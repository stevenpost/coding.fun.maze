package coding.fun.maze;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import coding.fun.maze.solvers.MazeSolver;
import coding.fun.maze.solvers.RecursiveSolver;

public class Main {

	private static final Logger LOG = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws IOException {
		File imageFile = new File("src/main/resources/tiny.png");
		File solvedMazeImage = new File("solved.png");

		MazeImageHandler imageHandler = new MazeImageHandler();

		LOG.info("Start loading maze");
		long startTime = System.currentTimeMillis();
		boolean[][] array = imageHandler.loadMaze(imageFile);
		long endMazeLoadingTime = System.currentTimeMillis();
		LOG.info("Loaded maze in " + (endMazeLoadingTime - startTime) + " ms");

		NodeCreator creator = new NodeCreator(array);
		List<Node> nodes = creator.createNodes();
		long endNodeCreationTime = System.currentTimeMillis();
		LOG.info("Created " + nodes.size() + " node(s) in " + (endNodeCreationTime - endMazeLoadingTime) + " ms");

		MazeSolver solver = new RecursiveSolver(array);
		solver.solve();
		long endMazeSolvingTime = System.currentTimeMillis();
		LOG.info("Solved maze in " + (endMazeSolvingTime - endNodeCreationTime) + " ms");
		solver.printStatistics();

		imageHandler.writeOutputMaze(solver.getSolvedMaze(), solvedMazeImage);
		long endOutputWritingTime = System.currentTimeMillis();
		LOG.info("Output written in " + (endOutputWritingTime - endMazeSolvingTime) + " ms");

		LOG.info("Total runtime: " + (endOutputWritingTime - startTime) + " ms");
	}

}
