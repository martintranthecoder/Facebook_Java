// Bryan Mundia, Eric Park, Sean Htet, Martin Tran, & Cyrus Foroudian
// Professor Abolghasemi
// Final Project

/**

 An interface representing a generic map data structure.

 @param <K> The type of the keys in the map.

 @param <V> The type of the values in the map.
 */
public interface MapInterface<K, V> {

    /**

     Associates the specified value with the specified key in this map.
     @param key The key with which the specified value is to be associated.
     @param value The value to be associated with the specified key.
     @return The previous value associated with the specified key, or null if there was no mapping for the key.
     */
    public V put(K key, V value);
    /**

     Removes the mapping for the specified key from this map if present.
     @param key The key whose mapping is to be removed from the map.
     @return The previous value associated with the specified key, or null if there was no mapping for the key.
     */
    public V remove(K key);
    /**

     Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     @param key The key whose associated value is to be returned.
     @return The value to which the specified key is mapped, or null if this map contains no mapping for the key.
     */
    public V get(K key);
    /**

     Checks if this map contains a mapping for the specified key.
     @param key The key whose presence in this map is to be tested.
     @return true if this map contains a mapping for the specified key, false otherwise.
     */
    public boolean containsKey(K key);
    /**

     Checks if this map maps one or more keys to the specified value.
     @param value The value to check for presence in this map.
     @return true if this map maps one or more keys to the specified value, false otherwise.
     */
    public boolean containsValue(V value);
    /**

     Checks if this map contains no key-value mappings.
     @return true if this map contains no key-value mappings, false otherwise.
     */
    public boolean isEmpty();
    /**

     Returns the number of key-value mappings in this map.
     @return The number of key-value mappings in this map.
     */
    public int size();
    /**

     Removes all of the mappings from this map.
     */
    public void clear();
    /**

     Returns a set view of the keys contained in this map.
     @return A set view of the keys contained in this map.
     */
    public CustomHashSet<K> keySet();
    /**

     Returns a set view of the values contained in this map.
     @return A set view of the values contained in this map.
     */
    public CustomHashSet<V> valueSet();
    /**

     Returns a set view of the key-value mappings contained in this map.
     @return A set view of the key-value mappings contained in this map.
     */
    public CustomHashSet<TableEntry<K,V>> entrySet();
}