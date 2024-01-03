// Bryan Mundia, Eric Park, Sean Htet, Martin Tran, & Cyrus Foroudian
// Professor Abolghasemi
// Final Project

public class UndirectedGraph<T> implements GraphInterface<T> {
    private HashMap<T, CustomHashSet<T>> friendsMap;
    int edgeCount = 0;


    /**
     * Constructs an empty UndirectedGraph.
     */
    public UndirectedGraph() {
        friendsMap = new HashMap<>();
    }


    /**
     * Adds a vertex with the given label to the graph.
     *
     * @param vertexLabel the label of the vertex to be added
     * @return true if the vertex is added successfully, false if the vertex already exists in the graph
     */
    @Override
    public boolean addVertex(T vertexLabel) {
        if (friendsMap.containsKey(vertexLabel)) {
            System.out.println("The given profile is already added in the Graph.");
            return false;
        } else {
            friendsMap.put(vertexLabel, new CustomHashSet<>());
            System.out.println("Profile added successfully to the Graph.");
            return true;
        }
    }


    /**
     * Removes a vertex with the given label from the graph.
     *
     * @param vertexLabel the label of the vertex to be removed
     */
    @Override
    public void removeVertex(T vertexLabel) {
        if (friendsMap.containsKey(vertexLabel)) {
            for (T friend : friendsMap.get(vertexLabel)) {
                friendsMap.get(friend).remove(vertexLabel);
                edgeCount--;
            }
            friendsMap.remove(vertexLabel);
            System.out.println("Profile removed successfully from the Graph.");
        } else {
            System.out.println("The given profile is not present in the Graph.");
        }
    }


    /**
     * Adds an edge between two vertices in the graph.
     *
     * @param begin the label of the first vertex
     * @param end   the label of the second vertex
     * @return true if the edge is added successfully, false if either vertex is not present in the graph
     */
    @Override
    @Deprecated
    public boolean addEdge(T begin, T end, double edgeWeight) {
        return addEdge(begin, end);
    }


    /**
     * Adds an edge between two vertices in the graph.
     *
     * @param begin the label of the first vertex
     * @param end   the label of the second vertex
     * @return true if the edge is added successfully, false if either vertex is not present in the graph
     */
    @Override
    public boolean addEdge(T begin, T end) {
        if (friendsMap.containsKey(begin) && friendsMap.containsKey(end) && !begin.equals(end)) {
            friendsMap.get(begin).add(end);
            friendsMap.get(end).add(begin);
            System.out.println("Two profiles became friends successfully!");
            edgeCount++;
            return true;
        } else {
            System.out.println("The given value is not initialized/added into the graph."
                    + "\nPlease use addVertex(T vertexLabel) to add the Profile first.");
            return false;
        }
    }


    /**
     * Checks if there is an edge between two vertices in the graph.
     *
     * @param begin the label of the first vertex
     * @param end   the label of the second vertex
     * @return true if there is an edge between the vertices, false otherwise
     */
    @Override
    public boolean hasEdge(T begin, T end) {
        if (friendsMap.containsKey(begin)) {
            if (friendsMap.get(begin).contains(end)) {
                System.out.println("Two profiles are friends!");
                return true;
            } else {
                System.out.println("Two profiles are NOT friends!");
                return false;
            }
        } else {
            System.out.println("The given value is not initialized/added into the graph."
                    + "\nPlease use addVertex(T vertexLabel) to add the Profile first.");
            return false;
        }
    }


    /**
     * Checks if the graph is empty.
     *
     * @return true if the graph is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return friendsMap.size() == 0;
    }


    /**
     * Gets the number of vertices in the graph.
     *
     * @return the number of vertices in the graph
     */
    @Override
    public int getNumberOfVertices() {
        return friendsMap.size();
    }


    /**
     * Gets the number of edges in the graph.
     *
     * @return the number of edges in the graph
     */
    @Override
    public int getNumberOfEdges() {
        return edgeCount;
    }


    /**
     * Clears the graph by removing all vertices and edges.
     */
    @Override
    public void clear() {
        friendsMap.clear();
    }


    /**
     * Removes an edge between two vertices in the graph.
     *
     * @param begin the label of the first vertex
     * @param end   the label of the second vertex
     * @return true if the edge is removed successfully, false if either vertex is not present in the graph or there is no edge between them
     */
    @Override
    public boolean removeEdge(T begin, T end) {
        if (friendsMap.containsKey(begin) && friendsMap.containsKey(end)) {
            if (friendsMap.get(begin).remove(end) && friendsMap.get(end).remove(begin)) {
                System.out.println("Two profiles disconnected successfully");
                return true;
            } else {
                System.out.println("Two profiles are NOT friends!");
                return false;
            }
        } else {
            System.out.println("The given value is not initialized/added into the graph."
                    + "\nPlease use addVertex(T vertexLabel) to add the Profile first.");
            return false;
        }
    }


    /**
     * Retrieves a set of all vertices in the graph.
     *
     * @return a set of all vertices in the graph
     */
    public CustomHashSet<T> retainAll() {
        return friendsMap.keySet();
    }


    /**
     * Gets the underlying friends map of the graph.
     *
     * @return the friends map representing the relationships between vertices
     */
    public HashMap<T, CustomHashSet<T>> getFriendsMap() {
        return friendsMap;
    }

}


