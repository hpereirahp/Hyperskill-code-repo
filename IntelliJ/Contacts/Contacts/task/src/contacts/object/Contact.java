package contacts.object;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Contact implements Serializable {

    private static long serialVersionUID = 1L;

    protected String name;
    protected String phoneNumber;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    private static final Pattern pattern = Pattern.compile("\\+?(\\([\\da-zA-Z]+\\)|[\\da-zA-Z]+([ -]\\([\\da-zA-Z]{2,}\\))?)([ -][\\da-zA-Z]{2,})*");

    protected Contact() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getPhoneNumber() {
        return phoneNumber;
    }

    protected void setPhoneNumber(String phoneNumber) {
        if (validatePhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            this.phoneNumber = "";
        }
    }

    private void updateLastEditDate() {
        this.updatedAt = LocalDateTime.now();
    }

    private static boolean validatePhoneNumber(String phoneNumber) {
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean hasNumber() {
        return !phoneNumber.isEmpty();
    }

    public abstract String getInfo();
    public abstract Field[] getPossibleFields();
    public void setField(Field field, String value) {
        updateLastEditDate();
    }
    public abstract String getField(Field field);
    public abstract String getSearchString();

    @Override
    public String toString() {
        return String.format("%s, %s\n", name, hasNumber() ? phoneNumber : "[no number]");
    }

}
