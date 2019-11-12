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
import model.data_structures.IEstructura;
import model.data_structures.ListaSencillamenteEncadenada;
import model.data_structures.MaxHeapCP;
import model.data_structures.Nodo;
import model.data_structures.RedBlackBST;
import model.data_structures.TablaHashSeparateChaining;

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
	//Para el 1A usar Tabla de hash separate chaining, que sea de tamano 27 para facilitar las cosas, la llave sera la letra inicial necesarios add set y getset
	//En el 2A Usar cola de prioridad para los nodos
	//Para el 3A usar arbloes binarios.

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
			reader = new CSVReader(new FileReader("./data/Nodes_of_red_vial-wgs84_shp.txt"));
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
                ListaSencillamenteEncadenada<Punto> coordenadas = new ListaSencillamenteEncadenada<Punto>();
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
                    coordenadas.addLast(new Punto(longitud, latitud));
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

	//Parte A
	public MaxHeapCP<ListaSencillamenteEncadenada<Zona>> letrasMasFrecuentesNombreZona()
	{
		MaxHeapCP<ZonaAux1> temp = new MaxHeapCP<ZonaAux1>();
		MaxHeapCP<ListaSencillamenteEncadenada<Zona>> respuesta = new MaxHeapCP<ListaSencillamenteEncadenada<Zona>>();
		for(Zona laZona: zonas)
		{
			temp.agregar((ZonaAux1) laZona);
		}
		for(char i = 'a'; i <= 'z'; i = (char) (i +1))
		{
			ListaSencillamenteEncadenada<Zona> listaZonas = new ListaSencillamenteEncadenada<Zona>();
			boolean listo = false;
			while(!listo && !temp.esVacia())
			{
				Zona laZona = temp.sacarMax();
				if(laZona.getNombre().charAt(0) == i)
				{
					listaZonas.addLast(laZona);
				}
				else
				{
					listo = true;
				}
			}
			respuesta.agregar(listaZonas);
		}
		return respuesta;
	}

	public ListaSencillamenteEncadenada<NodoZona> darNodosDelimitantesDeZona(double latitud, double longitud)
	{
		double latitudTrun = latitud;
		latitudTrun=latitudTrun*100;
		latitudTrun = (int)latitudTrun;
		latitudTrun = latitudTrun/100;

		double longitudTrun = longitud;
		longitudTrun=longitudTrun*100;
		longitudTrun = (int)longitudTrun;
		longitudTrun = longitudTrun/100;

		ListaSencillamenteEncadenada<NodoZona> respuesta = new ListaSencillamenteEncadenada<NodoZona>();

		TablaHashSeparateChaining<String, NodoZona> hashTable = new TablaHashSeparateChaining<String, NodoZona>();
		for(Zona laZona: zonas)
		{
			for(Punto point : laZona.getCoordenadas())
			{
				NodoZona nuevo = new NodoZona(laZona.getNombre(), point.getLongitud(), point.getLatitud());
				hashTable.put(point.toString() + "-" + laZona.getNombre(), nuevo);
			}
		}
		Iterator<String> llaves = hashTable.keys();
		while(llaves.hasNext())
		{
			String cadena = llaves.next();
			String[] info = cadena.split("-");
			double latitudNodo = Double.parseDouble(info[0]);
			latitudNodo = latitudNodo*100;
			latitudNodo = (int)latitudNodo;
			latitudNodo = latitudNodo/100;

			double longitudNodo = Double.parseDouble(info[1]);
			longitudNodo = longitudNodo*100;
			longitudNodo = (int)longitudNodo;
			longitudNodo = longitudNodo/100;

			if(latitudTrun == latitudNodo && longitudTrun == longitudNodo)
			{
				respuesta.addLast(hashTable.get(cadena));
			}
		}
		return respuesta;
	}

	public ListaSencillamenteEncadenada<Viaje> tiemposPrimerTrimestreDentroDeRango(double minimo, double maximo)
	{
		RedBlackBST<String, Viaje> arbol = new RedBlackBST<String, Viaje>();
		ListaSencillamenteEncadenada<Viaje> resp = new ListaSencillamenteEncadenada<Viaje>();
		for(Viaje temp : meses)
		{
			arbol.put(temp.darTiempoViaje() + "-" + temp.darIDOrigen() + "-" + temp.darIdDestino(), temp);
		}
		Iterator<Viaje> it = arbol.valuesInRange(minimo + "", maximo + "-999999999" + "-999999999");
		while(it.hasNext())
		{
			Viaje elemento = it.next();
			if(elemento.darHoraOMesODia() <= 3 && elemento.darHoraOMesODia() > 0)
			{
				resp.addLast(elemento);
			}
		}
		return resp;
	}

	//Parte B
		/**
		 * Mostrar las zonas ordenadas desde las que 
		 * estén más al norte. De cada zona se debe imprimir el 
		 * nombre y la (latitud, longitud) de su punto más al norte.
		 * @param n es un valor de entrada
		 * @return las N zonas que están más al norte. 
		 */
		public MaxHeapCP<ListaSencillamenteEncadenada<Zona>> darZonasMasAlNorte(int n)
		{
			MaxHeapCP<ZonaAux1> meanWhile = new MaxHeapCP<ZonaAux1>();
			MaxHeapCP<ListaSencillamenteEncadenada<Zona>> answer = new MaxHeapCP<ListaSencillamenteEncadenada<Zona>>();
			
			for(Zona laZona: zonas)
			{
				meanWhile.agregar((ZonaAux1) laZona);
			}
			
			int howManyZones = 0;
			
			for(int i = 0; i < zonas.size() && howManyZones < n; i++)
			{
				ListaSencillamenteEncadenada<Zona> listaZonas = zonas;
				ListaSencillamenteEncadenada<Punto> maxCoordenadas= zonas.get(i).getCoordenadas();
				
				for (int j = 0; j < listaZonas.size(); j++ )
				{
					if((maxCoordenadas.compareTo(listaZonas.get(j).getCoordenadas())) > 0 && i !=j)
						{
							maxCoordenadas = listaZonas.get(j).getCoordenadas();
							howManyZones++;
						}
				}
				
				answer.agregar(maxCoordenadas);
			}
			return answer;
		}

		/**
		 * Dado una latitud y una longitud, 
		 * se deben mostrar todos los nodos 
		 * que tengan esas mismas latitud y longitud 
		 * truncando a 2 cifras decimales.
		 * @param latitud
		 * @param longitud
		 * @return
		 */
		public ListaSencillamenteEncadenada<NodoZona> darNodosMallaVial(double latitud, double longitud)
		{
			double latitudTrun = latitud;
			latitudTrun=latitudTrun*10;
			latitudTrun = (int)latitudTrun;
			latitudTrun = latitudTrun/10;

			double longitudTrun = longitud;
			longitudTrun=longitudTrun*10;
			longitudTrun = (int)longitudTrun;
			longitudTrun = longitudTrun/10;

			ListaSencillamenteEncadenada<NodoZona> respuesta = new ListaSencillamenteEncadenada<NodoZona>();

			TablaHashSeparateChaining<String, NodoZona> hashTable = new TablaHashSeparateChaining<String, NodoZona>();
			for(Zona laZona: zonas)
			{
				for(Punto point : laZona.getCoordenadas())
				{
					NodoZona nuevo = new NodoZona(laZona.getNombre(), point.getLongitud(), point.getLatitud());
					hashTable.put(point.toString() + "-" + laZona.getNombre(), nuevo);
				}
			}
			Iterator<String> llaves = hashTable.keys();
			while(llaves.hasNext())
			{
				String cadena = llaves.next();
				String[] info = cadena.split("-");
				double latitudNodo = Double.parseDouble(info[0]);
				latitudNodo = latitudNodo*10;
				latitudNodo = (int)latitudNodo;
				latitudNodo = latitudNodo/10;

				double longitudNodo = Double.parseDouble(info[1]);
				longitudNodo = longitudNodo*10;
				longitudNodo = (int)longitudNodo;
				longitudNodo = longitudNodo/10;

				if(latitudTrun == latitudNodo && longitudTrun == longitudNodo)
				{
					respuesta.addLast(hashTable.get(cadena));
				}
			}
			return respuesta;
		}

		/**
		 * Buscar los tiempos de espera que tienen una desviación 
		 * estándar en un rango dado y que son del primer trimestre del 2018.
		 * @param minimo
		 * @param maximo
		 * @return
		 */
		public ListaSencillamenteEncadenada<Viaje> tiemposPrimerTrimestreConDesvEstandEnRango(double minimo, double maximo)
		{
			RedBlackBST<String, Viaje> arbol = new RedBlackBST<String, Viaje>();
			ListaSencillamenteEncadenada<Viaje> resp = new ListaSencillamenteEncadenada<Viaje>();
			for(Viaje temp : meses)
			{
				arbol.put(temp.darDesviacionTiempo() + "-" + temp.darIDOrigen() + "-" + temp.darIdDestino(), temp);
			}
			Iterator<Viaje> it = arbol.valuesInRange(minimo + "", maximo + "-999999999" + "-999999999");
			while(it.hasNext())
			{
				Viaje elemento = it.next();
				if(elemento.darHoraOMesODia() <= 3 && elemento.darHoraOMesODia() > 0)
				{
					resp.addLast(elemento);
				}
			}
			return resp;
		}

	//Parte C
	public ListaSencillamenteEncadenada<Viaje> darTiemposZonaOrigenHora(int idOrigen, int hora)
	{
		ListaSencillamenteEncadenada<Viaje> respuesta = new ListaSencillamenteEncadenada<Viaje>();
		TablaHashSeparateChaining<String, Viaje> hashTable = new TablaHashSeparateChaining<String, Viaje>();
		for(Viaje temp : horas)
		{
			hashTable.put(temp.darIDOrigen() + "-" + temp.darHoraOMesODia() + "-" + temp.darIdDestino(), temp);
		}
		Iterator<String> llaves = hashTable.keys();
		while(llaves.hasNext())
		{
			String cadena = llaves.next();
			if(cadena.startsWith(idOrigen + "-" + hora))
			{
				respuesta.addLast(hashTable.get(cadena));
			}
		}
		return respuesta;
	}

	public Iterator<Viaje> darTiemposZonaDestRangoHoras(int idDestino, int horaMin, int horaMax)
	{
		RedBlackBST<String, Viaje> arbol = new RedBlackBST<String, Viaje>();
		for(Viaje temp : horas)
		{
			arbol.put(temp.darIdDestino() + "-" + temp.darHoraOMesODia() + "-" + temp.darIDOrigen(), temp);
		}
		Iterator<Viaje> resp = arbol.valuesInRange(idDestino + "-" + horaMin, idDestino + "-" + horaMax + "-999999");
		return resp;
	}

	public MaxHeapCP<ZonaAux> zonasMasNodos()
	{
		MaxHeapCP<ZonaAux> respuesta = new MaxHeapCP<ZonaAux>();
		for(Zona laZona: zonas)
		{
			respuesta.agregar((ZonaAux)laZona);
		}

		return respuesta;
	}

	public ListaSencillamenteEncadenada<Pair<Integer, Double>> datosFaltantesPrimerSemestre()
	{
		int numDatosComp = 48*darNumZonas();
		MaxHeapCP<ZonaAux4> temp = new MaxHeapCP<ZonaAux4>();
		ListaSencillamenteEncadenada<Pair<Integer, Double>> resp = new ListaSencillamenteEncadenada<Pair<Integer,Double>>();
		for(Zona laZona: zonas)
		{
			temp.agregar((ZonaAux4)laZona);
		}
		int actual = 1;
		int conteo = 0;
		while(!temp.esVacia())
		{
			Zona laZona = temp.sacarMax();
			if(actual != laZona.getId())
			{
				double porcentaje = (1 - (conteo/numDatosComp))*100;
				Pair<Integer, Double> datos = new Pair<Integer, Double>(actual, porcentaje);
				resp.addLast(datos);
				actual++;
				conteo = 0;
			}
			conteo++;
		}
		return resp;
	}
}