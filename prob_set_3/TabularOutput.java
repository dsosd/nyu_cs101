package edu.nyu.cs.masked_netid.prob_set_3;

import java.util.Vector;

/**
*forms a table for output
*@author Andy Huang
*@version 2018.10.24
*/
public class TabularOutput{
//enums
	public enum Justify{
		Left, Right, Center
	}
//methods
	/**
	*generates table of output
	*@param data vector of column-major vectors of data
	*@param justify vector of justification specifiers
	*@param paddingChar padding character
	*@param columnSeparator string that separates columns
	*@return table as a string. if bad input, returns ""
	*@exception N/A
	*/
	public static String genTable(Vector<Vector<String>> data, Vector<Justify> justify,
			char paddingChar, String columnSeparator){
		//checks that data and justify are valid
		if (data.size()!=justify.size() || data.size()==0 || data.get(0).size()==0){
			return "";
		}

		Vector<Vector<String>> justifiedData=new Vector<Vector<String>>();
		for (int i=0; i<data.size(); ++i){
			justifiedData.add(genColumn(data.get(i), justify.get(i), ' '));
		}

		//convert table to string
		StringBuilder ret=new StringBuilder();
		for (int i=0; i<justifiedData.get(0).size(); ++i){
			ret.append(justifiedData.get(0).get(i));
			for (int j=1; j<justifiedData.size(); ++j){
				ret.append(columnSeparator + justifiedData.get(j).get(i));
			}
			ret.append("\n");
		}
		return ret.toString();
	}
	/**
	*justifies data in column
	*@param data vector of data
	*@param justify justification specifier
	*@param paddingChar padding character
	*@return vector of justified data
	*@exception N/A
	*/
	public static Vector<String> genColumn(Vector<String> data, Justify justify, char paddingChar){
		int minSize=0;//minimum size for column that fits every element
		for (String it: data){
			minSize=Math.max(minSize, it.length());
		}

		Vector<String> ret=new Vector<String>();
		for (String it: data){
			int paddingSize=minSize-it.length();
			String padding="";
			for (int i=0; i<paddingSize; ++i){
				padding+=paddingChar;
			}

			switch (justify){
			case Left:
				ret.add(it+padding);
				break;
			case Right:
				ret.add(padding+it);
				break;
			case Center:
				int leftPaddingSize=paddingSize/2;
				ret.add(padding.substring(0, leftPaddingSize) + it + padding.substring(leftPaddingSize, padding.length()));
				break;
			}
		}
		return ret;
	}
}
