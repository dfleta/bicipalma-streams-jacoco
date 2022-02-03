package domain.bicicleta;

public class Bicicleta implements Movil {

	private final Integer id;

	// Martin Fowler's Replace Primitive with Object refactor	
	public Bicicleta(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id.intValue();
	}

	@Override
	public String toString() {
		return this.id.toString();
	}
}
