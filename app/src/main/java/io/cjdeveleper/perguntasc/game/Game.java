/*
 * Game.java This project is distributed under a Apache 2
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
public class Game implements Runnable, Serializable {

    /**
     * Ativa ou desetiva o loop principal.
     */
    private volatile boolean active;
    private volatile boolean pause;
    private FpsCount fpsCount;
    /**
     * Quantidade de ticks por segundo.
     */
    private int expectedFPS;
    private Screen screen;

    public Game(int expectedFPS, Screen screen) {
        this.expectedFPS = expectedFPS;
        this.screen = screen;
        fpsCount = new FpsCount();
    }

    @Override
    public void run() {
        active = true;
        fpsCount.start();
        screen.load();
        double expectedNanoFPS = FpsCount.NANOS_IN_SECOND / expectedFPS;
        long next = System.nanoTime();
        while (active) {
            if (!pause) {
                fpsCount.countFPS();
                if (System.nanoTime() > next) {
                    next += expectedNanoFPS;
                    screen.update();
                    Thread.yield();
                }
            }
        }
        screen.terminate();
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    /**
     * Deve ser invocado para finalizar o ciclo de vida da aplicaçao e assim
     * encerrar todas as atividades de maneira adequada.
     */
    public void finish() {
        active = false;
    }

    public interface Screen {

        void load();

        void update();

        void terminate();
    }
}
