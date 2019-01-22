package edu.nyu.cs.masked_netid.prob_set_2;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import edu.nyu.cs.masked_netid.util.Std;

/**
*implements simplified blackjack. some differences noted in `diff.txt`
*@author Andy Huang
*@version 2019.01.23
*/
public class SimplifiedBlackjack{
	/**
	*assignment entry point
	*@param args not used
	*@return N/A
	*@exception N/A
	*/
	public static void main(String[] args){
		Deck game_deck=new Deck();
		game_deck.genDeck();
		game_deck.shuffle();

		//the dealer is a player: player 0. user is player 1
		Vector<Vector<Card>> players=new Vector<Vector<Card>>();
		for (int i=0; i<2; ++i){
			players.add(new Vector<Card>());
		}

		Lambda_int_Card getValue=(Card card)->{
			HashMap<Character, Integer> values=new HashMap<Character, Integer>();
			values.put('A', 11);
			values.put('T', 10);
			values.put('J', 10);
			values.put('Q', 10);
			values.put('K', 10);
			for (char c='2'; c<='9'; ++c){
				values.put(c, c-'2' + 2);
			}
			return values.get(card.getRank());
		};

		Lambda_int_VecCard getHandValue=(Vector<Card> hand)->{
			int ret=0;
			for (int i=0; i<hand.size(); ++i){
				ret+=getValue.act(hand.get(i));
			}
			return ret;
		};

		//steps available at https://knowledge.kitchen/Blackjack_Assignment#The_rules
		//and https://web.archive.org/web/20171029130234/http://knowledge.kitchen:80/Blackjack_Assignment
		//step 1
		//step 2
		for (int i=players.size()-1; i>=0; --i){
			for (int j=0; j<2; ++j){
				players.get(i).add(game_deck.draw());
			}
		}

		//accounts for busting of ALL players
		boolean gameOver=false;

		//step 3, also a bit of step 1
		boolean satisfied=false;
		while (!satisfied && !gameOver){
			Std.cout("Your cards are: ");
			for (int i=0; i<players.get(1).size(); ++i){
				Std.cout("" + players.get(1).get(i).getRank() + players.get(1).get(i).getSuit() + " ");
			}
			Std.cout("\n");
			if (getHandValue.act(players.get(1))>21){
				Std.cout("You have busted.\n");
				satisfied=true;
				gameOver=true;
				continue;
			}

			Std.cout("Do you want to (h)it or (s)tand? ");
			Scanner reader=new Scanner(System.in);
			String input=reader.nextLine();
			if (input.equals("h") || input.equals("hit")){
				players.get(1).add(game_deck.draw());
			}
			else if (input.equals("s") || input.equals("stand") || input.equals("stop") || input.equals("pass")){
				satisfied=true;
			}
			//reader.close();
		}

		//step 4
		satisfied=false;
		while (!satisfied && !gameOver){
			if (getHandValue.act(players.get(0))>21){
				Std.cout("Dealer has busted.\n");
				satisfied=true;
				gameOver=true;
				continue;
			}

			//dealer hits if player 1 has not busted and dealer<player 1
			if (getHandValue.act(players.get(0))<getHandValue.act(players.get(1))
					&& getHandValue.act(players.get(1))<=21){
				players.get(0).add(game_deck.draw());
			}
			else{
				satisfied=true;
			}
		}

		//step 5
		Vector<Integer> scores=new Vector<Integer>();
		for (int i=0; i<players.size(); ++i){
			scores.add(getHandValue.act(players.get(i)));
		}
		if (scores.get(1)>21){
			Std.cout("You have lost because you busted.\n");
		}
		else if (scores.get(0)>21){
			Std.cout("You have won because the dealer busted.\n");
		}
		else if (scores.get(0)>=scores.get(1)){
			Std.cout("You have lost. Dealer wins pushes.\n");
		}
		else{
			Std.cout("You have won.\n");
		}

		//step 6
		Std.cout("Dealer has the cards: ");
		for (int i=0; i<players.get(0).size(); ++i){
			Std.cout("" + players.get(0).get(i).getRank() + players.get(0).get(i).getSuit() + " ");
		}
		Std.cout("\n");
	}
//lambda types
	public interface Lambda_int_Card{
		public int act(Card card);
	}
	public interface Lambda_int_VecCard{
		public int act(Vector<Card> vecCard);
	}
}
