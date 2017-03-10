package coding.fun.maze.solvers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import coding.fun.maze.Position;
import coding.fun.maze.TileType;

public class RecursiveSolverTest {

	@Test
	public void testNextCandidateDown() {
		TileType[][] maze = {{TileType.WALL, TileType.FREE, TileType.WALL},
		                     {TileType.WALL, TileType.FREE, TileType.WALL}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		Position nextPos = solver.getNextCandidatePosition(new Position(1, 0));

		assertEquals(new Position(1, 1),  nextPos);

	}

	@Test
	public void testNextCandidateRight() {
		TileType[][] maze = {{TileType.WALL, TileType.FREE, TileType.FREE},
		                     {TileType.WALL, TileType.WALL, TileType.WALL}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		Position nextPos = solver.getNextCandidatePosition(new Position(1, 0));

		assertEquals(new Position(2, 0),  nextPos);

	}

	@Test
	public void testNextCandidateLeft() {
		TileType[][] maze = {{TileType.FREE, TileType.FREE, TileType.WALL},
		                     {TileType.WALL, TileType.WALL, TileType.WALL}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		Position nextPos = solver.getNextCandidatePosition(new Position(1, 0));

		assertEquals(new Position(0, 0),  nextPos);

	}

	@Test
	public void testNextCandidateUp() {
		TileType[][] maze = {{TileType.WALL, TileType.FREE, TileType.WALL},
		                     {TileType.WALL, TileType.FREE, TileType.WALL}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		Position nextPos = solver.getNextCandidatePosition(new Position(1, 1));

		assertEquals(new Position(1, 0),  nextPos);

	}

	@Test
	public void testNextCandidateRightVisited() {
		TileType[][] maze = {{TileType.WALL, TileType.FREE, TileType.FREE},
		                     {TileType.WALL, TileType.FREE, TileType.WALL}};

		TileType[][] visitedMaze = {{TileType.WALL, TileType.VISITED, TileType.FREE},
		                            {TileType.WALL, TileType.VISITED, TileType.WALL}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		solver.setVisited(visitedMaze);
		Position nextPos = solver.getNextCandidatePosition(new Position(1, 0));

		assertEquals(new Position(2, 0),  nextPos);

	}

	@Test
	public void testSmallest() {
		TileType[][] maze = {{TileType.WALL, TileType.FREE, TileType.WALL},
		                     {TileType.WALL, TileType.FREE, TileType.WALL}};

		TileType[][] expected = {{TileType.WALL, TileType.VISITED, TileType.WALL},
		                         {TileType.WALL, TileType.VISITED, TileType.WALL}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		solver.solve();
		TileType[][] solvedMaze = solver.getSolvedMaze();

		assertArrayEquals(expected, solvedMaze);
	}

	@Test
	public void testSmall() {
		TileType[][] maze = {{TileType.WALL, TileType.FREE, TileType.WALL, TileType.WALL},
		                     {TileType.WALL, TileType.FREE, TileType.FREE, TileType.WALL},
		                     {TileType.WALL, TileType.WALL, TileType.FREE, TileType.WALL},
		                     {TileType.WALL, TileType.FREE, TileType.FREE, TileType.WALL},
		                     {TileType.WALL, TileType.FREE, TileType.WALL, TileType.WALL}};

		TileType[][] expected = {{TileType.WALL, TileType.VISITED, TileType.WALL, TileType.WALL},
		                         {TileType.WALL, TileType.VISITED, TileType.VISITED, TileType.WALL},
		                         {TileType.WALL, TileType.WALL, TileType.VISITED, TileType.WALL},
		                         {TileType.WALL, TileType.VISITED, TileType.VISITED, TileType.WALL},
		                         {TileType.WALL, TileType.VISITED, TileType.WALL, TileType.WALL}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		solver.solve();
		TileType[][] solvedMaze = solver.getSolvedMaze();

		assertArrayEquals(expected, solvedMaze);
	}

	@Test
	public void testSmallDeadEnd() {
		TileType[][] maze = {{TileType.WALL, TileType.FREE, TileType.WALL, TileType.WALL, TileType.WALL},
		                     {TileType.WALL, TileType.FREE, TileType.FREE, TileType.WALL, TileType.WALL},
		                     {TileType.WALL, TileType.WALL, TileType.FREE, TileType.WALL, TileType.WALL},
		                     {TileType.WALL, TileType.FREE, TileType.FREE, TileType.WALL, TileType.WALL},
		                     {TileType.WALL, TileType.FREE, TileType.WALL, TileType.WALL, TileType.WALL},
		                     {TileType.WALL, TileType.FREE, TileType.FREE, TileType.FREE, TileType.WALL},
		                     {TileType.WALL, TileType.FREE, TileType.WALL, TileType.FREE, TileType.WALL},
		                     {TileType.WALL, TileType.WALL, TileType.FREE, TileType.FREE, TileType.WALL},
		                     {TileType.WALL, TileType.WALL, TileType.FREE, TileType.WALL, TileType.WALL},
		                     {TileType.WALL, TileType.WALL, TileType.FREE, TileType.WALL, TileType.WALL}};

		TileType[][] expected = {{TileType.WALL, TileType.VISITED, TileType.WALL, TileType.WALL, TileType.WALL},
		                         {TileType.WALL, TileType.VISITED, TileType.VISITED, TileType.WALL, TileType.WALL},
		                         {TileType.WALL, TileType.WALL, TileType.VISITED, TileType.WALL, TileType.WALL},
		                         {TileType.WALL, TileType.VISITED, TileType.VISITED, TileType.WALL, TileType.WALL},
		                         {TileType.WALL, TileType.VISITED, TileType.WALL, TileType.WALL, TileType.WALL},
		                         {TileType.WALL, TileType.VISITED, TileType.VISITED, TileType.VISITED, TileType.WALL},
		                         {TileType.WALL, TileType.FREE, TileType.WALL, TileType.VISITED, TileType.WALL},
		                         {TileType.WALL, TileType.WALL, TileType.VISITED, TileType.VISITED, TileType.WALL},
		                         {TileType.WALL, TileType.WALL, TileType.VISITED, TileType.WALL, TileType.WALL},
		                         {TileType.WALL, TileType.WALL, TileType.VISITED, TileType.WALL, TileType.WALL}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		solver.solve();
		TileType[][] solvedMaze = solver.getSolvedMaze();

		assertArrayEquals(expected, solvedMaze);
	}

}
