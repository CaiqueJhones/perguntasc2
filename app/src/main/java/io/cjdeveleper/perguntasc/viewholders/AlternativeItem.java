/*
 * QuestionItem.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.viewholders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import io.cjdeveleper.perguntasc.R;
import io.cjdeveleper.perguntasc.entities.Alternative;


/**
 *
 * @author Caique Jhones
 * @version 1
 * @since 0.1.0
 */
@EViewGroup(R.layout.item_alternative)
public class AlternativeItem extends LinearLayout {

    @ViewById
    Button itemAlternative;

    public AlternativeItem(Context context) {
        super(context);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void bind(final Alternative alternative, final AlternativeClickListener listener) {

        itemAlternative.setText(alternative.getBody());
        itemAlternative.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(alternative);
            }
        });
    }

    public interface AlternativeClickListener {

        void onClick(Alternative alternative);
    }
}
