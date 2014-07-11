package com.wroblicky.andrew.joust.unused;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.wroblicky.andrew.joust.Main;


public class Test {
	
	
	public static void main(String[] args) {
		FileWriter fstream = null;
		try {
			fstream = new FileWriter("out.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			
			boolean foundMatch = Main.run("pgnpractice.pgn", "canAttackTest", "start");
			out.write(foundMatch + "\n");
			
			// add all config test cases here and then compare to our reference output
			
			/* occupies
			 * captured
			 * sameFile
			 * higherFile
			 * lowerFile
			 * higherRank
			 * lowerRank
			 * betweenFile
			 * betweenRank
			 * file
			 * sameRank
			 * rank
			 * relRank
			 * canAttack
			 * isDefended
			 * check
			 * checkmate
			 * start
			 * end
			 * only
			 * exists
			 * sameColor
			 * wildcard 
			 */
			
			out.close();
		} catch (Exception e) {
			System.err.println("Could not find file to print result to");
			System.exit(-1);
		}
		
		
	}
		

}
