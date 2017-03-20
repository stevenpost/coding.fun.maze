package coding.fun.maze;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coding.fun.maze.solvers.AStarSolver;
import coding.fun.maze.solvers.DijkstraSolver;
import coding.fun.maze.solvers.MazeSolver;
import coding.fun.maze.solvers.RecursiveNodeSolver;
import coding.fun.maze.solvers.RecursiveSolver;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
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

		System.gc();
		solveRecursive(input, outputParent);

		System.gc();
		solveRecursiveNode(input, outputParent);

		System.gc();
		solveDijkstra(input, outputParent);

		System.gc();
		solveAStar(input, outputParent);
	}

	private static void solveRecursive(File input, File outputParent) throws IOException {
		TileType[][] maze = loadMazeTileType(input, IMAGE_HANDLER);

		File resursiveFolder = new File(outputParent, "recursive");
		resursiveFolder.mkdirs();
		File output = new File(resursiveFolder, input.getName());
		MazeSolver solver = new RecursiveSolver(maze);
		try {
			solver.solve();
			solver.writeSolutionImage(input, output);
		}
		catch (@SuppressWarnings("unused") StackOverflowError soe) {
			LOG.error("Recursing to deep on " + input.getName());
		}
		solver.printStatistics();


	}

	private static void solveRecursiveNode(File input, File outputParent) throws IOException {
		File resursiveNodeFolder = new File(outputParent, "recursivenodes");
		resursiveNodeFolder.mkdirs();
		File output = new File(resursiveNodeFolder, input.getName());
		VisitableNode startNode = (VisitableNode) createNodes(input, VisitableNode.class);

		MazeSolver solver = new RecursiveNodeSolver(startNode);
		try {
			solver.solve();
			solver.writeSolutionImage(input, output);
		}
		catch (@SuppressWarnings("unused") StackOverflowError soe) {
			LOG.error("Recursing nodes to deep on " + input.getName());
		}
		solver.printStatistics();
	}

	private static void solveDijkstra(File input, File outputParent) throws IOException {
		File resursiveNodeFolder = new File(outputParent, "dijkstra");
		resursiveNodeFolder.mkdirs();
		File output = new File(resursiveNodeFolder, input.getName());
		DijkstraNode startNode = (DijkstraNode) createNodes(input, DijkstraNode.class);
		MazeSolver solver = new DijkstraSolver(startNode);
		solver.solve();
		solver.printStatistics();
		solver.writeSolutionImage(input, output);
	}

	private static void solveAStar(File input, File outputParent) throws IOException {
		File resursiveNodeFolder = new File(outputParent, "astar");
		resursiveNodeFolder.mkdirs();
		File output = new File(resursiveNodeFolder, input.getName());
		AStarNode startNode = (AStarNode) createNodes(input, AStarNode.class);
		MazeSolver solver = new AStarSolver(startNode);
		solver.solve();
		solver.printStatistics();
		solver.writeSolutionImage(input, output);
	}

	private static Node createNodes(File input, Class<? extends Node> nodeType) throws IOException {
		long startTime = System.currentTimeMillis();
		NodeCreator creator = new NodeCreator(input, nodeType);
		LOG.info("Start creating nodes");
		Node startNode = creator.createNodes();
		long endTime = System.currentTimeMillis();
		int nrOfNodes = creator.getNumberOfCreateNodes();
		LOG.info("Created " + nrOfNodes + " node(s) in " + (endTime - startTime) + " ms");
		return startNode;
	}

	private static TileType[][] loadMazeTileType(File imageFile, MazeImageHandler imageHandler) throws IOException {
		long startTime = System.currentTimeMillis();
		LOG.info("Start loading maze");
		TileType[][] maze = imageHandler.loadMazeTileType(imageFile);
		long endTime = System.currentTimeMillis();
		LOG.info("Loaded maze in " + (endTime - startTime) + " ms");
		return maze;
	}

}
