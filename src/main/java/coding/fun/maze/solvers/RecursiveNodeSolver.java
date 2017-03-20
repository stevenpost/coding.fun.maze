package coding.fun.maze.solvers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coding.fun.maze.MazeImageHandler;
import coding.fun.maze.Node;
import coding.fun.maze.TileType;
import coding.fun.maze.VisitableNode;

public class RecursiveNodeSolver implements MazeSolver {

	private static final Logger LOG = LoggerFactory.getLogger(RecursiveNodeSolver.class);

	private final VisitableNode startNode;
	private final List<VisitableNode> solutionNodes = new ArrayList<>();
	private int nrOfSteps = 0;
	private int nrOfBackTracks = 0;
	private long runTimeInMs = 0;
	private boolean solved = false;

	public RecursiveNodeSolver(VisitableNode startNode) {
		this.startNode = startNode;
	}

	@Override
	public void solve() {
		long startTime = System.currentTimeMillis();

		step(this.startNode);
		this.solutionNodes.add(this.startNode);

		long endTime = System.currentTimeMillis();
		this.runTimeInMs = endTime - startTime;
		this.solved = true;
	}

	private boolean step(VisitableNode currentNode) {
		this.nrOfSteps++;
		currentNode.setVisited(true);
		if (currentNode.isExit()) {
			return true;
		}

		VisitableNode nextNode;

		// down
		nextNode = (VisitableNode) currentNode.getLinkDown();
		if (nextNode != null && !nextNode.isVisited()) {
			if (step(nextNode)) {
				this.solutionNodes.add(nextNode);
				return true;
			}
			currentNode.unlinkDown();
		}

		// right
		nextNode = (VisitableNode) currentNode.getLinkRight();
		if (nextNode != null && !nextNode.isVisited()) {
			if (step(nextNode)) {
				this.solutionNodes.add(nextNode);
				return true;
			}
			currentNode.unlinkRight();
		}

		// left
		nextNode = (VisitableNode) currentNode.getLinkLeft();
		if (nextNode != null && !nextNode.isVisited()) {
			if (step(nextNode)) {
				this.solutionNodes.add(nextNode);
				return true;
			}
			currentNode.unlinkLeft();
		}

		// up
		nextNode = (VisitableNode) currentNode.getLinkUp();
		if (nextNode != null && !nextNode.isVisited()) {
			if (step(nextNode)) {
				this.solutionNodes.add(nextNode);
				return true;
			}
			currentNode.unlinkUp();
		}

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
		System.out.println("Nodes in solution: " + this.solutionNodes.size());
		if (this.solved) {
			System.out.println("Needed " + this.runTimeInMs + " ms to solve");
		}
		else {
			System.out.println("Not solved");
		}
	}

	@Override
	public void writeSolutionImage(File input, File output) throws IOException {
		long startTime = System.currentTimeMillis();
		MazeImageHandler handler = new MazeImageHandler();
		handler.writeSolutionForNodes(input, output, this.solutionNodes);
		long endtime = System.currentTimeMillis();
		LOG.info("Output written in " + (endtime - startTime) + " ms");
	}

	@Override
	public List<? extends Node> getSolutionNodes() {
		return this.solutionNodes;
	}

}
