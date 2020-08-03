package bot;

import java.util.Scanner;

public class SimpleBot {

	public static void main(String[] args) {

		// stage 3
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Hello! My name is Aid.");
		System.out.println("I was created in 2020.");
		System.out.println("Please, remind me your name.");
		System.out.printf("What a great name you have, %s!%n", scanner.nextLine());
		System.out.println("Let me guess your age.");
		System.out.println("Enter remainders of dividing your age by 3, 5 and 7.");
		int age = (scanner.nextInt() * 70 + scanner.nextInt() * 21 + scanner.nextInt() * 15) % 105;
		System.out.printf("Your age is %d; that's a good time to start programming!%n", age);
		
		scanner.close();
		
//		// stage 2
//		Scanner scanner = new Scanner(System.in);
//		
//		System.out.println("Hello! My name is Aid.");
//		System.out.println("I was created in 2020.");
//		System.out.println("Please, remind me your name.");
//		System.out.printf("What a great name you have, %s!%n", scanner.nextLine());
//		
//		scanner.close();
		

//		// stage 1
//		System.out.println("Hello! My name is Aid.");
//		System.out.println("I was created in 2020.");
	}

}
