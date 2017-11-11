/*
 * Alternative.java This project is distributed under a Apache 2
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
public class Alternative {

    private String body;
    private boolean correct;

    public Alternative() {
    }

    public Alternative(String body, boolean correct) {
        this.body = body;
        this.correct = correct;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("body", body)
                .append("correct", correct)
                .toString();
    }
}
