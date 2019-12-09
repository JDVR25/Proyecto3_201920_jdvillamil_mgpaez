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
	
	public ListaSencillamenteEncadenada<NodoMallaVial> darNodos()
	{
		return nodos;
	}

	public EdgeWeightedGraph darGrafo()
	{
		return grafoCiudad;
	}

	//Parte A
	//Requerimientos 4,5 y 6 
	// Hecho por Juan David Villamil 
	/**
	 * Encontrar el camino de costo mÃ­nimo 
	 * para un viaje entre dos localizaciones geogrÃ¡ficas de la ciudad
	 * Requerimiento 4 
	 * @param origen del viaje.
	 * @param destino del viaje. 
	 */
	public DijkstraSPReq4 caminoCostoMinimo(Coordenadas origen, Coordenadas destino)
	{
		int s = -1;
		for(NodoMallaVial temp: nodos)
		{
			if(temp.darCoordenada().coincide(origen.getLatitud(), origen.getLongitud()))
				s = temp.darId();
		}
		DijkstraSPReq4 dijk = new DijkstraSPReq4(grafoCiudad, s);
		

		return dijk;
	}



	/**
	 * Determinar los n vÃ©rtices con menor velocidad promedio en la ciudad de BogotÃ¡.
	 * Siendo la velocidad promedio de un vÃ©rtice v, el promedio de las velocidades de todos sus arcos.
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
	 * Calcular un Ã¡rbol de expansiÃ³n mÃ­nima con el criterio de distancia, 
	 * utilizando el algoritmo de Prim, aplicado al componente conectado (subgrafo)
	 * mÃ¡s grande de la malla vial de BogotÃ¡.
	 * Requerimiento 6
	 * @return
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

	//Parte b
	//Requerimientos 7,8 y 9 
	// Hecho por Juan David Villamil
	/**
	 * Encontrar el camino de menor costo (menor distancia Haversine)
	 * para un viaje entre dos localizaciones geogrÃ¡ficas de la ciudad
	 * @param origen
	 * @param destina
	 * @return las N zonas que estaÌ�n maÌ�s al norte. 
	 */
	public int caminoMenorCosto(int origen, int destino)
	{
		return destino;
	}

	/**
	 * Calcular un Ã¡rbol de expansiÃ³n mÃ­nima (MST) con criterio distancia, utilizando el algoritmo de Kruskal,
	 *  aplicado al componente conectado (subgrafo) mÃ¡s grande de la malla vial de BogotÃ¡.
	 */
	public ListaSencillamenteEncadenada<NodoZona> MSTKruscal(int T)
	{
		return null;

	}
	
	public void temporal()
	{
		EdgeWeightedGraph nuevo = new EdgeWeightedGraph(1160);
		
	}
	//
	//	/**
	//	 * Buscar los tiempos de espera que tienen una desviaciÃ³n 
	//	 * estÃ¡ndar en un rango dado y que son del primer trimestre del 2018.
	//	 * @param minimo
	//	 * @param maximo
	//	 * @return
	//	 */
	//	public ListaSencillamenteEncadenada<Viaje> tiemposPrimerTrimestreConDesvEstandEnRango(double minimo, double maximo)
	//	{
	//		RedBlackBST<String, Viaje> arbol = new RedBlackBST<String, Viaje>();
	//		ListaSencillamenteEncadenada<Viaje> resp = new ListaSencillamenteEncadenada<Viaje>();
	//		for(Viaje temp : meses)
	//		{
	//			arbol.put(temp.darDesviacionTiempo() + "-" + temp.darIDOrigen() + "-" + temp.darIdDestino(), temp);
	//		}
	//		Iterator<Viaje> it = arbol.valuesInRange(minimo + "", maximo + "-999999999" + "-999999999");
	//		while(it.hasNext())
	//		{
	//			Viaje elemento = it.next();
	//			if(elemento.darHoraOMesODia() <= 3 && elemento.darHoraOMesODia() > 0)
	//			{
	//				resp.addLast(elemento);
	//			}
	//		}
	//		return resp;
	//	}

	//Parte C
	//	public ListaSencillamenteEncadenada<Viaje> darTiemposZonaOrigenHora(int idOrigen, int hora)
	//	{
	//		ListaSencillamenteEncadenada<Viaje> respuesta = new ListaSencillamenteEncadenada<Viaje>();
	//		TablaHashSeparateChaining<String, Viaje> hashTable = new TablaHashSeparateChaining<String, Viaje>();
	//		for(Viaje temp : horas)
	//		{
	//			hashTable.put(temp.darIDOrigen() + "-" + temp.darHoraOMesODia() + "-" + temp.darIdDestino(), temp);
	//		}
	//		Iterator<String> llaves = hashTable.keys();
	//		while(llaves.hasNext())
	//		{
	//			String cadena = llaves.next();
	//			if(cadena.startsWith(idOrigen + "-" + hora))
	//			{
	//				respuesta.addLast(hashTable.get(cadena));
	//			}
	//		}
	//		return respuesta;
	//	}
	//
	//	public Iterator<Viaje> darTiemposZonaDestRangoHoras(int idDestino, int horaMin, int horaMax)
	//	{
	//		RedBlackBST<String, Viaje> arbol = new RedBlackBST<String, Viaje>();
	//		for(Viaje temp : horas)
	//		{
	//			arbol.put(temp.darIdDestino() + "-" + temp.darHoraOMesODia() + "-" + temp.darIDOrigen(), temp);
	//		}
	//		Iterator<Viaje> resp = arbol.valuesInRange(idDestino + "-" + horaMin, idDestino + "-" + horaMax + "-999999");
	//		return resp;
	//	}
	//
	//	public MaxHeapCP<ZonaAux> zonasMasNodos()
	//	{
	//		MaxHeapCP<ZonaAux> respuesta = new MaxHeapCP<ZonaAux>();
	//		for(Zona laZona: zonas)
	//		{
	//			respuesta.agregar((ZonaAux)laZona);
	//		}
	//
	//		return respuesta;
	//	}
	//
	//	public ListaSencillamenteEncadenada<Pair<Integer, Double>> datosFaltantesPrimerSemestre()
	//	{
	//		int numDatosComp = 48*darNumZonas();
	//		MaxHeapCP<ZonaAux4> temp = new MaxHeapCP<ZonaAux4>();
	//		ListaSencillamenteEncadenada<Pair<Integer, Double>> resp = new ListaSencillamenteEncadenada<Pair<Integer,Double>>();
	//		for(Zona laZona: zonas)
	//		{
	//			temp.agregar((ZonaAux4)laZona);
	//		}
	//		int actual = 1;
	//		int conteo = 0;
	//		while(!temp.esVacia())
	//		{
	//			Zona laZona = temp.sacarMax();
	//			if(actual != laZona.getId())
	//			{
	//				double porcentaje = (1 - (conteo/numDatosComp))*100;
	//				Pair<Integer, Double> datos = new Pair<Integer, Double>(actual, porcentaje);
	//				resp.addLast(datos);
	//				actual++;
	//				conteo = 0;
	//			}
	//			conteo++;
	//		}
	//		return resp;
	//	}
}