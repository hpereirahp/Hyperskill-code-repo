package contacts.service;

public enum Command {
    ADD("add") {
        @Override
        public void execute(PhoneBookService phoneBookService) {
            phoneBookService.add();
        }
    },
    LIST("list") {
        @Override
        public void execute(PhoneBookService phoneBookService) {
            phoneBookService.list();
        }
    },
    SEARCH("search") {
        @Override
        public void execute(PhoneBookService phoneBookService) {
            phoneBookService.search();
        }
    },
    COUNT("count") {
        @Override
        public void execute(PhoneBookService phoneBookService) {
            phoneBookService.count();
        }
    },
    EXIT("exit") {
        @Override
        public void execute(PhoneBookService phoneBookService) {
            // TODO
        }
    };

    private final String text;

    Command(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Command fromText(String text) {
        for (Command c : Command.values()) {
            if (c.text.equals(text)) {
                return c;
            }
        }
        return null;
    }

    public abstract void execute(PhoneBookService phoneBookService);
}
