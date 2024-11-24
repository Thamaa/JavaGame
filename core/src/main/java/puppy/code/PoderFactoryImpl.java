
package puppy.code;

public class PoderFactoryImpl implements PoderFactory {

    @Override
    public Poder crearInvencibilidad(float duracion) {
        return new Invencibilidad(duracion);
    }

    @Override
    public Poder crearVelocidadExtra(float duracion) {
        return new VelocidadExtra(duracion);
    }

    @Override
    public Poder crearVidaExtra(float duracion, int cantidadVidas) {
        return new VidaExtra(duracion, cantidadVidas);
    }
}
