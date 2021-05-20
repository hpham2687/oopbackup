package helper;

import java.util.Random;

public class NumberHelper {
	public int getRandomNumber(int min, int max) {
		Random r = new Random();
		int result = r.nextInt(max-min) + min;
	return result;	
	}
}
