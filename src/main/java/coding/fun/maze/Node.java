package coding.fun.maze;

public interface Node {

	public Position getPosition();

	public void linkUp(Node aboveN);
	public void linkDown(Node under);
	public void linkLeft(Node leftN);
	public void linkRight(Node rightN);
	public Node getLinkUp();
	public Node getLinkDown();
	public Node getLinkLeft();
	public Node getLinkRight();
	public void unlinkUp();
	public void unlinkDown();
	public void unlinkLeft();
	public void unlinkRight();

}
