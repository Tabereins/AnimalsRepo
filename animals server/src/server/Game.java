package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import animals.Animal;
import animals.Team;
import teams.GoodTeam;

public class Game {

	private Player player1;
	private Player player2;
	private ArrayList<Integer> player1Life;
	private ArrayList<Integer> player2Life;

	private HashMap<Integer, ArrayList<String>> player1History;
	private HashMap<Integer, ArrayList<String>> player2History;
	
	
	public Game(){
	}
	
	
	public boolean registerTeam(Team team){
		if(player1 == null){
			try {
				player1 = new Player(team);
				player1Life = new ArrayList<Integer>();
				player1History = new HashMap<Integer, ArrayList<String>>();
				for(int i = 0; i < player1.teamMembersLeft(); i++){
					player1Life.add(10);
					player1History.put(i, new ArrayList<String>());
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return false;
			}
			return true;
		} else if(player2 == null){
			try {
				player2 = new Player(team);
				player2Life = new ArrayList<Integer>();
				player2History = new HashMap<Integer, ArrayList<String>>();
				for(int i = 0; i < player2.teamMembersLeft(); i++){
					player2Life.add(10);
					player2History.put(i, new ArrayList<String>());
					System.out.println("i is " + i + " history is " + player2History.get(i));
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return false;
			}
			startGame();
			return true;
		}
		System.err.println("Game already has 2 players");
		return false;
	}
	
	private void startGame(){
		while(!player1.lost() && !player2.lost()){
			Random combatantChooser = new Random();
			int player1DMG = 0;
			int player2DMG = 0;
			Integer player1Num =  combatantChooser.nextInt(player1.teamMembersLeft());
			
			Integer player2Num = combatantChooser.nextInt(player2.teamMembersLeft());
			Animal player1Animal = player1.getCombatant(player1Num);
			while( player1Animal == null){
				player1Num =  combatantChooser.nextInt(player1.teamMembersLeft());
				player1Animal = player1.getCombatant(player1Num);
			}
			Animal player2Animal = player2.getCombatant(player2Num);
			while( player1Animal == null){
				player2Num =  combatantChooser.nextInt(player2.teamMembersLeft());
				player2Animal = player2.getCombatant(player2Num);						
			}
			
			String player1Speaks = player1Animal.speak(player2History.get(player2Num));
			String player2Speaks = player2Animal.speak(player1History.get(player1Num));
			player1History.get(player1Num).add(player1Speaks.toUpperCase());
			player2History.get(player2Num).add(player2Speaks.toUpperCase());
			player1DMG += player1.speak("player1", player1Speaks, player1Num);
			player2DMG += player2.speak("player2", player2Speaks, player2Num);
			player1Life.set(player1Num, player1Life.get(player1Num) - player1DMG);
			player2Life.set(player2Num, player2Life.get(player2Num) - player2DMG);
			checkPlayer1Health(player1Num);
			checkPlayer2Health(player2Num);
			String player1Action = player1Animal.act(player2History.get(player2Num));
			String player2Action = player2Animal.act(player1History.get(player1Num));
			fight(player1Action, player1Num, player2Action, player2Num);
			checkPlayer1Health(player1Num);
			checkPlayer2Health(player2Num);
		}
		if(player1.lost() && !player2.lost()){
			System.out.println("Player2 Wins! with " + player2.teamMembersLeft() + " animals remaining");
		} else if(!player1.lost() && player2.lost()){
			System.out.println("Player1 Wins!  with " + player1.teamMembersLeft() + " animals remaining");
		} else {
			System.out.println("Both players died on the same round");
		}
	}


	private void fight(String player1Action, Integer player1Num, String player2Action, Integer player2Num) {
		Animal player1Animal = player1.getCombatant(player1Num);
		Animal player2Animal = player2.getCombatant(player2Num);
		String player1Type = player1Animal.getType();
		String player2Type = player2Animal.getType();
		if(player1Action.equalsIgnoreCase("Run") && player2Action.equalsIgnoreCase("Run") ){
			System.out.println("They both ran away");
			return;
		} else if(player1Action.equalsIgnoreCase("Run") && player2Action.equalsIgnoreCase("Fight") ){
			Random escapeChance = new Random();
			int escapeInt = escapeChance.nextInt(8);
			boolean escape = true;
			if(player1Type.equals("Cat")){
				escape = escapeInt > 1;
			} else {
				escape = escapeInt > 3;
			}
			if(escape){
				System.out.println("Player1 successfully ran away");
				return;
			} else {
				player1Life.set(player1Num, player1Life.get(player1Num) - 2);
				System.out.println("Player1 tried to run away, but got caught by Player2. It took 2 damage and has " + player1Life.get(player1Num) + " life left.") ;
				return;
			}
		} else if(player2Action.equalsIgnoreCase("Run") && player1Action.equalsIgnoreCase("Fight") ){
			Random escapeChance = new Random();
			int escapeInt = escapeChance.nextInt(8);
			boolean escape = true;
			if(player2Type.equals("Cat")){
				escape = escapeInt > 1;
			} else {
				escape = escapeInt > 3;
			}
			if(escape){
				System.out.println("Player2 successfully ran away");				
				return;
			} else {
				player2Life.set(player2Num, player2Life.get(player2Num) - 2);
				System.out.println("Player2 tried to run away, but Player1 caught them. It took 2 damage and has " + player2Life.get(player2Num) + " life left.");
				
				return;
			}
		} else {
			resolveFight(player1Animal, player1Num, player2Animal, player2Num);
			return;
		}
			
	}

	public static void main(String[] args){
		Game mainGame = new Game();
		mainGame.registerTeam(new GoodTeam());
		mainGame.registerTeam(new GoodTeam());
	}

	private void resolveFight(Animal player1Animal, Integer player1Num, Animal player2Animal, Integer player2Num) {
		player1Animal.reveal();
		player2Animal.reveal();
		String player1Type = player1Animal.getType();
		String player2Type = player2Animal.getType();
		
		if(sameAnimal(player1Animal, player2Animal, player1Num, player2Num)){
			player1Life.set(player1Num, player1Life.get(player1Num) - 2);
			player2Life.set(player2Num, player2Life.get(player2Num) - 2);
			System.out.println("Player1's animal has " + player1Life.get(player1Num) + " life left.");				
			System.out.println("Player2's animal has " + player2Life.get(player2Num) + " life left.");
			}
		if(player1Type.equals("Dog")){
			if(player2Type.equals("Cat")){
				player2Life.set(player2Num, player2Life.get(player2Num) - 6);
				System.out.println("Player1's Dog bites Player2's Cat. It took 6 damage and has " + player2Life.get(player2Num) + " life left.");				
				player1History.get(player1Num).add("Dog");
				player2History.get(player2Num).add("Cat");
			}
			if(player2Type.equals("Snake")){
				player1Life.set(player1Num, player1Life.get(player1Num) - 5);
				System.out.println("Player2's Snake bites Player1's Dog. It took 5 damage and has " + player1Life.get(player1Num) + " life left.");
				player1History.get(player1Num).add("Dog");
				player2History.get(player2Num).add("Snake");
			}
		}
		if(player1Type.equals("Cat")){
			if(player2Type.equals("Dog")){
				player1Life.set(player1Num, player1Life.get(player1Num) - 6);

				System.out.println("Player2's Dog bites Player1's Cat. It took 6 damage and has " + player1Life.get(player1Num) + " life left.");
				player2History.get(player2Num).add("Dog");
				player1History.get(player1Num).add("Cat");
			}
			if(player2Type.equals("Snake")){
				player2Life.set(player2Num, player2Life.get(player2Num) - 5);
				System.out.println("Player1's Cat bites Player2's Snake. It took 5 damage and has " + player2Life.get(player2Num) + " life left.");		
				player1History.get(player1Num).add("Cat");
				player2History.get(player2Num).add("Snake");
			}
		}
		if(player1Type.equals("Snake")){
			if(player2Type.equals("Dog")){
				player2Life.set(player2Num, player2Life.get(player2Num) - 5);
				System.out.println("Player1's Snake bites Player2's Dog. It took 5 damage and has " + player2Life.get(player2Num) + " life left.");
				player2History.get(player2Num).add("Dog");
				player1History.get(player1Num).add("Snake");
			}
			if(player2Type.equals("Cat")){
				player1Life.set(player1Num, player1Life.get(player1Num) - 5);
				System.out.println("Player2's Cat bites Player1's Snake. It took 5 damage and has " + player1Life.get(player1Num) + " life left.");
				player1History.get(player1Num).add("Snake");
				player2History.get(player2Num).add("Cat");
			}
		}

	}


	private boolean sameAnimal(Animal player1Animal, Animal player2Animal, int player1Num, int player2Num) {
		String player1Type = player1Animal.getType();
		String player2Type = player2Animal.getType();
		
		if( player1Type.equals("Dog") && player2Type.equals("Dog")){
			player1History.get(player1Num).add("Dog");
			player2History.get(player2Num).add("Dog");
			System.out.println("They are both Dogs, they both take 2 damage");
			return true;
		}
		if( player1Type.equals("Cat") && player2Type.equals("Cat")){
			player1History.get(player1Num).add("Cat");
			player2History.get(player2Num).add("Cat");
			System.out.println("They are both Cats, they both take 2 damage");
			
			return true;
		}
		if( player1Type.equals("Snake") && player2Type.equals("Snake")){
			player1History.get(player1Num).add("Snake");
			player2History.get(player2Num).add("Snake");
			System.out.println("They are both Snakes, they both take 2 damage");
			
			return true;
		}
		return false;
	}


	private void checkPlayer1Health(Integer player1Num) {
		if(player1Life.get(player1Num) <= 0){
			System.out.println("Player1's animal fainted");
			player1.animalDied(player1Num);
			player1Life.remove(player1Life.get(player1Num));
		}
	}
	
	private void checkPlayer2Health(Integer player2Num) {
		if(player2Life.get(player2Num) <= 0){
			System.out.println("Player2's animal fainted");
			player2.animalDied(player2Num);
			player2Life.remove(player2Life.get(player2Num));
		}
	}


	
}
