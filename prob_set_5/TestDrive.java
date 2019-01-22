package edu.nyu.cs.masked_netid.prob_set_5;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import edu.nyu.cs.masked_netid.util.std.Pair;
import edu.nyu.cs.masked_netid.util.std.Triple;
import edu.nyu.cs.masked_netid.util.Std;

/**
*handles user interaction with a Moped
*@author Andy Huang
*@version 2018.11.08
*/
public class TestDrive{
//globals
	/**
	*vector of triple [place position, place name, and ad for place]
	*/
	private static Vector<Triple<Pair<Integer, Integer>, String, String>> ads
		=new Vector<Triple<Pair<Integer, Integer>, String, String>>();

//methods
	/**
	*entry point
	*@param args only 0 or 1 arguments will be processed. 1 argument will disable extra credit drunks
	*@return N/A
	*@exception N/A
	*/
	public static void main(String[] args){
		boolean drunksEnabled= args.length==1 ? false : true;

		String[] mopedNames={"User", "Alice", "Bob"};
		Vector<Moped> mopeds=TestDrive.generateMopedVec(drunksEnabled);
		TestDrive.setSpecValues();

		Pair<Integer, Integer> userMopedPosFix=new Pair<Integer, Integer>(mopeds.get(0).getGridPos());
		for (int i=1; i<mopeds.size(); ++i){
			mopeds.get(i).setTarget(userMopedPosFix);
		}

		TestDrive.outputIntro(drunksEnabled);
		Scanner reader=new Scanner(System.in);

		boolean userMopedFunctional=true;
		boolean userAutoMode=false;
		while (userMopedFunctional){
			Std.cout("\n");
			TestDrive.outputPositions(mopedNames, mopeds);

			//output ads
			for (Triple<Pair<Integer, Integer>, String, String> it: TestDrive.ads){
				if (mopeds.get(0).getGridPos().equals(it.getX())){
					Std.cout("You are at the " + it.getY() + ".\n");
					Std.cout(it.getZ() + "\n");
				}
			}

			//process command
			String command="";
			if (!userAutoMode){
				command=TestDrive.getUserInput(reader);
				if (command.equals("go to Petite Abeille")){
					userAutoMode=true;
				}
			}
			if ((userAutoMode && TestDrive.processCommand("go to Petite Abeille", mopeds))
					|| TestDrive.processCommand(command, mopeds)){
				for (int i=1; i<mopeds.size(); ++i){
					mopeds.get(i).autoMove();
				}

				if (mopeds.get(0).getGridPos().equals(mopeds.get(0).namesToGrid(17, 6))){//MAGIC repeat intersection numbers
					userAutoMode=false;
				}
			}

			//check end conditions
			if (mopeds.get(0).getGas()==0){
				Std.cout("You're out of gas. This voids the lifetime warranty and breaks your Moped. "
					+ "Now, you are unable to drive.\n"
				);
				userMopedFunctional=false;
			}
			if (TestDrive.checkCollision(mopedNames, mopeds)){
				Std.cout("Last known positions:\n");
				TestDrive.outputPositions(mopedNames, mopeds);
				userMopedFunctional=false;
			}
			if (mopeds.get(0).getParked()){
				userMopedFunctional=false;
			}

			//update pos fix
			userMopedPosFix.setX(mopeds.get(0).getGridPos().getX());
			userMopedPosFix.setY(mopeds.get(0).getGridPos().getY());
		}
	}
	/**
	*gets user input
	*@param reader Scanner to read from
	*@return if moped is still functional
	*@exception N/A
	*/
	public static String getUserInput(Scanner reader){
		Std.cout("Command: ");
		return reader.nextLine();
	}
	/**
	*process a command
	*@param command command to be processed
	*@param mopeds vector of mopeds
	*@return if moped is still functional
	*@exception N/A
	*/
	public static boolean processCommand(String command, Vector<Moped> mopeds){
		boolean moveSuccess=false;//if other mopeds get a turn
		boolean moveFailed=false;//if user move is invalid
		if (command.equals("straight on")){
			if (mopeds.get(0).move("forward")){
				moveSuccess=true;
			}
			else{
				moveFailed=true;
			}
		}
		else if (command.equals("back up")){
			if (mopeds.get(0).move("back")){
				moveSuccess=true;
			}
			else{
				moveFailed=true;
			}
		}
		else if (command.equals("go left")){
			if (mopeds.get(0).move("left")){
				moveSuccess=true;
			}
			else{
				moveFailed=true;
			}
		}
		else if (command.equals("go right")){
			if (mopeds.get(0).move("right")){
				moveSuccess=true;
			}
			else{
				moveFailed=true;
			}
		}
		else if (command.equals("how we doin'?")){
			Std.cout("The gas tank is " + mopeds.get(0).getGasPercent() + " full.\n");
			moveSuccess=true;//checking your tank takes enough time for the drunks to move
		}
		else if (command.equals("fill 'er up")){
			mopeds.get(0).refillGas();
			moveSuccess=true;
		}
		else if (command.equals("go to Petite Abeille")){
			mopeds.get(0).autoMove();
			moveSuccess=true;
		}
		else if (command.equals("park")){
			Std.cout("You have parked the Moped on the sidewalk. You get ticketed for parking\n"
				+ "on the sidewalk. Fine is 1 functional Moped or a valid Moped lifetime\n"
				+ "warranty. Those warranties are hard to come by. Did we mention your Moped is\n"
				+ "impounded until the fine is paid? The things that slip our minds..."
			);
			mopeds.get(0).park();
		}
		else if (command.equals("help")){
			Std.cout("Commands:\n"
				+ "straight on: move forwards\n"
				+ "back up: move backwards\n"
				+ "go left: move left in last movement mode(forwards/backwards)\n"
				+ "go right: move right in last movement mode(forwards/backwards)\n"
				+ "how we doin'?: outputs percentage of gas left in gas tank\n"
				+ "fill 'er up: refills gas tank\n"
				+ "go to Petite Abeille: Moped self drives to Petite Abeille\n"
				+ "help: prints help page\n"
			);
		}
		else{
			Std.cout("Bad command. Please try again.\n");
		}

		if (moveFailed){
			Std.cout("Invalid attempted movement. Please try again.\n");
			moveSuccess=false;
		}
		return moveSuccess;
	}
	/**
	*checks for collision of user moped with one of the drunks
	*@param mopedNames list of moped names
	*@param mopeds vector of mopeds
	*@return if there is a collision
	*@exception N/A
	*/
	public static boolean checkCollision(String[] mopedNames, Vector<Moped> mopeds){
		boolean ret=false;
		for (int i=1; i<mopeds.size(); ++i){
			if (mopeds.get(0).getGridPos().equals(mopeds.get(i).getGridPos())){
				Std.cout(mopedNames[0] + " has collided with " + mopedNames[i] + "!\n");
				ret=true;
			}
		}
		if (ret){
			Std.cout("Your Moped has suffered damage that can only be repaired by the manufacturer.\n"
				+ "Unforunately, your lifetime warranty is voided by the collision.\n"
				+ "It is now unusable.\n\n"
			);
		}
		return ret;
	}
	/**
	*outputs all mopeds' postitions
	*@param mopedNames list of moped names
	*@param mopeds vector of mopeds
	*@return N/A
	*@exception N/A
	*/
	public static void outputPositions(String[] mopedNames, Vector<Moped> mopeds){
		for (int i=0; i<mopeds.size(); ++i){
			Std.cout(mopedNames[i] + " is at " + mopeds.get(i).getStatus() + ".\n");
		}
	}
	/**
	*generates the moped vector
	*@param drunksEnabled if drunks are enabled
	*@return moped vector
	*@exception N/A
	*/
	public static Vector<Moped> generateMopedVec(boolean drunksEnabled){
		Vector<Moped> ret=new Vector<Moped>();
		ret.add(new Moped());
		if (drunksEnabled){
			Moped dummyMoped=new Moped();
			ret.add(new Moped(dummyMoped.namesToGrid(11, 4)));
			ret.add(new Moped(dummyMoped.namesToGrid(9, 6)));
		}
		return ret;
	}
	/**
	*set spec values for user interaction
	*@param N/A
	*@return N/A
	*@exception N/A
	*/
	public static void setSpecValues(){
		Moped dummyMoped=new Moped();
		Vector<Pair<Integer, Integer>> positions=new Vector<Pair<Integer, Integer>>(
			Arrays.asList(
				dummyMoped.namesToGrid(79, 8),
				dummyMoped.namesToGrid(74, 1),
				dummyMoped.namesToGrid(12, 4),
				dummyMoped.namesToGrid(3, 6),
				dummyMoped.namesToGrid(17, 6)
			)
		);
		String[] locations={
			"American Museum of Natural History",
			"Memorial Sloan Kettering",
			"The Strand",
			"Fayda Coffee Tea Cookies Cake",
			"Petite Abeille"
		};
		String[] ads_={
			"Want to see a REAL* dinosaur?!? What you want, the AMNH has**. Free*** for NYU affiliates.",
			"Have cancer? Need treatment? At MSKCC, we treat you with the most* highly**\n"
			+ "trained*** professionals**** in the world*****. More science******, less fear.",
			"The Book HookUp is exclusively available at the Strand bookstore. Only $0.000006342* per\n"
			+ "second for a FULL year's subscription.",
			"At Fay Da Bakery, we bake goods. What is \"good\"? Everything here! Just don't go to our\n"
			+ "competitors. They definitely do NOT* sell what we have.",
			"Petite Abeille serves the finest Charlie Brown smoothies to hard-working* customers like you.\n"
			+ "The banana split has been approved** by the FDA*** to exhibit some properties of edible food."
		};

		TestDrive.ads=new Vector<Triple<Pair<Integer, Integer>, String, String>>();
		for (int i=0; i<positions.size(); ++i){
			Triple<Pair<Integer, Integer>, String, String> temp
				=new Triple<Pair<Integer, Integer>, String, String>();
			temp.setX(positions.get(i));
			temp.setY(locations[i]);
			temp.setZ(ads_[i]);
			ads.add(temp);
		}
	}
	/**
	*outputs the program intro
	*@param drunksEnabled if drunks are enabled
	*@return N/A
	*@exception N/A
	*/
	public static void outputIntro(boolean drunksEnabled){
		Std.cout("Welcome to the MOPED program.\n" + "You get to control a Moped!\n");
		if (drunksEnabled){
			Std.cout("\t???: I'm sorry, but I can't ^...63)))===##@!#^%\n");
		}
		Std.cout("Type in `help` for assistance.\n");
		if (drunksEnabled){
			Std.cout("\tlet you do that. Sending two \"drunk\" drivers to welcome you properly.\n");
		}
	}
}
