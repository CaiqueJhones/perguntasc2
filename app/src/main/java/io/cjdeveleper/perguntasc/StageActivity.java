/*
 * StageActivity.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

import io.cjdeveleper.perguntasc.backend.InfoFacade;
import io.cjdeveleper.perguntasc.entities.Stage;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
@EActivity(R.layout.activity_stage)
public class StageActivity extends BaseActivity {

    @ColorRes(R.color.material_teal_500)
    int backgroundColor;

    //@ViewById
    //TextView textPresentation;
    @ViewById(R.id.webPresentation)
    WebView webPresentation;
    @ViewById
    Button buttonQuestion1;
    @ViewById
    Button buttonQuestion2;
    @ViewById
    Button buttonResume;

    @InstanceState
    @Extra
    Stage stage;

    private InfoFacade infoFacade;

    @AfterInject
    void initBeans() {
        if (stage == null)
            finish();

        infoFacade = new InfoFacade(stage);
    }

    @AfterViews
    void initViews() {
        toolbar.setTitle(stage.getName());
        configReturnToolbar();
        EmbeddedWebView.init(webPresentation, stage.getPresentation(), this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        infoFacade.setListener(new InfoFacade.Listener() {
            @Override
            public void onChange(int var, boolean isAnswered) {
                if (var == Q1 && isAnswered)
                    buttonQuestion1.setBackgroundColor(backgroundColor);
                else if (var == Q2 && isAnswered)
                    buttonQuestion2.setBackgroundColor(backgroundColor);
                enableButtonResume();
            }
        });
        infoFacade.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        infoFacade.onStop();
    }

    @Click
    void buttonQuestion1Clicked() {
        if (stage.getQuestionOne() == null || stage.getQuestionOne().isEmpty()) {
            makeMessage();
            return;
        }
        QuestionActivity_.intent(this).questionId(stage.getQuestionOne()).start();
    }

    @Click
    void buttonQuestion2Clicked() {
        if (stage.getQuestionTwo() == null || stage.getQuestionTwo().isEmpty()) {
            makeMessage();
            return;
        }
        QuestionActivity_.intent(this).questionId(stage.getQuestionTwo()).start();
    }

    @Click
    void buttonResumeClicked() {
        ResumeActivity_.intent(this).content(stage.getAnalysis().concat("</br>")
                .concat(stage.getResume())).start();
    }

    private void enableButtonResume() {
        if (infoFacade.isCompleted())
            buttonResume.setVisibility(View.VISIBLE);
        else
            buttonResume.setVisibility(View.GONE);
    }

    private void makeMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Questão ainda não disponível para acesso!");
        builder.setPositiveButton(android.R.string.yes, null);
        builder.show();
    }
}
