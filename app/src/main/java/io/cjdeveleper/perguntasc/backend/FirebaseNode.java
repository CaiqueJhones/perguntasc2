/*
 * FirebaseNode.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.backend;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Para leitura de dados raíz.
 * @author Caique Jhones
 * @version 1
 * @since 0.1.0
 */
public class FirebaseNode<E> implements Database<E>, ValueEventListener{

    protected DatabaseReference databaseReference;
    protected DataObserver<E> dataObserver;

    private Class<E> clazz;

    public FirebaseNode(DatabaseReference databaseReference, Class<E> clazz) {
        this.databaseReference = databaseReference;
        this.clazz = clazz;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataObserver != null) {
            E value = dataSnapshot.getValue(clazz);
            dataObserver.onChildAdded(value);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        if (dataObserver != null)
            dataObserver.onCancelled(databaseError.getMessage() + " : " + databaseError.getDetails(),
                    databaseError.toException());
    }

    @Override
    public void onStart() {
        databaseReference.addValueEventListener(this);
    }

    @Override
    public void onStop() {
        databaseReference.removeEventListener(this);
    }

    @Override
    public void registerObserver(DataObserver<E> observer) {
        this.dataObserver = observer;
    }

    @Override
    public void unregisterObserver() {
        this.dataObserver = null;
    }
}
