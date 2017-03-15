package coding.fun.maze;

public class DijkstraNode extends SimpleNode implements Comparable<DijkstraNode> {

	private DijkstraNode shortestPath;
	private int weigth = 0;

	public DijkstraNode(Position pos, int weigth, boolean exit) {
		super(pos, exit);
		this.weigth = weigth;
	}

	public DijkstraNode getShortestPath() {
		return this.shortestPath;
	}

	public void setShortestPath(DijkstraNode shortestPath) {
		this.shortestPath = shortestPath;
	}

	@Override
	public int compareTo(DijkstraNode o) {
		return this.weigth - o.weigth;
	}

	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}

	public int getWeigth() {
		return this.weigth;
	}

}
