package domain.estacion;

import domain.bicicleta.Bicicleta;

class Anclaje {

    private boolean ocupado = false;
    // Anclaje tiene una / has-a Bicicleta
    private Bicicleta bici = null;

    Anclaje() {};

    boolean isOcupado() {
        return this.ocupado;
    }

    Bicicleta getBici() {
        return this.bici;
    }

    void anclarBici(Bicicleta bici) {
        this.bici = bici;
        this.ocupado = true;
    }

    void liberarBici() {
        this.bici = null;
        this.ocupado = false;
    }

    @Override
    public String toString() {
        return "ocupado: " + Boolean.toString(isOcupado());
    }
}