package view;

import java.util.Iterator;

import controller.Controller;
import javafx.util.Pair;
import model.data_structures.ListaSencillamenteEncadenada;
import model.data_structures.MaxHeapCP;
import model.logic.MVCModelo;
import model.logic.NodoZona;
import model.logic.Viaje;
import model.logic.Zona;
import model.logic.ZonaAux;

public class MVCView 
{
	/**
	 * Metodo constructor
	 */
	public MVCView()
	{

	}

	public void printMenu()
	{
		System.out.println("1. Cargar datos");
		System.out.println("2. Consultar las letras mas frecuentes por las que comienza el nombre de una zona");
		System.out.println("3. Buscar los nodos que delimitan las zonas por localizacion geografica");
		System.out.println("4. Buscar tiempos de viaje del primer trimestre del año 2018 que estan dentro de un rango");
		System.out.println("5. Buscar las zonas que se encuentran mas al norte");
		System.out.println("6. Buscar nodos de la malla vial");
		System.out.println("7. Buscar tiempos de viaje del primer trimestre del 2018 cuya desviacion estandar se encuentra dentro de un rango");
		System.out.println("8. Buscar viejas que salen de una zona determinada a cierta hora");
		System.out.println("9. Buscar viajes que llegan a una zona dada y salen en un rango de horas");
		System.out.println("10.Consultar las zonas con la mayor cantidad de nodos en sus fronteras");
		System.out.println("11.Sacar a grafica ASCII del porcentaje de datos faltantes en el primer semestre de 2018");
		System.out.println("12.Exit");
		System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
	}

	public void iniciarCarga()
	{

		System.out.println("Se cargaran los datos");
	}

	public void finalizarCarga(MVCModelo modelo)
	{

		System.out.println("Datos cargados");
		System.out.println("-----Numero de datos cargados");
		System.out.println("Viajes por horas: " + modelo.darNumViajesHora());
		System.out.println("Viajes por dias: " + modelo.darNumViajesDia());
		System.out.println("Viajes por meses: " + modelo.darNumViajesMes());
		System.out.println("Zonas: " + modelo.darNumZonas());
		System.out.println("Nodos malla vial: " + modelo.darNumNodos());
	}

	public void solicitarNumDatos()
	{
		System.out.println("Ingrese la cantidad de datos que desea consultar");
	}

	public void informacionLetras(MaxHeapCP<ListaSencillamenteEncadenada<Zona>> cola, int consultadas)
	{
		int i = 0;
		while(!cola.esVacia() && i < consultadas)
		{
			ListaSencillamenteEncadenada<Zona> lista = cola.sacarMax();
			System.out.println(lista.get(0).getNombre().charAt(0));
			for(Zona temp: lista)
			{
				temp.getNombre();
			}
			i++;
		}
	}

	public void solicitarLatitud()
	{
		System.out.println("Ingrese una latitud:");
	}

	public void solicitarLongitud()
	{
		System.out.println("Ingrese una longitud:");
	}

	public void darNodosZona(ListaSencillamenteEncadenada<NodoZona> lista)
	{
		System.out.println("Nodos encontrados: " + lista.size());
		for(NodoZona temp: lista)
		{
			System.out.println("Latitud: " + temp.darLatitud() + ". Longitud: " + temp.darLongitud() + ". Zona: " + temp.darZona());
		}
	}

	public void pedirLimiteBajo()
	{
		System.out.println("Ingrese el limite bajo del rango:");
	}

	public void pedirLimiteAlto()
	{
		System.out.println("Ingrese el limite alto del rango:");
	}

	public void darInfoViajes(ListaSencillamenteEncadenada<Viaje> lista)
	{
		int i = 0;
		Iterator<Viaje> it = lista.iterator();
		while(it.hasNext() && i < Controller.N)
		{
			Viaje temp = it.next();
			System.out.println("Zona origen: " + temp.darIDOrigen() + ". Zona destino: " + temp.darIdDestino() + ". Mes: " + temp.darHoraOMesODia() + ". Tiempo de viaje: " + temp.darTiempoViaje());
		}
	}
	
	public void solicitarZona()
	{
		System.out.println("Ingrese el id de la zona que desea consultar");
	}
	
	public void solicitarHora()
	{
		System.out.println("Ingrese la hora que desea consultar");
	}
	
	public void impTiemposZonaOrigen(ListaSencillamenteEncadenada<Viaje> lista)
	{
		for(Viaje temp: lista)
		{
			System.out.println("Origen: " + temp.darIDOrigen() + ". Destino: " + temp.darIdDestino() + ". Hora: " + temp.darHoraOMesODia() + ". Tiempo promedio: " + temp.darTiempoViaje());
		}
	}
	
	public void impTiemposZonaDestino(Iterator<Viaje> it)
	{
		while(it.hasNext())
		{
			Viaje temp = it.next();
			System.out.println("Origen: " + temp.darIDOrigen() + ". Destino: " + temp.darIdDestino() + ". Hora: " + temp.darHoraOMesODia() + ". Tiempo promedio: " + temp.darTiempoViaje());
		}
	}

	public void darZonasCantidadNodos(MaxHeapCP<ZonaAux> cola, int n)
	{
		int i = 0;
		while(!cola.esVacia() && i < n)
		{
			ZonaAux temp = cola.sacarMax();
			System.out.println("Nombre zona: " + temp.getNombre() + ". Nodos zona: " + temp.getCoordenadas().size() + ".");
			i++;
		}
	}

	public void iniciarCreacionAscii()
	{
		System.out.println("Se creara la grafica ASCII de los datos faltantes");
	}

	public void imprimirInformacionGrafica(ListaSencillamenteEncadenada<Pair<Integer, Double>> datos)
	{
		for(Pair<Integer,Double> pareja: datos)
		{
			double porcentajeFalta = pareja.getValue();
			String asteriscos = "";
			for(double p = porcentajeFalta; p > 0; p -= 2)
			{
				asteriscos += "*";
			}
			System.out.println(pareja.getKey() + " | " + asteriscos);
		}
	}

	public void errorDatosYaCargados()
	{
		System.out.println("Ya se han cargado los datos");
	}

	public void errorDatosNoCargados()
	{
		System.out.println("No se han cargado los datos");
	}

	public void errorEntero()
	{
		System.out.println("La informacion debe ingresarse como un numero y debe ser un entero");
	}

	public void errorEnteroNegativo()
	{
		System.out.println("La informacion debe ingresarse com un numero positivo");
	}

	public void errorNum()
	{
		System.out.println("La informacion debe ingresarse com un numero");
	}

	public void errorHora(int hora)
	{
		System.out.println("La hora " + hora + "no es una hora valida");
	}
	public void printModelo(MVCModelo modelo)
	{
		// TODO implementar
	}
}
