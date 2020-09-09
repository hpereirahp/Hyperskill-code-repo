package phonebook;

public class HashTable<K, T> {

    private int size;
    private TableEntry[] table;

    public HashTable(int size) {
        this.size = size;
        table = new TableEntry[size];
    }

    public boolean put(K key, T value) {
        int idx = findKey(key);

        if (idx == -1) {
            return false;
        }

        table[idx] = new TableEntry(key, value);
        return true;
    }

    public T get(K key) {
        int idx = findKey(key);

        if (idx == -1 || table[idx] == null) {
            return null;
        }

        return (T) table[idx].getValue();
    }

    public void remove(K key) {
        int idx = findKey(key);

        if (idx == -1) {
            table[idx] = null;
        }
    }

    private int findKey(K key) {
        int hashCode = Math.abs(key.hashCode()) % size;
        int hash = hashCode;

        while (table[hash] != null && !table[hash].getKey().equals(key)) {
            hash = (hash + 1) % size;

            if (hash == hashCode) {
                return -1;
            }
        }

        return hash;
    }
}
