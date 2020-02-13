package domain.estacion;

import domain.bicicleta.Bicicleta;

public class Anclajes {

    private final Bicicleta[] bicicletas; 

    public Anclajes(int numAnclajes) {
        this.bicicletas = new Bicicleta[numAnclajes];
    }

    public Bicicleta[] getBicicletas() {
        return this.bicicletas;
    }

    public void ocuparAnclaje(int posicion, Bicicleta bici ) {
        this.bicicletas[posicion] = bici;
    }

    public void liberarAnclaje(int posicion) {
        this.bicicletas[posicion] = null;
    }

    public boolean isAnclajeLibre(int posicion) {
        return this.bicicletas[posicion] == null? true : false;
    }

    public int numAnclajes() {
        return this.bicicletas.length;
    }

    public Bicicleta getBici(int posicion) {
        return this.bicicletas[posicion];
    }
}