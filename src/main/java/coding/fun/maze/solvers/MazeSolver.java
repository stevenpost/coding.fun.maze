package coding.fun.maze.solvers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import coding.fun.maze.Node;
import coding.fun.maze.TileType;

public interface MazeSolver {

	void solve();
	TileType[][] getSolvedMaze();
	void printStatistics();
	void writeSolutionImage(File input, File output) throws IOException;
	List<? extends Node> getSolutionNodes();

}
