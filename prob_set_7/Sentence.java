package edu.nyu.cs.masked_netid.prob_set_7;

import java.util.ArrayList;

/**
*sentence consisting of words
*@author Andy Huang
*@version 2018.12.10
*/
public class Sentence implements SequentiallyOrdered{
	//private
		/**
		*ArrayList of words
		*/
		private ArrayList<Word> words=new ArrayList<Word>();

	//methods
		/**
		*overloaded constructor
		*@param str the sentence
		*@return N/A
		*@exception N/A
		*/
		public Sentence(String str){
			int i=0;
			String[] split=str.split("[^\\w']+");//according to spec
			for (String it: split){
				Word temp=new Word(it, i);
				this.words.add(temp);
				++i;
			}
		}
		/**
		*returns first word of sentence
		*@param N/A
		*@return first word
		*@exception N/A
		*/
		public OrderedThing getFirst(){
			return this.words.get(0);
		}
		/**
		*returns last word of sentence
		*@param N/A
		*@return last word
		*@exception N/A
		*/
		public OrderedThing getLast(){
			return this.words.get(this.words.size()-1);
		}
		/**
		*returns casted ArrayList
		*@param N/A
		*@return casted ArrayList
		*@exception N/A
		*/
		public ArrayList<OrderedThing> getSequence(){
			ArrayList<OrderedThing> ret=new ArrayList<OrderedThing>();
			for (Word it: this.words){
				ret.add(it);
			}
			return ret;
		}
		/**
		*returns string representation
		*@param N/A
		*@return string representation
		*@exception N/A
		*/
		public String toString(){
			String ret=this.words.get(0).toString();
			for (int i=1; i<this.words.size(); ++i){
				ret+= " "+this.words.get(i).toString();
			}
			return ret;
		}
	}
