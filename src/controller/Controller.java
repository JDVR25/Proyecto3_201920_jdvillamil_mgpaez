package controller;

import java.util.Scanner;

import com.sun.tracing.dtrace.ModuleName;

import javafx.util.Pair;
import model.algoritmos_grafos_alg4.DijkstraSPReq4;
import model.data_structures.IEstructura;
import model.data_structures.ListaSencillamenteEncadenada;
import model.data_structures.MaxHeapCP;
import model.graph_alg4.Edge;
import model.graph_alg4.Stack;
import model.logic.Coordenadas;
import model.logic.MVCModelo;
import model.logic.NodoMallaVial;
import model.logic.NodoZona;
import model.logic.Viaje;
import model.logic.ZonaAux;
import view.MVCView;

public class Controller {

	/* Instancia del Modelo*/
	private MVCModelo modelo;

	/* Instancia de la Vista*/
	private MVCView view;

	private boolean cargaRealizada;

	public static final int N = 20;


	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new MVCView();
		modelo = new MVCModelo();
		cargaRealizada = false;
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
			case 1:
				if(!cargaRealizada)
				{
					view.iniciarCarga();
					modelo = new MVCModelo(); 
					modelo.cargarDatosCSV(1);
					modelo.cargarDatosCSV(2);
					modelo.cargarDatosNodos();
					modelo.cargarDatosZonas();
					view.finalizarCarga(modelo);
				}
				else
				{
					view.errorDatosYaCargados();
				}
				break;

			case 2:
				try
				{
					if(cargaRealizada)
					{
						view.solicitarNumDatos();
						dato = lector.nextLine();
						int numLetras = Integer.parseInt(dato);
						if(numLetras < 0)
						{
							view.errorEnteroNegativo();
						}
						else
						{
							view.informacionLetras(modelo.letrasMasFrecuentesNombreZona(), numLetras);
						}
					}
					else
					{
						view.errorDatosNoCargados();
					}
				}
				catch (NumberFormatException e)
				{
					view.errorEntero();				}
				break;

			case 3:
				try
				{
					if(cargaRealizada)
					{
						view.solicitarLatitud();
						dato = lector.nextLine();
						double latitud = Double.parseDouble(dato); 

						view.solicitarLongitud();
						dato = lector.nextLine();
						double longitud = Double.parseDouble(dato);

						ListaSencillamenteEncadenada<NodoZona> lista = modelo.darNodosDelimitantesDeZona(latitud, longitud);
						view.darNodosZona(lista);
					}
					else
					{
						view.errorDatosNoCargados();
					}
				}
				catch (NumberFormatException e)
				{
					view.errorNum();
				}
				break;

			case 4: 
				try
				{
					if(cargaRealizada)
					{
						System.out.println("Ingrese la latitud de origen:");
						dato = lector.nextLine();
						double latitudOr = Double.parseDouble(dato);

						System.out.println("Ingrese la longitud de origen:");
						dato = lector.nextLine();
						double longitudOr = Double.parseDouble(dato);

						System.out.println("Ingrese la latitud de origen:");
						dato = lector.nextLine();
						double latitudDes = Double.parseDouble(dato);

						System.out.println("Ingrese la longitud de origen:");
						dato = lector.nextLine();
						double longitudDes = Double.parseDouble(dato);

						Coordenadas origen = new Coordenadas(longitudOr, latitudOr);
						Coordenadas destino = new Coordenadas(longitudDes, latitudDes);

						DijkstraSPReq4 resultados = modelo.caminoCostoMinimo(origen, destino);
						int s = -1;
						for(NodoMallaVial temp: modelo.darNodos())
						{
							if(temp.darCoordenada().coincide(destino.getLatitud(), destino.getLongitud()))
								s = temp.darId();
						}
						Stack<Edge> pila = resultados.pathTo(s);
						Stack<Edge> inversa = new Stack<>();
						int tam = pila.size() > 0?pila.size()+1:0;
						System.out.println("El total de vertices en el camino es de " + tam);
						if(!pila.isEmpty())
						{
							inversa.push(pila.pop());
						}
						System.out.println("Los nodos en el camino son");
						for(NodoMallaVial temp: modelo.darNodos())
						{
							if(temp.darId() == s)
								System.out.println(temp);
						}
						for(Edge temp: inversa)
						{
							s = temp.other(s);
							for(NodoMallaVial nodo: modelo.darNodos())
							{
								if(nodo.darId() == s)
									System.out.println(nodo);
							}
						}
						pila = resultados.pathTo(s);
						System.out.println("Ruta a seguir");
						double tiempo = 0;
						double distancia = 0;
						for(Edge temp: pila)
						{
							System.out.println(temp);
							tiempo += temp.tiempoViaje();
							distancia += temp.costoDistanciaHarversine();
						}
						
						System.out.println("El tiempo minimo estimado es:" + tiempo);
						System.out.println("La distancia del recorrido es:" + distancia);
						
					}
					else
					{
						view.errorDatosNoCargados();
					}
				}
				catch (NumberFormatException e)
				{
					view.errorNum();
				}
				break;

			case 5: 
				//TODO pendiente
				break;

			case 6: 
				//TODO pendiente
				break;

			case 7: 
				//TODO pendiente
				break;

			case 8: 
				try
				{
					if(cargaRealizada)
					{
						view.solicitarZona();
						dato = lector.nextLine();
						int zonaOrigen = Integer.parseInt(dato);

						view.solicitarHora();
						dato = lector.nextLine();
						int hora = Integer.parseInt(dato);

						view.impTiemposZonaOrigen(modelo.darTiemposZonaOrigenHora(zonaOrigen, hora));
					}
					else
					{
						view.errorDatosNoCargados();
					}
				}
				catch (NumberFormatException e)
				{
					view.errorNum();
				}
				break;

			case 9: 
				try
				{
					if(cargaRealizada)
					{
						view.solicitarZona();
						dato = lector.nextLine();
						int zona = Integer.parseInt(dato);

						view.pedirLimiteBajo();
						dato = lector.nextLine();
						int low = Integer.parseInt(dato);

						view.pedirLimiteAlto();
						dato = lector.nextLine();
						int high = Integer.parseInt(dato);

						view.impTiemposZonaDestino(modelo.darTiemposZonaDestRangoHoras(zona, low, high));
					}
					else
					{
						view.errorDatosNoCargados();
					}
				}
				catch (NumberFormatException e)
				{
					view.errorNum();
				}
				break;

			case 10: 
				try
				{
					if(cargaRealizada)
					{
						view.solicitarNumDatos();
						dato = lector.nextLine();
						int cant = Integer.parseInt(dato);
						MaxHeapCP<ZonaAux> cola = modelo.zonasMasNodos();
						view.darZonasCantidadNodos(cola, cant);
					}
					else
					{
						view.errorDatosNoCargados();
					}
				}
				catch (NumberFormatException e)
				{
					view.errorNum();
				}
				break;

			case 11: 
				try
				{
					if(cargaRealizada)
					{
						view.iniciarCreacionAscii();
						ListaSencillamenteEncadenada<Pair<Integer,Double>> lista = modelo.datosFaltantesPrimerSemestre();
						view.imprimirInformacionGrafica(lista);
					}
					else
					{
						view.errorDatosNoCargados();
					}
				}
				catch (NumberFormatException e)
				{
					view.errorNum();
				}
				break;

			case 12: 
				System.out.println("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break; 

			default: 
				System.out.println("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}	
}
