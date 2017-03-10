package coding.fun.maze.solvers;

import java.io.File;
import java.io.IOException;

import coding.fun.maze.MazeImageHandler;
import coding.fun.maze.Position;
import coding.fun.maze.TileType;

public class RecursiveSolver implements MazeSolver {

	private int nrOfSteps = 0;
	private int nrOfBackTracks = 0;

	private TileType[][] visitedMaze;
	private final TileType[][] solvedMaze;
	private final int heigth;
	private final int width;

	public RecursiveSolver(TileType[][] maze) {
		this.visitedMaze = maze;
		this.heigth = maze.length;
		this.width = maze[0].length;

		this.solvedMaze = new TileType[this.heigth][this.width];
		for(int x = 0; x < maze.length; x++) {
			for(int y = 0; y < maze[0].length; y++) {
				if (maze[x][y] == TileType.FREE) {
					this.solvedMaze[x][y] = TileType.FREE;
				}
				else {
					this.solvedMaze[x][y] = TileType.WALL;
				}
			}
		}
	}

	@Override
	public TileType[][] getSolvedMaze() {
		return this.solvedMaze;
	}

	public void setVisited(TileType[][] visitedMaze) {
		this.visitedMaze = visitedMaze;
	}

	@Override
	public void solve() {
		int startX = 0;
		for (int x = 0; x < this.width; x++) {
			if (this.visitedMaze[0][x] == TileType.FREE) {
				startX = x;
				break;
			}
		}
		step(new Position(startX, 0));
	}

	public boolean step(Position currentPos) {
		this.nrOfSteps++;
		this.visitedMaze[currentPos.getY()][currentPos.getX()] = TileType.VISITED;
		this.solvedMaze[currentPos.getY()][currentPos.getX()] = TileType.VISITED;

		if (currentPos.getY() == this.heigth - 1) {
			// Found the exit
			return true;
		}

		try {
			boolean found = false;
			while (!found) {
				Position nextPos = getNextCandidatePosition(currentPos);
				found = step(nextPos);
			}
			return found;
		}
		catch (@SuppressWarnings("unused") IllegalArgumentException iae) {
			// We need to backtrack
			this.nrOfBackTracks++;
			this.solvedMaze[currentPos.getY()][currentPos.getX()] = TileType.FREE;
			return false;
		}
	}

	public Position getNextCandidatePosition(Position currentPos) {

		// look down
		if (currentPos.getY() + 1 < this.heigth && typeAtPos(currentPos.down()) == TileType.FREE) {
			return currentPos.down();
		}
		// look right
		if (currentPos.getX() + 1 < this.width && typeAtPos(currentPos.right()) == TileType.FREE) {
			return currentPos.right();
		}
		// look left
		if (currentPos.getX() > 0 && typeAtPos(currentPos.left()) == TileType.FREE) {
			return currentPos.left();
		}
		// look up
		if (currentPos.getY() > 0 && typeAtPos(currentPos.up()) == TileType.FREE) {
			return currentPos.up();
		}

		throw new IllegalArgumentException("Cannot go anywhere");

	}

	private TileType typeAtPos(Position pos) {
		return this.visitedMaze[pos.getY()][pos.getX()];
	}

	@Override
	public void printStatistics() {
		System.out.println("Method: " + this.getClass().getName());
		System.out.println("Number of steps taken: " + this.nrOfSteps);
		System.out.println("Number of times we had to backtrack: " + this.nrOfBackTracks);
	}

	@Override
	public void writeSolutionImage(File output) throws IOException {
		MazeImageHandler handler = new MazeImageHandler();
		handler.writeOutputMaze(this.solvedMaze, output);
	}

}
