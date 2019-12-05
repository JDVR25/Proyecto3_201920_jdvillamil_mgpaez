package model.data_structures;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.LinearProbingHashST;

public class Grafo<K, IV> {
	
	private EdgeWeightedGraph g;
	private LinearProbingHashST<K, Integer> keyToInteger;
	private LinearProbingHashST<Integer, K> integerToKey;

	private int ultimoId;
	private LinearProbingHashST<K, IV> infoVertex;

	
	public Grafo (int V) {
		g = new EdgeWeightedGraph(V);
		keyToInteger = new LinearProbingHashST<K, Integer>();
		integerToKey = new LinearProbingHashST<Integer, K>();
		infoVertex =  new LinearProbingHashST<K, IV>();
		ultimoId = 0;
	}
	
	public int V() {
		return g.V();
	}
	
	public int E() {
		return g.E();
	}
	
	public void addEdge(K idVertexIni, K idVertexFin, double cost) {		
		Edge e = new Edge(keyToInteger.get(idVertexIni), keyToInteger.get(idVertexFin), cost);
		g.addEdge(e);
	}

	public void addVertex(K idVertex, IV infoV) {
		
		if(ultimoId >= g.V()) {
			//resize, muy ineficiente
			EdgeWeightedGraph g2 = new EdgeWeightedGraph(ultimoId + 1);
			for(int n = 0; n < g.V(); n++) {
				for(Edge e: g.adj(n)) {
					g2.addEdge(e);
				}
			}
			g = g2;
		}
		
		infoVertex.put(idVertex, infoV);
		keyToInteger.put(idVertex, ultimoId);
		integerToKey.put(ultimoId, idVertex);
		ultimoId++;
		
	}

	
	public Iterable <K> adj (K idVertex){
		Iterable<Edge> adjacentes = g.adj(keyToInteger.get(idVertex));
		Bag<K> res = new Bag<>();

		for(Edge e: adjacentes) {
			K id1 = integerToKey.get(e.either());
			if(id1.equals(idVertex)) {
				K id2 = integerToKey.get(e.other(e.either()));
				res.add(id2);
			}
			else {
				res.add(id1);
			}
			
		}
		
		return res;
	}
	
	public IV getInfoVertex(K idVertex)
	{
		return infoVertex.get(idVertex);
	}
	
	public void setInfoVertex(K idVertex, IV infoV)
	{
		infoVertex.put(idVertex, infoV);
	}
	
	
	private CC calculatedCC;
	public int cc() 
	{
		calculatedCC = new CC(g);
		return calculatedCC.count();
	}
	
	public Iterable<K> getCC(K idVertex)
	{
		Bag<K> res = new Bag<>();
		
		int id = calculatedCC.id(keyToInteger.get(idVertex));
		for(int n = 0; n < g.V(); n++) {
			if(calculatedCC.id(n) == id) {
				res.add(integerToKey.get(n));
			}
		}
		return res;
	}
}
//Tomado del libro Algorithms. 