/*
 * Info.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * Informações do usuário referente a questão.
 * @author Caique Jhones
 * @version 1
 * @since 0.1.0
 */
@IgnoreExtraProperties
public class Info {

    private int hits;
    private int errors;

    public Info() {
    }

    public Info(int hits, int errors) {
        this.hits = hits;
        this.errors = errors;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public void addHit() {
        setHits(hits + 1);
    }

    public void addErrors() {
        setErrors(errors + 1);
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("hits", hits);
        result.put("errors", errors);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("hits", hits)
                .append("errors", errors)
                .toString();
    }
}
