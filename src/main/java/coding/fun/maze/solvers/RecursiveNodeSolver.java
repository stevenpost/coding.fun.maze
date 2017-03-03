package coding.fun.maze.solvers;

import coding.fun.maze.Node;
import coding.fun.maze.TileType;

public class RecursiveNodeSolver implements MazeSolver {

	private final Node startNode;
	private final int heigth;
	private int nrOfSteps = 0;
	private int nrOfBackTracks = 0;

	public RecursiveNodeSolver(Node startNode, int height) {
		this.startNode = startNode;
		this.heigth = height;
	}

	@Override
	public void solve() {
		step(this.startNode);
	}

	private boolean step(Node n) {
		this.nrOfSteps++;
		if (n.getPosition().getY() == this.heigth -1) {
			return true;
		}

		// down
		if (n.getLinkDown() != null && step(n.getLinkDown())) {
			return true;
		}
		// right
		if (n.getLinkRight() != null && step(n.getLinkRight())) {
			return true;
		}
		// left
		if (n.getLinkLeft() != null && step(n.getLinkLeft())) {
			return true;
		}
		// up
		if (n.getLinkUp() != null && step(n.getLinkUp())) {
			return true;
		}

		this.nrOfBackTracks++;
		return false;
	}

	@Override
	public TileType[][] getSolvedMaze() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printStatistics() {
		System.out.println("Method: " + this.getClass().getName());
		System.out.println("Number of steps taken: " + this.nrOfSteps);
		System.out.println("Number of times we had to backtrack: " + this.nrOfBackTracks);
	}

}
