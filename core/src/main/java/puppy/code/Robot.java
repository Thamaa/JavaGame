package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class Robot implements Interface{
    private boolean invencible;
    private long tiempoInvencible;
    private int vidas;
    private boolean herido;
    private long tiempoHerido;
    private long tiempoHeridoMax;
    private Sound sonidoHerido;
    private Texture bucketImage;
    private Rectangle bucket;
    private float velx;
    private int puntos;
    private Animation animacion;
    private float tiempo;
    private TextureRegion[] regionMovimiento;
    private TextureRegion frameActual;

    // Gestión de poderes
    private ArrayList<Poder> poderesActivos;
    private long tiempoInicioPoder;

    public Robot(Sound sonidoHerido) {
        this.sonidoHerido = sonidoHerido;
        this.vidas = 3;
        this.herido = false;
        this.tiempoHeridoMax = 50;
        this.puntos = 0;
        this.velx = 400;
        this.bucket = new Rectangle();
        this.bucket.x = 1280 / 2 - 64 / 2;
        this.bucket.y = 20;
        this.bucket.width = 32;
        this.bucket.height = 64;
        this.invencible = false;
        this.tiempoInvencible = 0;

        this.bucketImage = new Texture(Gdx.files.internal("robotAnimation.png"));
        TextureRegion[][] tmp = TextureRegion.split(bucketImage, bucketImage.getWidth() / 2, bucketImage.getHeight());
        regionMovimiento = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            regionMovimiento[i] = tmp[0][i];
        }
        animacion = new Animation(1, regionMovimiento);
        tiempo = 0f;

        // Inicializar poderes
        poderesActivos = new ArrayList<>();
    }

    public void crear() {
        bucket = new Rectangle();
        bucket.x = 1280 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 32;
        bucket.height = 64;
    }

    public void aumentarVelocidad(float incremento) {
        velx += incremento;
    }

    public void dañar() {
        if (!isInvencible()) {
            vidas--;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();
        }
    }

    public void dibujar(SpriteBatch batch) {
        tiempo += Gdx.graphics.getDeltaTime();
        frameActual = (TextureRegion) animacion.getKeyFrame(tiempo, true);

        if (!herido) {
            batch.draw(frameActual, bucket.x, bucket.y, bucket.width, bucket.height);
        } else {
            batch.draw(frameActual, bucket.x, bucket.y + MathUtils.random(-5, 5), bucket.width, bucket.height);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }

    public void actualizarMovimiento() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) bucket.x -= velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) bucket.x += velx * Gdx.graphics.getDeltaTime();
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 1280 - 64) bucket.x = 1280 - 64;
    }

    public void destruir() {
        bucketImage.dispose();
    }

    public boolean estaHerido() {
        return herido;
    }

    public Rectangle getArea() {
        return bucket;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getVidas() {
        return vidas;
    }

    public void sumarPuntos(int pp) {
        puntos += pp;
    }

    public void aumentarVida(int i) {
        vidas += i;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void activarInvencibilidad(long duracion) {
        invencible = true;
        tiempoInvencible = TimeUtils.nanoTime() + duracion * 1000000;
    }

    public boolean isInvencible() {
        if (invencible && TimeUtils.nanoTime() > tiempoInvencible) {
            invencible = false;
        }
        return invencible;
    }

    // Métodos para gestionar poderes
    public void agregarPoder(Poder poder) {
        poderesActivos.add(poder);
        poder.aplicar(this);
        tiempoInicioPoder = TimeUtils.nanoTime();  // Iniciar el tiempo del poder
    }

    public void actualizarPoderes() {
        poderesActivos.removeIf(poder -> poder.expirado(tiempoInicioPoder));
    }

    public void actualizar(float delta) {
        actualizarMovimiento();
        actualizarPoderes();
    }
}
