/*
 * ViewWrapper.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Caique Jhones
 * @version 4
 * @since APP-4
 */
class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private V view;

    ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }

}
