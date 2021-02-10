package domain.estacion;

import java.util.Arrays;
import java.util.Optional;

import domain.bicicleta.Movil;
import domain.tarjetausuario.Autenticacion;

public class Estacion {

	private final int id;
	private final String direccion;
	private final Anclajes anclajes;
	
	/** 
	 * Estacion tiene una (has-a) ADT anclajes, sea cual sea
	 * El ADT ha de estar en su clase anclajes por SRP.
	 * La estación de divide en centralita /lógica y hardware /anclajes
	 * que es la interfaz con el usuario/a.
	 * Hardware anclaje no es una capa de acceso a datos... pero casi
	 */

	public Estacion(int id, String direccion, int numAnclajes) {
		this.id = id;
		this.direccion = direccion;
		this.anclajes = new Anclajes(numAnclajes);
	}

	private int getId() {
		return id;
	}

	private String getDireccion() {
		return direccion;
	}

	@Override
	public String toString() {	
		return String.format("id: %d %ndireccion: %s %nanclajes: %s", 
							  getId(), getDireccion(), numAnclajes());
	}

	/**
	 * Acceso a anclajes
	 */

	private Anclaje[] anclajes() {
		return this.anclajes.anclajes();
	}

	private int numAnclajes() {
		return this.anclajes.numAnclajes();
	}

	/**
	 * Lógica
	 */

	public void consultarEstacion() {
		System.out.println(this);
	}

	public long anclajesLibres() {

		return Arrays.stream(anclajes()).filter(a -> !a.isOcupado()).count();

	}

	public void anclarBicicleta(Movil bici) {
		// insertar el objeto bicicleta en cualquier anclaje libre del array

		Optional<Anclaje> anclajeLibre = Arrays.stream(anclajes()).filter(a -> !a.isOcupado()).findAny();

		if (anclajeLibre.isPresent()) {
			anclajeLibre.get().anclarBici(bici);
		} else {
			System.out.println("No existen anclajes disponibles para bici " + bici);
		}
	}

	public boolean leerTarjetaUsuario(Autenticacion tarjetaUsuario) {
		return tarjetaUsuario.isActivada();
	}

	public void retirarBicicleta(Autenticacion tarjetaUsuario) {

		if (leerTarjetaUsuario(tarjetaUsuario)) {

			Optional<Anclaje> anclajeOcupado = Arrays.stream(anclajes()).filter(Anclaje::isOcupado).findAny();

			if (anclajeOcupado.isPresent()) {

				mostrarBicicleta(anclajeOcupado.get().getBici());
				anclajeOcupado.get().liberarBici();

			} else {
				System.out.println("No hay bicis");
			}

		} else {
			System.out.println("Tarjeta de usuario inactiva :(");
		}
	}

	private void mostrarBicicleta(Movil bicicleta /*, int numeroAnclaje*/) {
		System.out.println("bicicleta retirada: " + bicicleta.getId() 
							/*+ " del anclaje: " + numeroAnclaje*/);
	}

	private void mostrarAnclaje(Movil bicicleta, int numeroAnclaje) {
		System.out.println("bicicleta " + bicicleta.getId() 
							+ " anclada en el anclaje " + numeroAnclaje);
	}

	public void consultarAnclajes() {
		// Recorre el array anclajes y 
		// Muestra si está libre o el id de la bici anclada 

		Arrays.stream(anclajes()).map(a -> Optional.ofNullable(a.getBici()))
								 .forEach(bici -> System.out.print("Anclaje " + 
								 									(bici.isPresent()? bici.get(): "libre") 
																	 + '\n'));
	}
}
