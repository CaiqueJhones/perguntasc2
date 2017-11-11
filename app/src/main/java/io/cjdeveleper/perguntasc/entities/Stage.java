/*
 * Stage.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.entities;

import com.google.firebase.database.IgnoreExtraProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author Caique Jhones
 * @version 1
 * @since 0.1.0
 */
@IgnoreExtraProperties
public class Stage implements Serializable {

    private String name;
    private String theme;
    private String presentation;
    private String questionOne;
    private String questionTwo;
    private String analysis;
    private String resume;
    private boolean enabled;

    public Stage() {
    }

    public Stage(String name, String theme, String presentation, String questionOne,
                 String questionTwo, String analysis, String resume, boolean enabled) {
        this.name = name;
        this.theme = theme;
        this.presentation = presentation;
        this.questionOne = questionOne;
        this.questionTwo = questionTwo;
        this.analysis = analysis;
        this.resume = resume;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getQuestionOne() {
        return questionOne;
    }

    public void setQuestionOne(String questionOne) {
        this.questionOne = questionOne;
    }

    public String getQuestionTwo() {
        return questionTwo;
    }

    public void setQuestionTwo(String questionTwo) {
        this.questionTwo = questionTwo;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("theme", theme)
                .append("presentation", presentation)
                .append("questionOne", questionOne)
                .append("questionTwo", questionTwo)
                .append("analysis", analysis)
                .append("resume", resume)
                .append("enabled", enabled)
                .toString();
    }
}
