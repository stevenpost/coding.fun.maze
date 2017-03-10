package coding.fun.maze;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

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

		boolean[][] maze = loadMaze(input, IMAGE_HANDLER);

		List<Node> nodes = createNodes(maze);

		File resursiveFolder = new File(outputParent, "recursive");
		resursiveFolder.mkdirs();
		File recursive = new File(resursiveFolder, input.getName());
		try {
			solveRecursive(maze, recursive);
		}
		catch (@SuppressWarnings("unused") StackOverflowError soe) {
			LOG.severe("Recursing to deep on " + input.getName());
		}

		File resursiveNodeFolder = new File(outputParent, "recursivenodes");
		resursiveNodeFolder.mkdirs();
		File recursiveNode = new File(resursiveNodeFolder, input.getName());
		try {
			solveRecursiveNode(maze, recursiveNode, nodes);
		}
		catch (@SuppressWarnings("unused") StackOverflowError soe) {
			LOG.severe("Recursing nodes to deep on " + input.getName());
		}
	}

	private static void solveRecursive(boolean[][] maze, File output) throws IOException {
		MazeSolver solver = new RecursiveSolver(maze);
		solveMazeTimed(solver);

		writeOutput(output, solver);
	}

	private static void solveRecursiveNode(boolean[][] maze, File output, List<Node> nodes) throws IOException {
		MazeSolver solver = new RecursiveNodeSolver((VisitableNode) nodes.get(0), maze.length, maze);
		solveMazeTimed(solver);

		writeOutput(output, solver);
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

	private static void writeOutput(File outputImage, MazeSolver solver) throws IOException {
		long startTime = System.currentTimeMillis();
		solver.writeSolutionImage(outputImage);
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
