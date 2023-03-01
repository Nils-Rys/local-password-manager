package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {
	private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBER = "0123456789";
	private static final String SPECIAL = "!@#$%^&*()-+=";
			
	
	public String generatePass() {
		String pass = "";
		int length = 15;
		
		List<String> chars = new ArrayList<>(4);
		chars.add(LOWER);
		chars.add(UPPER);
		chars.add(NUMBER);
		chars.add(SPECIAL);
		Random random = new Random();
		for(int i = 0; i < length; i++) {
            String charType = chars.get(random.nextInt(chars.size()));
            int pos = random.nextInt(charType.length());
            pass += charType.charAt(pos);

		}
		
		return pass;
	}

}
