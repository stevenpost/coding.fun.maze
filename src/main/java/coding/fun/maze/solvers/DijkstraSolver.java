package coding.fun.maze.solvers;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import coding.fun.maze.DijkstraNode;
import coding.fun.maze.MazeImageHandler;
import coding.fun.maze.Node;
import coding.fun.maze.Position;
import coding.fun.maze.TileType;

public class DijkstraSolver implements MazeSolver {

	private final DijkstraNode startNode;
	private DijkstraNode endNode;
	private final LinkedList<Node> solutionNodes = new LinkedList<>();
	private final PriorityQueue<DijkstraNode> priorityQ = new PriorityQueue<>();
	private boolean solved = false;
	private int expandedNodes = 0;

	public DijkstraSolver(DijkstraNode startNode) {
		this.startNode = startNode;
	}

	@Override
	public void solve() {
		this.startNode.setWeigth(0);
		this.priorityQ.add(this.startNode);

		while(!this.solved) {
			DijkstraNode n = this.priorityQ.remove();
			expandNode(n);
		}
		backTraversal();
	}

	private void expandNode(DijkstraNode n) {
		this.expandedNodes++;

		addNewNodes(n, (DijkstraNode) n.getLinkDown());
		addNewNodes(n, (DijkstraNode) n.getLinkUp());
		addNewNodes(n, (DijkstraNode) n.getLinkLeft());
		addNewNodes(n, (DijkstraNode) n.getLinkRight());

		if (n.isExit()) {
			this.endNode = n;
			this.solved = true;
		}
	}

	private void backTraversal() {
		Node n = this.endNode;
		this.solutionNodes.addLast(n);

		DijkstraNode currentDNode = this.endNode;
		currentDNode.unlinkAll();
		while (currentDNode.getShortestPath() != null) {
			Node nextNode = currentDNode.getShortestPath();
			nextNode.unlinkAll();
			this.solutionNodes.addLast(nextNode);
			currentDNode = currentDNode.getShortestPath();
		}
	}

	private void addNewNodes(DijkstraNode node, DijkstraNode linkedNode) {
		if (linkedNode == null) {
			return;
		}

		Position pos = node.getPosition();

		int weigthtToNode = calculateWeigth(pos, linkedNode.getPosition());
		int totalWeigth = node.getWeigth() + weigthtToNode;

		int currentWeigth = linkedNode.getWeigth();
		if (totalWeigth < currentWeigth || currentWeigth == -1) {
			linkedNode.setWeigth(totalWeigth);
			linkedNode.setShortestPath(node);
			this.priorityQ.add(linkedNode);
		}

	}

	private int calculateWeigth(Position position, Position position2) {
		int xWeight = Math.abs(position.getX() - position2.getX());
		int yWeight = Math.abs(position.getY() - position2.getY());
		return xWeight + yWeight;
	}

	@Override
	public TileType[][] getSolvedMaze() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void printStatistics() {
		System.out.println("Method: " + this.getClass().getName());
		System.out.println("Number of nodes expanded: " + this.expandedNodes);
		System.out.println("Total path weigth: " + this.endNode.getWeigth());
	}

	@Override
	public void writeSolutionImage(File input, File output) throws IOException {
		MazeImageHandler handler = new MazeImageHandler();
		handler.writeSolutionForNodes(input, output, this.solutionNodes);
	}

	@Override
	public List<? extends Node> getSolutionNodes() {
		return this.solutionNodes;
	}

}
