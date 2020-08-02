package machine;

import java.util.Scanner;

public class CoffeeMachine6 {
	
	// stage 6
	public enum CoffeeMachineStatus {
		WAITING,
		BUYING_COFFEE,
		FILLING_MACHINE_WATER,
		FILLING_MACHINE_MILK,
		FILLING_MACHINE_COFFEE,
		FILLING_MACHINE_CUPS
	}
	
	public static class CoffeeMachineResources {

		private int water;
		private int milk;
		private int coffee;
		private int cups;
		private int money;
		
		/**
		 * Constructor
		 * 
		 * @param water
		 * @param milk
		 * @param coffee
		 * @param cups
		 * @param money
		 */
		public CoffeeMachineResources(int water, int milk, int coffee, int cups, int money) {
			this.water = water;
			this.milk = milk;
			this.coffee = coffee;
			this.cups = cups;
			this.money = money;
		}

		public int getWater() {
			return water;
		}

		public void setWater(int water) {
			this.water = water;
		}

		public int getMilk() {
			return milk;
		}

		public void setMilk(int milk) {
			this.milk = milk;
		}

		public int getCoffee() {
			return coffee;
		}

		public void setCoffee(int coffee) {
			this.coffee = coffee;
		}

		public int getCups() {
			return cups;
		}

		public void setCups(int cups) {
			this.cups = cups;
		}

		public int getMoney() {
			return money;
		}

		public void setMoney(int money) {
			this.money = money;
		}
	}
	
	public static class CoffeeMachineClass {
		
		private CoffeeMachineStatus status;
		private CoffeeMachineResources resources;
		
		/**
		 * Constructor
		 * 
		 * @param water
		 * @param milk
		 * @param coffee
		 * @param cups
		 * @param money
		 */
		public CoffeeMachineClass(int water, int milk, int coffee, int cups, int money) {
			this.resources = new CoffeeMachineResources(water, milk, coffee, cups, money);
			this.status = CoffeeMachineStatus.WAITING;
		}

		private void printMachineResources() {
			System.out.println("\nThe coffee machine has:");
			System.out.println(resources.getWater() + " ml of water");
			System.out.println(resources.getMilk() + " ml of milk");
			System.out.println(resources.getCoffee() + " g of coffee beans");
			System.out.println(resources.getCups() + " of disposable cups");
			System.out.println(resources.getMoney() + " of money\n");
		}

		private boolean hasEnoughWater(int water) {
			if (resources.getWater() < water) {
				System.out.println("Sorry, not enough water!\n");
				return false;
			}
			return true;
		}
		
		private boolean hasEnoughMilk(int milk) {
			if (resources.getMilk() < milk) {
				System.out.println("Sorry, not enough milk!\n");
				return false;
			}
			return true;
		}
		
		private boolean hasEnoughCoffee(int coffee) {
			if (resources.getCoffee() < coffee) {
				System.out.println("Sorry, not enough coffee beans!\n");
				return false;
			}
			return true;
		}
		
		private boolean hasEnoughCups() {
			if (resources.getCups() < 1) {
				System.out.println("Sorry, not enough disposable cups!\n");
				return false;
			}
			return true;
		}
		
		private void processBuyRequest(int water, int milk, int coffee, int money) {
			if (hasEnoughWater(water) && hasEnoughMilk(milk) &&
					hasEnoughCoffee(coffee) && hasEnoughCups()) {
				System.out.println("I have enough resources, making you a coffee!\n");

				resources.setWater(resources.getWater() - water);
				resources.setMilk(resources.getMilk() - milk);
				resources.setCoffee(resources.getCoffee() - coffee);
				resources.setCups(resources.getCups() - 1);
				resources.setMoney(resources.getMoney() + money);
			} 
		}
		
		private void takeMoney() {
			System.out.println("I gave you $" + resources.getMoney());
			resources.setMoney(0);
		}
		
		private void printStatusMessage() {
			switch (status) {
				case WAITING:
					System.out.println("Write action (buy, fill, take, remaining, exit):");
					break;
				case BUYING_COFFEE:
					System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
					break;
				case FILLING_MACHINE_WATER:
					System.out.println("Write how many ml of water do you want to add:");
					break;
				case FILLING_MACHINE_MILK:
					System.out.println("Write how many ml of milk do you want to add:");
					break;
				case FILLING_MACHINE_COFFEE:
					System.out.println("Write how many grams of coffee beans do you want to add:");
					break;
				case FILLING_MACHINE_CUPS:
					System.out.println("Write how many disposable cups of coffee do you want to add:");
					break;
				default:
					break;
			}
		}
		
		public void start() {
			this.status = CoffeeMachineStatus.WAITING;
			printStatusMessage();
		}
		
		public boolean handleCommand(String command) {
			switch (status) {
				case WAITING:
					switch (command) {
						case "buy":
							status = CoffeeMachineStatus.BUYING_COFFEE;
							break;
						case "fill":
							status = CoffeeMachineStatus.FILLING_MACHINE_WATER;
							break;
						case "take":
							takeMoney();
							break;
						case "remaining":
							printMachineResources();
							break;
						case "exit":
							return false;
						default:
							break;
					}
					break;

				case BUYING_COFFEE:
					switch (command) {
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
					status = CoffeeMachineStatus.WAITING;
					break;
				case FILLING_MACHINE_WATER:
					status = CoffeeMachineStatus.FILLING_MACHINE_MILK;
					resources.setWater(resources.getWater() + Integer.parseInt(command));
					break;
				case FILLING_MACHINE_MILK:
					status = CoffeeMachineStatus.FILLING_MACHINE_COFFEE;
					resources.setMilk(resources.getMilk() + Integer.parseInt(command));
					break;
				case FILLING_MACHINE_COFFEE:
					status = CoffeeMachineStatus.FILLING_MACHINE_CUPS;
					resources.setCoffee(resources.getCoffee() + Integer.parseInt(command));
					break;
				case FILLING_MACHINE_CUPS:
					status = CoffeeMachineStatus.WAITING;
					resources.setCups(resources.getCups() + Integer.parseInt(command));
					System.out.println();
					break;
				default:
					break;
			}
			printStatusMessage();
			return true;
		}
	}

	public static void main(String[] args) {

		final Scanner scanner = new Scanner(System.in);
		
		CoffeeMachineClass coffeeMachine = new CoffeeMachineClass(400, 540, 120, 9, 550);
		coffeeMachine.start();
		
		while (coffeeMachine.handleCommand(scanner.nextLine())) { }
		
		scanner.close();
	}
	
	
}
