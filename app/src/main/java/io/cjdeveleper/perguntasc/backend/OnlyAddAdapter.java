/*
 * OnlyAddAdapter.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.backend;

/**
 * Observador para somente adicionar.
 * @author Caique Jhones
 * @version 1
 * @since 0.1.0
 */
public abstract class OnlyAddAdapter<E> implements DataObserver<E>{

    @Override
    public void onChildChanged(E value) {

    }

    @Override
    public void onChildRemoved(E value) {

    }

    @Override
    public void onChildMoved(E value) {

    }

    @Override
    public void onCancelled(String error, Exception e) {

    }
}
