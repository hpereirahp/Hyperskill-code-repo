package contacts.object;

public enum Gender {
    MALE("M"), FEMALE("F");

    private final String symbol;

    Gender(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Gender fromSymbol(String symbol) {
        for (Gender g : Gender.values()) {
            if (g.symbol.equals(symbol)) {
                return g;
            }
        }
        return null;
    }
}
