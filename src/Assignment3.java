import java.util.*;

abstract class GraphBase {
    protected int numNodes;
    protected Map<Integer, List<Integer>> adjList;

    GraphBase(int numNodes) {
        this.numNodes = numNodes;
        this.adjList = new HashMap<>();

        for (int i = 0; i < numNodes; i++) {
            adjList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination){
        adjList.get(source).add(destination);
        adjList.get(destination).add(source);
    };

    public void printGraph() {
        for (int i = 0; i < numNodes; i++) {
            System.out.println("Node " + i + " has the following adjacent nodes: " + adjList.get(i));
        }
    }

    abstract void search(int source);
}


class DFSGraph extends GraphBase {
    DFSGraph(int numNodes) {
        super(numNodes);
    }

    @Override
    public void search(int target) {
        System.out.println("=== DEPTH-FIRST SEARCH (DFS) ===");
        System.out.println("Mencari node: " + target);
        System.out.println("Graf yang digunakan:");
        printGraph();

        boolean[] visited = new boolean[numNodes];
        List<Integer> path = new ArrayList<>();

        System.out.println("\nProses pencarian DFS:");
        boolean found = dfsRecursive(0, target, visited, path);

        if (found) {
            System.out.println("\n✓ Node " + target + " DITEMUKAN!");
            System.out.println("Path yang dilalui: " + path);
        } else {
            System.out.println("\n✗ Node " + target + " TIDAK DITEMUKAN!");
        }
    }

    private boolean dfsRecursive(int current, int target, boolean[] visited, List<Integer> path) {
        visited[current] = true;
        path.add(current);

        System.out.println("Mengunjungi node: " + current);

        if (current == target) {
            return true;
        }

        for (int neighbor : adjList.get(current)) {
            if (!visited[neighbor]) {
                System.out.println("  → Dari node " + current + " ke node " + neighbor);
                if (dfsRecursive(neighbor, target, visited, path)) {
                    return true;
                }
            }
        }

        System.out.println("  ← Backtrack dari node " + current);
        return false;
    }

}

class BFSGraph extends GraphBase {
    BFSGraph(int numNodes) {
        super(numNodes);
    }

    @Override
    public void search(int target) {
        System.out.println("=== BREADTH-FIRST SEARCH (BFS) ===");
        System.out.println("Mencari node: " + target);
        System.out.println("Graf yang digunakan:");
        printGraph();

        boolean[] visited = new boolean[numNodes];
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> parent = new HashMap<>();

        queue.offer(0);
        visited[0] = true;
        parent.put(0, -1);

        System.out.println("\nProses pencarian BFS:");
        System.out.println("Inisialisasi: Queue = [0]");

        boolean found = false;

        while (!queue.isEmpty() && !found) {
            int current = queue.poll();
            System.out.println("\nMengambil node dari queue: " + current);
            System.out.println("Queue sekarang: " + queue);

            if (current == target) {
                found = true;
                System.out.println("✓ Node " + target + " DITEMUKAN!");

                List<Integer> path = reconstructPath(parent, target);
                System.out.println("Path yang dilalui: " + path);
                break;
            }

            System.out.println("Tetangga dari node " + current + ": " + adjList.get(current));
            for (int neighbor : adjList.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent.put(neighbor, current);
                    queue.offer(neighbor);
                    System.out.println("  → Menambahkan node " + neighbor + " ke queue");
                }
            }
            System.out.println("Queue setelah penambahan: " + queue);
        }

        if (!found) {
            System.out.println("\n✗ Node " + target + " TIDAK DITEMUKAN!");
        }

    }

    private List<Integer> reconstructPath(Map<Integer, Integer> parent, int target) {
        List<Integer> path = new ArrayList<>();
        int node = target;
        while (node != -1) {
            path.add(0, node);
            node = parent.get(node);
        }
        return path;
    }


}


public class Assignment3 {
    private static final int TARGET_NODE = 4; // Static target node

    public static void main(String[] args) {
        System.out.println("PROGRAM PENCARIAN GRAF - DFS & BFS");
        System.out.println("===========================");
        System.out.println("Graf memiliki 7 node: 0, 1, 2, 3, 4, 5, 6");

        setupAndRunDFS();
        System.out.println("\n" + "=".repeat(50) + "\n");
        setupAndRunBFS();
    }

    private static void setupAndRunDFS() {
        System.out.println("=== DEMONSTRASI DFS ===");

        DFSGraph dfsGraph = new DFSGraph(7);
        setupGraphConnections(dfsGraph);

        System.out.println("Mencari node: " + TARGET_NODE);
        dfsGraph.search(TARGET_NODE);
    }

    private static void setupAndRunBFS() {
        System.out.println("=== DEMONSTRASI BFS ===");

        BFSGraph bfsGraph = new BFSGraph(7);
        setupGraphConnections(bfsGraph);

        System.out.println("Mencari node: " + TARGET_NODE);
        bfsGraph.search(TARGET_NODE);
    }

    private static void setupGraphConnections(GraphBase graph) {
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(4, 6);
    }
}
