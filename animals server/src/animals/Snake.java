package animals;

import java.util.ArrayList;

public abstract class Snake extends Animal{

	public Snake(){
		hp = 10;
		personalHistory= new ArrayList<String>();
	}
	
	public final String getType(){
		return "Snake";
	}
	
	public void takeDamage(int damage) {
		hp = hp - damage;
	}

}
