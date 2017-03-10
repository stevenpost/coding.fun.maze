package coding.fun.maze;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NodeTest {

	@Test
	public void testGetPosition() {
		Position pos = new Position(0, 0);
		SimpleNode n = new SimpleNode(pos);

		assertEquals(pos, n.getPosition());
	}

	@Test
	public void Vertical() {
		Position pos = new Position(0, 1);
		SimpleNode n = new SimpleNode(pos);

		Position above = pos.up();
		SimpleNode aboveN = new SimpleNode(above);

		n.linkUp(aboveN);

		assertEquals(aboveN, n.getLinkUp());
		assertEquals(n, aboveN.getLinkDown());
	}

	@Test
	public void Horizontal() {
		Position pos = new Position(1, 0);
		SimpleNode n = new SimpleNode(pos);

		Position left = pos.left();
		SimpleNode leftN = new SimpleNode(left);

		n.linkLeft(leftN);

		assertEquals(leftN, n.getLinkLeft());
		assertEquals(n, leftN.getLinkRight());
	}

}
