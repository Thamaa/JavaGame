
package puppy.code;

public interface PoderFactory {
    Poder crearInvencibilidad(float duracion);
    Poder crearVelocidadExtra(float duracion);
    Poder crearVidaExtra(float duracion, int cantidadVidas);
}
