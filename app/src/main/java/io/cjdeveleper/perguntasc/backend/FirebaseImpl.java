/*
 * AbstractDatabase.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.backend;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
public class FirebaseImpl<E> implements Database<E>, ChildEventListener {
    
    public static final String TAG = FirebaseImpl.class.getSimpleName();
    
    protected DatabaseReference databaseReference;
    protected DataObserver<E> dataObserver;

    private Class<E> clazz;

    public FirebaseImpl(String reference, Class<E> clazz) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(reference);
        this.clazz = clazz;
    }

    public FirebaseImpl(DatabaseReference databaseReference, Class<E> clazz) {
        this.databaseReference = databaseReference;
        this.clazz = clazz;
    }

    @Override
    public void onStart() {
        databaseReference.addChildEventListener(this);
        Log.i(TAG, "onStart");
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

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataObserver != null) {
            E value = dataSnapshot.getValue(clazz);
            dataObserver.onChildAdded(value);
        }
        Log.i(TAG, "onChildAdded: " + dataSnapshot.getKey());
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        if (dataObserver != null) {
            E value = dataSnapshot.getValue(clazz);
            dataObserver.onChildChanged(value);
        }
        Log.i(TAG, "onChildChanged");
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        if (dataObserver != null) {
            E value = dataSnapshot.getValue(clazz);
            dataObserver.onChildRemoved(value);
        }
        Log.i(TAG, "onChildRemoved");
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        if (dataObserver != null) {
            E value = dataSnapshot.getValue(clazz);
            dataObserver.onChildMoved(value);
        }
        Log.i(TAG, "onChildMoved");
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        if (dataObserver != null)
            dataObserver.onCancelled(databaseError.getMessage() + " : " + databaseError.getDetails(),
                    databaseError.toException());
        Log.i(TAG, "onCancelled");
    }
}
