package edu.nyu.cs.masked_netid.prob_set_1;
/**
*assignment 1 problem 1.11
*@author Andy Huang
*@version 2018.09.18
*/

import util.Std;

public class Assignment_1_11{
	/**
	*completes assignment 1.11
	*@param args not used
	*@return N/A
	*@exception N/A
	*/
	public static void main(String[] args){
		double increment=1.0/7 - 1.0/13 + 1.0/45;
		double pop=312032486.0d;
		for (int i=1; i<=5; ++i){
			Std.cout((pop+i*365*24*60*60*increment) + "\n");
		}
	}
}
