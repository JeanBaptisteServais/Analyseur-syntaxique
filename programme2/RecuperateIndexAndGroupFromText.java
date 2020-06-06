package fr.jbaw.programme2;

import java.util.ArrayList;
import java.util.Map;

public interface RecuperateIndexAndGroupFromText {

	void RecuperateIndex(String[] groupSyntaxe, ArrayList<Integer> recupIndex, String mode);
	void RecuperateGroup(Map<Integer, String> GroupMap, ArrayList<Integer> thisList);
}
