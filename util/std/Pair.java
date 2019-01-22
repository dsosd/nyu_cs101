package edu.nyu.cs.masked_netid.util.std;

/**
*pair of data
*@author Andy Huang
*@version 2018.11.19
*/
public class Pair<T, U>{
//private
	/**
	*first variable in pair
	*/
	T x;
	/**
	*second variable in pair
	*/
	U y;

//methods
	/**
	*default constructor
	*@param N/A
	*@return N/A
	*@exception N/A
	*/
	public Pair(){
	}
	/**
	*copy constructor
	*@param rhs Pair to copy
	*@return N/A
	*@exception N/A
	*/
	public Pair(Pair<T, U> rhs){
		this.x=rhs.getX();
		this.y=rhs.getY();
	}
	/**
	*check for equality
	*@param obj Pair to compare to
	*@return N/A
	*@exception N/A
	*/
	public boolean equals(Pair<T, U> obj){
		return this.x==obj.getX() && this.y==obj.getY();
	}

	/**
	*returns x
	*@param N/A
	*@return x
	*@exception N/A
	*/
	public T getX(){
		return x;
	}
	/**
	*returns y
	*@param N/A
	*@return y
	*@exception N/A
	*/
	public U getY(){
		return y;
	}
	/**
	*sets x
	*@param x_ value to set
	*@return N/A
	*@exception N/A
	*/
	public void setX(T x_){
		this.x=x_;
	}
	/**
	*sets y
	*@param y_ value to set
	*@return N/A
	*@exception N/A
	*/
	public void setY(U y_){
		this.y=y_;
	}
}
