package coding.fun.maze.solvers;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import coding.fun.maze.DijkstraNode;
import coding.fun.maze.Node;
import coding.fun.maze.Position;

public class DijkstraSolverTest {

	@Test
	public void testSimple() {
		Position startPos = new Position(1, 0);
		DijkstraNode startNode = new DijkstraNode(startPos, -1);

		Position endPos = new Position(1, 2);
		Node endNode = new DijkstraNode(endPos, -1, true);

		startNode.linkDown(endNode);

		MazeSolver solver = new DijkstraSolver(startNode);
		solver.solve();
		List<? extends Node> solution = solver.getSolutionNodes();

		assertEquals(2, solution.size());
	}

}
