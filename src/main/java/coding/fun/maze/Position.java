package coding.fun.maze;

public class Position {

	private final int x;
	private final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Position down() {
		return new Position(this.x, this.y + 1);
	}

	public Position right() {
		return new Position(this.x + 1, this.y);
	}

	public Position left() {
		if (this.x == 0) {
			throw new IndexOutOfBoundsException();
		}
		return new Position(this.x - 1, this.y);
	}

	public Position up() {
		if (this.y == 0) {
			throw new IndexOutOfBoundsException();
		}
		return new Position(this.x, this.y - 1);
	}

	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x;
		result = prime * result + this.y;
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
		Position other = (Position) obj;
		if (this.x != other.x) {
			return false;
		}
		if (this.y != other.y) {
			return false;
		}
		return true;
	}



}
