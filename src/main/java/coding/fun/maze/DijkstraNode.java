package coding.fun.maze;

public class DijkstraNode implements Node, Comparable<DijkstraNode> {

	private final Position pos;
	private final boolean exit;
	private DijkstraNode shortestPath;
	private int weigth = 0;

	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}

	private Node up;
	private Node down;
	private Node left;
	private Node right;

	public DijkstraNode(Position pos, int weigth) {
		this.pos = pos;
		this.weigth = weigth;
		this.exit = false;
	}

	public DijkstraNode(Position pos, int weigth, boolean exit) {
		this.pos = pos;
		this.weigth = weigth;
		this.exit = exit;
	}

	public DijkstraNode(Position pos, DijkstraNode shortestPath, int weigth) {
		this.pos = pos;
		this.shortestPath = shortestPath;
		this.weigth = weigth;
		this.exit = false;
	}

	public DijkstraNode(Position pos, DijkstraNode shortestPath, int weigth, boolean exit) {
		this.pos = pos;
		this.shortestPath = shortestPath;
		this.weigth = weigth;
		this.exit = exit;
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

	public int getWeigth() {
		return this.weigth;
	}

	@Override
	public void linkUp(Node aboveN) {
		this.up = aboveN;
		if (aboveN.getLinkDown() != this) {
			aboveN.linkDown(this);
		}
	}

	@Override
	public void linkDown(Node under) {
		this.down = under;
		if (under.getLinkUp() != this) {
			under.linkUp(this);
		}
	}

	@Override
	public Node getLinkUp() {
		return this.up;
	}

	@Override
	public Node getLinkDown() {
		return this.down;
	}

	@Override
	public void linkLeft(Node leftN) {
		this.left = leftN;
		if (leftN.getLinkRight() != this) {
			leftN.linkRight(this);
		}
	}

	@Override
	public void linkRight(Node rightN) {
		this.right = rightN;
		if (rightN.getLinkLeft() != this) {
			rightN.linkLeft(this);
		}
	}

	@Override
	public Node getLinkLeft() {
		return this.left;
	}

	@Override
	public Node getLinkRight() {
		return this.right;
	}

	@Override
	public Position getPosition() {
		return this.pos;
	}

	@Override
	public void unlinkUp() {
		if (this.up != null) {
			Node lup = this.up;
			this.up = null;
			lup.unlinkDown();
		}
	}

	@Override
	public void unlinkDown() {
		if (this.down != null) {
			Node ldown = this.down;
			this.down = null;
			ldown.unlinkUp();
		}
	}

	@Override
	public void unlinkLeft() {
		if (this.left != null) {
			Node lleft = this.left;
			this.left = null;
			lleft.unlinkRight();
		}
	}

	@Override
	public void unlinkRight() {
		if (this.right != null) {
			Node lright = this.right;
			this.right = null;
			lright.unlinkLeft();
		}
	}

	@Override
	public boolean isExit() {
		return this.exit;
	}

}
