/*
 * EmbeddedWebView.java
 *
 * Copyright 2017 Caique Jhones
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package io.cjdeveleper.perguntasc;

import android.app.ProgressDialog;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Caique Jhones
 * @version 1
 * @since 1
 */
public class EmbeddedWebView {

    private static final String ASSETS = "file:///android_asset/";

    public static void init(WebView webView, String content, Context context) {
        final ProgressDialog pd = ProgressDialog.show(context, "", "Carregando...", true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });

        try {
            InputStream inputStream = context.getAssets().open("html/index.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null ) {
                builder.append(line);
            }
            reader.close();

            String str = builder.toString();
            str = str.replace("CONTENT", content);

            webView.loadDataWithBaseURL(ASSETS.concat("html/"), str, "text/HTML", "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
