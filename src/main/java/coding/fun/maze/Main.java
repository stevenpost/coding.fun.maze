package coding.fun.maze;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import coding.fun.maze.solvers.MazeSolver;
import coding.fun.maze.solvers.RecursiveNodeSolver;
import coding.fun.maze.solvers.RecursiveSolver;

public class Main {

	private static final Logger LOG = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();

		File imageFile = new File("src/main/resources/tiny.png");
		File solvedMazeImage = new File("solved.png");

		MazeImageHandler imageHandler = new MazeImageHandler();

		boolean[][] maze = loadMaze(imageFile, imageHandler);

		List<Node> nodes = createNodes(maze);

		MazeSolver solver = new RecursiveSolver(maze);
		solveMaze(solver);

		MazeSolver nodeSolver = new RecursiveNodeSolver(nodes.get(0), maze.length);
		solveMaze(nodeSolver);

		writeOutput(solvedMazeImage, imageHandler, solver);

		long endtime = System.currentTimeMillis();
		LOG.info("Total runtime: " + (endtime - startTime) + " ms");
	}

	private static boolean[][] loadMaze(File imageFile, MazeImageHandler imageHandler) throws IOException {
		long startTime = System.currentTimeMillis();
		LOG.info("Start loading maze");
		boolean[][] maze = imageHandler.loadMaze(imageFile);
		long endTime = System.currentTimeMillis();
		LOG.info("Loaded maze in " + (endTime - startTime) + " ms");
		return maze;
	}

	private static List<Node> createNodes(boolean[][] maze) {
		long startTime = System.currentTimeMillis();
		NodeCreator creator = new NodeCreator(maze);
		List<Node> nodes = creator.createNodes();
		long endTime = System.currentTimeMillis();
		LOG.info("Created " + nodes.size() + " node(s) in " + (endTime - startTime) + " ms");
		return nodes;
	}

	private static void writeOutput(File outputImage, MazeImageHandler imageHandler,
	                                MazeSolver solver) throws IOException {
		long startTime = System.currentTimeMillis();
		imageHandler.writeOutputMaze(solver.getSolvedMaze(), outputImage);
		long endtime = System.currentTimeMillis();
		LOG.info("Output written in " + (endtime - startTime) + " ms");
	}

	private static void solveMaze(MazeSolver solver) {
		long startTime = System.currentTimeMillis();
		solver.solve();
		long endtime = System.currentTimeMillis();
		LOG.info("Solved maze in " + (endtime - startTime) + " ms");
		solver.printStatistics();
	}

}
