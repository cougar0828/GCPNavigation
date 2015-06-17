package com.cougar.gcpnavigation.main;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appspot.waldo_gcp.waldo.Waldo;
import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.global.Params;
import com.cougar.gcpnavigation.interfaces.OnServerListener;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.IOException;
import java.io.Serializable;

public class Login extends Activity implements OnServerListener {

    private GoogleAccountCredential mCredential;
    private Waldo mWaldoService;

    private boolean ifDoubleBack;
    private String selectedAccount = "", accountToken = "";

    private SharedPreferences mShare;
    private final String DATA = "DATA";
    private final String ACCOUNT = "ACCOUNT";
    private final String TOKEN = "TOKEN";

    private final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initData();
        initUI();
    }

    private void initData() {
        // TODO Auto-generated method stub
        mShare = getSharedPreferences(DATA, 0);
    }

    private void initUI() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onBackPressed() {

        if (ifDoubleBack) {
            super.onBackPressed();
            return;
        }

        this.ifDoubleBack = true;
        setToast(getString(R.string.str_ifDoubleBack));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ifDoubleBack = false;
            }
        }, 2000);
        return;
    }

    public void toStartSSO(View v) {

        mCredential = GoogleAccountCredential.usingAudience(this, Params.WEB_CLIENT_ID);
        startActivityForResult(mCredential.newChooseAccountIntent(), Params.REQUEST_ACCOUNT_PICKER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Params.REQUEST_ACCOUNT_PICKER:
                    if (data != null && data.getExtras() != null) {
                        selectedAccount = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                        if (selectedAccount != null) {

                            mCredential.setSelectedAccountName(selectedAccount);
                            getOAuthToken();
                        }
                        break;
                    }
                case Params.REQUEST_AUTHORIZATION:
                    getOAuthToken();
                    break;
            }
        }
    }

    private void getOAuthToken() {

        AsyncTask<Void, Void, String> async = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {

                try {
//                   accountToken = mCredential.getToken();
                    accountToken = GoogleAuthUtil.getToken(Login.this, selectedAccount, Params.SCOPE);
                } catch (UserRecoverableAuthException e) {
                    startActivityForResult(e.getIntent(),
                            Params.REQUEST_AUTHORIZATION);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (GoogleAuthException e) {
                    e.printStackTrace();
                }
                return accountToken;
            }

            @Override
            protected void onPostExecute(String result) {
                accountToken = result;
                if (!accountToken.equals("")) {
                    Log.d(TAG, "Fetching token finish, go to login");
                    setSharePre(selectedAccount, accountToken);
                } else {
                    Log.d(TAG, "Fetching token failed");
                    showFetchTokenFailed();
                }
            }
        };
        async.execute();
    }

    private void setSharePre(String account, String token) {

        Log.d(TAG, "Account : " + account);
        Log.d(TAG, "Token : " + token);

        SharedPreferences.Editor editor = mShare.edit();
        editor.putString(ACCOUNT, account);
        editor.putString(TOKEN, token);
        editor.commit();

        checkCageEndpoint();
    }

    private void checkCageEndpoint() {

        LoginAsync async = new LoginAsync(this);
        async.setmListener(this);
        async.setmWaldoService(mWaldoService);
        async.setmCredential(mCredential);
        async.execute(0);
    }

    private void showFetchTokenFailed() {
        setToast(getString(R.string.str_getTokenFailed));
    }

    private void setToast(String msg) {
        Toast.makeText(Login.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinishListener(Object result) {

        setToast(getString(R.string.str_success));

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Params.JOURNEY_LOBBY, (Serializable) result);

        intent.putExtras(bundle);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onErrorListener(int cancelCode) {
        switch (cancelCode) {

            case -1:
                setToast(getString(R.string.str_noData));
                break;
            case -2:
                setToast(getString(R.string.str_paramError));
                break;
        }
    }

    @Override
    public void onConnectionFailureListener() {
        setToast(getString(R.string.str_connectionFailure));
    }
}
