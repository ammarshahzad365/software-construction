package graph;

import java.util.*;

/**
 * An implementation of Graph using vertices represented by a mutable Vertex class.
 */
public class ConcreteVerticesGraph implements Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();

    // Abstraction function:
    //   Represents a graph where each vertex has its edges stored in the corresponding Vertex object.
    // Representation invariant:
    //   Each vertex in the list is unique (no duplicates).
    // Safety from rep exposure:
    //   The vertices list is private and final. No direct references are exposed.

    // Constructor
    public ConcreteVerticesGraph() {
        checkRep();
    }

    // Check representation invariant
    private void checkRep() {
        Set<String> vertexSet = new HashSet<>();
        for (Vertex vertex : vertices) {
            assert vertex != null;
            assert vertexSet.add(vertex.getLabel()); // Ensures no duplicate vertices
        }
    }

    @Override
    public boolean add(String vertex) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(vertex)) {
                return false;
            }
        }
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }

    @Override
    public int set(String source, String target, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        Vertex sourceVertex = null;
        Vertex targetVertex = null;
        for (Vertex v : vertices) {
            if (v.getLabel().equals(source)) {
                sourceVertex = v;
            }
            if (v.getLabel().equals(target)) {
                targetVertex = v;
            }
        }
        if (sourceVertex == null) {
            sourceVertex = new Vertex(source);
            vertices.add(sourceVertex);
        }
        if (targetVertex == null) {
            targetVertex = new Vertex(target);
            vertices.add(targetVertex);
        }

        int previousWeight = sourceVertex.setTarget(target, weight);
        checkRep();
        return previousWeight;
    }

    @Override
    public boolean remove(String vertex) {
        Vertex toRemove = null;
        for (Vertex v : vertices) {
            if (v.getLabel().equals(vertex)) {
                toRemove = v;
                break;
            }
        }
        if (toRemove == null) {
            return false;
        }
        vertices.remove(toRemove);
        for (Vertex v : vertices) {
            v.setTarget(vertex, 0); // Remove edges pointing to the removed vertex
        }
        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex v : vertices) {
            vertexLabels.add(v.getLabel());
        }
        return vertexLabels;
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex v : vertices) {
            int weight = v.getWeightTo(target);
            if (weight > 0) {
                sources.put(v.getLabel(), weight);
            }
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(source)) {
                return v.getTargets();
            }
        }
        return Map.of(); // Empty map if source doesn't exist
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Vertices: ").append(vertices()).append("\nEdges:\n");
        for (Vertex v : vertices) {
            for (Map.Entry<String, Integer> entry : v.getTargets().entrySet()) {
                result.append(v.getLabel()).append(" -> ").append(entry.getKey())
                        .append(" (").append(entry.getValue()).append(")\n");
            }
        }
        return result.toString();
    }
}

/**
 * A mutable representation of a vertex in a graph.
 */
class Vertex {
    private final String label;
    private final Map<String, Integer> edges;

    // Abstraction function:
    //   Represents a vertex with a label and outgoing edges to other vertices with weights.
    // Representation invariant:
    //   Weight of all edges must be positive.
    // Safety from rep exposure:
    //   The label is immutable and edges are encapsulated in a private map.

    public Vertex(String label) {
        this.label = label;
        this.edges = new HashMap<>();
        checkRep();
    }

    private void checkRep() {
        for (int weight : edges.values()) {
            assert weight > 0;
        }
    }

    public String getLabel() {
        return label;
    }

    public Map<String, Integer> getTargets() {
        return Map.copyOf(edges);
    }

    public int getWeightTo(String target) {
        return edges.getOrDefault(target, 0);
    }

    public int setTarget(String target, int weight) {
        int previousWeight = edges.getOrDefault(target, 0);
        if (weight == 0) {
            edges.remove(target);
        } else {
            edges.put(target, weight);
        }
        checkRep();
        return previousWeight;
    }

    @Override
    public String toString() {
        return "Vertex(" + label + ", " + edges + ")";
    }
}
