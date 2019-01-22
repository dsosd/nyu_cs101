package edu.nyu.cs.masked_netid.prob_set_1;
/**
*assignment 1 problem 2.13
*@author Andy Huang
*@version 2018.09.18
*/

import java.util.Scanner;

import util.Std;

public class Assignment_2_13{
	/**
	*completes assignment 2.13
	*@param args not used
	*@return N/A
	*@exception N/A
	*/
	public static void main(String[] args){
		Scanner reader=new Scanner(System.in);
		Std.cout("Enter the monthly saving amount: ");
		float initPrincipal=reader.nextFloat();
		float interestRate=5/100.0f /12;

		float balance=0.0f;
		for (int i=0; i<6; ++i){
			balance=(initPrincipal+balance)*(1+interestRate);
		}
		Std.cout("After the sixth month, the account value is $" + balance);
		reader.close();
	}
}
