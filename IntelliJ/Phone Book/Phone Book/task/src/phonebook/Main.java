package phonebook;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // ../../Hyperskill-code-repo/Hyperskill-code-repo/java/src/main/resources/phonebook/directory.txt
        // ../../Hyperskill-code-repo/Hyperskill-code-repo/java/src/main/resources/phonebook/find.txt

        // D:\CV\Github\Hyperskill\Hyperskill-code-repo\Hyperskill-code-repo\java\src\main\resources\phonebook\directory.txt
        // D:\CV\Github\Hyperskill\Hyperskill-code-repo\Hyperskill-code-repo\java\src\main\resources\phonebook\find.txt

        try {
            List<Person> phoneBook = new ArrayList<>();
            List<String> names = new ArrayList<>();

            // Read PhoneBook from directory.txt
            Scanner scanner = new Scanner(new File("D:\\CV\\Github\\Hyperskill\\Hyperskill-code-repo\\Hyperskill-code-repo\\java\\src\\main\\resources\\phonebook\\directory.txt"));
            while (scanner.hasNextInt()) {
                phoneBook.add(new Person(scanner.nextInt(), scanner.nextLine().trim()));
            }
            scanner.close();

            // Read people's names from find.txt
            scanner = new Scanner(new File("D:\\CV\\Github\\Hyperskill\\Hyperskill-code-repo\\Hyperskill-code-repo\\java\\src\\main\\resources\\phonebook\\find.txt"));
            while (scanner.hasNext()) {
                names.add(scanner.nextLine().trim());
            }
            scanner.close();

            // Linear Search
            System.out.println("Start searching (linear search)...");
            long start = System.currentTimeMillis();
            int count = 0;
            for (String name : names) {
                if (Search.linearSearch(phoneBook, name)) {
                    count++;
                }
            }
            long duration = System.currentTimeMillis() - start;

//            System.out.printf("First Person: %d %s%n", people.get(0).getPhone(), people.get(0).getFullName());
//            System.out.printf("Number of people: %s%n", people.size());
//            System.out.printf("Number of names: %s%n", names.size());
//            System.out.printf("Count: %d%n", count);
            System.out.printf("Found %d / %d entries. Time taken: %s%n%n", count, names.size(), Search.getDurationToPrint(duration));

            // Bubble Sort
            System.out.println("Start searching (bubble sort + jump search)...");
            List<Person> sortedPhoneBook = new ArrayList<>(phoneBook);
            Collections.copy(sortedPhoneBook, phoneBook);

            long startBubble = System.currentTimeMillis();
            boolean bubbleSortFinished = Search.bubbleSort(sortedPhoneBook);
            long durationBubble = System.currentTimeMillis() - startBubble;


            // Jump Search
            long startJump = System.currentTimeMillis();
            int countJump = 0;
            if (bubbleSortFinished) {
                for (String name : names) {
                    if (Search.jumpSearch(sortedPhoneBook, name) != -1) {
                        countJump++;
                    }
                }
            } else {
                for (String name : names) {
                    if (Search.linearSearch(sortedPhoneBook, name)) {
                        countJump++;
                    }
                }
            }
//            // jumpSearch with Ordered String[]
//            String[] sss = new String[phoneBook.size()];
//            for (int i = 0; i < sss.length; i++) {
//                sss[i] = phoneBook.get(i).getFullName();
//            }
//            Arrays.sort(sss);
//            for (String name : names) {
//                if (Search.jumpSearch(sss, name) != -1) {
//                    countJump++;
//                }
//            }
            long durationJump = System.currentTimeMillis() - startJump;

            System.out.printf("Found %d / %d entries. Time taken: %s%n", countJump, names.size(), Search.getDurationToPrint(durationBubble + durationJump));
            System.out.printf("Sorting time: %s", Search.getDurationToPrint(durationBubble));
            System.out.printf("%s%n", bubbleSortFinished ? "" : " - STOPPED, moved to linear search");
            System.out.printf("Searching time: %s%n", Search.getDurationToPrint(durationJump));


            // Quick Sort
            System.out.println("Start searching (quick sort + binary search)...");
            sortedPhoneBook = new ArrayList<>(phoneBook);
            Collections.copy(sortedPhoneBook, phoneBook);

            long startQuick = System.currentTimeMillis();
            Search.quickSort(sortedPhoneBook, 0, sortedPhoneBook.size() - 1);
            long durationQuick = System.currentTimeMillis() - startQuick;

            // Binary Search
            long startBinary = System.currentTimeMillis();
            int countBinary = 0;
            for (String name : names) {
                if (Search.binarySearch(sortedPhoneBook, name) != -1) {
                    countBinary++;
                }
            }
            long durationBinary = System.currentTimeMillis() - startBinary;

            System.out.printf("Found %d / %d entries. Time taken: %s%n", countBinary, names.size(), Search.getDurationToPrint(durationQuick + durationBinary));
            System.out.printf("Sorting time: %s%n", Search.getDurationToPrint(durationQuick));
            System.out.printf("Searching time: %s%n", Search.getDurationToPrint(durationBinary));


            // HashTable creation
            System.out.println("Start searching (hash table)...");
            long startHashCreate = System.currentTimeMillis();
            HashTable<String, Integer> table = new HashTable<>(12000000);
            for (Person person : phoneBook) {
                table.put(person.getFullName(), person.getPhone());
            }
            long durationHashCreate = System.currentTimeMillis() - startHashCreate;

            // HashTable Search
            long startHashSearch = System.currentTimeMillis();
            int countHashSearch = 0;
            for (String name : names) {
                if (table.get(name) != null) {
                    countHashSearch++;
                }
            }
            long durationHashSearch = System.currentTimeMillis() - startHashSearch;

            System.out.printf("Found %d / %d entries. Time taken: %s%n", countHashSearch, names.size(), Search.getDurationToPrint(durationHashCreate + durationHashSearch));
            System.out.printf("Creating time: %s%n", Search.getDurationToPrint(durationHashCreate));
            System.out.printf("Searching time: %s%n", Search.getDurationToPrint(durationHashSearch));

        } catch (Exception e) {
            System.out.printf("ERROR: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }
}
