package it.unimol.codesurvey.utilities;

import java.util.ArrayList;
import java.util.Collections;

public class Test {

	public static void main(String[] args) {
		
		
		ArrayList<Integer> test = new ArrayList<Integer>();
		test.add(3);
		test.add(1);
		test.add(6);
		
		
		Collections.sort(test);
		System.out.println(test.get(0));
	}

}
