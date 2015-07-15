package animals;

import java.util.ArrayList;

public abstract class Dog extends Animal{

	public Dog(){
		hp = 10;
		personalHistory= new ArrayList<String>();
	}
	
	public final String getType(){
		return "Dog";
	}
	
	@Override
	public void takeDamage(int damage) {
		hp = hp - damage;
	}

}
