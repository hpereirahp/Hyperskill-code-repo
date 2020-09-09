package contacts.object.factory;

import contacts.object.Contact;
import contacts.object.Organization;
import contacts.object.Person;

public class ContactFactory {

    public Contact createContact(String type) {
        switch (type) {
            case "person":
                return new Person();
            case "organization":
                return new Organization();
            default:
                return null;
        }
    }
}
