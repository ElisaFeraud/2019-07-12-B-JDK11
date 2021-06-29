package it.polito.tdp.food.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;




public class Model {
   FoodDao dao;
   Map<Integer,Food> idMap;
   Graph<Food,DefaultWeightedEdge> grafo;
   public Model() {
	   dao = new FoodDao();
   }
   public void creaGrafo(int porzioni) {
	   idMap = new HashMap<>();
		 this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		 dao.getVertici(idMap, porzioni);
		 Graphs.addAllVertices(this.grafo, idMap.values());
		 for(Collegamento c: dao.getArchi(idMap,porzioni)) {
	    		if(this.grafo.containsVertex(c.getOrigine()) && this.grafo.containsVertex(c.getDestinazione())) {
	    			DefaultWeightedEdge e = this.grafo.getEdge(c.getOrigine(),c.getDestinazione());
	    			if(e==null) {
	    				Graphs.addEdgeWithVertices(grafo,c.getOrigine(),c.getDestinazione(),c.getPeso());
	    			}
	    		}
	    	}
		 
	 }
	 public String infoGrafo() {
		 return "Grafo creato con "+ this.grafo.vertexSet().size()+ " vertici e " + this.grafo.edgeSet().size()+" archi\n";
	 }
    
	 public List<Food> getVerticiGrafoCreato(){
		 List<Food> result = new LinkedList<>(this.grafo.vertexSet());
		 return result;
	 }
	 
	public List<Corrispondenza> getCinqueCibi(Food food){
		 List<Corrispondenza> result = new LinkedList<>();
			List<Corrispondenza> vicini = new LinkedList<>();
			List<Food> prova = Graphs.successorListOf(this.grafo, food);
			for(Food f: prova) {
				DefaultWeightedEdge d = this.grafo.getEdge(food, f);		 
				  double peso =  this.grafo.getEdgeWeight(d);
				  Corrispondenza c= new Corrispondenza(f,peso);
				  vicini.add(c);
				 
			}
			 vicini.sort(new CorrispondenzaPerPeso());
			 if(vicini.size()<=5) {
			    	for(int i=0; i<vicini.size(); i++) {
			    		
			    		 result.add(vicini.get(i));
			    		
			    	}}
			    	else {
			    		for(int i=0; i<5; i++) {
			    			
			   		         result.add(vicini.get(i));
			   		
			   	}}
			    		return result;
			   }
			    		
			    	
	     
				 
			
	}
   

