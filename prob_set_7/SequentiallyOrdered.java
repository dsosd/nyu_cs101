package edu.nyu.cs.masked_netid.prob_set_7;

import java.util.ArrayList;

/**
*interface for sequentially ordered objects
*@author Foo Barstein(edited by Andy Huang)
*@version 2018.12.10
*/
public interface SequentiallyOrdered{
	/**
	*abstract method to return first OrderedThing
	*/
	public abstract OrderedThing getFirst();
	/**
	*abstract method to return last OrderedThing
	*/
	public abstract OrderedThing getLast();
	/**
	*abstract method to return ArrayList of OrderedThings
	*/
	public abstract ArrayList<OrderedThing> getSequence();
}
