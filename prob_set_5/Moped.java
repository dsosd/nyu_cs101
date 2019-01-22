package edu.nyu.cs.masked_netid.prob_set_5;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

import edu.nyu.cs.masked_netid.util.std.Pair;

/**
*represents a vehicle in the streets of Manhattan
*@author Andy Huang
*@version 2018.11.08
*/
public class Moped{
//globals
	/**
	*specified homing target position
	*/
	private Pair<Integer, Integer> target=new Pair<Integer, Integer>();
	/**
	*min bound on streets and avenues, inclusive
	*/
	private Pair<Integer, Integer> minBound=new Pair<Integer, Integer>();
	/**
	*max bound on streets and avenues, exclusive
	*/
	private Pair<Integer, Integer> maxBound=new Pair<Integer, Integer>();

//private
	/**
	*if moped is parked
	*/
	private boolean parked=false;
	/**
	*current position
	*/
	private Pair<Integer, Integer> pos=new Pair<Integer, Integer>();
	/**
	*if moped is in forwards mode
	*/
	private boolean forwards=true;//in spec
	/**
	*orientation. north is 0, east is 1, south is 2, and west is 3
	*/
	private int orientation=2;//in spec
	/**
	*current number of gas units
	*/
	private int gas=20;//in spec
	/**
	*maximum number of gas units
	*/
	private int gasMax=20;//in spec

//methods
	/**
	*constructor for user moped
	*@param N/A
	*@return N/A
	*@exception N/A
	*/
	public Moped(){
		this.setSpecValues(false);
	}
	/**
	*constructor for "drunk" driver moped
	*@param startPos starting position
	*@return N/A
	*@exception N/A
	*/
	public Moped(Pair<Integer, Integer> startPos){
		this.setSpecValues(true);

		this.pos=new Pair<Integer, Integer>(startPos);
		this.target=this.namesToGrid(10, 5);//target the user's initial position
	}
	/**
	*move moped to according to moveStr, if allowed
	*@param moveStr string describing how to move. possible values: "forward", "back", "left", and "right"
	*@return if move was successful
	*@exception N/A
	*/
	public boolean move(String moveStr){
		//work on copies of members just in case move is invalid
		Pair<Integer, Integer> nextPos=new Pair<Integer, Integer>(this.pos);
		boolean nextForwards=this.forwards;
		int moveOrientation=0;
		int nextOrientation=this.orientation;

		//set next orientations and forwards mode
		switch (moveStr){
		case "forward":
			moveOrientation=this.orientation;//move in the direction you face
			nextForwards=true;
			//keep last orientation

			break;
		case "back":
			moveOrientation=this.modulo(this.orientation+2, 4);//move in opposite direction you face
			nextForwards=false;
			//keep last orientation

			break;
		case "left":
			moveOrientation=this.modulo(this.orientation+3, 4);//move 90 deg left of direction you face
			//if you turn forwards, you look the way you move. if you turn backwards, it's the exact opposite direction
			nextOrientation= nextForwards ? moveOrientation : this.modulo(moveOrientation+2, 4);

			break;
		case "right":
			moveOrientation=this.modulo(this.orientation+1, 4);//move 90 deg right of direction you face
			//if you turn forwards, you look the way you move. if you turn backwards, it's the exact opposite direction
			nextOrientation= nextForwards ? moveOrientation : this.modulo(moveOrientation+2, 4);

			break;
		default:
			//should never happen
		}

		//displacement vector due to move
		Pair<Integer, Integer> delta=new Pair<Integer, Integer>();
		/*
		We want to map moveOrientation=0, 1, 2, 3 to x=1, 0, -1, 0 and y=0, 1, 0, -1.
		Let's define moveOrientation as `o` to shorten this explanation.
		Positive x is upward and positive y is rightward. This can be visualized with:
		  0
		3 x 1
		  2
		Note that x is non-zero for o%2==0 and y is non-zero for o%2==1. Applying the restrictions, we need the
			maps: o=0, 2 -> x=1, -1 and o=1, 3 -> y=1, -1. The x mapping can be 2-o-1 = 1-o. The y mapping
			can be 3-o-1 = 2-o. Now we can say that `x= o%2==0 ? 1-o : 0;` and `y= o%2==1 ? 2-o : 0;`.
		*/
		delta.setX(moveOrientation%2==0 ? 1-moveOrientation : 0);
		delta.setY(moveOrientation%2==1 ? 2-moveOrientation : 0);

		//apply delta pos
		nextPos.setX(nextPos.getX() + delta.getX());
		nextPos.setY(nextPos.getY() + delta.getY());

		//run validation
		if (this.validPos(nextPos)){
			this.pos=nextPos;
			this.orientation=nextOrientation;
			this.forwards=nextForwards;
			this.gas-=1;
			return true;
		}
		else{
			return false;
		}
	}
	/**
	*automatically move one step closer to target
	*@param N/A
	*@return N/A
	*@exception N/A
	*/
	public void autoMove(){
		if (this.getGas()==1){//almost out of gas
			this.refillGas();
			return;
		}

		if (this.pos.equals(this.target)){//already reached target so no move
			return;
		}

		//delta to target
		Pair<Integer, Integer> delta=new Pair<Integer, Integer>(this.target);
		delta.setX(delta.getX() - this.pos.getX());
		delta.setY(delta.getY() - this.pos.getY());

		Pair<Integer, Integer> absDelta=new Pair<Integer, Integer>();
		absDelta.setX(Math.abs(delta.getX()));
		absDelta.setY(Math.abs(delta.getY()));

		if (absDelta.getX()==absDelta.getY()){//equally far in both directions
			//randomly choose one absDelta value to be zero, so moped moves in the other direction
			Random rand=new Random();
			if (rand.nextBoolean()){
				absDelta.setX(0);
			}
			else{
				absDelta.setY(0);
			}
		}

		if (absDelta.getX()>absDelta.getY()){//farther* from target in x direction
			//move in the x direction
			delta.setX(delta.getX() / absDelta.getX());//normalize x
			delta.setY(0);
		}
		else{//farther* from target in y direction
			//move in the y direction
			delta.setX(0);
			delta.setY(delta.getY() / absDelta.getY());//normalize y
		}
		// *subject to the random setting of x or y to 0 in the previous if statement block

		int desiredMoveOrientation=0;
		if (delta.getX()!=0){
			//see move function for explanation. this is just the inverse
			desiredMoveOrientation=1-delta.getX();
		}
		else{//Y!=0 implicit
			//see move function for explanation. this is just the inverse
			desiredMoveOrientation=2-delta.getY();
		}

		int moveDirection=modulo(desiredMoveOrientation-this.orientation, 4);
		String[] possibleMoves={"forward", "right", "back", "left"};
		this.move(possibleMoves[moveDirection]);
	}
	/**
	*set spec values for the the user moped
	*@param autoDriver moped is a "drunk" driver
	*@return N/A
	*@exception N/A
	*/
	public void setSpecValues(boolean autoDriver){
		//set bounds
		this.minBound.setX(0);
		this.minBound.setY(0);
		this.maxBound.setX(200);
		this.maxBound.setY(10);

		if (!autoDriver){
			//set position(moped's starting point)
			this.pos=this.namesToGrid(10, 5);//in spec
			//set target(Petite Abeille)
			this.target=this.namesToGrid(17, 6);//in spec
		}
	}
	/**
	*returns pos
	*@param N/A
	*@return this.pos
	*@exception N/A
	*/
	public Pair<Integer, Integer> getGridPos(){
		return this.pos;
	}
	/**
	*converts internal grid positions to named positions
	*@param point grid point
	*@return named pos
	*@exception N/A
	*/
	public Pair<Integer, Integer> gridToNames(Pair<Integer, Integer> point){
		Pair<Integer, Integer> ret=new Pair<Integer, Integer>();
		ret.setX(point.getX()+1);//ex. 2 -> 3rd St
		ret.setY(10-point.getY());//ex. 4 -> 6th Ave
		return ret;
	}
	/**
	*converts named positions to internal grid positions
	*@param pointX named point X value(street)
	*@param pointY named point Y value(avenue)
	*@return grid pos
	*@exception N/A
	*/
	public Pair<Integer, Integer> namesToGrid(int pointX, int pointY){
		Pair<Integer, Integer> ret=new Pair<Integer, Integer>();
		ret.setX(pointX-1);//ex. 3rd St -> 2
		ret.setY(10-pointY);//ex. 6th Ave -> 4
		return ret;
	}
	/**
	*check if position is within bounds
	*@param pos_ position to check
	*@return if the position is valid
	*@exception N/A
	*/
	private boolean validPos(Pair<Integer, Integer> pos_){
		return this.minBound.getX()<=pos_.getX() && pos_.getX()<this.maxBound.getX()
			&& this.minBound.getY()<=pos_.getY() && pos_.getY()<this.maxBound.getY();
	}
	/**
	*performs a mod n
	*@param a a in a mod n
	*@return n n in a mod n
	*@exception N/A
	*/
	private int modulo(int a, int n){
		return (a%n + n)%n;
	}
	/**
	*returns number of gas units left in tank
	*@param N/A
	*@return number of gas units
	*@exception N/A
	*/
	public int getGas(){
		return this.gas;
	}
	/**
	*returns gas left as percentage in percent form
	*@param N/A
	*@return gas left %
	*@exception N/A
	*/
	public String getGasPercent(){
		DecimalFormat formatter=new DecimalFormat("#0");
		formatter.setRoundingMode(RoundingMode.HALF_UP);
		return formatter.format(100.0d* this.gas/this.gasMax) + "%";
	}
	/**
	*refills gas tank
	*@param N/A
	*@return N/A
	*@exception N/A
	*/
	public void refillGas(){
		this.gas=this.gasMax;
	}
	/**
	*set target tracking
	*@param target_ desired target reference(really pointer)
	*@return N/A
	*@exception N/A
	*/
	public void setTarget(Pair<Integer, Integer> target_){
		this.target=target_;//set once and forget. modifications on target_ are applied to this.target
	}
	/**
	*returns string of named position and orientation
	*@param N/A
	*@return string of named position and orientation
	*@exception N/A
	*/
	public String getStatus(){
		Pair<Integer, Integer> namedPos=this.gridToNames(this.pos);
		String[] directions={"North", "East", "South", "West"};
		return this.getOrdinal(namedPos.getX()) + " St. and "
			+ this.getOrdinal(namedPos.getY()) + " Ave., facing "
			+ directions[this.orientation];
	}
	/**
	*returns ordinal with the appropriate suffix
	*@param ordinal ordinal to be processed
	*@return ordinal with suffix
	*@exception N/A
	*/
	public String getOrdinal(int ordinal){
		String ret="" + ordinal;
		if (ordinal%100>=11 && ordinal%100<=19){
			ret+="th";
		}
		else if (ordinal%10==1){
			ret+="st";
		}
		else if (ordinal%10==2){
			ret+="nd";
		}
		else if (ordinal%10==3){
			ret+="rd";
		}
		else{
			ret+="th";
		}
		return ret;
	}
	/**
	*returns this.parked
	*@param N/A
	*@return if moped is parked
	*@exception N/A
	*/
	public boolean getParked(){
		return this.parked;
	}
	/**
	*parks the moped
	*@param N/A
	*@return N/A
	*@exception N/A
	*/
	public void park(){
		this.parked=true;
	}
}
