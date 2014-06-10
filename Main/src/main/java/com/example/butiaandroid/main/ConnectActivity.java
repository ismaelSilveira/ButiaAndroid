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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.devspark.appmsg.AppMsg;

import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.

 */
public class ConnectActivity extends Activity {

    @InjectView(R.id.login_progress) View mProgressView;
    @InjectView(R.id.connect_form) View mLoginFormView;

    @InjectView(R.id.puertoStreaming) EditText puertoS;
    @InjectView(R.id.ip) EditText in_ip;
    @InjectView(R.id.puerto) EditText in_puerto;
    @InjectView(R.id.enableVideo) CheckBox enableVideo;

    @InjectView(R.id.textinfo) TextView infoText;
    @InjectView(R.id.textgit) TextView gitText;

    @InjectView(R.id.layoutPort)  FrameLayout layoutStreaming;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        ButterKnife.inject(this);

        // Set up the login form
        infoText.setMovementMethod(LinkMovementMethod.getInstance());
        infoText.setText(Html.fromHtml(getString(R.string.mas_link)));

        gitText.setMovementMethod(LinkMovementMethod.getInstance());
        gitText.setText(Html.fromHtml(getString(R.string.mas_git)));

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        in_ip.setText(preferences.getString("IP",""));
        in_puerto.setText(preferences.getString("PORT",""));
        puertoS.setText(preferences.getString("PORTS",""));

        eneable_video();
    }


    @OnCheckedChanged(R.id.enableVideo)
    public void eneable_video(){
        if(!enableVideo.isChecked()){
            this.layoutStreaming.setVisibility(View.GONE);
        }else{
            this.layoutStreaming.setVisibility(View.VISIBLE);

        }
    }




    public void mostrarError(String error){
        AppMsg appMsg = AppMsg.makeText(this, error,  AppMsg.STYLE_ALERT);
        appMsg.setDuration(8000);
        appMsg.show();
    }





    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    String puertoStreaming;
    String ip;
    String puerto;

    @OnClick(R.id.conectar)
    public void attemptConnect() {

        // Reset errors.
        in_ip.setError(null);
        in_puerto.setError(null);
        puertoS.setError(null);

        // Store values at the time of the login attempt.
         ip = in_ip.getText().toString();
         puerto = in_puerto.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid IP address.
        if (TextUtils.isEmpty(ip) || !isIPValid(ip)) {
            in_ip.setError(getString(R.string.error_invalid_ip));
            focusView = in_ip;
            cancel = true;
        }


        // Check for a valid IP address.
        if (this.enableVideo.isChecked() ){
            puertoStreaming =puertoS.getText().toString();
            if (TextUtils.isEmpty(puertoStreaming) || !isPuertoValid(puertoStreaming)) {
                puertoS.setError(getString(R.string.error_invalid_puerto));
                focusView = puertoS;
                cancel = true;
            }
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
        //que lo haga siempre que se conecta
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("IP", ip);
        editor.putString("PORT", puerto);
        editor.putString("PORTS", puertoStreaming);
        editor.commit();

        Intent myIntent;

        if (enableVideo.isChecked()){
            Robot.getInstance().setPortStreaming(puertoStreaming);
            myIntent = new Intent(this, StreamingActivity.class);
        }else{
            myIntent = new Intent(this, SinStreamingActivity.class);
        }
        startActivity(myIntent);
        showProgress(false);

    }

    private class ConectarButia extends AsyncTask<String, Void, Boolean > {

        @Override
        protected Boolean  doInBackground(String...params) {
            String ip = params[0];
            String puerto= params[1];

            try {
                Robot butia = Robot.getInstance();
                butia.conectar(ip, Integer.parseInt(puerto));

            } catch (Exception e) {
                return false;
           }

            return true;
        }




        @Override
        protected void onPostExecute(Boolean result) {
            showProgress(false);

            if (result) {
                conecto();
            } else {
                mostrarError(getString(R.string.error_connect));
            }

        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }


    }


}



