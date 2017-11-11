/*
 * Question.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.entities;

import com.google.firebase.database.IgnoreExtraProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Caique Jhones
 * @version 1
 * @since 0.1.0
 */
@IgnoreExtraProperties
public class Question {

    private String enunciation;
    private String feedback;

    public Question() {
    }

    public Question(String enunciation, String feedback) {
        this.enunciation = enunciation;
        this.feedback = feedback;
    }

    public String getEnunciation() {
        return enunciation;
    }

    public void setEnunciation(String enunciation) {
        this.enunciation = enunciation;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("enunciation", enunciation)
                .append("feedback", feedback)
                .toString();
    }
}
