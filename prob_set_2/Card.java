package edu.nyu.cs.masked_netid.prob_set_2;

/**
*card from 52-card deck
*@author Andy Huang
*@version 2018.09.26
*/
//faux data structure
public class Card{
//pseudo-public private
	private int id;

//methods
	/**
	*returns rank
	*@param N/A
	*@return rank
	*@exception N/A
	*/
	public char getRank(){
		final char ranks[]={
			'A', '2', '3', '4', '5', '6', '7', '8',
			'9', 'T', 'J', 'Q', 'K',
		};
		return ranks[id%13];
	}
	/**
	*returns suit
	*@param N/A
	*@return suit
	*@exception N/A
	*/
	public char getSuit(){
		final char suits[]={'S', 'H', 'C', 'D',};
		return suits[id/13];
	}
	/**
	*dummy getter method
	*@param N/A
	*@return id
	*@exception N/A
	*/
	public int getId(){
		return this.id;
	}
	/**
	*dummy setter method
	*@param id_ value that id will be set to
	*@return N/A
	*@exception N/A
	*/
	public void setId(int id_){
		this.id=id_;
	}
}
