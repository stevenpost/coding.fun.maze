package coding.fun.maze;

import coding.fun.maze.RecursiveSolver.TileType;

public interface MazeSolver {

	void solve();
	TileType[][] getSolvedMaze();

}
