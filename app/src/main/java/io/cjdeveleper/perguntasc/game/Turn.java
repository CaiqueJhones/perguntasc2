/*
 * Turn.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.game;

import org.joda.time.Duration;
import org.joda.time.LocalTime;

import java.io.Serializable;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
public class Turn implements Serializable {

    private LocalTime finishTime;

    public Turn(Duration maxDuration) {
        LocalTime startTime = LocalTime.now();
        finishTime = startTime.plus(maxDuration.toPeriod());
    }

    private Duration getCurrentDuration() {
        return new Duration(LocalTime.now().getMillisOfDay(), finishTime.getMillisOfDay());
    }

    public long update() {
        return getCurrentDuration().getMillis();
    }
}
