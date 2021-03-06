package coding.fun.maze;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("static-method")
public class VisitableNodeTest {

	@Test
	public void testGetPosition() {
		Position pos = new Position(0, 0);
		VisitableNode n = new VisitableNode(pos, false);

		assertEquals(pos, n.getPosition());
	}

	@Test
	public void Vertical() {
		Position pos = new Position(0, 1);
		VisitableNode n = new VisitableNode(pos, false);

		Position above = pos.up();
		VisitableNode aboveN = new VisitableNode(above, false);

		n.linkUp(aboveN);

		assertEquals(aboveN, n.getLinkUp());
		assertEquals(n, aboveN.getLinkDown());
	}

	@Test
	public void Horizontal() {
		Position pos = new Position(1, 0);
		VisitableNode n = new VisitableNode(pos, false);

		Position left = pos.left();
		VisitableNode leftN = new VisitableNode(left, false);

		n.linkLeft(leftN);

		assertEquals(leftN, n.getLinkLeft());
		assertEquals(n, leftN.getLinkRight());
	}

}
