package edu.nyu.cs.masked_netid.prob_set_1;
/**
*assignment 1 problem 2.5
*@author Andy Huang
*@version 2018.09.18
*/

import java.util.Scanner;

import util.Std;

public class Assignment_2_5{
	/**
	*completes assignment 2.5
	*@param args not used
	*@return N/A
	*@exception N/A
	*/
	public static void main(String[] args){
		Scanner reader=new Scanner(System.in);
		Std.cout("Enter the subtotal and a gratuity rate: ");
		float baseAmt=reader.nextFloat();
		float tip_rate=reader.nextFloat();
		float tip=Assignment_2_5.calcTip(baseAmt, tip_rate);
		Std.cout("The gratuity is $" + tip + " and total is $" + (baseAmt+tip));
		reader.close();
	}

	/**
	*calculates tip amount
	*@param amount the untipped total
	*@param tip percentage in non-proper form(x%)
	*@return tip amount
	*@exception N/A
	*/
	public static float calcTip(float amount, float tip){
		return amount*tip/100.0f;
	}
}
