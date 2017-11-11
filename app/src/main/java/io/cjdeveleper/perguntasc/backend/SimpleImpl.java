/*
 * SimpleImpl.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.backend;

import android.util.Log;

import io.cjdeveleper.perguntasc.entities.Alternative;
import io.cjdeveleper.perguntasc.entities.Info;
import io.cjdeveleper.perguntasc.entities.Question;
import io.cjdeveleper.perguntasc.entities.Stage;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
public abstract class SimpleImpl<E> implements Database<E>{

    private static final String TAG = SimpleImpl.class.getSimpleName();
    @SuppressWarnings("WeakerAccess")
    DataObserver<E> dataObserver;

    @Override
    public void registerObserver(DataObserver<E> observer) {
        this.dataObserver = observer;
    }

    @Override
    public void onStop() {
    }

    @Override
    public void unregisterObserver() {
        dataObserver = null;
    }

    public static class DataQuestion extends SimpleImpl<Question> {
        private Question question;

        public DataQuestion() {
            question = new Question("Qual o seu nome?", "Parabéns! Você acertou!");
            Log.i(TAG, "DataQuestion: " + question);
        }

        @Override
        public void onStart() {
            if (dataObserver != null)
                dataObserver.onChildAdded(question);
        }
    }

    public static class DataAlternative extends SimpleImpl<Alternative> {

        private Alternative[] alternatives = new Alternative[4];

        public DataAlternative() {
            for (int i = 0; i < alternatives.length; i++) {
                alternatives[i] = new Alternative("Alguém " + (i + 1), false);
                Log.i(TAG, "DataAlternative: " + alternatives[i]);
            }
            alternatives[1].setCorrect(true);
        }

        @Override
        public void onStart() {
            if (dataObserver != null)
                for (Alternative alternative : alternatives)
                    dataObserver.onChildAdded(alternative);
        }
    }

    public static class DataStage extends SimpleImpl<Stage> {

        private Stage[] stages = new Stage[8];

        public DataStage() {
            for (int i = 0; i < stages.length; i++) {
                stages[i] = new Stage("Fase " + (i + 1), "Tema A", "<b>Apresentacao</b>", "q1",
                        "q1", "Analise", "Resumo", false);
            }
            stages[0].setEnabled(true);
            stages[1].setEnabled(true);
        }

        @Override
        public void onStart() {
            if (dataObserver != null)
                for (Stage stage : stages)
                    dataObserver.onChildAdded(stage);
        }
    }

    public static class DataInfo extends SimpleImpl<Info> {

        private Info info;

        public DataInfo() {
            info = new Info(6, 2);
        }

        @Override
        public void onStart() {
            if (dataObserver != null)
                dataObserver.onChildAdded(info);
        }
    }
}
