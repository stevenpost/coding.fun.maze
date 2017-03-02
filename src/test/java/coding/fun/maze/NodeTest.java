package coding.fun.maze;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NodeTest {

	@Test
	public void testGetPosition() {
		Position pos = new Position(0, 0);
		Node n = new Node(pos);

		assertEquals(pos, n.getPosition());
	}

	@Test
	public void Vertical() {
		Position pos = new Position(0, 1);
		Node n = new Node(pos);

		Position above = pos.up();
		Node aboveN = new Node(above);

		n.linkUp(aboveN);

		assertEquals(aboveN, n.getLinkUp());
		assertEquals(n, aboveN.getLinkDown());
	}

	@Test
	public void Horizontal() {
		Position pos = new Position(1, 0);
		Node n = new Node(pos);

		Position left = pos.left();
		Node leftN = new Node(left);

		n.linkLeft(leftN);

		assertEquals(leftN, n.getLinkLeft());
		assertEquals(n, leftN.getLinkRight());
	}

}
