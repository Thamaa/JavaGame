package puppy.code;

import com.badlogic.gdx.utils.TimeUtils;

public class Invencibilidad extends Poder {

    public Invencibilidad(float duracion) {
        super("Invencibilidad", duracion);
    }

    @Override
    public void aplicar(Robot robot) {
        long duracionMilisegundos = (long) (duracion * 1000);
        robot.activarInvencibilidad(duracionMilisegundos);
    }

    @Override
    public boolean expirado(long tiempoInicio) {
        long tiempoActual = TimeUtils.nanoTime();
        return tiempoActual > (tiempoInicio + duracion * 1000000000);
    }
}
