/*
 * PerguntASC.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
public class PerguntASC extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
