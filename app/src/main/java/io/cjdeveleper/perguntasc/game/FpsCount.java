/*
 * FpsCount.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.game;

import java.io.Serializable;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
public class FpsCount implements Serializable {

    public static final double NANOS_IN_SECOND = 1E9;
    private long previous;
    private long total;
    private int count;
    private int fps;

    public void start() {
        previous = System.nanoTime();
        total = 0;
        count = 0;
        fps = 0;
    }

    private void update() {
        if (System.nanoTime() - previous > NANOS_IN_SECOND) {
            fps = count;
            count = 0;
            previous = System.nanoTime();
        }
    }

    public long countFPS() {
        count++;
        total++;
        update();
        return total;
    }

    public int getFps() {
        return fps;
    }
}
