/*
 * DataObserver.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.backend;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
public interface DataObserver<E> {

    void onChildAdded(E value);

    void onChildChanged(E value);

    void onChildRemoved(E value);

    void onChildMoved(E value);

    void onCancelled(String error, Exception e);
}
