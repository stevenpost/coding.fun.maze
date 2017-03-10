package coding.fun.maze;

public class SimpleNode implements Node {

	private final Position pos;

	private Node up;
	private Node down;
	private Node left;
	private Node right;
	private final boolean exit;

	public SimpleNode(Position pos) {
		this.pos = pos;
		this.exit = false;
	}

	public SimpleNode(Position pos, boolean exit) {
		this.pos = pos;
		this.exit = exit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.pos == null) ? 0 : this.pos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SimpleNode other = (SimpleNode) obj;
		if (this.pos == null) {
			if (other.pos != null) {
				return false;
			}
		}
		else if (!this.pos.equals(other.pos)) {
			return false;
		}
		return true;
	}

	@Override
	public Position getPosition() {
		return this.pos;
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
