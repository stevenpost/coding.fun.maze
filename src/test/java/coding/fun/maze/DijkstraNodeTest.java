package coding.fun.maze;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DijkstraNodeTest {

	@Test
	public void testWeight() {
		Position startPos = new Position(1, 0);
		DijkstraNode startD = new DijkstraNode(startPos, 0, false);

		Position endPos = new Position(1, 2);
		DijkstraNode endD = new DijkstraNode(endPos, 1, true);
		endD.setShortestPath(startD);

		assertTrue(endD.compareTo(startD) > 0);
		assertTrue(startD.compareTo(endD) < 0);
		assertTrue(endD.compareTo(endD) == 0);
	}

}
