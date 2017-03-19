package coding.fun.maze.solvers;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coding.fun.maze.AStarNode;
import coding.fun.maze.MazeImageHandler;
import coding.fun.maze.Node;
import coding.fun.maze.Position;
import coding.fun.maze.TileType;

public class AStarSolver implements MazeSolver {

	private static final Logger LOG = LoggerFactory.getLogger(AStarSolver.class);
	private final AStarNode startNode;
	private AStarNode endNode;
	private final LinkedList<Node> solutionNodes = new LinkedList<>();
	private final PriorityQueue<AStarNode> priorityQ = new PriorityQueue<>();
	private boolean solved = false;
	private int expandedNodes = 0;
	private long runTimeInMs = 0;

	public AStarSolver(AStarNode startNode) {
		this.startNode = startNode;
	}

	@Override
	public void solve() {
		long startTime = System.currentTimeMillis();
		this.startNode.setWeigth(0);
		this.priorityQ.add(this.startNode);

		while(!this.solved) {
			AStarNode n = this.priorityQ.remove();
			expandNode(n);
		}
		backTraversal();

		long endtime = System.currentTimeMillis();
		this.runTimeInMs = endtime - startTime;
	}

	private void expandNode(AStarNode n) {
		this.expandedNodes++;

		addNewNodes(n, (AStarNode) n.getLinkDown());
		addNewNodes(n, (AStarNode) n.getLinkUp());
		addNewNodes(n, (AStarNode) n.getLinkLeft());
		addNewNodes(n, (AStarNode) n.getLinkRight());

		n.unlinkAll();

		if (n.isExit()) {
			this.endNode = n;
			this.solved = true;
		}
	}

	private void backTraversal() {
		Node n = this.endNode;
		this.solutionNodes.addLast(n);

		AStarNode currentDNode = this.endNode;
		currentDNode.unlinkAll();
		while (currentDNode.getShortestPath() != null) {
			Node nextNode = currentDNode.getShortestPath();
			nextNode.unlinkAll();
			this.solutionNodes.addLast(nextNode);
			currentDNode = currentDNode.getShortestPath();
		}
	}

	private void addNewNodes(AStarNode node, AStarNode linkedNode) {
		if (linkedNode == null) {
			return;
		}

		Position pos = node.getPosition();

		int weigthtToNode = calculateWeigth(pos, linkedNode.getPosition(), linkedNode.getDistance());

		int currentWeigth = linkedNode.getWeigth();
		if (currentWeigth == -1) {
			linkedNode.setWeigth(weigthtToNode);
			linkedNode.setShortestPath(node);
			this.priorityQ.add(linkedNode);
		}

	}

	private int calculateWeigth(Position position, Position position2, int distance) {
		int xWeight = Math.abs(position.getX() - position2.getX());
		int yWeight = Math.abs(position.getY() - position2.getY());
		return xWeight + yWeight + distance;
	}

	@Override
	public TileType[][] getSolvedMaze() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void printStatistics() {
		System.out.println("Method: " + this.getClass().getName());
		System.out.println("Number of nodes expanded: " + this.expandedNodes);
		System.out.println("Nodes in solution: " + this.solutionNodes.size());
		System.out.println("Needed " + this.runTimeInMs + " ms to solve");
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
