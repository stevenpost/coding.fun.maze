package coding.fun.maze;

public class NodeCreator {

	private final boolean[][] maze;
	private final Node[][] mazeNodes;
	private final int heigth;
	private final int width;
	private int nrOfCreateNodes = 0;

	public NodeCreator(boolean[][] maze) {
		this.maze = maze;
		this.heigth = maze.length;
		this.width = maze[0].length;
		this.mazeNodes = new Node[this.heigth][this.width];
	}

	public Node createNodes() {
		Node startNode = null;
		for (int y = 0; y < this.maze.length; y++) {
			for (int x = 0; x < this.maze[y].length; x++) {
				if (isNode(x, y)) {
					Position pos = new Position(x, y);
					boolean exitNode = (y == this.heigth -1);
					Node n = new VisitableNode(pos, exitNode);
					this.nrOfCreateNodes++;
					if (startNode == null) {
						startNode = n;
					}
					this.mazeNodes[y][x] = n;
					linkPreviousNodes(n);
				}
			}
		}
		return startNode;
	}

	public DijkstraNode createDijkstraNodes() {
		DijkstraNode startNode = null;
		for (int y = 0; y < this.maze.length; y++) {
			for (int x = 0; x < this.maze[y].length; x++) {
				if (isNode(x, y)) {
					Position pos = new Position(x, y);
					boolean exitNode = (y == this.heigth -1);
					DijkstraNode n = new DijkstraNode(pos, -1, exitNode);
					this.nrOfCreateNodes++;
					if (startNode == null) {
						startNode = n;
					}
					this.mazeNodes[y][x] = n;
					linkPreviousNodes(n);
				}
			}
		}
		return startNode;
	}

	private void linkPreviousNodes(Node n) {
		linkNodeAbove(n);
		linkNodeLeft(n);
	}

	private void linkNodeLeft(Node n) {
		Position pos = n.getPosition();

		int x = pos.getX() - 1;
		int y = pos.getY();
		while (x >= 0 && this.maze[y][x]) {
			Node leftN = this.mazeNodes[y][x];
			if (leftN != null) {
				n.linkLeft(leftN);
				break;
			}
			x--;
		}
	}

	private void linkNodeAbove(Node n) {
		Position pos = n.getPosition();

		int x = pos.getX();
		int y = pos.getY() - 1;
		while (y >= 0 && this.maze[y][x]) {
			Node aboveN = this.mazeNodes[y][x];
			if (aboveN != null) {
				n.linkUp(aboveN);
				break;
			}
			y--;
		}
	}

	public boolean isNode(int x, int y) {
		if (!this.maze[y][x]) {
			return false;
		}

		// Check for the start and end
		if (y == 0 || y == this.heigth - 1) {
			return true;
		}

		boolean vertical = false;
		boolean horizontal = false;

		// look up, then down
		if (y > 0 && this.maze[y - 1][x]) {
			vertical = true;
		}
		else if (y < (this.heigth -1) && this.maze[y + 1][x]) {
			vertical = true;
		}

		// look left, then right
		if (x > 0 && this.maze[y][x - 1]) {
			horizontal = true;
		}
		else if (x < (this.width - 1) && this.maze[y][x + 1]) {
			horizontal = true;
		}

		return (horizontal && vertical);
	}

	public int getNumberOfCreateNodes() {
		return this.nrOfCreateNodes;
	}

}
