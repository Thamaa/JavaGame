package puppy.code;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MovimientoRobot implements MovimientoStrategy {
    @Override
    public void mover(Robot robot) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) robot.getBucket().x -= robot.getVelx() * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) robot.getBucket().x += robot.getVelx() * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) robot.getBucket().x -= robot.getVelx() * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) robot.getBucket().x += robot.getVelx() * Gdx.graphics.getDeltaTime();
        if (robot.getBucket().x < 0) robot.getBucket().x = 0;
        if (robot.getBucket().x > 1280 - 64) robot.getBucket().x = 1280 - 64;
    }
}
