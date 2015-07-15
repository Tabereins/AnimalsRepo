package server;

import java.util.Random;

import animals.Animal;
import animals.Team;

public class Player {
	private Team team;
	private int teamMembersLeft;
	
	public Player(Team team) throws Exception{
		if(team.getMembers().size() > 5){
			throw(new Exception("Only 5 team members allowed"));	
		}
		if(team.getMembers().size() < 1){
			throw(new Exception("you have to have at least 1 team member"));	
		}
		this.team = team;
		teamMembersLeft = team.getMembers().size();
	}
	
	public Animal getCombatant(int animalNumber){
		try{
		if(team.getMembers().size() <= animalNumber){
			return null;
		}
		return team.getMembers().get(animalNumber);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	public int teamMembersLeft(){
		return teamMembersLeft;
	}
	
	public void animalDied(int animalNumber){
		teamMembersLeft--;
		team.getMembers().remove(animalNumber);
	}
	
	public boolean lost(){
		return teamMembersLeft <= 0;
	}

	public int speak(String name, String word, Integer player1Num) {
		System.out.println(name + " says " + word);
		Animal animal = getCombatant(player1Num);
		if(word.equalsIgnoreCase("WOOF")){
			if( animal.getType().equals("Dog")){
				return 0;
			}
			if( animal.getType().equals("Cat")){
				System.out.println("But it is a lying cat, and takes 1 damage");
				return 1;
			}
			if( animal.getType().equals("Snake")){
				Random halfdmg = new Random();
				System.out.println("But it is a lying snake, and might take 1 damage");				
				return halfdmg.nextInt(2);
				
			}
		} else if(word.equalsIgnoreCase("MEOW")){
			if( animal.getType().equals("Cat")){
				return 0;
			}
			if( animal.getType().equals("Dog")){
				System.out.println("But it is a lying dog, and takes 1 damage");
				return 1;
			}
			if( animal.getType().equals("Snake")){
				Random halfdmg = new Random();
				System.out.println("But it is a lying snake, and might take 1 damage");				
				return halfdmg.nextInt(2);
				
			}
		} else if(word.equalsIgnoreCase("HISS")){
			if( animal.getType().equals("Snake")){
				return 0;
			}
			if( animal.getType().equals("Cat")){
				System.out.println("But it is a lying cat, and takes 1 damage");
				return 1;
			}
			if( animal.getType().equals("Dog")){
				System.out.println("But it is a lying dog, and takes 1 damage");
				
				return 1;
				
			}
		} else {
			System.out.println(word + " is not a valid animal sound");
			return 4;
		}
		return 0;
	}
}
