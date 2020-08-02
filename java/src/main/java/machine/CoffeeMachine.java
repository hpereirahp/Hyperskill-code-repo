package machine;

import java.util.Scanner;

public class CoffeeMachine {

	// stage 4
	static final Scanner scanner = new Scanner(System.in);
	static int storedWater = 400;
	static int storedMilk = 540;
	static int storedCoffee = 120;
	static int storedCups = 9;
	static int storedMoney = 550;

	public static void printMachineContents() {
		System.out.println("The coffee machine has:");
		System.out.println(storedWater + " ml of water");
		System.out.println(storedMilk + " ml of milk");
		System.out.println(storedCoffee + " g of coffee beans");
		System.out.println(storedCups + " of disposable cups");
		System.out.println(storedMoney + " of money\n");
	}

	public static void buyCoffee() {
		System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
		switch (scanner.nextInt()) {
		case 1:
			storedWater -= 250;
			storedCoffee -= 16;
			storedCups--;
			storedMoney += 4;
			break;
		case 2:
			storedWater -= 350;
			storedMilk -= 75;
			storedCoffee -= 20;
			storedCups--;
			storedMoney += 7;
			break;
		case 3:
			storedWater -= 200;
			storedMilk -= 100;
			storedCoffee -= 12;
			storedCups--;
			storedMoney += 6;
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
	}
	
	public static void takeMoney() {
		System.out.println("I gave you $" + storedMoney);
		storedMoney = 0;
	}

	public static void main(String[] args) {
		
		while (true) {
			printMachineContents();
			
			System.out.println("Write action (buy, fill, take):");
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
			default:
				break;
			}
			
		}
		
		
//		Stage 3
//		Scanner scanner = new Scanner(System.in);
//		int water = 200;
//		int milk = 50;
//		int coffee = 15;
//		
//		System.out.println("Write how many ml of water the coffee machine has:");
//		int currentWater = scanner.nextInt();
//		System.out.println("Write how many ml of milk the coffee machine has:");
//		int currentMilk = scanner.nextInt();
//		System.out.println("Write how many grams of coffee beans the coffee machine has:");
//		int currentCoffee = scanner.nextInt();
//		System.out.println("Write how many cups of coffee you will need:");
//		int cups = scanner.nextInt();
//		
//		int currentCups = Math.min(
//				Math.min(currentWater / water, currentMilk / milk), 
//				currentCoffee / coffee
//		);
//		
//		if (cups < currentCups) {
//			System.out.println("Yes, I can make that amount of coffee (and even " + (currentCups - cups) + " more than that)");
//		} else if (cups == currentCups) {
//			System.out.println("Yes, I can make that amount of coffee");
//		} else {
//			System.out.println("No, I can make only " + currentCups + " cup(s) of coffee");
//		}
//		
//		scanner.close();
		
//		Stage 2
//		Scanner scanner = new Scanner(System.in);
//		int water = 200;
//		int milk = 50;
//		int coffee = 15;
//		
//		System.out.println("Write how many cups of coffee you will need:");
//		int cups = scanner.nextInt();
//		
//		System.out.println("For " + cups + " cups of coffee you will need:");
//		System.out.println((cups * water) + " ml of water");
//		System.out.println((cups * milk) + " ml of milk");
//		System.out.println((cups * coffee) + " g of coffee beans");
//		
//		scanner.close();
		
//		Stage 1
//		System.out.println("Starting to make a coffee");
//		System.out.println("Grinding coffee beans");
//		System.out.println("Boiling water");
//		System.out.println("Mixing boiled water with crushed coffee beans");
//		System.out.println("Pouring coffee into the cup");
//		System.out.println("Pouring some milk into the cup");
//		System.out.println("Coffee is ready!");

	}
}
