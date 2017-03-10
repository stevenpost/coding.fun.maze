package coding.fun.maze.solvers;

import java.io.File;
import java.io.IOException;

import coding.fun.maze.MazeImageHandler;
import coding.fun.maze.TileType;
import coding.fun.maze.VisitableNode;

public class RecursiveNodeSolver implements MazeSolver {

	private final VisitableNode startNode;
	private final int heigth;
	private int nrOfSteps = 0;
	private int nrOfBackTracks = 0;
	private final boolean[][] maze;

	public RecursiveNodeSolver(VisitableNode startNode, int height, boolean[][] maze) {
		this.startNode = startNode;
		this.heigth = height;
		this.maze = maze;
	}

	@Override
	public void solve() {
		step(this.startNode);
	}

	private boolean step(VisitableNode currentNode) {
		this.nrOfSteps++;
		currentNode.setVisited(true);
		if (currentNode.getPosition().getY() == this.heigth -1) {
			return true;
		}

		VisitableNode nextNode;

		// down
		nextNode = (VisitableNode) currentNode.getLinkDown();
		if (nextNode != null && !nextNode.isVisited() && step(nextNode)) {
			return true;
		}
		currentNode.unlinkDown();

		// right
		nextNode = (VisitableNode) currentNode.getLinkRight();
		if (nextNode != null && !nextNode.isVisited() && step(nextNode)) {
			return true;
		}
		currentNode.unlinkRight();

		// left
		nextNode = (VisitableNode) currentNode.getLinkLeft();
		if (nextNode != null && !nextNode.isVisited() && step(nextNode)) {
			return true;
		}
		currentNode.unlinkLeft();

		// up
		nextNode = (VisitableNode) currentNode.getLinkUp();
		if (nextNode != null && !nextNode.isVisited() && step(nextNode)) {
			return true;
		}
		currentNode.unlinkUp();

		this.nrOfBackTracks++;
		return false;
	}

	@Override
	public TileType[][] getSolvedMaze() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void printStatistics() {
		System.out.println("Method: " + this.getClass().getName());
		System.out.println("Number of steps taken: " + this.nrOfSteps);
		System.out.println("Number of times we had to backtrack: " + this.nrOfBackTracks);
	}

	@Override
	public void writeSolutionImage(File output) throws IOException {
		MazeImageHandler handler = new MazeImageHandler();
		handler.writeSolutionForNodes(this.maze, output, this.startNode);
	}

}
