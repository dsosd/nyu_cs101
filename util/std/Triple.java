package edu.nyu.cs.masked_netid.util.std;

/**
*triple of data
*@author Andy Huang
*@version 2018.11.19
*/
public class Triple<T, U, V>{
//private
	/**
	*first variable in triple
	*/
	T x;
	/**
	*second variable in triple
	*/
	U y;
	/**
	*third variable in triple
	*/
	V z;

//methods
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
	*returns z
	*@param N/A
	*@return z
	*@exception N/A
	*/
	public V getZ(){
		return z;
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
	/**
	*sets z
	*@param z_ value to set
	*@return N/A
	*@exception N/A
	*/
	public void setZ(V z_){
		this.z=z_;
	}
}
