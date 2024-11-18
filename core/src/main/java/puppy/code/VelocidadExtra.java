package puppy.code;

public class VelocidadExtra extends Poder {
    private float incrementoVelocidad;

    public VelocidadExtra(float duracion, float incrementoVelocidad) {
        super("Velocidad Extra", duracion);
        this.incrementoVelocidad = incrementoVelocidad;
    }

    @Override
    public void aplicar(Robot robot) {
        robot.aumentarVelocidad(incrementoVelocidad);
    }
}
