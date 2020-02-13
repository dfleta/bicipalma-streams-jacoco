package domain.estacion;

import domain.bicicleta.Bicicleta;
import domain.tarjetausuario.TarjetaUsuario;
import domain.estacion.Anclajes;

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
	 * Este diseño se ve influido por el diseño de la BBDD: 
	 * Entidades libro UML Quique
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
		return 	"id: " + getId() + '\n' +
				"direccion: " + getDireccion() + '\n' +
				"numeroAnclajes: " + numAnclajes();
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

	public int anclajesLibres() {

		int anclajesLibres = 0;
		for (Anclaje anclaje : anclajes()) {
			// si el registro del array es null => anclaje libre
			anclajesLibres = anclaje.isOcupado()? anclajesLibres: ++anclajesLibres;
		}
		return anclajesLibres;
	}

	public void anclarBicicleta(Bicicleta bici) {
		// insertar el objeto bicicleta en el primer registro libre del array

		int posicion = 0;
		int numeroAnclaje = posicion + 1;

		for (Anclaje anclaje : anclajes()) {
			if (!anclaje.isOcupado()) { // leer anclaje
				anclajes.ocuparAnclaje(posicion, bici); // set anclaje
				mostrarAnclaje(bici, numeroAnclaje);
				break;
			} else {
				posicion++;
			}
			numeroAnclaje++;
		}
	}

	public boolean leerTarjetaUsuario(TarjetaUsuario tarjetaUsuario) {
		return tarjetaUsuario.isActivada();
	}

	public void retirarBicicleta(TarjetaUsuario tarjetaUsuario) {
		// genero un número de anclaje random = posicion en array
		// y retiro bici => poner a null

		if (leerTarjetaUsuario(tarjetaUsuario)) {

			boolean biciRetirada = false;

			while (!biciRetirada) {

				int posicion = anclajes.seleccionarAnclaje();
				int numeroAnclaje = posicion + 1;

				if (anclajes.isAnclajeOcupado(posicion)) { // leer anclaje
					mostrarBicicleta(anclajes.getBiciAt(posicion), numeroAnclaje);
					anclajes.liberarAnclaje(posicion); // set anclaje
					biciRetirada = true;
				} else
					; // generamos nuevo número de anclaje;
			}

		} else {
			System.out.println("Tarjeta de usuario inactiva :(");
		}
	}

	private void mostrarBicicleta(Bicicleta bicicleta, int numeroAnclaje) {
		System.out.println("bicicleta retirada: " + bicicleta.getId() 
							+ " del anclaje: " + numeroAnclaje);
	}

	private void mostrarAnclaje(Bicicleta bicicleta, int numeroAnclaje) {
		System.out.println("bicicleta " + bicicleta.getId() 
							+ " anclada en el anclaje " + numeroAnclaje);
	}

	public void consultarAnclajes() {
		// Recorre el array anclajes y 
		// Muestra si está libre o el id de la bici anclada 

		int posicion = 0;
		int numeroAnclaje = 0;

		for (Anclaje anclaje : anclajes()) {
			numeroAnclaje = posicion + 1;
			if (anclaje.isOcupado()) {
				System.out.println("Anclaje " + numeroAnclaje + " " + anclaje.getBici().getId());
			} else {
				System.out.println("Anclaje " + numeroAnclaje + " " + " libre");
			}
			posicion++;
		}
	}
}
