package fr.jbaw.programme2.isGroupRelative;

import java.util.ArrayList;
import java.util.Arrays;

import fr.jbaw.programme2.searchWordUtils;

public class isGroupRelative extends searchWordUtils{



	
	public isGroupRelative() {
		

		
		
	}
	
	
	
	public String[] recuperateSchemas(ArrayList<String> GROUPS, int index) {

		String[] lastGroupSplit = GROUPS.get(index - 1).split("[+]");
		boolean  lengthNotOne1  = lastGroupSplit.length > 1 && index >= 1;
		String   lastFunction   = "";
		int      lastGroupBegin = -1;
		int      lastGroupEnd   = -1;
		if (lengthNotOne1) {
			lastFunction   = lastGroupSplit[0];
			lastGroupBegin = Integer.parseInt(lastGroupSplit[1]);
			lastGroupEnd   = Integer.parseInt(lastGroupSplit[2]);
		}

	
		String[] currentSplit    = GROUPS.get(index).split("[+]");
		boolean  lengthNotOne2   = currentSplit.length > 1;
		String   currentFunction = "";
		int      currentBegin = -1;
		int      currentEnd   = -1;
		if (lengthNotOne2) { 
			currentFunction = currentSplit[0];
			currentBegin = Integer.parseInt(currentSplit[1]);
			currentEnd   = Integer.parseInt(currentSplit[2]);
		}
		
		
		
		String[] nextGroupSplit =  GROUPS.get(index + 1).split("[+]");
		boolean  lengthNotOne3  = nextGroupSplit.length > 1 && index <= GROUPS.size() - 1;
		String   nextFunction   = "";
		int      nextGroupBegin = -1;
		int      nextGroupEnd   = -1;
		if (lengthNotOne3) {
			nextFunction   = nextGroupSplit[0];
			nextGroupBegin = Integer.parseInt(nextGroupSplit[1]);
			nextGroupEnd   = Integer.parseInt(nextGroupSplit[2]);
		}
		

		
		String[] nextX2GroupSplit = GROUPS.get(index + 2).split("[+]");
		boolean  lengthNotOne4    = nextX2GroupSplit.length > 1  && index <= GROUPS.size() - 2;
		String   nextX2Function   = "";
		int      nextX2GroupBegin = -1;
		int      nextX2GroupEnd   = -1;
		if (lengthNotOne4) {
			nextX2Function   = nextX2GroupSplit[0];
			nextX2GroupBegin = Integer.parseInt(nextX2GroupSplit[1]);
			nextX2GroupEnd   = Integer.parseInt(nextX2GroupSplit[2]);
		}
		
		
		String[] data = {lastFunction, currentFunction, nextFunction, nextX2Function, 
						Integer.toString(nextGroupBegin), Integer.toString(nextX2GroupEnd),
						Integer.toString(lastGroupBegin), Integer.toString(lastGroupEnd)};
		
		return data;
		
	}
	
	
	private void last() {
		
	}
	private void  current() {
		
	}
	private void next() {
		
	}
	private void nextX2() {
		
	}
	
	
	
	
	
	public String[] recuperateSchemaIndexFirst(ArrayList<String> GROUPS, int index) {

		
		String[] lastX2GroupSplit = GROUPS.get(index - 2).split("[+]");
		boolean  lengthNotOne     = lastX2GroupSplit.length > 1 && index >= 2;
		String   lastX2Function   = "";
		int      lastX2GroupBegin = -1;
		int      lastX2GroupEnd   = -1;
		if (lengthNotOne) {
			lastX2Function   = lastX2GroupSplit[2];
			lastX2GroupBegin = Integer.parseInt(lastX2GroupSplit[0]);
			lastX2GroupEnd   = Integer.parseInt(lastX2GroupSplit[1]);
		}
		
		
		String[] lastGroupSplit = GROUPS.get(index - 1).split("[+]");
		boolean  lengthNotOne1  = lastGroupSplit.length > 1 && index >= 1;
		String   lastFunction   = "";
		int      lastGroupBegin = -1;
		int      lastGroupEnd   = -1;
		if (lengthNotOne1) {
			lastFunction   = lastGroupSplit[2];
			lastGroupBegin = Integer.parseInt(lastGroupSplit[0]);
			lastGroupEnd   = Integer.parseInt(lastGroupSplit[1]);
		}

	
		String[] currentSplit    = GROUPS.get(index).split("[+]");
		boolean  lengthNotOne2   = currentSplit.length > 1;
		String   currentFunction = "";
		int      currentBegin = -1;
		int      currentEnd   = -1;
		if (lengthNotOne2) { 
			currentFunction = currentSplit[2];
			currentBegin = Integer.parseInt(currentSplit[0]);
			currentEnd   = Integer.parseInt(currentSplit[1]);
		}
		
		String[] data = {lastX2Function, lastFunction, currentFunction, 
						 Integer.toString(currentBegin), Integer.toString(currentEnd),
						 Integer.toString(lastX2GroupBegin), Integer.toString(lastX2GroupEnd)
						};
		
		return data;
	}
	
	
	
	

}


