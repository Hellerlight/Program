package app;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileNotFoundException;

public class Engine {	
	static boolean devmode = true;	// Specifies wether the devmode is on.	
	
	public static void main(String[] args) {	
		
		if(devmode == true) System.out.println("DEVMODE ON");
		saveread();
		Levels.mainmenu(); // Starts the whole program
	}
	static void options() {
		// TODO options screen
		System.out.println("Helo");
		Levels.mainmenu();
	}	
	
	static void combat(int enemydata, double difficulty){  
		// TODO Combat
	}
	static void roomaction(int roomdata) {
		
		/* 				DOCUMENTATION
		 *  0yz is the value for the hub
		 * 	1yz is the value for the small room
		 *  2yz is the value for the medium room
		 *  3yz is the value for the large room
		 *  x1z specifies if the room has an enemy
		 *  x2z specifies if the room has a strong enemy
		 *  x3z specifies if the room has a Boss enemy
		 *  x4z specifies if the room has a 
		 *  	MODIFIED: xy0 specifies that there is no event in this room.
		 *  	MODIFIED: xy1 specifies that there is a trap in this room.
		 *  	MODIFIED: xy2 specifies that there is a potion.
		 *  	MODIFIED: xy3 specifies that there is small treasure in this room.
		 *  	MODIFIED: xy4 specifies that there is medium treasure in this room.
		 *  	MODIFIED: xy5 specifies that there is large treasure in this room.
		 *  Format: 000 xyz xyz ... xyz 000
		 */			
		Scanner input = new Scanner(System.in);
		
		int enemydata;
		int roomtype;
		int eventtype;
		boolean response;
		String option;
		String potname;
		int potionvar;
		
		enemydata = (roomdata/10) % 10;
		roomtype = roomdata/100;
		eventtype = (roomdata%100)%10;
		
		System.out.println();
		
		if(devmode == true) {
			System.out.println("Room type: " + roomtype);
			System.out.println("Enemy data: " + enemydata);			
			System.out.println("event type: " + eventtype);
		}		
		
		switch(roomtype) {
		case 1:
			System.out.println("You are in a small room.");
			break;
		case 2:
			System.out.println("You are in medium-sized room.");
			break;
		case 3:
			System.out.println("You are in a large room.");
			break;
		}
		if(enemydata == 1 || enemydata == 2 || enemydata == 3) {
			System.out.println("You have encountered an enemy!");
			combat(enemydata, Levels.dungeondiff);		
		}
		switch(eventtype) {
		case 0:
			System.out.println("Doesn't seem to be anything interesting in here.");
			break;
		case 1:
			System.out.println("A huge treasure chest! Do you want to open it?");
			response = request();
			if(response == true) {
				Inventory.hp -= 10;
				System.out.println("It was a trap!");
				System.out.println("You have lost 10 HP.");
				System.out.println("Your current hp: " + Inventory.hp);
			}
			break;
		case 2:
			System.out.println("You find a potion on the floor.");
			potionvar = (int) (Math.random() * Namings.potiontypes.length);		
			Inventory.potinfo[0] = Namings.potiontypes[potionvar];	
			Inventory.potinfo[1] = Namings.potioneffects[potionvar];	
			Inventory.potinfo[2] = String.valueOf(potionvar);				
			System.out.println("It's a " + Inventory.potinfo[0]);
			System.out.println("The label reads: " + Inventory.potinfo[1]);
			System.out.println("What do you want to do with it?");
			System.out.println("Leave it [L] || Drink it [D] || Keep it [K]");
			option = input.nextLine();
			switch (option) {
			case "L":				
			case "l":
				System.out.println("Probably would've done more harm than good anyway.");
				break;
			case "D":				
			case "d":			
				System.out.println("Do you REALLY want to drink this potion?");
				response = request();
				if (response == true) {
					System.out.println("You drink the potion.");
					Inventory.poteffect[0] = potionvar;
					Inventory.poteffect[1] = (int) (Math.random()*8)+2;
				} else System.out.println("Very well.");			
				break;
			case "K":
			case "k":
				System.out.println("You decide to keep it for later use.");
				Inventory.slotfill(Inventory.potinfo[1]);
				break;
			default:
				System.out.println("Please input a valid response.");
				break;
			}
		case 3:
			System.out.println("undeveloped");
			break;
		case 4:
			System.out.println("undeveloped");
			break;
		case 5:
			System.out.println("undeveloped");
			break;
		default:
			System.out.println("Please input a valid response.");
			break;
		
		}
	}	
	
	static void savewrite() {	// Writes to save file
		
		try {
			File savefile = new File("Savedata.txt");			
			if(savefile.createNewFile()) {
				System.out.println("Save file successfully created.");
		} else {
			System.out.println("Savefile already exists, writing.");
			}			
		} catch (IOException e) {
			System.out.println("ERROR CREATING A SAVEFILE");
			System.out.println("YOUR PROGRESS WILL NOT BE SAVED.");
			System.out.println("Error log:");
			System.out.println();
			e.printStackTrace();
		}
		try {		
			FileWriter output = new FileWriter("Savedata.txt");
			output.write(String.format("%s%n", Progresslog.xploredrooms));
			output.write(String.format("%s%n", Progresslog.dmgsoaked));
			output.write(String.format("%s%n", Progresslog.dmgdealt));
			output.write(String.format("%s%n", Progresslog.playtime));
			output.write(String.format("%s%n", Progresslog.enemykills));
			output.write(String.format("%s%n", Progresslog.hubsfound));
			output.write(String.format("%s%n", Progresslog.hublevel));
			output.write(String.format("%s%n", Progresslog.forgottenroll));
			output.write(String.format("%s%n", Progresslog.forgottenbeat));
			output.write(String.format("%s%n", Progresslog.cadetroll));
			output.write(String.format("%s%n", Progresslog.cadetbeat));
			output.write(String.format("%s%n", Progresslog.cadetplaytime));
			output.close();
			if(devmode == true) {
				System.out.println("--SAVING DATA--");
				System.out.println(Progresslog.xploredrooms);
				System.out.println(Progresslog.dmgsoaked);
				System.out.println(Progresslog.dmgdealt);
				System.out.println(Progresslog.playtime);
				System.out.println(Progresslog.enemykills);
				System.out.println(Progresslog.hubsfound);
				System.out.println(Progresslog.hublevel);
				System.out.println(Progresslog.forgottenroll);
				System.out.println(Progresslog.forgottenbeat);
				System.out.println(Progresslog.cadetroll);
				System.out.println(Progresslog.cadetbeat);
				System.out.println(Progresslog.cadetplaytime);				
			}
			System.out.println("Progress saved!");
			
		} catch (IOException e) {
			System.out.println("ERROR WRITING TO SAVEFILE");
			System.out.println("YOUR PROGRESS WILL NOT BE SAVED.");
			e.printStackTrace();
		}
	}
	
	static void saveread() {	// Reads from save file
		
		int progressnum = 0;
		int[] progressvalue;
		progressvalue = new int[(Progresslog.progresscount)];
		try {	// Creates file if not present
			File savefile = new File("Savedata.txt");			
			if(savefile.createNewFile()) {
				System.out.println("Save file successfully created.");
		} else {
			System.out.println("Savefile already exists, writing.");
			}				
		} catch (IOException e) {	// Catches excpetions
			System.out.println("ERROR CREATING A SAVEFILE");
			System.out.println("YOUR PROGRESS WILL NOT BE SAVED.");
			System.out.println("Error log:");
			System.out.println();
			e.printStackTrace();
		}		
		try {	// When save file is present, read from file
		File savefile = new File("Savedata.txt");
		Scanner input = new Scanner(savefile);
		while (input.hasNextLine()) {
			String savedata = input.nextLine();
			try {
				progressvalue[progressnum] = Integer.parseInt(savedata);
			} catch(NumberFormatException e) {
				progressvalue[progressnum] = 9;
				System.out.println("Error converting at: " + savedata);
			}
			progressnum++;
		}
		input.close();		
		Progresslog.xploredrooms = progressvalue[0];
		Progresslog.dmgsoaked = progressvalue[1];
		Progresslog.dmgdealt = progressvalue[2];
		Progresslog.playtime = progressvalue[3];
		Progresslog.enemykills = progressvalue[4];
		Progresslog.hubsfound = progressvalue[5];
		Progresslog.hublevel = progressvalue[6];
		Progresslog.forgottenroll = progressvalue[7];
		Progresslog.forgottenbeat = progressvalue[8];
		Progresslog.cadetroll = progressvalue[9];
		Progresslog.cadetbeat = progressvalue[10];
		Progresslog.cadetplaytime = progressvalue[11];		
		if(devmode == true) {	// Devmode check
			System.out.println("--SAVEREAD DEBUG--");
			int sk = 0;		
			for(int i:progressvalue) {
				System.out.println("	Array" + sk + ": " + i);
				sk++;
			}
		}		
		} catch (FileNotFoundException e) {
			System.out.println("Error reading from file");
			e.printStackTrace();
		}
	}	
	
  	static void sleep(int sec) {	// Sleep engine
  		//
  			sec = sec*1000;
  			if(devmode == true) {
  			System.out.println("	Sleep for: "+ sec/1000 + " sec. started");
  			}
  			if(devmode == false) {
		  		try {
					Thread.sleep(sec);					
				} catch (InterruptedException e) {
					e.printStackTrace();
					if(devmode == true) System.out.println("	Sleep error");
				}
  			}
  	}  		
  	
  	static boolean request() {		// Handles the main [y/n] response logic
		
		Scanner input = new Scanner(System.in);
		
		String request;
		boolean response = false;
		
		System.out.println("	[Y/N]");
		
		request = input.nextLine();		// Checks wether the response is a Y or N, not case sensitive
			if(request.contentEquals("y") || request.contentEquals("n")|| request.contentEquals("Y") || request.contentEquals("N")) {
				if(request.contentEquals("y") || request.contentEquals("Y")) {
					response = true;
					System.out.println();
					return response;
				} else {					
					response = false;
					System.out.println();
					return response;
				}
			} else {				
				System.out.println("Please input a valid response. [Y/N]");		
				request();
			}
		return true;
	}	
  	
  	static void hubintro() {	// Announces the hub name.
  		
  		String hubname = Namings.hubname[(int) (Math.random()*(Namings.hubname.length))];
  		if (devmode == true)System.out.println("Hubname length: " + Namings.hubname.length);
  		
  		System.out.println();
  		System.out.println("Welcome to " + hubname + "!");
		System.out.println();
		sleep(1);		
		System.out.println("What do you wish to do?");
		System.out.println("RESTING saves your progress.");
				
  	}
  	
  		// These two values are necessary to calculate wether the player has not saved and needs to rest
  		// or the player has already been in the hub and there is no need to repeat the hub name.
  	static boolean visited = false;
  	static boolean reststatus = false;   
  	
	static void hublogic() {	// Handles the main hub logic
		
		Scanner input = new Scanner(System.in);
		
		String request;
		boolean response;
		String hubname = Namings.hubname[(int) (Math.random()*20)];
		
		if(visited == false) hubintro();	
		System.out.println();
		if(Progresslog.hublevel > 0) {	// Options depend on hub level
			//System.out.println("You are able to ");			
			if(Progresslog.hublevel > 1) {
				System.out.println("You have reached the highest hub level.");
				System.out.println("Leave [L] || Shop [S] || Rest [R] || Inventory [I] || Wizard Tower [W] || Alchemist's abode [A]");
			} else {				
				System.out.println("Leave [L] || Shop [S] || Rest [R] || Wizard Tower [W]");			
			}		
		} else {						
			System.out.println("Leave [L] || Shop [S] || Rest [R] || Inventory [I]");
		}
		request = input.nextLine();
		switch(request) {
		case ("L"):
		case ("l"):
			if(reststatus == false) {
				System.out.println("Adventurer! You haven't rested in this hub!");
				System.out.println("If you leave now your progress will not be saved!");
				System.out.println("Do you wish to rest before your next journey?");
				response = request();
				if(response  == true) {
					System.out.println("Take a nap, adventurer.");
					restlogic();
				} else {
					System.out.println("Your choice.");
					visited = true;
				}
			}
			visited = false;
			System.out.println("Farewell, adventurer.");
			System.out.println();
			break;
		case ("S"):
		case ("s"):
			shoplogic();
			break;				
		case ("R"):
		case ("r"):
			restlogic();
			break;
		case ("I"):
		case ("i"):
			Inventory.gearstatus();
			visited = true;
			hublogic();
			break;
		case ("W"):
		case ("w"):
			if(Progresslog.hublevel>0) {
				wiztowlogic();
				break;
			} else {
				System.out.println("wiztower\nPlease input a valid response.");
				visited = true;
				hublogic();
				break;
			}
		case ("A"):
		case ("a"):
			if(Progresslog.hublevel>1) {
				alchemlogic();
				break;
			} else {
				System.out.println("alchem\nPlease input a valid response.");
				visited = true;
				hublogic();
				break;
			}
		default:
			System.out.println("Please input a valid response.");
			visited = true;
			hublogic();
			break;
		}
				
	}
	
	static void restlogic() {	// Handles the resting logic		
		visited = true;
		reststatus = true;
		System.out.print("Resting");
		for (int i = 0; i < 4; i++) {
			sleep(1);
			System.out.print(".");
		}
		System.out.println();
		savewrite();
		hublogic();
	}
	
	static boolean buylogic(int price) {	// Handles the buying logic
		if(Inventory.gold >= price) {
			Inventory.gold = Inventory.gold - price;
			System.out.println("Purchased item for " + price + " gold.");
			System.out.println("Current gold: " + Inventory.gold);	
			return true;
		} else {
			System.out.println("Maybe choose something you can afford?");
			shoplogic();
		}		
		return false;
	}	
	
	static void shoplogic() {	// Handles the shop logic in hubs		
		Scanner input = new Scanner(System.in);
		String response;
		visited = true;
		System.out.println("---Shop---");
		System.out.println("Weapon upgrade: 10 Gold [W]");
		System.out.println("Armor upgrade: 5 Gold [A]");
		System.out.println("Torch: 1 Gold [T]");
		System.out.println("Pebble: 0 Gold [P]");
		System.out.println("Bomb: 15 Gold [B]");
		System.out.println("Health potion: 8 Gold [H]");
		System.out.println();
		System.out.println("Leave: [L]");
		response = input.nextLine();
		switch (response) {	// Checks for lowercase and UPPERCASE
		case("W"):
		case("w"):
			if(buylogic(10) == true) Inventory.weapon++;	
			break;
		case("A"):
		case("a"):
			if(buylogic(5) == true) Inventory.boots++;
			break;
		case("T"):
		case("t"):
			if(buylogic(1) == true) Inventory.slotfill("Torch");
			break;
		case("P"):
		case("p"):
			if(buylogic(0) == true) Inventory.slotfill("Pebble");
			break;
		case("B"):
		case("b"):
			if(buylogic(15) == true) Inventory.slotfill("Bomb");
			break;
		case("H"):
		case("h"):			
			if(buylogic(10) == true) Inventory.hppotions++;		
		case("L"):
		case("l"):
			hublogic();
			break;
		default:
			System.out.println("Please input a valid response.");
			shoplogic();
			break;
		}	
	}
	
	static void wiztowlogic() {	// Handles the wizard tower logic in hubs		
		visited = true;
		// TODO Wizard tower
		System.out.println("Go away pls.");
		
		hublogic();
	}		
	static void alchemlogic() {
		visited = true;
		// TODO Alchemist's abode
		System.out.println("Alchem");
		hublogic();
	}
	
}
