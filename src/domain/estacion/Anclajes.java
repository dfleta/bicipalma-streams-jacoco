package domain.estacion;

import java.util.concurrent.ThreadLocalRandom;

import domain.bicicleta.Bicicleta;
import domain.estacion.Anclaje;

class Anclajes {

    /**
     * Anclajes es una composición
     * (agregacion fuerte) de Anclaje
     */
     
    private final Anclaje[] anclajes;

    Anclajes(int numAnclajes) {
        this.anclajes = new Anclaje[numAnclajes];
        crearAnclajes();
    }

    private void crearAnclajes() {
		for (int i = 0; i < anclajes.length; i++) {
			this.anclajes[i] = new Anclaje();
		}
    }
    
    Anclaje[] anclajes() {
		return this.anclajes;
    }
    
    int numAnclajes() {
        return this.anclajes.length;
    }

    void ocuparAnclaje(int posicion, Bicicleta bici) {
		this.anclajes[posicion].anclarBici(bici);
    }

    boolean isAnclajeOcupado(int posicion) {
		return this.anclajes[posicion].isOcupado();
	}

    void liberarAnclaje(int posicion) {
		this.anclajes[posicion].liberarBici();
    }
    
    Bicicleta getBiciAt(int posicion) {
        return this.anclajes[posicion].getBici();
    }

    int seleccionarAnclaje() {
		Integer idAnclaje = ThreadLocalRandom.current().nextInt(0, numAnclajes());
		return idAnclaje;
    }

    @Override
    public String toString() {
        return "Numero de anclajes: " + Integer.toString(numAnclajes());
    }
}