package com.cougar.gcpnavigation.functions;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.global.Params;
import com.cougar.gcpnavigation.interfaces.OnServerListener;
import com.gc.materialdesign.widgets.ProgressDialog;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cougar0828 on 15/5/27.
 */
public class InsertJourneyAsync extends AsyncTask {

    private Context mContext;
    private OnServerListener mListener;
    private ProgressDialog mDialog;

    private String journeyName, token, addressSet;

    private final String TAG = "InsertJourneyAsync";

    public InsertJourneyAsync(Context mContext) {
        this.mContext = mContext;
        initDialog();
    }

    public void setmListener(OnServerListener mListener) {
        this.mListener = mListener;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setmAddressSet(String addressSet) {
        this.addressSet = addressSet;
    }

    private void initDialog() {
        mDialog = new ProgressDialog(mContext, mContext.getString(R.string.str_loading));
        mDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog.show();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return queryInsertResult();
    }

    private Object queryInsertResult() {

        int resultCode = 1;
        Log.d(TAG, "Start insert journey");

        //ready httpclient, httppost
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(Params.WAYPOINT_INSERT);

        //set httppost header
        httpPost.setHeader(Params.AUTH_KEY, Params.AUTH_VALUE + token);
        httpPost.setHeader(Params.CONTENTTYPE_KEY, Params.CONTENTTYPE_VALUE);

        //transfer string to base64
        String encodedAddress = Base64.encodeToString(addressSet.getBytes(),
                Base64.NO_WRAP);

        //set async timeout
        HttpParams params = httpClient.getParams();
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, new Integer(9000));
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, new Integer(9000));
        httpClient.setParams(params);

        //set httppost json
        JSONObject jsObject = new JSONObject();
        try {
            jsObject.put(Params.TAG_BODY_KEY, encodedAddress);
            jsObject.put(Params.TAG_CONTENTTYPE_KEY,Params.TAG_CONTENTTYPE_VALUE);
            jsObject.put(Params.TAG_FILENAME_KEY, journeyName + ".txt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String message = jsObject.toString();

        Log.d(TAG, "Encoded address : " + encodedAddress);
        Log.d(TAG, "File name : " + journeyName);

        try {
            httpPost.setEntity(new StringEntity(message,"UTF8"));
            org.apache.http.HttpResponse httpResponse = httpClient.execute(httpPost);

            Log.d(TAG, "Response code : " + httpResponse.getStatusLine().getStatusCode());

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                resultCode = 0;
            } else {
                resultCode = -4;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            resultCode = -2;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            resultCode = -4;
        } catch (IOException e) {
            e.printStackTrace();
            resultCode = -4;
        }
        return resultCode;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        int resultCode = (int) result;

        switch (resultCode) {

            case 0:
                mListener.onFinishListener(resultCode);
                break;
            case -1:
                mListener.onErrorListener(resultCode);
                break;
            case -2:
                mListener.onErrorListener(resultCode);
                break;
            case -4:
                mListener.onConnectionFailureListener();
                break;
        }
        mDialog.dismiss();
    }
}
