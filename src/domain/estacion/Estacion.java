package domain.estacion;

import java.util.concurrent.ThreadLocalRandom;

import domain.estacion.Anclajes;
import domain.bicicleta.Bicicleta;
import domain.tarjetausuario.TarjetaUsuario;

public class Estacion {

	private final int id;
	private final String direccion;
	private final int numeroAnclajes;
	private final Anclajes anclajes; 
	
	/** 
	 * referencia a estructura datos anclajes, sea cual sea
	 * La estructura ha de estar en su clase hardware /anclajes por SRP:
	 * La estación de divide en centralita = lógica y hardware /anclajes
	 * que es la interfaz con el usuario/a.
	 * Hardware anclaje no es una capa de acceso a datos... pero casi ¿?
	 * Este diseño se ve influido por el diseño de la BBDD: 
	 * Entidades libro UML Quique
	 */

	public Estacion(int id, String direccion, int numAnclajes) {

		this.id = id;
		this.direccion = direccion;
		this.numeroAnclajes = numAnclajes;
		this.anclajes = new Anclajes(numAnclajes);
	}

	private int getId() {
		return id;
	}

	private String getDireccion() {
		return direccion;
	}

	private int getNumeroAnclajes() {
		return numeroAnclajes;
	}

	private Bicicleta[] getAnclajes() {
		return this.anclajes.getBicicletas();
	}

	private void ocuparAnclaje(int posicion, Bicicleta bici) {
		this.anclajes.ocuparAnclaje(posicion, bici);
	}

	private void liberarAnclaje(int posicion) {
		this.anclajes.liberarAnclaje(posicion);
	}

	private boolean isAnclajeLibre(int posicion) {
		return this.anclajes.isAnclajeLibre(posicion);
	}

	private int numAnclajes() {
		return this.anclajes.numAnclajes();
	}

	private Bicicleta getBici(int posicion) {
		return this.anclajes.getBici(posicion);
	}

	@Override
	public String toString() {
		return 	"id: " + getId() + '\n' +
				"direccion: " + getDireccion() + '\n' +
				"numeroAnclajes: " + getNumeroAnclajes();
	}

	/* LOGICA */

	public void consultarEstacion() {
		System.out.println(this);
	}

	public int anclajesLibres() {

		int anclajesLibres = 0;
		for (Bicicleta anclaje : getAnclajes()) {
			// si el registro del array es null => anclaje libre
			anclajesLibres = anclaje == null? ++anclajesLibres : anclajesLibres;
		}
		return anclajesLibres;
	}

	public void anclarBicicleta(Bicicleta bicicleta) {
		// insertar el objeto bicicleta en el primer registro libre del array

		int posicion = 0;
		int numeroAnclaje = posicion + 1;

		for (Bicicleta anclaje : getAnclajes()) {
			if (anclaje == null) { // leer anclaje
				ocuparAnclaje(posicion, bicicleta); // set anclaje
				mostrarAnclaje(bicicleta, numeroAnclaje);
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

				int posicion = generarAnclaje();
				int numeroAnclaje = posicion + 1;

				if (!isAnclajeLibre(posicion)) { // leer anclaje
					mostrarBicicleta(getBici(posicion), numeroAnclaje);
					liberarAnclaje(posicion); // set anclaje
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
		// recorre el array anclajes y muestra el id de la bici anclada o si está libre

		int posicion = 0;
		int numeroAnclaje = 0;

		for (Bicicleta bicicleta : getAnclajes()) {
			numeroAnclaje = posicion + 1;
			if (bicicleta != null) {
				System.out.println("Anclaje " + numeroAnclaje + " " + bicicleta.getId());
			} else {
				System.out.println("Anclaje " + numeroAnclaje + " " + " libre");
			}
			posicion++;
		}
	}

	private int generarAnclaje() { // a hardware anclaje
		Integer numeroEntero = ThreadLocalRandom.current().nextInt(0, numAnclajes());
		return numeroEntero;
	}

}
