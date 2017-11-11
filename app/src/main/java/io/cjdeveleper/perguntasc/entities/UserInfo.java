/*
 * User.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.entities;

import com.google.firebase.database.IgnoreExtraProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Representa dados pessoais do usuário.
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
@IgnoreExtraProperties
public class UserInfo {

    // Id usuário.
    private String userId;
    // Número de acessos.
    private int accesses;

    public UserInfo() {
    }

    public int getAccesses() {
        return accesses;
    }

    public void setAccesses(int accesses) {
        this.accesses = accesses;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("userId", userId)
                .append("accesses", accesses)
                .toString();
    }
}
