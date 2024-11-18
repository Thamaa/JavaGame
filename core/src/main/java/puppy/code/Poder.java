package puppy.code;

import com.badlogic.gdx.utils.TimeUtils;

public abstract class Poder {
    protected String nombre;
    protected float duracion;

    public Poder(String nombre, float duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
    }

    public abstract void aplicar(Robot robot);

    public boolean expirado(long tiempoInicio) {
        return TimeUtils.nanoTime() > tiempoInicio + duracion * 1000000000;
    }

    public String getNombre() {
        return nombre;
    }
}
