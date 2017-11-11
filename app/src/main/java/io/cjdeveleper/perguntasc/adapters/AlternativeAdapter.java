/*
 * AlternativeAdapter.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.adapters;

import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;

import io.cjdeveleper.perguntasc.backend.DataObserver;
import io.cjdeveleper.perguntasc.entities.Alternative;
import io.cjdeveleper.perguntasc.viewholders.AlternativeItem;
import io.cjdeveleper.perguntasc.viewholders.AlternativeItem_;


/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
@EBean
public class AlternativeAdapter extends BasicRecyclerAdapter<Alternative, AlternativeItem>
        implements DataObserver<Alternative> {

    private AlternativeItem.AlternativeClickListener alternativeClickListener;

    public void setAlternativeClickListener(AlternativeItem.AlternativeClickListener alternativeClickListener) {
        this.alternativeClickListener = alternativeClickListener;
    }

    @Override
    protected AlternativeItem onCreateItemView(ViewGroup parent, int viewType) {
        return AlternativeItem_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<AlternativeItem> holder, int position) {
        AlternativeItem item = holder.getView();
        item.bind(data.get(position), alternativeClickListener);
    }

    @Override
    public void onChildAdded(Alternative value) {
        this.add(value);
    }

    @Override
    public void onChildChanged(Alternative value) {
        int i = this.data.indexOf(value);
        if (i > -1)
            notifyItemChanged(i);
    }

    @Override
    public void onChildRemoved(Alternative value) {
        int i = this.data.indexOf(value);
        if (i > -1)
            remove(i);
    }

    @Override
    public void onChildMoved(Alternative value) {

    }

    @Override
    public void onCancelled(String error, Exception e) {

    }
}
