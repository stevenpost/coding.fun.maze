package coding.fun.maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

@SuppressWarnings("static-method")
public class NodeCreatorTest {

	@Test
	public void testIsNodeBend() {
		boolean[][] maze = {{false, true, false, false},
		                    {false, true, false, false},
		                    {false, true, true, false},
		                    {false, false, true, false}};

		NodeCreator creator = new NodeCreator(maze);
		assertTrue(creator.isNode(1, 2));
	}

	@Test
	public void testIsNodeCorridor() {
		boolean[][] maze = {{false, true, false, false},
		                    {false, true, false, false},
		                    {false, true, true, false},
		                    {false, false, true, false}};

		NodeCreator creator = new NodeCreator(maze);
		assertFalse(creator.isNode(1, 1));
	}

	@Test
	public void testNodeCreation() {
		boolean[][] maze = {{false, true, false, false},
		                    {false, true, false, false},
		                    {false, true, true, false},
		                    {false, false, true, false}};

		NodeCreator creator = new NodeCreator(maze);
		Node startNode = creator.createNodes();

		assertEquals(4, creator.getNumberOfCreateNodes());
		assertEquals(new Position(1, 0), startNode.getPosition());
		assertEquals(new Position(1, 2), startNode.getLinkDown().getPosition());
		assertEquals(new Position(2, 2), startNode.getLinkDown().getLinkRight().getPosition());
		assertEquals(new Position(2, 3), startNode.getLinkDown().getLinkRight().getLinkDown().getPosition());

	}

	@Test
	public void testNodeCreationLinked() {
		boolean[][] maze = {{false, true, false, false},
		                    {false, true, false, false},
		                    {false, true, true, false},
		                    {false, false, true, false}};

		NodeCreator creator = new NodeCreator(maze);
		Node startNode = creator.createNodes();

		assertNull(startNode.getLinkUp());
		assertNull(startNode.getLinkLeft());
		assertNull(startNode.getLinkRight());
		assertNotNull(startNode.getLinkDown());

		Node secondNode = startNode.getLinkDown();
		assertEquals(new Position(1, 2), secondNode.getPosition());
		assertNull(secondNode.getLinkDown());
		assertNull(secondNode.getLinkLeft());
		assertNotNull(secondNode.getLinkUp());
		assertNotNull(secondNode.getLinkRight());

		Node thirdNode = secondNode.getLinkRight();
		System.out.println(thirdNode.getPosition());
		assertEquals(new Position(2, 2), thirdNode.getPosition());
		assertNotNull(thirdNode.getLinkDown());
		assertNotNull(thirdNode.getLinkLeft());
		assertNull(thirdNode.getLinkUp());
		assertNull(thirdNode.getLinkRight());
	}

}
