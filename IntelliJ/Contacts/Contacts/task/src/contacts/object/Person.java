package contacts.object;

import java.time.LocalDate;

public class Person extends Contact {

    private static long serialVersionUID = 1L;

    protected String surname;
    protected LocalDate birthDate;
    protected Gender gender;

    public Person() {
        super();
    }

    protected String getSurname() {
        return surname;
    }

    protected String getBirthDate() {
        return birthDate == null ? "[no data]" : birthDate.toString();
    }

    protected String getGender() {
        return gender == null ? "[no data]" : gender.getSymbol();
    }

    protected void setSurname(String surname) {
        this.surname = surname;
    }

    protected void setBirthDate(String birthDate) {
        try {
            this.birthDate = LocalDate.parse(birthDate);
        } catch (Exception ex) {
            System.out.println("Bad birth date!");
            this.birthDate = null;
        }
    }

    protected void setGender(String gender) {
        this.gender = Gender.fromSymbol(gender);
        if (this.gender == null) {
            System.out.println("Bad gender!");
        }
    }

    @Override
    public Field[] getPossibleFields() {
        return new Field[] {Field.NAME, Field.SURNAME, Field.BIRTH_DATE, Field.GENDER, Field.PHONE_NUMBER};
    }

    @Override
    public void setField(Field field, String value) {
        super.setField(field, value);

        switch (field) {
            case NAME:
                setName(value);
                break;
            case SURNAME:
                setSurname(value);
                break;
            case BIRTH_DATE:
                setBirthDate(value);
                break;
            case GENDER:
                setGender(value);
                break;
            case PHONE_NUMBER:
                setPhoneNumber(value);
                break;
            default:
                // TODO error
                break;
        }
    }

    @Override
    public String getField(Field field) {

        switch (field) {
            case NAME:
                return getName();
            case SURNAME:
                return getSurname();
            case BIRTH_DATE:
                return getBirthDate();
            case GENDER:
                return getGender();
            case PHONE_NUMBER:
                return getPhoneNumber();
            default:
                // TODO error
                return null;
        }
    }

    @Override
    public String getSearchString() {
        return new StringBuilder()
                .append(name).append(surname).append(birthDate).append(gender).append(phoneNumber)
                .toString();
    }

    @Override
    public String getInfo() {
        return String.format("%s %s", name, surname);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name)
                .append("\nSurname: ").append(surname)
                .append("\nBirth date: ").append(birthDate == null ? "[no data]" : birthDate.toString())
                .append("\nGender: ").append(gender == null ? "[no data]" : gender.getSymbol())
                .append("\nNumber: ").append(phoneNumber)
                .append("\nTime created: ").append(createdAt)
                .append("\nTime last edit: ").append(updatedAt).append("\n");

        return sb.toString();
    }
}
