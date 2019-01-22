package edu.nyu.cs.masked_netid.prob_set_1;
/**
*assignment 1 problem 1.10
*@author Andy Huang
*@version 2018.09.18
*/

import util.Std;

public class Assignment_1_10{
	/**
	*completes assignment 1.10
	*@param args not used
	*@return N/A
	*@exception N/A
	*/
	public static void main(String[] args){
		Std.cout(Assignment_1_10.kmsToMph(14, 45*60+30));
	}
	/**
	*converts km/s to mph
	*@param distance distance of movement in km
	*@param duration duration of movement in s
	*@return speed in mph
	*@exception N/A
	*/
	public static float kmsToMph(float distance, float duration){
		return distance/duration *60*60 /1.6f;
	}
}
