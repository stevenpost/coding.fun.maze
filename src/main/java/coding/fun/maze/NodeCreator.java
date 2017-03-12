package coding.fun.maze;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class NodeCreator {

	private final WritableRaster raster;
	private final Map<Position, Node> mazeNodes = new HashMap<>();
	private final int heigth;
	private final int width;
	private int nrOfCreateNodes = 0;

	public NodeCreator(File mazeImage) throws IOException {
		BufferedImage mazeImg = ImageIO.read(mazeImage);
		this.raster = mazeImg.getRaster();
		this.heigth = mazeImg.getHeight();
		this.width = mazeImg.getWidth();
	}

	public Node createNodes() {

		Node startNode = null;
		for (int y = 0; y < this.heigth; y++) {
			for (int x = 0; x < this.width; x++) {
				if (isNode(x, y)) {
					Position pos = new Position(x, y);
					boolean exitNode = (y == this.heigth -1);
					Node n = new VisitableNode(pos, exitNode);
					this.nrOfCreateNodes++;
					if (startNode == null) {
						startNode = n;
					}
					this.mazeNodes.put(pos, n);
					linkPreviousNodes(n);
					removeNodesAbove(pos);
				}
			}
		}
		return startNode;
	}

	private void removeNodesAbove(Position pos) {
		int x = pos.getX();
		for (int y = pos.getY()-1; y >= 0; y--) {
			Position posToRemove = new Position(x, y);
			if (this.mazeNodes.remove(posToRemove) != null) {
				break;
			}
		}
	}

	public DijkstraNode createDijkstraNodes() {
		DijkstraNode startNode = null;
		for (int y = 0; y < this.heigth; y++) {
			for (int x = 0; x < this.width; x++) {
				if (isNode(x, y)) {
					Position pos = new Position(x, y);
					boolean exitNode = (y == this.heigth -1);
					DijkstraNode n = new DijkstraNode(pos, -1, exitNode);
					this.nrOfCreateNodes++;
					if (startNode == null) {
						startNode = n;
					}
					this.mazeNodes.put(pos, n);
					linkPreviousNodes(n);
					removeNodesAbove(pos);
				}
			}
		}
		return startNode;
	}

	private void linkPreviousNodes(Node n) {
		linkNodeAbove(n);
		linkNodeLeft(n);
	}

	private void linkNodeLeft(Node n) {
		Position pos = n.getPosition();

		int x = pos.getX() - 1;
		int y = pos.getY();
		while (x >= 0 && isPassable(x, y)) {
			Node leftN = this.mazeNodes.get(new Position(x, y));
			if (leftN != null) {
				n.linkLeft(leftN);
				break;
			}
			x--;
		}
	}

	private void linkNodeAbove(Node n) {
		Position pos = n.getPosition();

		int x = pos.getX();
		int y = pos.getY() - 1;
		while (y >= 0 && isPassable(x, y)) {
			Node aboveN = this.mazeNodes.get(new Position(x, y));
			if (aboveN != null) {
				n.linkUp(aboveN);
				break;
			}
			y--;
		}
	}

	public boolean isNode(int x, int y) {
		if (!isPassable(x, y)) {
			return false;
		}

		// Check for the start and end
		if (y == 0 || y == this.heigth - 1) {
			return true;
		}

		boolean vertical = false;
		boolean horizontal = false;

		// look up, then down
		if (y > 0 && isPassable(x, y - 1)) {
			vertical = true;
		}
		else if (y < (this.heigth -1) && isPassable(x, y + 1)) {
			vertical = true;
		}

		// look left, then right
		if (x > 0 && isPassable(x - 1, y)) {
			horizontal = true;
		}
		else if (x < (this.width - 1) && isPassable(x + 1, y)) {
			horizontal = true;
		}

		return (horizontal && vertical);
	}

	private boolean isPassable(int x, int y) {
		Object o = this.raster.getDataElements(x, y, null);
		if (o instanceof byte[]) {
			byte color = ((byte[])o)[0];
			if (color == 0) {
				return false;
			}
			return true;
		}

		throw new IllegalArgumentException("This wasn't an array as expected");

	}

	public int getNumberOfCreateNodes() {
		return this.nrOfCreateNodes;
	}

}
