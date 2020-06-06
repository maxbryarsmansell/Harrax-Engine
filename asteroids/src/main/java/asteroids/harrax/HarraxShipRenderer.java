/* 
 * NOTICE OF LICENSE
 * 
 * This source file is subject to the Open Software License (OSL 3.0) that is 
 * bundled with this package in the file LICENSE.txt. It is also available 
 * through the world-wide-web at http://opensource.org/licenses/osl-3.0.php
 * If you did not receive a copy of the license and are unable to obtain it 
 * through the world-wide-web, please send an email to magnos.software@gmail.com 
 * so we can send you a copy immediately. If you use any of this software please
 * notify me via our website or email, your feedback is much appreciated. 
 * 
 * @copyright   Copyright (c) 2011 Magnos Software (http://www.magnos.org)
 * @license     http://opensource.org/licenses/osl-3.0.php
 * 				Open Software License (OSL 3.0)
 */

package asteroids.harrax;

import asteroids.Vector;
import com.max.harrax.graphics.Colour;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.maths.Mat4;
import com.max.harrax.maths.Vec2;
import com.max.harrax.maths.Vec3;
import com.max.harrax.maths.Vec4;
import org.magnos.entity.Entity;
import org.magnos.entity.RendererSingle;

import java.awt.*;
import java.util.ArrayList;

import static asteroids.Components.*;

public class HarraxShipRenderer extends RendererSingle
{
    @Override
    public void begin( Entity e, Object drawState )
    {
        Renderer renderer = (Renderer) drawState;

        Vector pos = e.get( POSITION );
        float ang = e.get( ANGLE ).v;
        float rad = e.get( RADIUS ).v;
        Color clr = e.get( COLOR );

        Colour colour = new Colour(clr.getRed() / 255f, clr.getGreen() / 255f, clr.getBlue() / 255f, clr.getAlpha() / 255f);

        Mat4 translation = Mat4.translation(new Vec3(pos.x, pos.y, 0));
        Mat4 rotation = Mat4.zAxisRotation(ang);
        Mat4 scale = Mat4.scale(rad);

        Mat4 transform = new Mat4(1.0f).mult(translation).mult(rotation).mult(scale);

        ArrayList<Vec4> vertices = new ArrayList<>();
        vertices.add(new Vec4(1,     0,     0,1));
        vertices.add(new Vec4(-0.8f, 0.6f,  0,1));
        vertices.add(new Vec4(1,     0,     0,1));
        vertices.add(new Vec4(-0.8f, -0.6f, 0,1));
        vertices.add(new Vec4(-0.6f, -0.4f, 0,1));
        vertices.add(new Vec4(-0.6f, 0.4f , 0,1));

        for (Vec4 v : vertices)
            v.mult(transform);

        renderer.submitPolygon(vertices, colour);
    }

    @Override
    public void destroy( Entity e )
    {
    }
}