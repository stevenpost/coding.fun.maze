package coding.fun.maze;

public class AStarNode extends SimpleNode implements Comparable<AStarNode> {

	private AStarNode shortestPath;
	private int weigth = 0;
	private final int distance;

	public AStarNode(Position pos, int weigth, int distance, boolean exit) {
		super(pos, exit);
		this.weigth = weigth;
		this.distance = distance;
	}

	public AStarNode getShortestPath() {
		return this.shortestPath;
	}

	public void setShortestPath(AStarNode shortestPath) {
		this.shortestPath = shortestPath;
	}

	public int getDistance() {
		return this.distance;
	}

	@Override
	public int compareTo(AStarNode o) {
		return this.weigth - o.weigth;
	}

	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}

	public int getWeigth() {
		return this.weigth;
	}

}
