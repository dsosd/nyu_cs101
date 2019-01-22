package edu.nyu.cs.masked_netid.prob_set_1;
/**
*assignment 1 problem 1.7
*@author Andy Huang
*@version 2018.09.18
*/

import util.Std;

public class Assignment_1_7{
	/**
	*completes assignment 1.7
	*@param args not used
	*@return N/A
	*@exception N/A
	*/
	public static void main(String[] args){
		Std.cout(Assignment_1_7.genPi(6) + "\n");
		Std.cout(Assignment_1_7.genPi(7));
	}
	/**
	*calculates pi using an alternating series
	*@param n number of terms in the series
	*@return approx. value of pi
	*@exception N/A
	*/
	public static float genPi(int n){
		float ret=0.0f;
		//sigma i=0, n-1, (-1)^n*1/(2n+1)
		for (int i=0; i<n; ++i){
			ret+= (i%2==0 ? 1 : -1) * (1.0/(2*i+1));
		}
		return 4*ret;
	}
}
