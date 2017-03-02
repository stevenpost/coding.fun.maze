package coding.fun.maze.solvers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import coding.fun.maze.Position;
import coding.fun.maze.TileType;
import coding.fun.maze.solvers.RecursiveSolver;

public class RecursiveSolverTest {

	@Test
	public void testNextCandidateDown() {
		boolean[][] maze = {{false, true, false},
		                    {false, true, false}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		Position nextPos = solver.getNextCandidatePosition(new Position(1, 0));

		assertEquals(new Position(1, 1),  nextPos);

	}

	@Test
	public void testNextCandidateRight() {
		boolean[][] maze = {{false, true, true},
		                    {false, false, false}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		Position nextPos = solver.getNextCandidatePosition(new Position(1, 0));

		assertEquals(new Position(2, 0),  nextPos);

	}

	@Test
	public void testNextCandidateLeft() {
		boolean[][] maze = {{true, true, false},
		                    {false, false, false}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		Position nextPos = solver.getNextCandidatePosition(new Position(1, 0));

		assertEquals(new Position(0, 0),  nextPos);

	}

	@Test
	public void testNextCandidateUp() {
		boolean[][] maze = {{false, true, false},
		                    {false, true, false}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		Position nextPos = solver.getNextCandidatePosition(new Position(1, 1));

		assertEquals(new Position(1, 0),  nextPos);

	}

	@Test
	public void testNextCandidateRightVisited() {
		boolean[][] maze = {{false, true, true},
		                    {false, true, false}};

		TileType[][] visitedMaze = {{TileType.WALL, TileType.VISITED, TileType.FREE},
		                            {TileType.WALL, TileType.VISITED, TileType.WALL}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		solver.setVisited(visitedMaze);
		Position nextPos = solver.getNextCandidatePosition(new Position(1, 0));

		assertEquals(new Position(2, 0),  nextPos);

	}

	@Test
	public void testSmallest() {
		boolean[][] maze = {{false, true, false},
		                    {false, true, false}};

		TileType[][] expected = {{TileType.WALL, TileType.VISITED, TileType.WALL},
		                         {TileType.WALL, TileType.VISITED, TileType.WALL}};

		RecursiveSolver solver = new RecursiveSolver(maze);
		solver.solve();
		TileType[][] solvedMaze = solver.getSolvedMaze();

		assertArrayEquals(expected, solvedMaze);
	}

	@Test
	public void testSmall() {
		boolean[][] maze = {{false, true, false, false},
		                    {false, true, true, false},
		                    {false, false, true, false},
		                    {false, true, true, false},
		                    {false, true, false, false}};

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
		boolean[][] maze = {{false, true, false, false, false},
		                    {false, true, true, false, false},
		                    {false, false, true, false, false},
		                    {false, true, true, false, false},
		                    {false, true, false, false, false},
		                    {false, true, true, true, false},
		                    {false, true, false, true, false},
		                    {false, false, true, true, false},
		                    {false, false, true, false, false},
		                    {false, false, true, false, false}};

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
