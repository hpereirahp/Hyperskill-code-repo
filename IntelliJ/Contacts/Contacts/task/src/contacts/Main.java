package contacts;

import contacts.controller.Controller;
import contacts.object.utils.SerializationUtils;
import contacts.repository.PhoneBookRepository;
import contacts.service.Command;
import contacts.service.PhoneBookService;
import contacts.object.factory.ContactFactory;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);
        final Controller controller = new Controller(scanner);

        final ContactFactory contactFactory = new ContactFactory();

        final String fileName = args.length > 0 ? args[0] : null;
        PhoneBookRepository repository;
        try {
            repository = (PhoneBookRepository) SerializationUtils.deserialize(fileName);
        } catch (Exception ex) {
            controller.print(String.format("%s%n", ex.getMessage()));
            repository = new PhoneBookRepository();
        }

        final PhoneBookService phoneBook = new PhoneBookService(repository, controller, contactFactory, fileName);

        Command command = null;
        while (command != Command.EXIT) {
            controller.print("[menu] Enter action (add, list, search, count, exit): ");
            command = Command.fromText(scanner.nextLine());
            if (command != null) {
                command.execute(phoneBook);
            } else {
                controller.print("Bad action!\n");
            }
            controller.print("\n");
        }
    }
}
