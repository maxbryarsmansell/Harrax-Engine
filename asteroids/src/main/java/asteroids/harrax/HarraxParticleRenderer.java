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
import com.max.harrax.maths.Vec2;
import org.magnos.entity.Entity;
import org.magnos.entity.RendererSingle;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static asteroids.Components.*;


public class HarraxParticleRenderer extends RendererSingle
{
    
    @Override
    public void begin( Entity e, Object drawState )
    {
        com.max.harrax.graphics.Renderer renderer = (com.max.harrax.graphics.Renderer) drawState;

        Vector pos = e.get( POSITION );
        float rad = e.get( RADIUS ).v;
        Color clr = e.get( COLOR );

        Colour colour = new Colour(clr.getRed() / 255f, clr.getGreen() / 255f, clr.getBlue() / 255f, clr.getAlpha() / 255f);

        renderer.submitQuad(pos.x, pos.y, rad, rad, colour);
    }
    
}
