package others;

import java.io.File;
import java.util.Scanner;

public class ReadingFiles {
	
	public static void main(String[] args) {
		// Count even numbers
		
		try (Scanner scanner = new Scanner(new File("./src/main/resources/others/dataset_91065.txt"))) {
			
			int number = 0;
			int count = 0;
			while (scanner.hasNextInt()) {
				number = scanner.nextInt();
				if (number == 0) {
					break;
				} else if (number % 2 == 0) {
					count++;
				}
			}
			System.out.println(count);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
