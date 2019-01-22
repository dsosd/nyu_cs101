package edu.nyu.cs.masked_netid.prob_set_7;

/**
*character wrapper around a "byte"
*@author Andy Huang
*@version 2018.12.10
*/
public class Character extends OrderedThing{
//private
	/**
	*stored char
	*/
	private byte char_='\0';

//methods
	/**
	*sets stored char
	*@param char_ char to be set
	*@return N/A
	*@exception N/A
	*/
	public void setChar(byte char__){
		this.char_=char__;
	}
	/**
	*gets stored char
	*@param N/A
	*@return this.char_
	*@exception N/A
	*/
	public byte getChar(){
		return this.char_;
	}
}
