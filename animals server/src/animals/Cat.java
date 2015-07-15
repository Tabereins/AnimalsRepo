package animals;

import java.util.ArrayList;

public abstract class Cat extends Animal{

	public Cat(){
		hp = 10;
		personalHistory= new ArrayList<String>();
	}
	

	@Override
	public void takeDamage(int damage) {
		hp = hp - damage;
	}
	
	public final String getType(){
		return "Cat";
	}

}

