/*
 * InfoFacade.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.backend;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import io.cjdeveleper.perguntasc.entities.Info;
import io.cjdeveleper.perguntasc.entities.Stage;

/**
 * Classe Utilitaria para verificar se as questoes da Fase já foram respondidas corretamente.
 * @author Caique Jhones
 * @version 1
 * @since 0.1.1
 */
public class InfoFacade {

    private Database<Info> infoDatabase1;
    private Database<Info> infoDatabase2;
    private Listener listener;
    private boolean q1, q2;

    public InfoFacade(Stage stage) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        infoDatabase1 = new FirebaseNode<>(database.getReference("information")
                .child(user.getUid()).child(stage.getQuestionOne()), Info.class);
        infoDatabase2 = new FirebaseNode<>(database.getReference("information")
                .child(user.getUid()).child(stage.getQuestionTwo()), Info.class);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void onStart() {
        infoDatabase1.registerObserver(new OnlyAddAdapter<Info>() {
            @Override
            public void onChildAdded(Info value) {
                q1 = (value != null && value.getHits() > 0);
                if (listener != null)
                    listener.onChange(Listener.Q1, q1);
            }
        });
        infoDatabase1.onStart();

        infoDatabase2.registerObserver(new OnlyAddAdapter<Info>() {
            @Override
            public void onChildAdded(Info value) {
                q2 = (value != null && value.getHits() > 0);
                if (listener != null)
                    listener.onChange(Listener.Q2, q2);
            }
        });
        infoDatabase2.onStart();
    }

    public void onStop() {
        infoDatabase1.onStop();
        infoDatabase2.onStop();
    }

    public boolean q1IsAnswered() {
        return q1;
    }

    public boolean q2IsAnswered() {
        return q2;
    }

    public boolean isCompleted() {
        return q1 && q2;
    }

    public interface Listener {

        int Q1 = 1;
        int Q2 = 2;

        /**
         * Notifica quando recebe dados do banco de dados.
         * @param var Q1 ou Q2
         * @param isAnswered {@code true} se já respondido.
         */
        void onChange(int var, boolean isAnswered);
    }
}
