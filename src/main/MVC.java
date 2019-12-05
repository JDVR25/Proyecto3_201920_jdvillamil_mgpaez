package main;
import controller.Controller;
import model.data_structures.Grafo;

public class MVC 
{
	
	public static void main(String[] args) 
	{
		Controller controler = new Controller();
		controler.run();
	}
	

		public static void main1(String [] args) 
		{
		
			
			Grafo<String, String> g = new Grafo<String, String>(4);
			
			System.out.println("Grafo con " + g.V() + " nodos");
			
			g.addVertex("V1", "Hola");
			g.addVertex("V2", " Que");
			g.addVertex("V3", " Tal");
			g.addVertex("V4", " ?");
			
			System.out.print(g.getInfoVertex("V1"));
			System.out.print(g.getInfoVertex("V2"));
			System.out.print(g.getInfoVertex("V3"));
			System.out.print(g.getInfoVertex("V4"));
			System.out.println();

			System.out.println();

			System.out.println("Numero de ccs:" + g.cc());
			imprimirIterable(g.getCC("V1"));
			imprimirIterable(g.getCC("V2"));
			imprimirIterable(g.getCC("V3"));
			imprimirIterable(g.getCC("V4"));
			
			g.addEdge("V1", "V2", 1);
			g.addEdge("V1", "V3", 2);
			g.addEdge("V2", "V3", 3);

			System.out.println();
			
			System.out.println("Numero de ccs:" + g.cc());
			imprimirIterable(g.getCC("V1"));
			imprimirIterable(g.getCC("V2"));
			imprimirIterable(g.getCC("V3"));
			imprimirIterable(g.getCC("V4"));
			
			System.out.println();
			
			System.out.println("Agregar un vertice (causa resize y es muy ineficiente!!)");
			g.addVertex("V5", " !!!!!");
			System.out.println("Numero de ccs:" + g.cc());
			imprimirIterable(g.getCC("V1"));
			imprimirIterable(g.getCC("V2"));
			imprimirIterable(g.getCC("V3"));
			imprimirIterable(g.getCC("V4"));		
			imprimirIterable(g.getCC("V5"));

		}
		
		private static void imprimirIterable(Iterable i) {
			System.out.print("[");
			boolean first = true;
			for(Object o: i) {
				if(!first) {
					System.out.print(", ");
				}
				System.out.print(o);
				first = false;
			}
			System.out.print("]");
			System.out.println("");
		}
		
}

// Tomado del libro Algorithms. 
