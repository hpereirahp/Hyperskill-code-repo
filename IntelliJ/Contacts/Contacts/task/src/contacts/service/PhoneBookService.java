package contacts.service;

import contacts.controller.Controller;
import contacts.object.Contact;
import contacts.object.Field;
import contacts.object.factory.ContactFactory;
import contacts.object.utils.SerializationUtils;
import contacts.repository.PhoneBookRepository;

import java.util.Arrays;
import java.util.List;

public class PhoneBookService {
    private PhoneBookRepository repository;
    private Controller controller;
    private ContactFactory contactFactory;
    private String fileName;

    public PhoneBookService(PhoneBookRepository repository, Controller controller,
                            ContactFactory contactFactory, String fileName) {
        this.repository = repository;
        this.controller = controller;
        this.contactFactory = contactFactory;
        this.fileName = fileName;
    }

    public void add() {
        controller.print("[add] Enter the type (person, organization): ");
        Contact contact = contactFactory.createContact(controller.getInput());

        Field[] fields = contact.getPossibleFields();
        for (Field field : fields) {
            controller.print(String.format("Enter the %s: ", field.getFullName()));
            contact.setField(field, controller.getInput());
        }

        repository.addContact(contact);
        controller.print("A record created!\n");
        save();
    }

    public void list() {
        controller.print(repository.printList());

        controller.print("\n[list] Enter action ([number], back): ");
        String action = controller.getInput();

        if (action.equals("back")) {
            return;
        }

        record(Integer.parseInt(action) - 1);
    }

    public void search() {
        controller.print("Enter search query: ");
        List<Integer> indexes = repository.searchContacts(controller.getInput());

        controller.print(String.format("Found %d results: %n", indexes.size()));
        for (int i = 0; i < indexes.size(); i++) {
            controller.print(String.format("%d. %s%n", i + 1, repository.printContactInfo(indexes.get(i))));
        }

        controller.print("\n[search] Enter action ([number], back, again): ");
        String action = controller.getInput();
        switch (action) {
            case "back":
                return;
            case "again":
                search();
                break;
            default:
                record(indexes.get(Integer.parseInt(action)));
                break;
        }
    }

    public void count() {
        controller.print(
                String.format("The Phone Book has %d records.%n", repository.getCount())
        );
    }

    private void record(int index) {
        controller.print(repository.printContact(index));

        while (true) {
            controller.print("\n[record] Enter action (edit, delete, menu): ");
            switch (controller.getInput()) {
                case "edit":
                    edit(index);
                    break;
                case "remove":
                    remove(index);
                    break;
                case "menu":
                    return;
                default:
                    // TODO error
                    break;
            }
        }
    }

    private void remove(int index) {
        if (repository.getCount() == 0) {
            controller.print("No records to remove!\n");
            return;
        }

        repository.removeContact(index);

        controller.print("The record removed!\n");
        save();
    }

    private void edit(int index) {
        if (repository.getCount() == 0) {
            controller.print("No records to edit!\n");
            return;
        }

        Field[] fields = repository.getPossibleFields(index);

        controller.print(
                String.format("Select a field (%s): ",
                        String.join(", ",
                                Arrays.stream(fields)
                                        .map(Field::getText)
                                        .toArray(String[]::new)
                        )
                )
        );
        Field field = Field.fromText(controller.getInput());
        controller.print(String.format("Enter %s: ", field.getFullName()));

        repository.editContact(index, field, controller.getInput());

        save();
        controller.print(repository.printContact(index));
    }
    
    private void save() {
        if (fileName != null && !fileName.isEmpty()) {
            try {
                SerializationUtils.serialize(repository, fileName);
                controller.print("Saved\n");
            } catch (Exception ex) {
                controller.print(String.format("%s%n", ex.getMessage()));
            }
        }
    }
}
