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
	private Node exitNode;
	private final int heigth;
	private final int width;
	private int nrOfCreateNodes = 0;
	private final Class<? extends Node> nodeType;

	public NodeCreator(File mazeImage, Class<? extends Node> nodeType) throws IOException {
		BufferedImage mazeImg = ImageIO.read(mazeImage);
		this.raster = mazeImg.getRaster();
		this.heigth = mazeImg.getHeight();
		this.width = mazeImg.getWidth();
		this.nodeType = nodeType;
	}

	public Node createNodes() {

		createExitNode();

		Node startNode = null;
		for (int x = 0; x < this.width; x++) {
			if (isNode(x, 0)) {
				Position pos = new Position(x, 0);
				int distance = calculateDistane(pos);
				Node n = createNode(pos, false, distance);
				this.nrOfCreateNodes++;
				startNode = n;
				this.mazeNodes.put(pos, n);
			}
		}

		int lastRow = this.heigth - 1;
		for (int y = 1; y < lastRow; y++) {
			for (int x = 0; x < this.width; x++) {
				if (isNode(x, y)) {
					Position pos = new Position(x, y);
					int distance = calculateDistane(pos);
					Node n = createNode(pos, false, distance);
					this.nrOfCreateNodes++;
					this.mazeNodes.put(pos, n);
					linkPreviousNodes(n);
					removeNodesAbove(pos);
				}
			}
		}

		linkPreviousNodes(this.exitNode);
		removeNodesAbove(this.exitNode.getPosition());

		return startNode;
	}

	private int calculateDistane(Position pos) {
		Position exitPos = this.exitNode.getPosition();
		int xDiff = exitPos.getX() - pos.getX();
		int yDiff = exitPos.getY() - pos.getY();

		return xDiff + yDiff;
	}

	private void createExitNode() {
		int lastRow = this.heigth - 1;
		for (int x = 0; x < this.width; x++) {
			if (isNode(x, lastRow)) {
				Position pos = new Position(x, lastRow);
				Node n = createNode(pos, true, 0);
				this.nrOfCreateNodes++;
				this.exitNode = n;
				this.mazeNodes.put(pos, n);
			}
		}
	}

	private Node createNode(Position pos, boolean isExitNode, int distance) {
		Node n;
		if (this.nodeType == VisitableNode.class) {
			n = new VisitableNode(pos, isExitNode);
		}
		else if (this.nodeType == DijkstraNode.class) {
			n = new DijkstraNode(pos, -1, isExitNode);
		}
		else if (this.nodeType == AStarNode.class) {
			n = new AStarNode(pos, -1, distance, isExitNode);
		}
		else {
			throw new IllegalArgumentException("Unknown Node type: " + this.nodeType.getName());
		}
		return n;
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

	public Node getExitNode() {
		return this.exitNode;
	}

}
