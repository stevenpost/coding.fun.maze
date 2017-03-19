package coding.fun.maze;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NodeCreator {

	private final WritableRaster raster;
	private Node exitNode;
	private final int heigth;
	private final int width;
	private int nrOfCreateNodes = 0;
	private final Class<? extends Node> nodeType;
	private final int maxX;
	private final int maxY;
	private final Node[] nodesAbove;

	public NodeCreator(File mazeImage, Class<? extends Node> nodeType) throws IOException {
		BufferedImage mazeImg = ImageIO.read(mazeImage);
		this.raster = mazeImg.getRaster();
		this.heigth = mazeImg.getHeight();
		this.width = mazeImg.getWidth();
		this.maxX = this.width -1;
		this.maxY = this.heigth -1;
		this.nodeType = nodeType;
		this.nodesAbove = new Node[this.width];
	}

	public Node createNodes() {

		createExitNode();

		Node startNode = createStartNode();

		for (int y = 1; y < this.maxY; y++) {
			for (int x = 1; x < this.maxX; x++) {
				if (isNode(x, y)) {
					Position pos = new Position(x, y);
					int distance = calculateDistane(pos);
					Node n = createNode(pos, false, distance);
					this.nrOfCreateNodes++;
					linkPreviousNodes(n);
					this.nodesAbove[x] = n;
				}
			}
		}

		linkPreviousNodes(this.exitNode);

		return startNode;
	}

	private Node createStartNode() {
		Node startNode = null;
		for (int x = 1; x < this.width; x++) {
			if (isNode(x, 0)) {
				Position pos = new Position(x, 0);
				int distance = calculateDistane(pos);
				Node n = createNode(pos, false, distance);
				this.nrOfCreateNodes++;
				startNode = n;
				this.nodesAbove[x] = n;
				break;
			}
		}
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
		for (int x = 1; x < this.width; x++) {
			if (isNode(x, lastRow)) {
				Position pos = new Position(x, lastRow);
				Node n = createNode(pos, true, 0);
				this.nrOfCreateNodes++;
				this.exitNode = n;
				this.nodesAbove[x] = n;
				break;
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

	private void linkPreviousNodes(Node n) {
		linkNodeAbove(n);
		linkNodeLeft(n);
	}

	private void linkNodeLeft(Node n) {
		Position pos = n.getPosition();

		int x = pos.getX() - 1;
		int y = pos.getY();
		while (x >= 0 && isPassable(x, y)) {
			Node leftN = this.nodesAbove[x];
			if (leftN != null && leftN.getPosition().getY() == y) {
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
		Node aboveN = this.nodesAbove[x];
		if (aboveN == null) {
			return;
		}

		while (y >= 0 && isPassable(x, y)) {
			if (aboveN.getPosition().getY() == y) {
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
