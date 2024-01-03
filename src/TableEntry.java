// Bryan Mundia, Eric Park, Sean Htet, Martin Tran, & Cyrus Foroudian
// Professor Abolghasemi
// Final Project

/**

 A class representing an entry in a table, with a key-value pair.

 @param <K> The type of the key.

 @param <V> The type of the value.
 */
public class TableEntry<K, V> {

    private K key;
    private V value;

    /**

     Constructs a table entry with the specified key and value.
     @param searchKey The key of the entry.
     @param dataValue The value of the entry.
     */
    public TableEntry(K searchKey, V dataValue) {
        key = searchKey;
        value = dataValue;
    }
    /**

     Retrieves the key of the entry.
     @return The key of the entry.
     */
    public K getKey() {
        return key;
    }
    /**

     Retrieves the value of the entry.
     @return The value of the entry.
     */
    public V getValue() {
        return value;
    }
    /**

     Sets the value of the entry.
     @param newValue The new value to be set.
     */
    public void setValue(V newValue) {
        value = newValue;
    }
}