/*
 * ResumeActivity.java This project is distributed under a Apache 2
 * license. See the included License file for
 * details.
 */
package io.cjdeveleper.perguntasc;

import android.app.ProgressDialog;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Caique Jhones
 * @version 1
 * @since APP-1
 */
@EActivity(R.layout.activity_resume)
public class ResumeActivity extends BaseActivity {
    @ViewById
    WebView webResume;

    @Extra
    String content;

    @AfterViews
    void initViews() {
        this.toolbar.setTitle(R.string.resume);
        configReturnToolbar();

        EmbeddedWebView.init(webResume, content, this);
    }
}
