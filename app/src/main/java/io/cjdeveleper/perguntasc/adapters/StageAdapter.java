/*
 * StageAdapter.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.adapters;

import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;

import io.cjdeveleper.perguntasc.backend.DataObserver;
import io.cjdeveleper.perguntasc.entities.Stage;
import io.cjdeveleper.perguntasc.viewholders.StageItem;
import io.cjdeveleper.perguntasc.viewholders.StageItem_;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
@EBean
public class StageAdapter extends BasicRecyclerAdapter<Stage, StageItem>
        implements DataObserver<Stage>{

    @Override
    protected StageItem onCreateItemView(ViewGroup parent, int viewType) {
        return StageItem_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<StageItem> holder, int position) {
        holder.getView().bind(data.get(position));
    }

    @Override
    public void onChildAdded(Stage value) {
        this.add(value);
    }

    @Override
    public void onChildChanged(Stage value) {
        int i = this.data.indexOf(value);
        if (i > -1)
            notifyItemChanged(i);
    }

    @Override
    public void onChildRemoved(Stage value) {
        int i = this.data.indexOf(value);
        if (i > -1)
            this.remove(i);
    }

    @Override
    public void onChildMoved(Stage value) {

    }

    @Override
    public void onCancelled(String error, Exception e) {

    }
}
