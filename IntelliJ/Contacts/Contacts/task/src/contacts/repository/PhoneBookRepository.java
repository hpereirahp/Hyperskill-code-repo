package contacts.repository;

import contacts.object.Contact;
import contacts.object.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBookRepository implements Serializable {

    private static long serialVersionUID = 1L;

    private List<Contact> contacts;

    public PhoneBookRepository() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(int index) {
        contacts.remove(index);
    }

    public void editContact(int index, Field field, String value) {
        contacts.get(index).setField(field, value);
    }

    public List<Integer> searchContacts(String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < contacts.size(); i++) {
            Matcher matcher = pattern.matcher(contacts.get(i).getSearchString());
            if (matcher.find()) {
                indexes.add(i);
            }
        }

        return indexes;
    }

    public Field[] getPossibleFields(int index) {
        return contacts.get(index).getPossibleFields();
    }

    public int getCount() {
        return contacts.size();
    }

    public String printList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contacts.size(); i++) {
            sb.append(i + 1).append(". ").append(contacts.get(i).getInfo()).append("\n");
        }
        return sb.toString();
    }

    public String printContactInfo(int index) {
        return contacts.get(index).getInfo();
    }

    public String printContact(int index) {
        return contacts.get(index).toString();
    }
}
