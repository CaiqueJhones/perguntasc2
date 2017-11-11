/*
 * StageItem.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc.viewholders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;

import io.cjdeveleper.perguntasc.R;
import io.cjdeveleper.perguntasc.StageActivity_;
import io.cjdeveleper.perguntasc.backend.InfoFacade;
import io.cjdeveleper.perguntasc.entities.Stage;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
@EViewGroup(R.layout.item_stage)
public class StageItem extends LinearLayout {

    @DrawableRes(R.drawable.ic_disabled)
    Drawable icDisabled;
    @DrawableRes(R.drawable.ic_answered)
    Drawable icCompleted;

    @ViewById
    ImageView imageStageStatus;
    @ViewById
    TextView textStageName;

    private Stage stage;
    private InfoFacade infoFacade;

    public StageItem(Context context) {
        super(context);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        float density = context.getResources().getDisplayMetrics().density;
        int p = (int) (5 * density);
        setPadding(p, p, p, p);
    }

    public void bind(Stage stage) {
        this.stage = stage;
        textStageName.setText(stage.getName());
        if (!stage.isEnabled())
            imageStageStatus.setImageDrawable(icDisabled);

        infoFacade = new InfoFacade(stage);
        infoFacade.setListener(new InfoFacade.Listener() {
            @Override
            public void onChange(int var, boolean isAnswered) {
                if (infoFacade.isCompleted()) {
                    imageStageStatus.setImageDrawable(icCompleted);
                    infoFacade.onStop();
                }
            }
        });
        infoFacade.onStart();
    }

    @Click
    void cardClicked() {
        if (stage.isEnabled())
            StageActivity_.intent(getContext()).stage(stage).start();
    }

    @LongClick
    void cardLongClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(stage.getTheme());
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }
}
