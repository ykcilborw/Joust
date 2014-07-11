package com.wroblicky.andrew.joust.unused;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Takes in two files and returns whether or not they are the same.
 *
 * Author: Andrew Wroblicky
 */
public class FileComparator {
	
	public static boolean compareFiles(String file1, String file2) {
		boolean toReturn = true;
		String next1 = "";
		String next2 = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(file1));
			BufferedReader in2 = new BufferedReader(new FileReader(file2));
			while ((next1 = in.readLine()) != null && (next2 = in2.readLine()) != null) {
				if (next1.equals(next2) != true) {
					toReturn = false;
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("IO Error in FileComparator");
		}
		if (next1 == null && next2 != null || next1 != null && next2 == null) {
			toReturn = false;
		}
		return toReturn;
	}
}