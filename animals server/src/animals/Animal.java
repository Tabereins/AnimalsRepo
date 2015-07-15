package animals;

import java.util.ArrayList;

public abstract class Animal {

	protected int hp;
	public ArrayList<String> personalHistory;
	public abstract String speak(ArrayList<String> history);
	public abstract String act(ArrayList<String> history);
	public abstract void takeDamage(int damage);
	public abstract void reveal();
	public abstract String getType();
}
