package model.logic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;

import javafx.util.Pair;
import model.algoritmos_grafos_alg4.DijkstraSP;
import model.algoritmos_grafos_alg4.DijkstraSPReq4;
import model.algoritmos_grafos_alg4.IndexMinPQ;
import model.algoritmos_grafos_alg4.KruskalMST;
import model.algoritmos_grafos_alg4.PrimMST;
import model.algoritmos_grafos_alg4.PrimMSTReq6;
import model.data_structures.IEstructura;
import model.data_structures.ListaSencillamenteEncadenada;
import model.data_structures.MaxHeapCP;
import model.data_structures.Nodo;
import model.data_structures.RedBlackBST;
import model.data_structures.TablaHashSeparateChaining;
import model.graph_alg4.CC;
import model.graph_alg4.Edge;
import model.graph_alg4.EdgeWeightedGraph;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo 
{
	/**
	 * Atributos del modelo del mundo
	 */
	private ListaSencillamenteEncadenada<Viaje> horas;

	private ListaSencillamenteEncadenada<Viaje> dias;

	private ListaSencillamenteEncadenada<Viaje> meses;

	private ListaSencillamenteEncadenada<NodoMallaVial> nodos;

	private ListaSencillamenteEncadenada<Zona> zonas;

	private EdgeWeightedGraph grafoCiudad;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public MVCModelo()
	{
		horas = new ListaSencillamenteEncadenada<Viaje>();

		dias = new ListaSencillamenteEncadenada<Viaje>();

		meses = new ListaSencillamenteEncadenada<Viaje>();

		nodos = new ListaSencillamenteEncadenada<NodoMallaVial>();

		zonas = new ListaSencillamenteEncadenada<Zona>();

	}

	//Requerimiento funcional 1
	/**
	 * Carga los datos del trimestre. 
	 * @param trimestre. el numero del trimestre.
	 */
	public void cargarDatosCSV(int trimestre)
	{
		CSVReader reader = null;
		try
		{
			reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-" + trimestre + "-All-HourlyAggregate.csv"));
			for(String[] param : reader)
			{
				try
				{
					Viaje nuevo = new Viaje(Integer.parseInt(param[0]), Integer.parseInt(param[1]),
							Integer.parseInt(param[2]), Double.parseDouble(param[3]), Double.parseDouble(param[4]),
							Double.parseDouble(param[5]), Double.parseDouble(param[6]), trimestre);
					horas.addLast(nuevo);
				}
				catch(NumberFormatException e)
				{

				}
			}

			reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-"+ trimestre + "-All-MonthlyAggregate.csv"));
			for(String[] param : reader)
			{
				try
				{
					Viaje nuevo = new Viaje(Integer.parseInt(param[0]), Integer.parseInt(param[1]),
							Integer.parseInt(param[2]), Double.parseDouble(param[3]), Double.parseDouble(param[4]),
							Double.parseDouble(param[5]), Double.parseDouble(param[6]), trimestre);
					meses.addLast(nuevo);
				}
				catch(NumberFormatException e)
				{

				}
			}

			reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-"+ trimestre + "-All-WeeklyAggregate.csv"));
			for(String[] param : reader)
			{
				try
				{
					Viaje nuevo = new Viaje(Integer.parseInt(param[0]), Integer.parseInt(param[1]),
							Integer.parseInt(param[2]), Double.parseDouble(param[3]), Double.parseDouble(param[4]),
							Double.parseDouble(param[5]), Double.parseDouble(param[6]), trimestre);
					dias.addLast(nuevo);
				}
				catch(NumberFormatException e)
				{

				}
			}

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

		}
	}

	public void cargarDatosNodos()
	{
		CSVReader reader = null;
		try
		{
			reader = new CSVReader(new FileReader("./data/bogota_vertices.txt"));
			for(String[] param : reader)
			{
				try
				{
					NodoMallaVial nuevo = new NodoMallaVial(Integer.parseInt(param[0]), Double.parseDouble(param[1]), Double.parseDouble(param[2]));
					nodos.addLast(nuevo);
				}
				catch(NumberFormatException e)
				{

				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

		}
	}

	public void cargarDatosZonas()
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("./data/bogota_cadastral.json"));
			JsonElement todo = new JsonParser().parse(br);
			JsonObject general = todo.getAsJsonObject();
			JsonArray arrZonas = general.getAsJsonArray("features");


			for (int i = 0; i < arrZonas.size(); i++)
			{
				ListaSencillamenteEncadenada<Coordenadas> coordenadas = new ListaSencillamenteEncadenada<Coordenadas>();
				JsonObject zona = arrZonas.get(i).getAsJsonObject();

				JsonObject geometria = zona.getAsJsonObject("geometry");
				JsonArray puntos = geometria.getAsJsonArray("coordinates");
				puntos = puntos.get(0).getAsJsonArray();
				puntos = puntos.get(0).getAsJsonArray();
				for (int e = 0; e < puntos.size(); e++)
				{
					JsonArray posicion = puntos.get(e).getAsJsonArray();
					double longitud = posicion.get(0).getAsDouble();
					double latitud = posicion.get(1).getAsDouble();
					coordenadas.addLast(new Coordenadas(longitud, latitud));
				}
				JsonObject datos = zona.getAsJsonObject("properties");
				int id = datos.get("MOVEMENT_ID").getAsInt();
				String nombre = datos.get("scanombre").getAsString();
				double perimetro = datos.get("shape_leng").getAsDouble();
				double area = datos.get("shape_area").getAsDouble();
				Zona cadastral = new ZonaAux(nombre, perimetro, area, id, coordenadas);
				zonas.addLast(cadastral);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public int darNumViajesMes()
	{
		return meses.size();
	}

	public int darNumViajesHora()
	{
		return horas.size();
	}

	public int darNumViajesDia()
	{
		return dias.size();
	}

	public int darNumNodos()
	{
		return nodos.size();
	}

	public int darNumZonas()
	{
		return zonas.size();
	}

	public EdgeWeightedGraph darGrafo()
	{
		return grafoCiudad;
	}

	//Parte A
	//Requerimientos 4,5 y 6 
	// Hecho por Juan David Villamil 
	/**
	 * Encontrar el camino de costo mi­nimo 
	 * para un viaje entre dos localizaciones geogra¡ficas de la ciudad
	 * Requerimiento 4 
	 * @param origen del viaje.
	 * @param destino del viaje. 
	 */
	public Iterable<Edge> caminoCostoMinimo(Coordenadas origen, Coordenadas destino)
	{
		int s = -1;
		for(NodoMallaVial temp: nodos)
		{
			if(temp.darCoordenada().coincide(origen.getLatitud(), origen.getLongitud()))
				s = temp.darId();
		}
		DijkstraSPReq4 dijk = new DijkstraSPReq4(grafoCiudad, s);
		s = -1;
		for(NodoMallaVial temp: nodos)
		{
			if(temp.darCoordenada().coincide(destino.getLatitud(), destino.getLongitud()))
				s = temp.darId();
		}

		return dijk.pathTo(s);
	}



	/**
	 * Determinar los n vertices con menor velocidad promedio en la ciudad de BogotÃ¡.
	 * Siendo la velocidad promedio de un vertice v, el promedio de las velocidades de todos sus arcos.
	 * Requerimiento 5
	 * @param latitud
	 * @param longitud
	 * @return lista con los vertices 
	 */
	public IndexMinPQ<Double> menorVelocidadPromedio()
	{
		IndexMinPQ<Double> respuesta = new IndexMinPQ<Double>(100000);
		double[] velocidades = new double[nodos.size()];
		int[] cant = new int[nodos.size()];
		for(Edge temp :grafoCiudad.edges())
		{
			int e = temp.either();
			velocidades[e] += temp.velocidad();
			cant[e]++;

			int w = temp.other(e);
			velocidades[w] += temp.velocidad();
			cant[w]++;
		}
		for(int i = 0; i < velocidades.length; i++)
		{
			if(cant[i] > 0)
			{
				double prom = velocidades[i]/cant[i];
				respuesta.insert(i, prom);
			}
		}
		return respuesta;
	}

	/**
	 * Calcular un arbol de expansión mÃinima con el criterio de distancia, 
	 * utilizando el algoritmo de Prim, aplicado al componente conectado (subgrafo)
	 * mas grande de la malla vial de Bogota.
	 * Requerimiento 6
	 * @return PrimMSTReq6.
	 */

	public PrimMSTReq6 mstDist()
	{
		CC componentes = new CC(grafoCiudad);
		int id = -1;
		int comps = -1;
		for(int i = 0; i < componentes.count(); i++)
		{
			if(componentes.size2(i) < comps)
			{
				id = i;
				comps = componentes.size2(i);
			}
		}
		int[] vertices = new int[comps]; 
		int pos = 0;
		for(int i = 0; i < grafoCiudad.V(); i ++)
		{
			if(componentes.id(i) == id)
			{
				vertices[pos] = i;
				pos++;
			}
		}
		EdgeWeightedGraph arbol = new EdgeWeightedGraph(grafoCiudad.V());
		for(int i = vertices.length-1; i >= 0 ;i--)
		{
			for(Edge temp: grafoCiudad.adj(vertices[i]))
			{
				boolean anadir = false;
				int uno = temp.either();
				int otro = temp.other(uno);
				for(int e = 0; e < i && !anadir; e++)
				{
					if(uno == e || otro == e)
					{
						anadir = true;
					}
				}
				if(anadir)
				{
					arbol.addEdge(temp);
				}
			}
		}
		return new PrimMSTReq6(arbol); 
	}

	//Parte B
	//Requerimientos 7,8 y 9 
	// Hecho por Gabriela Páez
	/**
	 * Encontrar el camino de menor costo (menor distancia Haversine)
	 * para un viaje entre dos localizaciones geogrÃ¡ficas de la ciudad
	 * Requirimiento 7
	 * @param origen
	 * @param destina
	 */
	public Iterable<Edge> caminoMenorCosto(Coordenadas origen, Coordenadas destino)
	{
		int s = -1;
		for(NodoMallaVial temp: nodos)
		{
			if(temp.darCoordenada().coincide(origen.getLatitud(), origen.getLongitud()))
				s = temp.darId();
		}
		DijkstraSP dijk = new DijkstraSP(grafoCiudad, s);
		s = -1;
		for(NodoMallaVial temp: nodos)
		{
			if(temp.darCoordenada().coincide(destino.getLatitud(), destino.getLongitud()))
				s = temp.darId();
		}

		return dijk.pathTo(s);
	}
	
	/**
	 * A partir de las coordenadas de una localización 
	 * geográfica de la ciudad de origen, indique cuáles vértices son alcanzables para un tiempo T
	 * Requirimiento 8
	 *  @param T. 
	 *  @param origen
	 *  @return numeroVertices. 
	 */
	public int verticesAlcanzables(Coordenadas origen, int T)
	{
		int numeroVertices = 0;
		int tiempoElegido = T; 
		double timeTaken = 0.0; 
		
		for(int i= 0; i < grafoCiudad.E(); i++)
		{
			if(grafoCiudad.edges().iterator().hasNext())
			{
				Edge p =grafoCiudad.edges().iterator().next();
				timeTaken = p.tiempoViaje(); 
				if (timeTaken <= tiempoElegido )
				{
					numeroVertices++; 
				}
				
			}
		}
		
		return numeroVertices;
	}

	/**
	 * Calcular un arbol de expansion mi­nima (MST) con criterio distancia, utilizando el algoritmo de Kruskal,
	 *  aplicado al componente conectado (subgrafo) mas grande de la malla vial de Bogota.
	 *  Requirimiento 9
	 */
	public KruskalMST MSTKruscal()
	{
		CC componentes = new CC(grafoCiudad);
		int id = -1;
		int comps = -1;
		KruskalMST kruscal = new KruskalMST(grafoCiudad);
		for(int i = 0; i < componentes.count(); i++)
		{
			if(componentes.size2(i) < comps)
			{
				id = i;
				comps = componentes.size2(i);
			}
		}
		int[] vertices = new int[comps]; 
		int pos = 0;
		for(int i = 0; i < grafoCiudad.V(); i ++)
		{
			if(componentes.id(i) == id)
			{
				vertices[pos] = i;
				pos++;
			}
		}
		EdgeWeightedGraph arbol = new EdgeWeightedGraph(grafoCiudad.V());
		for(int i = vertices.length-1; i >= 0 ;i--)
		{
			for(Edge temp: grafoCiudad.adj(vertices[i]))
			{
				boolean anadir = false;
				int uno = temp.either();
				int otro = temp.other(uno);
				for(int e = 0; e < i && !anadir; e++)
				{
					if(uno == e || otro == e)
					{
						anadir = true;
					}
				}
				if(anadir)
				{
					arbol.addEdge(temp);
				}
			}
		}
		return new KruskalMST(arbol); 
	}


	//Parte C
	//Requerimientos 10,11 y 12 
	// Hecho por Juan David Villamil y Gabriela Páez
	
	public void nuevoGrafoSimpleNoDirijido()
	{
		
	}
	
	
}