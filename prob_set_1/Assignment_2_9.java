package edu.nyu.cs.masked_netid.prob_set_1;
/**
*assignment 1 problem 2.9
*@author Andy Huang
*@version 2018.09.18
*/

import java.util.Scanner;

import util.Std;

public class Assignment_2_9{
	/**
	*completes assignment 2.9
	*@param args not used
	*@return N/A
	*@exception N/A
	*/
	public static void main(String[] args){
		Scanner reader=new Scanner(System.in);
		Std.cout("Enter v0, v1, and t: ");
		float v0=reader.nextFloat();
		float v1=reader.nextFloat();
		float t=reader.nextFloat();
		float a=(v1-v0)/t;
		Std.cout("The average acceleration is " + a);
		reader.close();
	}
}
