/*
 * Database.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.backend;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
public interface Database<E> {

    void onStart();

    void onStop();

    void registerObserver(DataObserver<E> observer);

    void unregisterObserver();
}
