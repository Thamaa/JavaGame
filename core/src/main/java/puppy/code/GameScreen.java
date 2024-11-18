package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Robot robot;
    private Lluvia lluvia;
    private Texture backgroundTexture;
    private float backgroundX;
    private Music music;

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        backgroundTexture = new Texture(Gdx.files.internal("fondo2.jpg"));
        backgroundX = 0;
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("playerhit.mp3"));
        robot = new Robot(hurtSound);
        Texture gota = new Texture(Gdx.files.internal("battery.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("evilrobot.png"));
        Texture gotaEspecial = new Texture(Gdx.files.internal("dropSpecial.png"));
        Texture gotaCurativa = new Texture(Gdx.files.internal("vidaextra.png"));
        Texture gotaFatal = new Texture(Gdx.files.internal("jefeFinal.png"));
        Texture gotaInvencible = new Texture(Gdx.files.internal("invencible.png")); // Nueva textura para la gota invencible
        music = Gdx.audio.newMusic(Gdx.files.internal("Knockout.mp3"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        Poderes poderes = new Poderes(gotaEspecial, gotaInvencible);// Pasar la nueva textura al constructor de Poderes
        Piezas gotaBuena = new Piezas(gota);
        lluvia = new Lluvia(gotaBuena, gotaMala, poderes, gotaCurativa, gotaFatal, dropSound, music);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720); // Configurar la cámara a 1280x720
        batch = new SpriteBatch();
        robot.crear();
        lluvia.crear();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // Dibujar el fondo repetidamente para cubrir toda la pantalla
        batch.draw(backgroundTexture, backgroundX, 0, 1280, 720);
        batch.draw(backgroundTexture, backgroundX + 1280, 0, 1280, 720);
        backgroundX -= 100 * delta;
        if (backgroundX <= -1280) {
            backgroundX = 0;
        }
        font.draw(batch, "Partes totales: " + robot.getPuntos(), 5, 715);
        font.draw(batch, "Vidas : " + robot.getVidas(), 1150, 715);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 715);
        if (!robot.estaHerido()) {
            robot.actualizarMovimiento();
            if (!lluvia.actualizarMovimiento(robot)) {
                if (game.getHigherScore() < robot.getPuntos()) {
                    game.setHigherScore(robot.getPuntos());
                }
                lluvia.pausar();
                music.stop(); // Detener la música del juego
                Music gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("gameover.mp3"));
                gameOverMusic.play();
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new GameOverScreen(game));
                    }
                });
            }
        }
        robot.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Si es necesario manejar el cambio de tamaño de la pantalla
    }

    @Override
    public void show() {
        lluvia.continuar();
    }

    @Override
    public void hide() {
        // Si es necesario manejar cuando la pantalla se oculta
    }

    @Override
    public void pause() {
        lluvia.pausar();
        game.setScreen(new PausaScreen(game, this));
    }

    @Override
    public void resume() {
        lluvia.continuar();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        robot.destruir();
        lluvia.destruir();
    }
}
