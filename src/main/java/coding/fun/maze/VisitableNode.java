package coding.fun.maze;

public class VisitableNode extends SimpleNode {

	private boolean visited = false;

	public VisitableNode(Position pos, boolean exit) {
		super(pos, exit);
	}

	public boolean isVisited() {
		return this.visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

}
