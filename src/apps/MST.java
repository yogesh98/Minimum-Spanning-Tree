package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		/* COMPLETE THIS METHOD */
		PartialTreeList finalList = new PartialTreeList();
		for(Vertex v : graph.vertices){
			PartialTree tree = new PartialTree(v);
			Vertex.Neighbor current = v.neighbors;
			for(Vertex.Neighbor i = current; i!=null; i=i.next){
				tree.getArcs().insert(new PartialTree.Arc(v, i.vertex, i.weight));
			}
			finalList.append(tree);
		}
		return finalList;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		/* COMPLETE THIS METHOD */
		ArrayList<PartialTree.Arc> arcList=new ArrayList<PartialTree.Arc>();
		while (ptlist.size()>1) {
			PartialTree PTX=ptlist.remove();
			MinHeap<PartialTree.Arc> PQX=PTX.getArcs();
			PartialTree.Arc min=PQX.deleteMin();
			Vertex v2=min.v2;
			while (v2.getRoot()==PTX.getRoot()) {
				min=PQX.deleteMin();
				v2=min.v2;
			}
			arcList.add(min);
			PartialTree PTY=ptlist.removeTreeContaining(v2);
		 	PTX.merge(PTY);
			ptlist.append(PTX);
		}
		return arcList;
	}
}