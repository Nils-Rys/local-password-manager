package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {
	private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBER = "0123456789";
	private static final String SPECIAL = "!@#$%^&*()-+=";
			
	// Generates the password with character parameters based on the passed arguments
	public String generatePass(int lower, int upper, int special, int number) {
		String pass = "";
		int length = lower+upper+special+number;
		int reqCount = 4;

		List<String> chars = new ArrayList<>(4);
		chars.add(LOWER);
		chars.add(UPPER);
		chars.add(SPECIAL);
		chars.add(NUMBER);

		// checks if any parameters are not wanted
		if (lower == 0){
			lower--;
			chars.remove(LOWER);
		}
		if (upper == 0){
			upper--;
			chars.remove(UPPER);
		}
		if (special == 0){
			special--;
			chars.remove(SPECIAL);
		}
		if (number == 0){
			number--;
			chars.remove(NUMBER);
		}

		Random random = new Random();
		// Selects random character to add to the password until password length is achieved
		while(chars.size() > 0 && length != 0) {
			int type = random.nextInt(chars.size());
			String charType = chars.get(type);
			if (charType.equals(LOWER)){
				lower--;
			}
			if (charType.equals(UPPER)){
				upper--;
			}
			if (charType.equals(SPECIAL)){
				special--;
			}
			if (charType.equals(NUMBER)){
				number--;
			}
            int pos = random.nextInt(charType.length());
            pass += charType.charAt(pos);

			if (lower == 0){
				lower--;
				chars.remove(LOWER);
			}
			if (upper == 0){
				upper--;
				chars.remove(UPPER);
			}
			if (special == 0){
				special--;
				chars.remove(SPECIAL);
			}
			if (number == 0){
				number--;
				chars.remove(NUMBER);
			}

		}

		
		return pass;
	}

}
