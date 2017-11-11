/*
 * BasicRecyclerAdapter.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
@EBean
public abstract class BasicRecyclerAdapter<T, V extends View> extends RecyclerView
        .Adapter<ViewWrapper<V>> {

    @RootContext
    protected Context context;
    protected List<T> data = new ArrayList<>();

    @Override
    public ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public int getItemCount() {
        return data.size();
    }

    public void addAll(Collection<T> collection) {
        int p1 = data.size();
        data.addAll(collection);
        notifyItemRangeInserted(p1, collection.size());
    }

    public void add(T entity) {
        int p = data.size();
        data.add(entity);
        notifyItemInserted(p);
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }

    public void clear() {
        int size = this.data.size();
        if (size > 0) {
            data.clear();
            this.notifyItemRangeRemoved(0, size);
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
