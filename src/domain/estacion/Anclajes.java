package domain.estacion;

import java.util.concurrent.ThreadLocalRandom;

import domain.bicicleta.Movil;

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

    @Override
    public String toString() {
        return "Numero de anclajes: " + Integer.toString(numAnclajes());
    }

    /**
     * Al introducir streams no es posible acceder
     * por posicion al ADT Anclaje.
     * Los metodos de acceso por posicion
     * ya no son necesarios. 
     *
     */
    void ocuparAnclaje(int posicion, Movil bici) {
		    this.anclajes[posicion].anclarBici(bici);
    }

    boolean isAnclajeOcupado(int posicion) {
		    return this.anclajes[posicion].isOcupado();
	  }

    void liberarAnclaje(int posicion) {
		    this.anclajes[posicion].liberarBici();
    }
    
    Movil getBiciAt(int posicion) {
        return this.anclajes[posicion].getBici();
    }

    /**
     * Substituido por Arrays.stream().findAny()
     */
    int seleccionarAnclaje() {
        Integer idAnclaje = ThreadLocalRandom.current().nextInt(0, numAnclajes());
        return idAnclaje;
    }
}