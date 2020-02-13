package domain.estacion;

import java.util.concurrent.ThreadLocalRandom;

import domain.estacion.Anclajes;
import domain.bicicleta.Bicicleta;
import domain.tarjetausuario.TarjetaUsuario;

public class Estacion {

	private final int id;
	private final String direccion;
	private final Anclaje[] anclajes; 
	
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
		this.anclajes = new Anclaje[numAnclajes];
		crearAnclajes(numAnclajes);
	}

	private void crearAnclajes(int numAnclajes) {
		for (int i = 0; i < numAnclajes; i++) {
			this.anclajes[i] = new Anclaje();
		}
	}

	private int getId() {
		return id;
	}

	private String getDireccion() {
		return direccion;
	}

	private Anclaje[] getAnclajes() {
		return this.anclajes;
	}

	private void ocuparAnclaje(int posicion, Bicicleta bici) {
		this.anclajes[posicion].anclarBici(bici);
	}

	private void liberarAnclaje(int posicion) {
		this.anclajes[posicion].liberarBici();
	}

	private boolean isAnclajeOcupado(int posicion) {
		return this.anclajes[posicion].isOcupado();
	}

	private int numAnclajes() {
		return this.anclajes.length;
	}

	private Bicicleta getBici(int posicion) {
		return this.anclajes[posicion].getBici();
	}

	@Override
	public String toString() {
		return 	"id: " + getId() + '\n' +
				"direccion: " + getDireccion() + '\n' +
				"numeroAnclajes: " + numAnclajes();
	}

	/* LOGICA */

	public void consultarEstacion() {
		System.out.println(this);
	}

	public int anclajesLibres() {

		int anclajesLibres = 0;
		for (Anclaje anclaje : getAnclajes()) {
			// si el registro del array es null => anclaje libre
			anclajesLibres = anclaje.isOcupado()? anclajesLibres: ++anclajesLibres;
		}
		return anclajesLibres;
	}

	public void anclarBicicleta(Bicicleta bici) {
		// insertar el objeto bicicleta en el primer registro libre del array

		int posicion = 0;
		int numeroAnclaje = posicion + 1;

		for (Anclaje anclaje : getAnclajes()) {
			if (!anclaje.isOcupado()) { // leer anclaje
				ocuparAnclaje(posicion, bici); // set anclaje
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

				int posicion = generarAnclaje();
				int numeroAnclaje = posicion + 1;

				if (isAnclajeOcupado(posicion)) { // leer anclaje
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

		for (Anclaje anclaje : getAnclajes()) {
			numeroAnclaje = posicion + 1;
			if (anclaje.isOcupado()) {
				System.out.println("Anclaje " + numeroAnclaje + " " + anclaje.getBici().getId());
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
