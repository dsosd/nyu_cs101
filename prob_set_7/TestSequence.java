package edu.nyu.cs.masked_netid.prob_set_7;

import edu.nyu.cs.masked_netid.util.Std;

/**
*class to test the classes in this problem set
*@author Andy Huang
*@version 2018.12.10
*/
public class TestSequence{
	/**
	*entry point
	*@param args not used
	*@return N/A
	*@exception N/A
	*/
	public static void main(String[] args){
		Sentence sentence=new Sentence("Create a test class with a main method that shows how a Sentence object can be instantiated with a sentence of your choosing, how each of the methods of the Sentence class can be called in a meaningful way, and how each of the methods of the Word class can be called on at least one of the Words encapsulated within the Sentence object's ArrayList<Word> instance field that you created.");

		Std.cout("sentence: " + sentence + "\n\n");
		Std.cout("first word of sentence: " + sentence.getFirst() + "\n");
		Std.cout("last word of sentence: " + sentence.getLast() + "\n");
		Std.cout("word amount: " + sentence.getSequence().size() + "\n");
		Std.cout("\n");

		//doing all of them satisfies the at least one requirement
		for (OrderedThing it_: sentence.getSequence()){
			Word it=(Word)it_;
			Std.cout("word: " + it + "\n");
			Std.cout("word position: " + it.getPosition() + "\n");
			Std.cout("first letter of word: " + (char)((Character)(it.getFirst())).getChar() + "\n");
			Std.cout("last letter of word: " + (char)((Character)(it.getLast())).getChar() + "\n");
			Std.cout("letter amount: " + it.getSequence().size() + "\n");
			Std.cout("\n");
		}
	}
}
