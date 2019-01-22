package edu.nyu.cs.masked_netid.prob_set_7;

import java.util.ArrayList;

/**
*word consisting of characters
*@author Andy Huang
*@version 2018.12.10
*/
public class Word extends OrderedThing implements SequentiallyOrdered{
//private
	/**
	*ArrayList of Characters
	*/
	private ArrayList<Character> chars=new ArrayList<Character>();
	/**
	*position of word in sentence
	*/
	private int pos=0;

//methods
	/**
	*overloaded constructor
	*@param str word
	*@param pos_ pos of word in sentence
	*@return N/A
	*@exception N/A
	*/
	public Word(String str, int pos_){
		for (byte it: str.getBytes()){
			Character temp=new Character();
			temp.setChar(it);
			this.chars.add(temp);
		}
		this.pos=pos_;
	}
	/**
	*returns first char of word
	*@param N/A
	*@return first char
	*@exception N/A
	*/
	public OrderedThing getFirst(){
		return this.chars.get(0);
	}
	/**
	*returns last char of word
	*@param N/A
	*@return last char
	*@exception N/A
	*/
	public OrderedThing getLast(){
		return this.chars.get(this.chars.size()-1);
	}
	/**
	*returns casted ArrayList
	*@param N/A
	*@return casted ArrayList
	*@exception N/A
	*/
	public ArrayList<OrderedThing> getSequence(){
		ArrayList<OrderedThing> ret=new ArrayList<OrderedThing>();
		for (Character it: this.chars){
			ret.add(it);
		}
		return ret;
	}
	/**
	*returns this.pos
	*@param N/A
	*@return this.pos
	*@exception N/A
	*/
	public int getPosition(){
		return this.pos;
	}
	/**
	*returns string representation
	*@param N/A
	*@return string representation
	*@exception N/A
	*/
	public String toString(){
		String ret="";
		for (Character it: this.chars){
			ret+=(char)it.getChar();//cast to ascii representation
		}
		return ret;
	}
}
