// Bryan Mundia, Eric Park, Sean Htet, Martin Tran, & Cyrus Foroudian
// Professor Abolghasemi
// Final Project

/**
 * A hash map implementation of the MapInterface.
 *
 * @param <K> The type of keys in the map.
 * @param <V> The type of values in the map.
 */
public class HashMap<K, V> implements MapInterface<K, V> {

    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 5; // Must be prime
    private static final int MAX_CAPACITY = 10000;

    private TableEntry<K, V>[] hashTable;
    private int tableSize; // Must be prime
    private static final int MAX_SIZE = 2 * MAX_CAPACITY;
    private boolean initialized = false;
    private static final double MAX_LOAD_FACTOR = 0.75;

    /**
     * Creates a new HashMap instance with the default initial capacity.
     */
    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates a new HashMap instance with the specified initial capacity.
     *
     * @param initialCapacity The initial capacity of the hash map.
     */
    public HashMap(int initialCapacity) {
        checkCapacity(initialCapacity);
        numberOfEntries = 0; // Dictionary is empty

        // Set up hash table:
        // Initial size of hash table is same as initialCapacity if it is prime;
        // otherwise increase it until it is prime size

        int tableSize = getNextPrime(initialCapacity);
        checkSize(tableSize);

        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        TableEntry<K, V>[] temp = (TableEntry<K, V>[]) new TableEntry[tableSize];
        hashTable = temp;
        initialized = true;
    } // end constructor


    /**
     * Adds or updates a key-value pair in the hash map.
     * If the hash table becomes too full, it will be enlarged.
     *
     * @param key   The key of the entry.
     * @param value The value of the entry.
     * @return The previous value associated with the key, or null if the key was not present.
     */
    public V put(K key, V value) {
        checkInitialization();
        V oldValue;

        if (isHashTableTooFull()) {
            enlargeHashTable();
        }

        int i = getHashIndex(key);
        i = probe(i, key); // check collision

        if (hashTable[i] == null) {
            hashTable[i] = new TableEntry<K, V>(key, value);
            numberOfEntries++;
            oldValue = null;
        } else {
            oldValue = hashTable[i].getValue();
            hashTable[i].setValue(value);
        }

        return oldValue;
    }


    /**
     * Removes the entry with the specified key from the hash map.
     *
     * @param key The key of the entry to be removed.
     * @return The value associated with the key, or null if the key was not present.
     */
    public V remove(K key) {
        checkInitialization();
        V removedValue = null;
        int i = getHashIndex(key);
        i = locate(i, key);

        if (i != -1) {

            removedValue = hashTable[i].getValue();
            numberOfEntries--;
        }
        return removedValue;
    }


    /**
     * Retrieves the value associated with the specified key from the hash map.
     *
     * @param key The key of the entry to retrieve.
     * @return The value associated with the key, or null if the key was not found.
     */
    public V get(K key) {
        V result = null;
        int i = getHashIndex(key);
        i = locate(i, key);

        if (i != -1) {
            result = hashTable[i].getValue();
        }
        return result;
    }


    /**
     * Checks whether the hash map contains the specified key.
     *
     * @param key The key to check.
     * @return true if the key is found, false otherwise.
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }


    /**
     * Checks whether the hash map contains the specified value.
     *
     * @param value The value to check.
     * @return true if the value is found, false otherwise.
     */
    public boolean containsValue(V value) {
        checkInitialization();
        for (TableEntry<K, V> entry : hashTable) {
            if (entry != null && entry.getValue().equals(value)) {
                return true; // Value found
            }
        }
        return false; // Value not found
    }

    /**
     * Checks if the hash map is empty.
     *
     * @return true if the hash map is empty, false otherwise.
     */
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }


    /**
     * Returns the number of entries in the hash map.
     *
     * @return The number of entries.
     */
    public int size() {
        return numberOfEntries;
    }


    /**
     * Removes all entries from the hash map.
     */
    public void clear() {
        checkInitialization();
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = null;
        }
        numberOfEntries = 0;
    }


    /**
     * Calculates the hash index for a given key.
     *
     * @param key The key to calculate the hash index for.
     * @return The hash index.
     */
    private int getHashIndex(K key) {
        int hashIndex = key.hashCode() % hashTable.length;
        if (hashIndex < 0) {
            hashIndex = hashIndex + hashTable.length;
        } // end if

        return hashIndex;
    }


    /**
     * Finds the next prime number greater than the given integer.
     *
     * @param integer The integer to find the next prime for.
     * @return The next prime number.
     */
    private int getNextPrime(int integer) {

        // if even, add 1 to make odd
        if (integer % 2 == 0) {
            integer++;
        } // end if

        // test odd integers
        while (!isPrime(integer)) {
            integer = integer + 2;
        } // end while
        return integer;
    } // end getNextPrime

    // Returns true if the given integer is prime.

    /**
     * Checks if the given integer is a prime number.
     *
     * @param integer The integer to check.
     * @return true if the integer is prime, false otherwise.
     */
    private boolean isPrime(int integer) {
        boolean result;
        boolean done = false;

        // 1 and even numbers are not prime
        if ((integer == 1) || (integer % 2 == 0)) {
            result = false;
        }

        // 2 and 3 are prime
        else if ((integer == 2) || (integer == 3)) {
            result = true;
        } else // integer is odd and >= 5
        {
            assert (integer % 2 != 0) && (integer >= 5);
            // a prime is odd and not divisible by every odd integer up to its square root
            result = true; // assume prime
            for (int divisor = 3; !done && (divisor * divisor <= integer); divisor = divisor + 2) {
                if (integer % divisor == 0) {
                    result = false; // divisible; not prime
                    done = true;
                } // end if
            } // end for
        } // end if
        return result;
    } // end isPrime


    /**
     * Probes for the next available index using linear probing.
     *
     * @param index The starting index.
     * @param key   The key to probe for.
     * @return The index of the next available slot.
     */
    private int probe(int index, K key) {
        boolean found = false;

        while (!found && (hashTable[index] != null)) {
            if (key.equals(hashTable[index].getKey()))
                found = true; // Key found
            else // Follow probe sequence
                index = (index + 1) % hashTable.length; // Linear probing

        } // end while
        return index;
    } // end probe


    /**
     * Locates the index of the entry with the specified key.
     *
     * @param index The starting index.
     * @param key   The key to locate.
     * @return The index of the entry with the specified key, or -1 if not found.
     */
    private int locate(int index, K key) {
        checkInitialization();  // Precondition: checkInitialization has been called.
        boolean found = false;

        while (!found && (hashTable[index] != null)) {
            if (key.equals(hashTable[index].getKey()))
                found = true; // Key found
            else // Follow probe sequence
                index = (index + 1) % hashTable.length; // Linear probing
        } // end while
        // Assertion: Either key or null is found at hashTable[index]
        int result = -1;
        if (found)
            result = index;
        return result;
    } // end locate


    /**
     * Checks if the hash map has been initialized.
     *
     * @throws IllegalStateException if the hash map has not been initialized.
     */
    private void checkInitialization() {
        if (!initialized)
            throw new SecurityException("HashedDictionary object is not initialized properly.");
    } // end checkInitialization


    /**
     * Checks if the specified capacity is within the allowed range.
     *
     * @param capacity The capacity to check.
     * @throws IllegalArgumentException if the capacity is out of range.
     */
    private void checkCapacity(int capacity) {
        if (capacity < DEFAULT_CAPACITY)
            capacity = DEFAULT_CAPACITY;
        else if (capacity > MAX_CAPACITY)
            throw new IllegalStateException(
                    "Attempt to create a dictionary " + "whose capacity is larger than " + MAX_CAPACITY);
    } // end checkCapacity

    // Throws an exception if the hash table becomes too large.
    private void checkSize(int size) {
        if (tableSize > MAX_SIZE)
            throw new IllegalStateException("Dictionary has become too large.");
    } // end checkSize

    /**
     * Doubles the size of the hash table and rehashes all the entries.
     */
    private void enlargeHashTable() {

        TableEntry<K, V>[] oldTable = hashTable;
        int oldSize = hashTable.length;
        int newSize = getNextPrime(oldSize + oldSize);

        checkSize(newSize);

        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        TableEntry<K, V>[] tempTable = (TableEntry<K, V>[]) new TableEntry[newSize]; // Increase size of array
        hashTable = tempTable;
        numberOfEntries = 0; // Reset number of dictionary entries, since

        for (int index = 0; index < oldSize; index++) {
            if ((oldTable[index] != null))
                put(oldTable[index].getKey(), oldTable[index].getValue());
        } // end for
    } // end enlargeHashTable


    /**
     * Checks if the hash map is too full, based on the maximum load factor.
     *
     * @return true if the hash map is too full, false otherwise.
     */
    private boolean isHashTableTooFull() {
        return numberOfEntries > MAX_LOAD_FACTOR * hashTable.length;
    }


    @SuppressWarnings("unchecked")
    @Override
    public CustomHashSet<K> keySet() {
        CustomHashSet<K> keySet = new CustomHashSet<>();
        for (TableEntry<K,V> t : hashTable) {
            if (t != null)
                keySet.add((K) t.getKey());
        }
        return keySet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CustomHashSet<V> valueSet() {
        CustomHashSet<V> valueSet = new CustomHashSet<>();
        for (TableEntry<K,V> t : hashTable) {
            if (t != null)
                valueSet.add((V) t.getValue());
        }
        return valueSet;
    }

    @Override
    public CustomHashSet<TableEntry<K, V>> entrySet() {
        CustomHashSet<TableEntry<K, V>> entrySet = new CustomHashSet<>();
        for (TableEntry<K,V> entry : hashTable) {
            if (entry != null)
                entrySet.add(entry);
        }
        return entrySet;
    }


}
