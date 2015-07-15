package animals;

import java.util.ArrayList;

public class Team {

	ArrayList<Animal> team;
	
	public Team(){
		team = new ArrayList<Animal>();
	}
	
	public void addAnimal(Animal teamMember){
		team.add(teamMember);
	}
	
	public ArrayList<Animal> getMembers(){
		return team;
	}
}
