package machine;

import java.util.Scanner;

public class CoffeeMachine2 {

	// stage 5
	static final Scanner scanner = new Scanner(System.in);
	static int storedWater = 400;
	static int storedMilk = 540;
	static int storedCoffee = 120;
	static int storedCups = 9;
	static int storedMoney = 550;

	public static boolean hasEnoughWater(int water) {
		if (storedWater < water) {
			System.out.println("Sorry, not enough water!\n");
			return false;
		}
		return true;
	}
	
	public static boolean hasEnoughMilk(int milk) {
		if (storedMilk < milk) {
			System.out.println("Sorry, not enough milk!\n");
			return false;
		}
		return true;
	}
	
	public static boolean hasEnoughCoffee(int coffee) {
		if (storedCoffee < coffee) {
			System.out.println("Sorry, not enough coffee beans!\n");
			return false;
		}
		return true;
	}
	
	public static boolean hasEnoughCups() {
		if (storedCups < 1) {
			System.out.println("Sorry, not enough disposable cups!\n");
			return false;
		}
		return true;
	}
	
	public static void processBuyRequest(int water, int milk, int coffee, int money) {
		if (hasEnoughWater(water) && hasEnoughMilk(milk) &&
				hasEnoughCoffee(coffee) && hasEnoughCups()) {
			System.out.println("I have enough resources, making you a coffee!\n");

			storedWater -= water;
			storedMilk -= milk;
			storedCoffee -= coffee;
			storedCups--;
			storedMoney += money;
		} 
	}

	public static void printMachineResources() {
		System.out.println("\nThe coffee machine has:");
		System.out.println(storedWater + " ml of water");
		System.out.println(storedMilk + " ml of milk");
		System.out.println(storedCoffee + " g of coffee beans");
		System.out.println(storedCups + " of disposable cups");
		System.out.println(storedMoney + " of money\n");
	}

	public static void buyCoffee() {
		System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
		switch (scanner.nextLine()) {
			case "1":
				processBuyRequest(250, 0, 16, 4);
				break;
			case "2":
				processBuyRequest(350, 75, 20, 7);
				break;
			case "3":
				processBuyRequest(200, 100, 12, 6);
				break;
			case "back":
				break;
			default:
				break;
		}
	}
	
	public static void fillMachine() {
		System.out.println("Write how many ml of water do you want to add:");
		storedWater += scanner.nextInt();
		System.out.println("Write how many ml of milk do you want to add:");
		storedMilk += scanner.nextInt();
		System.out.println("Write how many grams of coffee beans do you want to add:");
		storedCoffee += scanner.nextInt();
		System.out.println("Write how many disposable cups of coffee do you want to add:");
		storedCups += scanner.nextInt();
		System.out.println();
	}
	
	public static void takeMoney() {
		System.out.println("I gave you $" + storedMoney);
		storedMoney = 0;
	}

	public static void main(String[] args) {
		
		boolean canRun = true;
		while (canRun) {
			
			System.out.println("Write action (buy, fill, take, remaining, exit):");
			switch (scanner.nextLine()) {
				case "buy":
					buyCoffee();
					break;
				case "fill":
					fillMachine();
					break;
				case "take":
					takeMoney();
					break;
				case "remaining":
					printMachineResources();
					break;
				case "exit":
					canRun = false;
					break;
				default:
					break;
			}
			
		}
		scanner.close();
	}

}
