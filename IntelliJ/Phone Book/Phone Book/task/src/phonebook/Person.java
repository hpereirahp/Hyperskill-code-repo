package phonebook;

import org.jetbrains.annotations.NotNull;

public class Person implements Comparable<Person> {

    private String fullName;
    private int phone;

    public Person(int phone, String fullName) {
        this.fullName = fullName;
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public int compareTo(@NotNull Person person) {
        return this.fullName.compareTo(person.fullName);
    }
}
