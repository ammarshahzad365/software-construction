package graph;

import java.util.*;

/**
 * An implementation of Graph using a list of edges and a set of vertices.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction function:
    //   AF(vertices, edges) = A directed graph where `vertices` represents the set of all vertices
    //                         in the graph, and `edges` is a list of directed edges connecting
    //                         these vertices, each with a weight.
    // Representation invariant:
    //   - vertices != null
    //   - edges != null
    //   - All sources and targets in edges are in vertices
    //   - No edge in edges has a negative weight
    // Safety from rep exposure:
    //   - vertices is private and final, and returned as an unmodifiable set
    //   - edges is private and final, and elements are immutable

    /**
     * Constructs an empty graph.
     */
    public ConcreteEdgesGraph() {
        checkRep();
    }

    /**
     * Checks that the representation invariant holds.
     */
    private void checkRep() {
        assert vertices != null : "vertices set cannot be null";
        assert edges != null : "edges list cannot be null";
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource()) : "Edge source not in vertices";
            assert vertices.contains(edge.getTarget()) : "Edge target not in vertices";
            assert edge.getWeight() >= 0 : "Edge weight must be non-negative";
        }
    }

    @Override
    public boolean add(String vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }

    @Override
    public int set(String source, String target, int weight) {
        if (source == null || target == null || weight < 0) {
            throw new IllegalArgumentException("Invalid source, target, or weight");
        }
        add(source);
        add(target);

        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                int previousWeight = edge.getWeight();
                edges.remove(edge);
                if (weight > 0) {
                    edges.add(new Edge(source, target, weight));
                }
                checkRep();
                return previousWeight;
            }
        }

        if (weight > 0) {
            edges.add(new Edge(source, target, weight));
        }
        checkRep();
        return 0;
    }

    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\nEdges:\n");
        for (Edge edge : edges) {
            sb.append(edge).append("\n");
        }
        return sb.toString();
    }

    /**
     * Immutable Edge class representing a directed edge in the graph.
     */
    public static class Edge {
        private final String source;
        private final String target;
        private final int weight;

        // Abstraction function:
        //   AF(source, target, weight) = A directed edge from `source` to `target` with weight `weight`.
        // Representation invariant:
        //   - source and target are non-null
        //   - weight >= 0
        // Safety from rep exposure:
        //   - All fields are private and final
        //   - No methods return mutable references

        /**
         * Constructs a new Edge.
         * 
         * @param source the source vertex
         * @param target the target vertex
         * @param weight the weight of the edge, must be non-negative
         */
        public Edge(String source, String target, int weight) {
            if (source == null || target == null || weight < 0) {
                throw new IllegalArgumentException("Invalid source, target, or weight");
            }
            this.source = source;
            this.target = target;
            this.weight = weight;
            checkRep();
        }

        private void checkRep() {
            assert source != null : "Source cannot be null";
            assert target != null : "Target cannot be null";
            assert weight >= 0 : "Weight must be non-negative";
        }

        public String getSource() {
            return source;
        }

        public String getTarget() {
            return target;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return source + " -> " + target + " (" + weight + ")";
        }
    }
}
