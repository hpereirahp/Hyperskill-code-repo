package phonebook;

public class TableEntry<K, T> {
    private final K key;
    private final T value;

    public TableEntry(K key, T value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }
}
