package coding.fun.maze;

public class Node {

	private final Position pos;

	private Node up;
	private Node down;
	private Node left;
	private Node right;

	public Node(Position pos) {
		this.pos = pos;
	}

	public Position getPosition() {
		return this.pos;
	}

	public void linkUp(Node aboveN) {
		this.up = aboveN;
		if (aboveN.getLinkDown() != this) {
			aboveN.linkDown(this);
		}
	}

	public void linkDown(Node under) {
		this.down = under;
		if (under.getLinkUp() != this) {
			under.linkUp(this);
		}
	}

	public Node getLinkUp() {
		return this.up;
	}

	public Node getLinkDown() {
		return this.down;
	}

	public void linkLeft(Node leftN) {
		this.left = leftN;
		if (leftN.getLinkRight() != this) {
			leftN.linkRight(this);
		}
	}

	private void linkRight(Node rightN) {
		this.right = rightN;
		if (rightN.getLinkLeft() != this) {
			rightN.linkLeft(this);
		}
	}

	public Node getLinkLeft() {
		return this.left;
	}

	public Node getLinkRight() {
		return this.right;
	}

}
