package contacts.object;

public enum Field {
    NAME("name", "name"),
    PHONE_NUMBER("number", "number"),

    SURNAME("surname", "surname"),
    GENDER("gender", "gender"),
    BIRTH_DATE("birth", "birth date"),

    ADDRESS("address", "address");

    private final String text;
    private String fullName;

    Field(String text, String fullName) {
        this.text = text;
        this.fullName = fullName;
    }

    public String getText() {
        return text;
    }

    public String getFullName() {
        return fullName;
    }

    public Field setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public static Field fromText(String text) {
        for (Field f : Field.values()) {
            if (f.text.equals(text)) {
                return f;
            }
        }
        return null;
    }
}
