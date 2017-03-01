package coding.fun.maze;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("static-method")
public class PositionTest {

	@Test
	public void testDown() {
		Position pos = new Position(0, 0);
		Position down = pos.down();

		assertEquals(0, down.getX());
		assertEquals(1, down.getY());
	}

	@Test
	public void testRight() {
		Position pos = new Position(0, 0);
		Position right = pos.right();

		assertEquals(1, right.getX());
		assertEquals(0, right.getY());
	}

	@Test
	public void testUp() {
		Position pos = new Position(1, 1);
		Position up = pos.up();

		assertEquals(1, up.getX());
		assertEquals(0, up.getY());
	}

	@Test
	public void testLeft() {
		Position pos = new Position(1, 1);
		Position left = pos.left();

		assertEquals(0, left.getX());
		assertEquals(1, left.getY());
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testLeftIOOB() {
		Position pos = new Position(0, 1);
		pos.left();
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testUpIOOB() {
		Position pos = new Position(1, 0);
		pos.up();
	}
}
