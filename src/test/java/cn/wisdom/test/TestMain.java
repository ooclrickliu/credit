package cn.wisdom.test;

import java.util.Random;

public class TestMain {

	public static void main(String[] args) {
		Random random = new Random();
		
		for (int i = 0; i < 100; i++) {
			System.out.println(random.nextInt(7000) + 3000);
		}
	}
}
