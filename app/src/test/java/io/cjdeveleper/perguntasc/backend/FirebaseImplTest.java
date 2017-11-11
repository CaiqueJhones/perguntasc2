package io.cjdeveleper.perguntasc.backend;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.cjdeveleper.perguntasc.BuildConfig;
import io.cjdeveleper.perguntasc.StartActivity_;
import io.cjdeveleper.perguntasc.entities.Question;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE, constants = BuildConfig.class, sdk = 21)
public class FirebaseImplTest {

    FirebaseImpl<Question> firebase;
    Testing testing;

    @Before
    public void setUp() throws Exception {
        StartActivity_ activity = Robolectric.setupActivity(StartActivity_.class);
        FirebaseApp.initializeApp(activity);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference questionsRef = database.getReference("questions");
        
        firebase = new FirebaseImpl<>(questionsRef, Question.class);
        firebase.registerObserver(testing = new Testing());
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Terminate");
        System.out.println(testing.question);
    }

    @Test
    public void onTest() throws Exception {
        firebase.onStart();
        Thread.sleep(10_000);
    }

    private class Testing implements DataObserver<Question> {

        Question question;

        @Override
        public void onChildAdded(Question value) {
            question = value;
            System.out.println(value);
        }

        @Override
        public void onChildChanged(Question value) {
            question = value;
            System.out.println(value);
        }

        @Override
        public void onChildRemoved(Question value) {
            question = value;
            System.out.println(value);
        }

        @Override
        public void onChildMoved(Question value) {
            question = value;
            System.out.println(value);
        }

        @Override
        public void onCancelled(String error, Exception e) {
            System.out.println(error);
        }
    }
}