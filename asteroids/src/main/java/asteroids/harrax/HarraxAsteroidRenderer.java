
package asteroids.harrax;

import asteroids.Vector;
import com.max.harrax.graphics.Colour;
import com.max.harrax.maths.*;
import org.magnos.entity.Entity;
import org.magnos.entity.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static asteroids.Components.*;
import static org.lwjgl.opengl.GL11.*;

public class HarraxAsteroidRenderer implements Renderer {

    public float dentPercent;
    public int spokeCount;
    public Random random;
    public java.util.List<Vec2> vertices;

    public HarraxAsteroidRenderer(float dentPercent, int spokeCount, Random random) {
        this.vertices = null;
        this.dentPercent = dentPercent;
        this.spokeCount = spokeCount;
        this.random = random;
    }

    public HarraxAsteroidRenderer(java.util.List<Vec2> vertices, float dentPercent, int spokeCount, Random random) {
        this.vertices = vertices;
        this.dentPercent = dentPercent;
        this.spokeCount = spokeCount;
        this.random = random;
    }

    @Override
    public Renderer create(Entity e) {
        final float angleIncrease = (float) (Math.PI * 2 / spokeCount);
        float ps = nextSpoke();

        vertices = new ArrayList<Vec2>();

        vertices.add(new Vec2(0, 0));

        for (int i = 0; i <= spokeCount; i++) {
            float s = (i == spokeCount ? ps : nextSpoke());
            float ang = i * angleIncrease;
            float dx = (float) Math.cos(ang);
            float dy = (float) Math.sin(ang);

            vertices.add(new Vec2(dx * s, dy * s));
        }

        return new HarraxAsteroidRenderer(vertices, dentPercent, spokeCount, random);
    }

    private float nextSpoke() {
        return 1.0f - (random.nextFloat() * dentPercent);
    }

    @Override
    public void begin(Entity e, Object drawState) {

        com.max.harrax.graphics.Renderer renderer = (com.max.harrax.graphics.Renderer) drawState;

        Vector pos = e.get(POSITION);
        float ang = e.get(ANGLE).v;
        float rad = e.get(RADIUS).v;
        Color clr = e.get(COLOR);

        Colour colour = new Colour(clr.getRed() / 255f, clr.getGreen() / 255f, clr.getBlue() / 255f, clr.getAlpha() / 255f);

        Mat4 translation = Mat4.translation(new Vec3(pos.x, pos.y, 0));
        Mat4 rotation = Mat4.zAxisRotation(ang);
        Mat4 scale = Mat4.scale(rad);

        Mat4 transform = new Mat4(1.0f).mult(translation).mult(rotation).mult(scale);

        ArrayList<Vec4> copy = new ArrayList<>();
        for (Vec2 v : vertices)
            copy.add(new Vec4(v.x, v.y, 0, 1).mult(transform));

        renderer.submitFilledPolygon(copy, colour);
    }

    @Override
    public void end(Entity e, Object drawState) {

    }

    @Override
    public void destroy(Entity e) {
    }

    @Override
    public void notify(Entity e, int message) {

    }

}
