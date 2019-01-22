package edu.nyu.cs.masked_netid.prob_set_3;

import java.io.File;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import edu.nyu.cs.masked_netid.util.Std;

/**
*searches a file for phrases
*@author Andy Huang
*@version 2018.10.03
*/
public class PhraseSearch{
	/**
	*entry point
	*@param args not used
	*@return N/A
	*@exception N/A
	*/
	public static void main(String[] args){
		try{
			//for some bizarre reason, Scanner closes the stream it uses on close()
			//this means when reader.close() gets called, System.in also gets closed. wtf
			//Scanner should *not* acquire the stream as a resource
			Scanner reader=new Scanner(System.in);
			String filename=promptUser("filename: ", reader);
			Vector<String> phrases=csvStripper(promptUser("tics: ", reader));
			reader.close();

			HashMap<String, Integer> results=phraseCount(phrases, filename);
			int wordCount=getWordCount(filename);
			int phraseWordCount=calcPhraseWordCount(results);
			float density=calcDensity(phraseWordCount, wordCount);
			int phraseFrequencyCount=calcPhraseFrequencyCount(results);

			printSummary(phraseFrequencyCount, density, results);
		}
		catch (Exception e){
			//ignore all exceptions
		}
	}
	/**
	*returns map of phrases to their frequency
	*@param phrases vector of tics
	*@param filename filename of file to load
	*@return map of phrases to frequency
	*@exception N/A
	*/
	public static HashMap<String, Integer> phraseCount(Vector<String> phrases, String filename){
		HashMap<String, Integer> phraseCount=new HashMap<String, Integer>();
		try{
			String fileContents=new String(Files.readAllBytes(Paths.get(filename)));
			for (String phrase: phrases){
				//phrase must* be surrounded by auxiliary characters to count as a phrase. *exception for ^ and $, non-multiline
				String auxChars="[ ,:;.!?\n\"'\\(\\)]";
				String originalPhrase=phrase;
				phrase=phrase.replaceAll(" ", "[ \n]+");//whitespace spec according* to Prof. Bloomberg's replies. *newline handling was not spec'ed
				//regex for "word": `(?:^|[ ,:;.!?\n"'\(\)])word(?:[ ,:;.!?\n"'\(\)]|$)`
				Pattern regex=Pattern.compile("(?:^|" + auxChars + ")" + phrase + "(?:" + auxChars + "|$)", Pattern.CASE_INSENSITIVE);
				Matcher engine=regex.matcher(fileContents);//dumps entire file into memory. basically no way around
					//this unless using different "algorithm" or converting the file to a stream for the regex engine
					//to run on. acceptable for "most" files given sufficient memory.

				int count=0;
				while (engine.find()){
					++count;
				}
				phraseCount.put(originalPhrase, count);
			}
		}
		catch (Exception e){
			//ignore all exceptions
		}
		return phraseCount;
	}
	/**
	*strips whitespace from a csv line
	*@param phrases vector of tics
	*@param filename filename of file to load
	*@return vector of phrases
	*@exception N/A
	*/
	public static Vector<String> csvStripper(String line){
		Vector<String> ret=new Vector<String>();
		Pattern stripRegex=Pattern.compile("^[\\s]*(.*?)[\\s]*$");//strips whitespace
		Matcher engine=stripRegex.matcher(line);
		engine.find();
		String strippedTics=engine.group(1);//get capture group
		String[] phraseList=strippedTics.split("\\s*,\\s*");//satisfies requirement #3: tics must be stored in an array
		for (String phrase: phraseList){
			ret.add(phrase);
		}
		return ret;
	}
	/**
	*counts number of words in file
	*@param filename filename of file
	*@return word count
	*@exception N/A
	*/
	public static int getWordCount(String filename){
		int ret=0;
		try{
			Scanner reader=new Scanner(new File(filename));
			while (reader.hasNextLine()){
				ret+=reader.nextLine().split("\\s+").length;
			}
			reader.close();
		}
		catch (Exception e){
			//ignore all exceptions
		}
		return ret;
	}
	/**
	*calculates density of tics(phrases)
	*@param phraseWordCount amount of phrase words
	*@param wordCount amount of words
	*@return density. if exception, returns 0.0f
	*@exception N/A
	*/
	public static float calcDensity(int phraseWordCount, int wordCount){
		try{
			DecimalFormat formatter=new DecimalFormat("0.00");
			formatter.setRoundingMode(RoundingMode.HALF_UP);
			return formatter.parse(
				formatter.format(1.0f*phraseWordCount/wordCount)
			).floatValue();
		}
		catch (Exception e){
			//ignore all exceptions
		}
		return 0.0f;
	}
	/**
	*calculates phrase word count
	*@param results map from phrases to frequencies
	*@return phrase word count
	*@exception N/A
	*/
	public static int calcPhraseWordCount(HashMap<String, Integer> results){
		int ret=0;
		for (String key: results.keySet()){
			ret+= results.get(key) * key.split("\\s+").length;
		}
		return ret;
	}
	/**
	*calculates phrase frequency count
	*@param results map from phrases to frequencies
	*@return phrase frequency count
	*@exception N/A
	*/
	public static int calcPhraseFrequencyCount(HashMap<String, Integer> results){
		int ret=0;
		for (String key: results.keySet()){
			ret+=results.get(key);
		}
		return ret;
	}
	/**
	*prints phrase frequency table
	*@param tics vector of tics
	*@param occurrences vector of occurrences
	*@param ticPercent vector of tic percentages
	*@return N/A
	*@exception N/A
	*/
	public static void printFreqTable(Vector<String> tics, Vector<String> occurrences, Vector<String> ticPercent){
		Vector<Vector<String>> freqTableData=new Vector<Vector<String>>();
		freqTableData.add(tics);
		freqTableData.add(occurrences);
		freqTableData.add(ticPercent);
		Vector<TabularOutput.Justify> freqTableJustify=new Vector<TabularOutput.Justify>();
		freqTableJustify.add(TabularOutput.Justify.Left);
		freqTableJustify.add(TabularOutput.Justify.Center);
		freqTableJustify.add(TabularOutput.Justify.Right);

		Std.cout(TabularOutput.genTable(freqTableData, freqTableJustify, ' ', " | "));
	}
	/**
	*populates the table with data
	*@param tics vector of tics
	*@param occurrences vector of occurrences
	*@param ticPercent vector of tic percentages
	*@param phraseFrequencyCount amount of times phrases show up
	*@param results map from phrases to frequencies
	*@return N/A
	*@exception N/A
	*/
	public static void populateTableData(Vector<String> tics, Vector<String> occurrences, Vector<String> ticPercent,
			int phraseFrequencyCount, HashMap<String, Integer> results){
		tics.add("Tics");
		occurrences.add("Occurrences");
		ticPercent.add("x% of all tics");

		DecimalFormat formatter=new DecimalFormat("##0");
		formatter.setRoundingMode(RoundingMode.HALF_UP);
		for (String key: results.keySet()){
			tics.add(key);
			occurrences.add(Integer.toString(results.get(key)));
			ticPercent.add(formatter.format( 100.0f*results.get(key)/phraseFrequencyCount ) + "%");
		}
	}
	/**
	*print summary of results
	*@param phraseFrequencyCount amount of times phrases show up
	*@param density density of tic words
	*@param results map from phrases to frequencies
	*@return N/A
	*@exception N/A
	*/
	public static void printSummary(int phraseFrequencyCount, float density, HashMap<String, Integer> results){
		Vector<String> tics=new Vector<String>();
		Vector<String> occurrences=new Vector<String>();
		Vector<String> ticPercent=new Vector<String>();

		Std.cout("~~~ Text analysis summary ~~~\n");
		Std.cout("Total number of tics: " + phraseFrequencyCount + "\n");
		Std.cout("Density of tics: " + density + "\n");

		Std.cout("\n~~~ Tic breakdown ~~~\n");
		populateTableData(tics, occurrences, ticPercent, phraseFrequencyCount, results);
		printFreqTable(tics, occurrences, ticPercent);
	}
	/**
	*prompts user for string
	*@param prompt prompt to display
	*@param reader Scanner on System.in
	*@return user response
	*@exception N/A
	*/
	public static String promptUser(String prompt, Scanner reader){
		Std.cout(prompt);
		return reader.nextLine();
	}
}
