package domain.tarjetausuario;

public class TarjetaUsuario {

	private String id = null;
	private Boolean activada = false;

	public TarjetaUsuario(String id, Boolean activada) {
		this.id = id;
		this.activada = activada;
	}

	public Boolean getActivada() {
		return this.activada;
	}

	public void setActivada(Boolean activada) {
		this.activada = activada;
	}
}
