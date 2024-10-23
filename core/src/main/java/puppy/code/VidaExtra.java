package puppy.code;

public class VidaExtra extends Poder {
    private int cantidadVidas;

    public VidaExtra(float duracion, int cantidadVidas) {
        super("Vida Extra", duracion);
        this.cantidadVidas = cantidadVidas;
    }

    @Override
    public void aplicar(Robot robot) {
        robot.aumentarVida(cantidadVidas);
    }
}
