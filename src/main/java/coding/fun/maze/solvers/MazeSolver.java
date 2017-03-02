package coding.fun.maze.solvers;

import coding.fun.maze.TileType;

public interface MazeSolver {

	void solve();
	TileType[][] getSolvedMaze();
	void printStatistics();

}
