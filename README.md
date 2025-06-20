# All about of DFS and BFS

I'll explain the DFS (Depth-First Search) part of your code in detail. Let me break down each section:

## **DFS Class Structure**

```java
class DFSGraph extends GraphBase {
    public DFSGraph(int numNodes) {
        super(numNodes);
    }
```

Like BFS, the DFS class extends the base graph class, inheriting the adjacency list and basic operations.

## **Main DFS Search Method**

The `search()` method orchestrates the DFS algorithm:

### **1. Initialization Phase**
```java
boolean[] visited = new boolean[numNodes];
List<Integer> path = new ArrayList<>();

boolean found = dfsRecursive(0, target, visited, path);
```

**Key data structures:**
- `visited[]`: Tracks which nodes have been visited (prevents infinite loops)
- `path`: Records the sequence of nodes visited (for displaying the traversal path)

### **2. DFS Core Algorithm (Recursive Implementation)**

```java
private boolean dfsRecursive(int current, int target, boolean[] visited, List<Integer> path) {
    visited[current] = true;  // Mark current node as visited
    path.add(current);        // Add to path for tracking
    
    System.out.println("Mengunjungi node: " + current);
    
    if (current == target) {
        return true;  // Found the target!
    }
    
    // Explore each unvisited neighbor
    for (int neighbor : adjacencyList.get(current)) {
        if (!visited[neighbor]) {
            System.out.println("  → Dari node " + current + " ke node " + neighbor);
            if (dfsRecursive(neighbor, target, visited, path)) {
                return true;  // Target found in this branch
            }
        }
    }
    
    System.out.println("  ← Backtrack dari node " + current);
    return false;  // Target not found in this branch
}
```

**How it works:**
1. **Visit**: Mark current node as visited and add to path
2. **Check**: Is this our target? If yes, return true!
3. **Explore**: For each unvisited neighbor:
    - Recursively search that neighbor
    - If target found in that branch, return true immediately
4. **Backtrack**: If no neighbors lead to target, backtrack (return false)

## **Key DFS Characteristics**

### **Depth-First Exploration**
- DFS goes as **deep as possible** before backtracking
- It's like exploring a maze by always taking the first available path until you hit a dead end

### **Recursive Nature**
- Each recursive call represents going deeper into the graph
- The call stack naturally handles the "backtracking" when a path doesn't lead to the target

### **Backtracking Behavior**
```java
System.out.println("  ← Backtrack dari node " + current);
```
This happens when:
- All neighbors have been visited, OR
- No neighbors exist, OR
- None of the neighbor paths lead to the target

## **Example Walkthrough**

For your graph structure:
```
       0
      / \
     1   2
    /   / \
   3   4   5
      /
     6
```

If searching for node 6, DFS might follow this path:

1. **Start at 0**: Visit 0, check neighbors [1, 2]
2. **Go to 1**: Visit 1 (first neighbor), check neighbors [3]
3. **Go to 3**: Visit 3, no unvisited neighbors → **Backtrack to 1**
4. **Back to 1**: No more unvisited neighbors → **Backtrack to 0**
5. **Back to 0**: Try next neighbor (2)
6. **Go to 2**: Visit 2, check neighbors [4, 5]
7. **Go to 4**: Visit 4 (first neighbor), check neighbors [6]
8. **Go to 6**: Visit 6 → **FOUND!**

**Path traversed**: 0 → 1 → 3 → (backtrack) → 2 → 4 → 6

## **Recursive Call Stack Visualization**

```
dfsRecursive(0, 6, visited, path)
├── dfsRecursive(1, 6, visited, path)
│   └── dfsRecursive(3, 6, visited, path) → returns false
├── dfsRecursive(2, 6, visited, path)
    └── dfsRecursive(4, 6, visited, path)
        └── dfsRecursive(6, 6, visited, path) → returns true!
```

## **Key Differences from BFS**

| Aspect | DFS | BFS |
|--------|-----|-----|
| **Data Structure** | Recursion stack (or explicit stack) | Queue |
| **Exploration Pattern** | Deep first, then backtrack | Level by level |
| **Path Found** | May not be shortest path | Always shortest path |
| **Memory Usage** | O(height of graph) | O(width of graph) |

## **DFS Advantages**

1. **Memory Efficient**: Uses less memory than BFS for deep, narrow graphs
2. **Path Finding**: Good for finding *any* path (not necessarily shortest)
3. **Cycle Detection**: Excellent for detecting cycles in graphs
4. **Topological Sorting**: Essential for dependency resolution

## **Time & Space Complexity**

- **Time**: O(V + E) where V = vertices, E = edges
- **Space**: O(V) for the recursion stack in worst case (when graph is a long chain)

## **Important Notes**

1. **Order Dependency**: DFS path depends on the order neighbors are stored in adjacency list
2. **Not Optimal**: First path found may not be the shortest
3. **Backtracking**: The algorithm naturally backtracks when it hits dead ends

I'll explain the BFS (Breadth-First Search) part of your code in detail. Let me break it down section by section:

## **BFS Class Structure**

```java
class BFSGraph extends GraphBase {
    public BFSGraph(int numNodes) {
        super(numNodes);
    }
```

The BFS class extends the base graph class, inheriting the adjacency list structure and basic graph operations.

## **Main BFS Search Method**

The `search()` method is where the BFS algorithm is implemented:

### **1. Initialization Phase**
```java
boolean[] visited = new boolean[numNodes];
Queue<Integer> queue = new LinkedList<>();
Map<Integer, Integer> parent = new HashMap<>();

queue.offer(0);      // Start from node 0
visited[0] = true;   // Mark starting node as visited
parent.put(0, -1);   // Root has no parent
```

**Key data structures:**
- `visited[]`: Tracks which nodes have been visited
- `queue`: FIFO structure that controls the order of exploration
- `parent`: Maps each node to its parent (used for path reconstruction)

### **2. BFS Core Algorithm**
```java
while (!queue.isEmpty() && !found) {
    int current = queue.poll();  // Remove front node from queue
    
    if (current == target) {
        found = true;
        // Reconstruct and display path
        break;
    }
    
    // Explore all unvisited neighbors
    for (int neighbor : adjacencyList.get(current)) {
        if (!visited[neighbor]) {
            visited[neighbor] = true;
            parent.put(neighbor, current);
            queue.offer(neighbor);  // Add to back of queue
        }
    }
}
```

**How it works:**
1. **Dequeue**: Remove the front node from queue
2. **Check**: Is this our target? If yes, we're done!
3. **Explore**: For each unvisited neighbor:
   - Mark it as visited
   - Set its parent (for path tracking)
   - Add it to the back of the queue

### **3. Path Reconstruction**
```java
private List<Integer> reconstructPath(Map<Integer, Integer> parent, int target) {
    List<Integer> path = new ArrayList<>();
    int node = target;
    while (node != -1) {
        path.add(0, node);        // Add to front of list
        node = parent.get(node);  // Move to parent
    }
    return path;
}
```

This traces back from the target to the root using the parent map, building the complete path.

## **Why BFS Works This Way**

**Level-by-Level Exploration:**
- BFS explores all nodes at distance 1, then distance 2, then distance 3, etc.
- This is why it guarantees the shortest path in unweighted graphs

**Queue Behavior:**
- FIFO (First In, First Out) ensures we process nodes in the order they were discovered
- This maintains the level-by-level exploration pattern

## **Example Walkthrough**

For your graph structure:
```
       0
      / \
     1   2
    /   / \
   3   4   5
      /
     6
```

If searching for node 6:
1. **Level 0**: Start with [0]
2. **Level 1**: Process 0, add its children → Queue: [1, 2]
3. **Level 2**: Process 1 (add 3), then 2 (add 4, 5) → Queue: [3, 4, 5]
4. **Level 3**: Process 3 (no children), then 4 (add 6) → Queue: [5, 6]
5. **Found**: Process 5, then find 6!

The path would be: 0 → 2 → 4 → 6 (shortest path with 3 edges)

## **Key Advantages of BFS**

1. **Shortest Path**: Always finds the path with minimum number of edges
2. **Complete**: Will find the target if it exists
3. **Optimal**: For unweighted graphs, the first path found is optimal

## **Time & Space Complexity**

- **Time**: O(V + E) where V = vertices, E = edges
- **Space**: O(V) for the queue and visited array
