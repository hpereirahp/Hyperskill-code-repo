package contacts.object;

public class Organization extends Contact {

    private static long serialVersionUID = 1L;

    protected String address;

    public Organization() {
        super();
    }

    protected String getAddress() {
        return address;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Field[] getPossibleFields() {
        return new Field[] {Field.NAME.setFullName("organization name"), Field.ADDRESS, Field.PHONE_NUMBER};
    }

    @Override
    public void setField(Field field, String value) {
        super.setField(field, value);

        switch (field) {
            case NAME:
                setName(value);
                break;
            case ADDRESS:
                setAddress(value);
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
            case ADDRESS:
                return getAddress();
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
                .append(name).append(address).append(phoneNumber)
                .toString();
    }

    @Override
    public String getInfo() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Organization name: ").append(name)
                .append("\nAddress: ").append(address)
                .append("\nNumber: ").append(phoneNumber)
                .append("\nTime created: ").append(createdAt)
                .append("\nTime last edit: ").append(updatedAt).append("\n");

        return sb.toString();
    }
}
