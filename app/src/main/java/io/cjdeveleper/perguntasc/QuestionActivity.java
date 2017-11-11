/*
 * QuestionActivity.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.joda.time.Duration;

import java.util.HashMap;
import java.util.Map;

import io.cjdeveleper.perguntasc.adapters.AlternativeAdapter;
import io.cjdeveleper.perguntasc.backend.Database;
import io.cjdeveleper.perguntasc.backend.FirebaseImpl;
import io.cjdeveleper.perguntasc.backend.FirebaseNode;
import io.cjdeveleper.perguntasc.backend.OnlyAddAdapter;
import io.cjdeveleper.perguntasc.entities.Alternative;
import io.cjdeveleper.perguntasc.entities.Info;
import io.cjdeveleper.perguntasc.entities.Question;
import io.cjdeveleper.perguntasc.game.Game;
import io.cjdeveleper.perguntasc.game.Turn;
import io.cjdeveleper.perguntasc.viewholders.AlternativeItem;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
@EActivity(R.layout.fragment_question)
public class QuestionActivity extends BaseActivity implements Game.Screen {

    private static final int DURATION = 30;

    @StringRes(R.string.message_you_missed)
    String messageMissed;
    @StringRes(R.string.message_time_out)
    String messageTimeOut;

    @ViewById
    ProgressBar progressBar;
    @ViewById
    TextView textQuestion;
    @ViewById
    RecyclerView recyclerAlternatives;
    @ViewById
    ProgressBar durationBar;

    @Bean
    AlternativeAdapter alternativeAdapter;

    @Extra
    String questionId;

    private Database<Question> dataQuestion;
    private Database<Alternative> dataAlternative;
    private Database<Info> infoDatabase;
    private FirebaseUser user;

    private Question question;
    private Info info;

//    private FirebaseRemoteConfig firebaseRemoteConfig;

    @InstanceState
    Game game;
    @InstanceState
    Turn turn;

    @AfterInject
    void initBeans() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dataQuestion = new FirebaseNode<>(database.getReference("questions")
                .child(questionId), Question.class);
        dataAlternative = new FirebaseImpl<>(database.getReference("alternatives")
                .child(questionId), Alternative.class);

        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference infoDataReference = database.getReference("information").child(user.getUid())
                .child(questionId);
        Log.d(FirebaseImpl.TAG, "infoDataReference: " + infoDataReference.getKey());
        infoDatabase = new FirebaseNode<>(infoDataReference, Info.class);
    }

    @AfterViews
    void iniViews() {
        this.toolbar.setTitle(R.string.question);
        configReturnToolbar();

        alternativeAdapter.setAlternativeClickListener(new AlternativeItem.AlternativeClickListener() {
            @Override
            public void onClick(Alternative alternative) {
                game.finish();
                makeFeedback(alternative.isCorrect(), alternative.isCorrect() ?
                        question.getFeedback() : messageMissed);
            }
        });

        recyclerAlternatives.setLayoutManager(new LinearLayoutManager(this));
        recyclerAlternatives.setAdapter(alternativeAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        //loadConfigs();
        dataQuestion.registerObserver(new OnlyAddAdapter<Question>() {
            @Override
            public void onChildAdded(Question value) {
                if (value != null) {
                    setQuestion(value);
                } else {
                    makeMessage();
                }
            }
        });
        dataQuestion.onStart();

        dataAlternative.registerObserver(alternativeAdapter);
        dataAlternative.onStart();

        infoDatabase.registerObserver(new OnlyAddAdapter<Info>() {
            @Override
            public void onChildAdded(Info value) {
                Log.d(FirebaseImpl.TAG, "onChildAdded: " + value);
                QuestionActivity.this.info = value;
            }
        });
        infoDatabase.onStart();

        configTime();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataQuestion.onStop();
        dataAlternative.onStop();
        infoDatabase.onStop();
        if (game != null)
            game.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setQuestion(Question question) {
        this.question = question;
        this.textQuestion.setText(question.getEnunciation());
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se sair agora irá contar como resposta errada, deseja mesmo sair?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                game.finish();
                saveInfo(false);
                finish();
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.show();
    }

    private void makeMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Questão ainda não disponível para acesso!");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (game != null)
                    game.finish();
                finish();
            }
        });
        builder.show();
    }

    @UiThread
    void makeFeedback(final boolean isCorrect, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveInfo(isCorrect);
                finish();
            }
        });
        builder.show();
    }

    private void saveInfo(boolean isCorrect) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (info == null) {
            Info i = new Info(isCorrect ? 1 : 0, isCorrect ? 0 : 1);
            saveInDatabase(database, i);
        } else {
            if (isCorrect)
                info.addHit();
            else
                info.addErrors();
            saveInDatabase(database, info);
        }
    }

    private void saveInDatabase(FirebaseDatabase database, Info i) {
        Map<String, Object> map = i.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(String.format("/information/%s/%s", user.getUid(), questionId), map);
        database.getReference().updateChildren(childUpdates);
    }

    private void configTime() {
        final int duration = DURATION;
        durationBar.setMax(duration * 1000);
        durationBar.setProgress(durationBar.getMax());
        initGame();
    }

    private void initGame() {
        game = new Game(60, this);
        Log.d(FirebaseImpl.TAG, "initGame");
        new Thread(game).start();
    }

    @Override
    public void load() {
        turn = new Turn(Duration.standardSeconds(DURATION));
    }

    @Override
    public void update() {
        int duration = (int) turn.update();
        durationBar.setProgress(duration);
        if (duration <= 0)
            game.finish();
    }

    @Override
    public void terminate() {
        Log.d(FirebaseImpl.TAG, "Game Terminate!");
        if (durationBar.getProgress() <= 0)
            makeFeedback(false, messageTimeOut);
    }
}
