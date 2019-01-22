package edu.nyu.cs.masked_netid.prob_set_2;

import java.util.Random;
import java.util.Vector;

/**
*collection of Card with special methods
*@author Andy Huang
*@version 2018.09.26
*/
public class Deck{
	Deck(){
		this.cards=new Vector<Card>();
	}
//private
	private Vector<Card> cards;

//methods
	/**
	*returns non-const ref to cards
	*@param N/A
	*@return non-const ref to cards
	*@exception N/A
	*/
	public Vector<Card> getCards(){
		return this.cards;
	}
	/**
	*generates the 52-card deck
	*@param N/A
	*@return N/A
	*@exception N/A
	*/
	public void genDeck(){
		this.cards.clear();
		for (int i=0; i<52; ++i){
			Card temp=new Card();
			temp.setId(i);
			this.cards.add(temp);
		}
	}
	/**
	*shuffles cards
	*@param N/A
	*@return N/A
	*@exception N/A
	*/
	public void shuffle(){
		Random rand=new Random();
		Lambda__IntInt swap=(int a, int b)->{
			Card temp=this.cards.get(a);
			this.cards.set(a, this.cards.get(b));
			this.cards.set(b, temp);
		};

		//Knuth shuffle
		for (int i=0; i<this.cards.size()-1; ++i){
			swap.act(i, i+rand.nextInt( this.cards.size()-1-i + 1 ));
		}
	}
	/**
	*draws a card
	*@param N/A
	*@return drawn card
	*@exception N/A
	*/
	public Card draw(){
		Card ret=this.cards.get(0);
		this.cards.remove(0);
		return ret;
	}

//lambda types
	public interface Lambda__IntInt{
		public void act(int a, int b);
	}
}
