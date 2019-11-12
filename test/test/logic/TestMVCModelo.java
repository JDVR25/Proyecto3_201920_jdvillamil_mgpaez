package test.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.data_structures.IEstructura;
import model.data_structures.ListaEncadenadaAbstracta;
import model.data_structures.ListaSencillamenteEncadenada;
import model.data_structures.Nodo;
import model.logic.MVCModelo;
import model.logic.Viaje;

import org.junit.Before;
import org.junit.Test;

public class TestMVCModelo {
	
	private MVCModelo modelo;

	/**
	 * lista para realizar las pruebas
	 */
	private ListaSencillamenteEncadenada<Viaje> lista;

//	/**
//	 * Prepara el escenario de pruebas
//	 */
//	@Before
//	public void setUpEscenario1()
//	{
//		modelo = new MVCModelo();
//		lista = new ListaSencillamenteEncadenada<Viaje>();
//		lista.addLast(new Viaje(0, 0, 0, 45, 0, 0, 0));
//		lista.addLast(new Viaje(0, 0, 0, 13, 0, 0, 0));
//		lista.addLast(new Viaje(0, 0, 0, 10, 0, 0, 0));
//		lista.addLast(new Viaje(0, 0, 0, 8, 0, 0, 0));
//		lista.addLast(new Viaje(0, 0, 0, -1, 0, 0, 0));
//		lista.addLast(new Viaje(0, 0, 0, -8, 0, 0, 0));
//		lista.addLast(new Viaje(0, 0, 0, -25, 0, 0, 0));
//	}
//
//	/**
//	 * Prueba 1: Se encarga de verificar el método ordenar de la clase.<br>
//	 * <b> Métodos a probar: </b> <br>
//	 * ordenar<br>
//	 * <b> Casos de prueba:</b><br>
//	 * 1. Ordena correctamente por todos los algoritmos ascendentemente.<br>
//	 * 2. Ordena correctamente por todos los algoritmos descendentemente.<br>
//	 */
//	@Test
//	public void testOrdenar()
//	{
//		//TODO Completar de acuerdo a la documentación
//		// Revise la documentación de Collections.shuffle(List<E>) puede ser interesante llamarlo cada vez que se va a ordenar.
//
//		List<Viaje> ascentente = new ArrayList<Viaje>();
//		ascentente.add(new Viaje(0, 0, 0, -25, 0, 0, 0));
//		ascentente.add(new Viaje(0, 0, 0, -8, 0, 0, 0));
//		ascentente.add(new Viaje(0, 0, 0, -1, 0, 0, 0));
//		ascentente.add(new Viaje(0, 0, 0, 8, 0, 0, 0));
//		ascentente.add(new Viaje(0, 0, 0, 10, 0, 0, 0));
//		ascentente.add(new Viaje(0, 0, 0, 13, 0, 0, 0));
//		ascentente.add(new Viaje(0, 0, 0, 45, 0, 0, 0));
//
//		List<Viaje> baraja = new ArrayList<Viaje>();
//		baraja.add(new Viaje(0, 0, 0, 45, 0, 0, 0));
//		baraja.add(new Viaje(0, 0, 0, 13, 0, 0, 0));
//		baraja.add(new Viaje(0, 0, 0, 10, 0, 0, 0));
//		baraja.add(new Viaje(0, 0, 0, 8, 0, 0, 0));
//		baraja.add(new Viaje(0, 0, 0, -1, 0, 0, 0));
//		baraja.add(new Viaje(0, 0, 0, -8, 0, 0, 0));
//		baraja.add(new Viaje(0, 0, 0, -25, 0, 0, 0));
//		
//		Nodo<Viaje> temp = lista.darNodo(0);
//		Collections.shuffle(baraja);
//		for(int i = 0; i < baraja.size(); i++)
//		{
//			temp.cambiarElemento(baraja.get(i));
//			temp = temp.darSiguiente();
//		}
//		
//		modelo.ordenarPorTiempo(lista);
//		
//		temp = lista.darNodo(0);
//		for(int i = 0; i < ascentente.size(); i++)
//		{
//			assertTrue("La lista no esta ordenada", ascentente.get(i).darTiempoViaje() == temp.darElemento().darTiempoViaje());
//			temp = temp.darSiguiente();
//		}
//
//	}

}
