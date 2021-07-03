package com.max.sandbox;

import com.max.harrax.Application;

public class Main {

    public static void main(String[] args) {

        Application app = new Application("Sandbox", 1280, 720);
        app.pushLayer(new SandboxLayer());
        app.run();

    }

}
