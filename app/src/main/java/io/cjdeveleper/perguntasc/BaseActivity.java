/*
 * BaseActivity.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;

    @AfterViews
    void afterViews() {
        setSupportActionBar(toolbar);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void configReturnToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
