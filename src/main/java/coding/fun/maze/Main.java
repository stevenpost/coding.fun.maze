package coding.fun.maze;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import coding.fun.maze.solvers.DijkstraSolver;
import coding.fun.maze.solvers.MazeSolver;
import coding.fun.maze.solvers.RecursiveNodeSolver;
import coding.fun.maze.solvers.RecursiveSolver;

public class Main {

	private static final Logger LOG = Logger.getLogger(Main.class.getName());
	private static final MazeImageHandler IMAGE_HANDLER = new MazeImageHandler();

	public static void main(String[] args) throws IOException {

		long startTime = System.currentTimeMillis();

		File inputFolder = new File("src/main/resources/input");
		File outputParent = new File("src/main/resources/output");
		File[] filelist = inputFolder.listFiles();
		Arrays.sort(filelist, new FileNameComparator());
		for (File input : filelist) {
			solveMazeWithAllAlgorithms(outputParent, input);
		}

		long endtime = System.currentTimeMillis();
		LOG.info("Total runtime: " + (endtime - startTime) + " ms");
	}

	private static void solveMazeWithAllAlgorithms(File outputParent, File input) throws IOException {
		LOG.info("Attempting " + input.getName());

		solveRecursive(input, outputParent);

		solveRecursiveNode(input, outputParent);

		solveDijkstra(input, outputParent);
	}

	private static void solveRecursive(File input, File outputParent) throws IOException {
		TileType[][] maze = loadMazeTileType(input, IMAGE_HANDLER);

		File resursiveFolder = new File(outputParent, "recursive");
		resursiveFolder.mkdirs();
		File output = new File(resursiveFolder, input.getName());
		try {
			MazeSolver solver = new RecursiveSolver(maze);
			solveMazeTimed(solver);

			writeOutput(input, output, solver);
		}
		catch (@SuppressWarnings("unused") StackOverflowError soe) {
			LOG.severe("Recursing to deep on " + input.getName());
		}


	}

	private static void solveRecursiveNode(File input, File outputParent) throws IOException {
		boolean[][] maze = loadMaze(input, IMAGE_HANDLER);
		File resursiveNodeFolder = new File(outputParent, "recursivenodes");
		resursiveNodeFolder.mkdirs();
		File output = new File(resursiveNodeFolder, input.getName());
		Node startNode = createNodes(maze);

		try {
			MazeSolver solver = new RecursiveNodeSolver((VisitableNode) startNode, maze.length);
			maze = null;
			solveMazeTimed(solver);

			writeOutput(input, output, solver);
		}
		catch (@SuppressWarnings("unused") StackOverflowError soe) {
			LOG.severe("Recursing nodes to deep on " + input.getName());
		}

	}

	private static void solveDijkstra(File input, File outputParent) throws IOException {
		boolean[][] maze = loadMaze(input, IMAGE_HANDLER);
		File resursiveNodeFolder = new File(outputParent, "dijkstra");
		resursiveNodeFolder.mkdirs();
		File output = new File(resursiveNodeFolder, input.getName());
		DijkstraNode startNode = createNodesDijkstra(maze);
		maze = null;
		try {
			MazeSolver solver = new DijkstraSolver(startNode);
			solveMazeTimed(solver);

			writeOutput(input, output, solver);
		}
		catch (@SuppressWarnings("unused") StackOverflowError soe) {
			LOG.severe("Recursing nodes to deep on " + input.getName());
		}

	}

	private static DijkstraNode createNodesDijkstra(boolean[][] maze) {
		long startTime = System.currentTimeMillis();
		NodeCreator creator = new NodeCreator(maze);
		DijkstraNode startNode = creator.createDijkstraNodes();
		long endTime = System.currentTimeMillis();
		int nrOfNodes = creator.getNumberOfCreateNodes();
		LOG.info("Created " + nrOfNodes + " node(s) in " + (endTime - startTime) + " ms");
		return startNode;
	}

	private static boolean[][] loadMaze(File imageFile, MazeImageHandler imageHandler) throws IOException {
		long startTime = System.currentTimeMillis();
		LOG.info("Start loading maze");
		boolean[][] maze = imageHandler.loadMaze(imageFile);
		long endTime = System.currentTimeMillis();
		LOG.info("Loaded maze in " + (endTime - startTime) + " ms");
		return maze;
	}

	private static TileType[][] loadMazeTileType(File imageFile, MazeImageHandler imageHandler) throws IOException {
		long startTime = System.currentTimeMillis();
		LOG.info("Start loading maze");
		TileType[][] maze = imageHandler.loadMazeTileType(imageFile);
		long endTime = System.currentTimeMillis();
		LOG.info("Loaded maze in " + (endTime - startTime) + " ms");
		return maze;
	}

	private static Node createNodes(boolean[][] maze) {
		long startTime = System.currentTimeMillis();
		LOG.info("Start creating nodes");
		NodeCreator creator = new NodeCreator(maze);
		Node startNode = creator.createNodes();
		long endTime = System.currentTimeMillis();
		int nrOfNodes = creator.getNumberOfCreateNodes();
		LOG.info("Created " + nrOfNodes + " node(s) in " + (endTime - startTime) + " ms");
		return startNode;
	}

	private static void writeOutput(File inputImage, File outputImage, MazeSolver solver) throws IOException {
		long startTime = System.currentTimeMillis();
		solver.writeSolutionImage(inputImage, outputImage);
		long endtime = System.currentTimeMillis();
		LOG.info("Output written in " + (endtime - startTime) + " ms");
	}

	private static void solveMazeTimed(MazeSolver solver) {
		long startTime = System.currentTimeMillis();
		solver.solve();
		long endtime = System.currentTimeMillis();
		LOG.info("Solved maze in " + (endtime - startTime) + " ms");
		solver.printStatistics();
	}

}
