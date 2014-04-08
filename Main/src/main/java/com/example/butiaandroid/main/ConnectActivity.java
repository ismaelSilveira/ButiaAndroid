package com.example.butiaandroid.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * A login screen that offers login via email/password.

 */
public class ConnectActivity extends Activity {
    // UI references.
    private EditText in_ip;
    private EditText in_puerto;

    private View mProgressView;
    private View mLoginFormView;
    private CheckBox enableVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        // Set up the login form.
        in_ip = (EditText) findViewById(R.id.ip);
        in_puerto = (EditText) findViewById(R.id.puerto);
        enableVideo = (CheckBox) findViewById(R.id.enableVideo);

        Button mEmailSignInButton = (Button) findViewById(R.id.conectar);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptConnect();
            }
        });

        mLoginFormView = findViewById(R.id.connect_form);
        mProgressView = findViewById(R.id.login_progress);

        TextView infoText = (TextView) findViewById(R.id.textinfo);
        infoText.setMovementMethod(LinkMovementMethod.getInstance());
        infoText.setText(Html.fromHtml(getString(R.string.mas_link)));

        TextView gitText = (TextView) findViewById(R.id.textgit);
        gitText.setMovementMethod(LinkMovementMethod.getInstance());
        gitText.setText(Html.fromHtml(getString(R.string.mas_git)));


        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        in_ip.setText(preferences.getString("IP",""));
        in_puerto.setText(preferences.getString("PORT",""));




    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptConnect() {

        // Reset errors.
        in_ip.setError(null);
        in_puerto.setError(null);

        // Store values at the time of the login attempt.
        String ip = in_ip.getText().toString();
        String puerto = in_puerto.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid IP address.
        if (TextUtils.isEmpty(ip) || !isIPValid(ip)) {
            in_ip.setError(getString(R.string.error_invalid_ip));
            focusView = in_ip;
            cancel = true;
        }

        // Check for a valid port.
        if (TextUtils.isEmpty(puerto) || !isPuertoValid(puerto)) {
            in_puerto.setError(getString(R.string.error_invalid_puerto));
            focusView = in_puerto;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            new ConectarButia().execute(ip, puerto);
        }
    }

    private boolean isIPValid(String ip) {
        String patron = "^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){2}(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5]))$";
        return Pattern.compile(patron).matcher(ip).matches();

    }

    private boolean isPuertoValid(String puerto) {
        String patron = "^(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[0-5]?([0-9]){0,3}[0-9])$";
        return Pattern.compile(patron).matcher(puerto).matches();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void conecto(){
        Intent myIntent = new Intent(this, VideoStreaming.class);
        startActivity(myIntent);
    }

    private class ConectarButia extends AsyncTask<String, Void, Boolean > {

        @Override
        protected Boolean  doInBackground(String...params) {
            String ip = params[0];
            String puerto= params[1];

            try {
                // Simulate network access.
            // Robot butia = Robot.getInstance();
            //  butia.conectar(ip, Integer.parseInt(puerto));
               // System.out.println("***************************** Módulos *****************************");
               // System.out.println(butia.get_modules_list());
            } catch (Exception e) {
                return  false;
           }


           //que lo haga siempre que se conecta
            SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("IP", ip);
            editor.putString("PORT", puerto);
            editor.commit();

            return true;
        }




        @Override
        protected void onPostExecute(Boolean result) {
            showProgress(false);

            if (result) {
                conecto();
            } else {
                in_ip.setError(getString(R.string.error_connect));
                in_ip.requestFocus();
            }

        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }


    }


}



