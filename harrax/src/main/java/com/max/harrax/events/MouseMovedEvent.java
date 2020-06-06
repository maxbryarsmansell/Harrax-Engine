package com.max.harrax.events;

import com.max.harrax.maths.Vec2;

public class MouseMovedEvent extends Event {

    private float xpos, ypos;

    public MouseMovedEvent(float xpos, float ypos) {
        super(EventType.MouseMoved);
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public float getMouseX() {
        return xpos;
    }

    public float getMouseY() {
        return ypos;
    }

    public Vec2 getPos() {
        return new Vec2(xpos, (float) ypos);
    }

}
