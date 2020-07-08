package com.KD.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import com.KD.MinHeap;
import com.KD.UnionFind;

@SuppressWarnings("unchecked")
public class ListGraph<V, E> extends Graph<V, E> {
	
	public ListGraph() {}
	public ListGraph(WeightManager<E> weightManager) {
		super(weightManager);
	}

	private static class Vertex<V, E> {
		V value;
		Set<Edge<V, E>> inEdges = new HashSet<>();
		Set<Edge<V, E>> outEdges = new HashSet<>();
		Vertex(V value) {
			this.value = value;
		}
		@Override
		public boolean equals(Object obj) {
			return Objects.equals(value, ((Vertex<V, E>)obj).value);
		}
		@Override
		public int hashCode() {
			return value == null ? 0 : value.hashCode();
		}
		@Override
		public String toString() {
			return value == null ? "null" : value.toString();
		}
	}
	
	private static class Edge<V, E> {
		Vertex<V, E> from;
		Vertex<V, E> to;
		E weight;
		
		Edge(Vertex<V, E> from, Vertex<V, E> to) {
			this.from = from;
			this.to = to;
		}
		
		EdgeInfo<V, E> info() {
			return new EdgeInfo<>(from.value, to.value, weight);
		}
		
		@Override
		public boolean equals(Object obj) {
			Edge<V, E> edge = (Edge<V, E>) obj;
			return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
		}
		@Override
		public int hashCode() {
			return from.hashCode() * 31 + to.hashCode();
		}

		@Override
		public String toString() {
			return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
		}
	}
	
	private Map<V, Vertex<V, E>> vertices = new HashMap<>();
	private Set<Edge<V, E>> edges = new HashSet<>();
	private Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> e1, Edge<V, E> e2) -> {
		return weightManager.compare(e1.weight, e2.weight);
	};
	
	public void print() {
		System.out.println("[vertex]-------------------");
		vertices.forEach((V v, Vertex<V, E> vertex) -> {
			System.out.println(v);
			System.out.println("out-----------");
			System.out.println(vertex.outEdges);
			System.out.println("in-----------");
			System.out.println(vertex.inEdges);
		});

		System.out.println("[edge]-------------------");
		edges.forEach((Edge<V, E> edge) -> {
			System.out.println(edge);
		});
	}

	@Override
	public int edgesSize() {
		return edges.size();
	}

	@Override
	public int verticesSize() {
		return vertices.size();
	}

	@Override
	public void addVertex(V v) {
		if (vertices.containsKey(v)) return;
		vertices.put(v, new Vertex<>(v));
	}

	@Override
	public void addEdge(V from, V to) {
		addEdge(from, to, null);
	}

	@Override
	public void addEdge(V from, V to, E weight) {
		
		Vertex<V, E> fromVertex = vertices.get(from);
		if (fromVertex == null) {
			fromVertex = new Vertex<>(from);
			vertices.put(from, fromVertex);
		}
		
		Vertex<V, E> toVertex = vertices.get(to);
		if (toVertex == null) {
			toVertex = new Vertex<>(to);
			vertices.put(to, toVertex);
		}

		Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
		edge.weight = weight;
		if (fromVertex.outEdges.remove(edge)) {
			toVertex.inEdges.remove(edge);
			edges.remove(edge);
		}
		fromVertex.outEdges.add(edge);
		toVertex.inEdges.add(edge);
		edges.add(edge);
	}

	@Override
	public void removeEdge(V from, V to) {
		
		Vertex<V, E> fromVertex = vertices.get(from);
		if (fromVertex == null) return;
		
		Vertex<V, E> toVertex = vertices.get(to);
		if (toVertex == null) return;
		
		Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
		if (fromVertex.outEdges.remove(edge)) {
			toVertex.inEdges.remove(edge);
			edges.remove(edge);
		}
	}

	@Override
	public void removeVertex(V v) {
		
		Vertex<V, E> vertex = vertices.remove(v);
		if (vertex == null) return;
		for (Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator(); iterator.hasNext();) {
			Edge<V, E> edge = iterator.next();
			edge.to.inEdges.remove(edge);
			// The current traversed element edge is removed from the collection vertex.outEdges
			iterator.remove(); 
			edges.remove(edge);
		}

		for (Iterator<Edge<V, E>> iterator = vertex.inEdges.iterator(); iterator.hasNext();) {
			Edge<V, E> edge = iterator.next();
			edge.from.outEdges.remove(edge);
			// The current traversed element edge is removed from the collection vertex.outEdges
			iterator.remove(); 
			edges.remove(edge);
		}
	}

	@Override
	public void bfs(V begin, VertexVisitor<V> visitor) {
		
		if (visitor == null) return;
		
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return;
		
		Set<Vertex<V, E>> visitedVertices = new HashSet<>();
		Queue<Vertex<V, E>> queue = new LinkedList<>();
		queue.offer(beginVertex);
		visitedVertices.add(beginVertex);
		while (!queue.isEmpty()) {
			Vertex<V, E> vertex = queue.poll();
			if (visitor.visit(vertex.value)) return;
			for (Edge<V, E> edge : vertex.outEdges) {
				if (visitedVertices.contains(edge.to)) continue;
				queue.offer(edge.to);
				visitedVertices.add(edge.to);
			}
		}
	}

	@Override
	public void dfs(V begin, VertexVisitor<V> visitor) {
		
		if (visitor == null) return;
		
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return;
		
		Set<Vertex<V, E>> visitedVertices = new HashSet<>();
		Stack<Vertex<V, E>> stack = new Stack<>();
		// Visit the starting point first
		stack.push(beginVertex);
		visitedVertices.add(beginVertex);
		if (visitor.visit(begin)) return;
		while (!stack.isEmpty()) {
			Vertex<V, E> vertex = stack.pop();
			for (Edge<V, E> edge : vertex.outEdges) {
				if (visitedVertices.contains(edge.to)) continue;
				stack.push(edge.from);
				stack.push(edge.to);
				visitedVertices.add(edge.to);
				if (visitor.visit(edge.to.value)) return;
				break;
			}
		}
	}

	@Override
	public List<V> topologicalSort() {
		List<V> list = new ArrayList<>();
		Queue<Vertex<V, E>> queue = new LinkedList<>();
		Map<Vertex<V, E>, Integer> ins = new HashMap<>();
		// Initialization (put all nodes with degree 0 into the queue)
		vertices.forEach((V v, Vertex<V, E> vertex) -> {
			int in = vertex.inEdges.size();
			if (in == 0) {
				queue.offer(vertex);
			} else {
				ins.put(vertex, in);
			}
		});
		while (!queue.isEmpty()) {
			Vertex<V, E> vertex = queue.poll();
			// Put in the returned results
			list.add(vertex.value);
			for (Edge<V, E> edge : vertex.outEdges) {
				int toIn = ins.get(edge.to) - 1;
				if (toIn == 0) {
					queue.offer(edge.to);
				} else {
					ins.put(edge.to, toIn);
				}
			}
		}
		return list;
	}

	@Override
	public Set<EdgeInfo<V, E>> mst() {
		return Math.random() > 0.5 ? prim() : kruskal();
	}
	
	private Set<EdgeInfo<V, E>> prim() {
		Iterator<Vertex<V, E>> it = vertices.values().iterator();
		if (!it.hasNext()) return null;
		Vertex<V, E> vertex = it.next();
		Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
		Set<Vertex<V, E>> addedVertices = new HashSet<>();
		addedVertices.add(vertex);
		MinHeap<Edge<V, E>> heap = new MinHeap<>(vertex.outEdges, edgeComparator);
		int verticesSize = vertices.size();
		while (!heap.isEmpty() && addedVertices.size() < verticesSize) {
			Edge<V, E> edge = heap.remove();
			if (addedVertices.contains(edge.to)) continue;
			edgeInfos.add(edge.info());
			addedVertices.add(edge.to);
			heap.addAll(edge.to.outEdges);
		}
		return edgeInfos;
	}
	
	private Set<EdgeInfo<V, E>> kruskal() {
		int edgeSize = vertices.size() - 1;
		if (edgeSize == -1) return null;
		Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
		MinHeap<Edge<V, E>> heap = new MinHeap<>(edges, edgeComparator);
		UnionFind<Vertex<V, E>> uf = new UnionFind<>();
		vertices.forEach((V v, Vertex<V, E> vertex) -> {
			uf.makeSet(vertex);
		});
		while (!heap.isEmpty() && edgeInfos.size() < edgeSize) {
			Edge<V, E> edge = heap.remove(); 
			if (uf.isSame(edge.from, edge.to)) continue; 
			edgeInfos.add(edge.info());
			uf.union(edge.from, edge.to);
		}
		return edgeInfos;
	}
	
	@Override
	public Map<V, PathInfo<V, E>> shortestPath(V begin) {
		return dijkstra(begin);
	}

	@SuppressWarnings("unused")
	private Map<V, PathInfo<V, E>> bellmanFord(V begin) {
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return null;
		Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
		selectedPaths.put(begin, new PathInfo<>(weightManager.zero()));
		int count = vertices.size() - 1;
		for (int i = 0; i < count; i++) { 	// (v-1) times
			for (Edge<V, E> edge : edges) {
				PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
				if (fromPath == null) continue;
				relax(edge, fromPath, selectedPaths);
			}
		}
		
		for (Edge<V, E> edge : edges) {
			PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
			if (fromPath == null) continue;
			if (relax(edge, fromPath, selectedPaths)) {
				System.out.println("Tehre is negative weight ring.");
				return null;
			}
		}
		selectedPaths.remove(begin);
		return selectedPaths;
	}

	
	/**
	 * Relaxant - Bellman-Ford
	 * @param edge Edges need relaxant
	 * @param fromPath The shortest path information of edge`s from
	 * @param paths holding the shortest path information of other points 
	 * 			(for Dijkstra, it is the point that has not left the desktop)
	 */
	private boolean relax(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<V, PathInfo<V, E>> paths) {
		// New alternative shortest path: shortest edge from 'beginVertex' to 'edge.from' + edge.weight
		E newWeight = weightManager.add(fromPath.weight, edge.weight);
		// Old shortest path: shortest edge from 'beginVertex' to 'edge.from' 
		PathInfo<V, E> oldPath = paths.get(edge.to.value);
		if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return false;
		if (oldPath == null) {
			oldPath = new PathInfo<>();
			paths.put(edge.to.value, oldPath);
		} else {
			oldPath.edgeInfos.clear();
		}
		oldPath.weight = newWeight;
		oldPath.edgeInfos.addAll(fromPath.edgeInfos);
		oldPath.edgeInfos.add(edge.info());
		return true;
	}
	
	private Map<V, PathInfo<V, E>> dijkstra(V begin) {
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return null;
		Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
		Map<Vertex<V, E>, PathInfo<V, E>> paths = new HashMap<>();
		paths.put(beginVertex, new PathInfo<>(weightManager.zero()));
		while (!paths.isEmpty()) {
			Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = getMinPath(paths);
			// 'minVertex': leave the desktop
			Vertex<V, E> minVertex = minEntry.getKey();
			PathInfo<V, E> minPath = minEntry.getValue();
			selectedPaths.put(minVertex.value, minPath);
			paths.remove(minVertex);
			// Do relaxant to 'minVertex'`s 'outEdges'
			for (Edge<V, E> edge : minVertex.outEdges) {
				// if 'edge.to' has left the desktop
				// no need to do relaxant
				if (selectedPaths.containsKey(edge.to.value)) continue;
				relaxForDijkstra(edge, minPath, paths);
			}
		}
		
		selectedPaths.remove(begin);
		return selectedPaths;
	}
	
	/**
	 * Relaxant - Dijkstra
	 * @param edge Edges need relaxant
	 * @param fromPath The shortest path information of edge`s from
	 * @param paths holding the shortest path information of other points 
	 * 			(for Dijkstra, it is the point that has not left the desktop)
	 */
	private void relaxForDijkstra(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<Vertex<V, E>, PathInfo<V, E>> paths) {
		// New alternative shortest path: shortest edge from 'beginVertex' to 'edge.from' + edge.weight
		E newWeight = weightManager.add(fromPath.weight, edge.weight);
		// Old shortest path: shortest edge from 'beginVertex' to 'edge.from' 
		PathInfo<V, E> oldPath = paths.get(edge.to);
		if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return;
		if (oldPath == null) {
			oldPath = new PathInfo<>();
			paths.put(edge.to, oldPath);
		} else {
			oldPath.edgeInfos.clear();
		}
		oldPath.weight = newWeight;
		oldPath.edgeInfos.addAll(fromPath.edgeInfos);
		oldPath.edgeInfos.add(edge.info());
	}
	
	/**
	 * Find a shortest path from 'paths'
	 * @param paths
	 * @return
	 */
	private Entry<Vertex<V, E>, PathInfo<V, E>> getMinPath(Map<Vertex<V, E>, PathInfo<V, E>> paths) {
		Iterator<Entry<Vertex<V, E>, PathInfo<V, E>>> it = paths.entrySet().iterator();
		Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = it.next();
		while (it.hasNext()) {
			Entry<Vertex<V, E>, PathInfo<V, E>> entry = it.next();
			if (weightManager.compare(entry.getValue().weight, minEntry.getValue().weight) < 0) {
				minEntry = entry;
			}
		}
		return minEntry;
	}
	
	@Override
	public Map<V, Map<V, PathInfo<V, E>>> shortestPath() {
		Map<V, Map<V, PathInfo<V, E>>> paths = new HashMap<>();
		// Initialization
		for (Edge<V, E> edge : edges) {
			Map<V, PathInfo<V, E>> map = paths.get(edge.from.value);
			if (map == null) {
				map = new HashMap<>();
				paths.put(edge.from.value, map);
			}
			PathInfo<V, E> pathInfo = new PathInfo<>(edge.weight);
			pathInfo.edgeInfos.add(edge.info());
			map.put(edge.to.value, pathInfo);
		}
		vertices.forEach((V v2, Vertex<V, E> vertex2) -> {
			vertices.forEach((V v1, Vertex<V, E> vertex1) -> {
				vertices.forEach((V v3, Vertex<V, E> vertex3) -> {
					if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) return;
					// v1 -> v2
					PathInfo<V, E> path12 = getPathInfo(v1, v2, paths);
					if (path12 == null) return;
					// v2 -> v3
					PathInfo<V, E> path23 = getPathInfo(v2, v3, paths);
					if (path23 == null) return;
					// v1 -> v3 
					PathInfo<V, E> path13 = getPathInfo(v1, v3, paths);
					E newWeight = weightManager.add(path12.weight, path23.weight);
					if (path13 != null && weightManager.compare(newWeight, path13.weight) >= 0) return;
					if (path13 == null) {
						path13 = new PathInfo<V, E>();
						paths.get(v1).put(v3, path13);
					} else {
						path13.edgeInfos.clear();
					}
					path13.weight = newWeight;
					path13.edgeInfos.addAll(path12.edgeInfos);
					path13.edgeInfos.addAll(path23.edgeInfos);
				});
			});
		});
		return paths;
	}
	
	private PathInfo<V, E> getPathInfo(V from, V to, Map<V, Map<V, PathInfo<V, E>>> paths) {
		Map<V, PathInfo<V, E>> map = paths.get(from);
		return map == null ? null : map.get(to);
	}
}
