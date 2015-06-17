package com.cougar.gcpnavigation.main;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.appspot.waldo_gcp.waldo.Waldo;
import com.appspot.waldo_gcp.waldo.model.ApplicationApisWaypointsMessagesWayPointsListResponse;
import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.global.JourneyLobbyData;
import com.cougar.gcpnavigation.global.Params;
import com.cougar.gcpnavigation.interfaces.OnServerListener;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cougar0828 on 15/5/12.
 */
public class LoginAsync extends AsyncTask {

    private Context mContext;
    private Waldo mWaldoService;
    private GoogleAccountCredential mCredential;
    private OnServerListener mListener;
    private ProgressDialog mDialog;

    private List<JourneyLobbyData> mLobbyDataList;
    private ApplicationApisWaypointsMessagesWayPointsListResponse mResponse;

    private final String TAG = "LoginAsync";

    public LoginAsync(Context mContext) {
        this.mContext = mContext;
        initDialog();
    }

    public void setmListener(OnServerListener mListener) {
        this.mListener = mListener;
    }

    public void setmWaldoService(Waldo mWaldoService) {
        this.mWaldoService = mWaldoService;
    }

    public void setmCredential(GoogleAccountCredential mCredential) {
        this.mCredential = mCredential;
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
        return queryWayPointData();
    }

    private Object queryWayPointData() {

        int resultCode = 1;
        Log.d(TAG, "Start query waypoint data.");

        try {

            //ready httpclient, httpget
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(Params.WAYPOINT_LIST);

            //set async timeout
            HttpParams params = httpClient.getParams();
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, new Integer(9000));
            params.setParameter(CoreConnectionPNames.SO_TIMEOUT, new Integer(9000));
            httpClient.setParams(params);

            // execute
            org.apache.http.HttpResponse httpResponse = httpClient.execute(httpGet);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {

                String result = EntityUtils.toString(httpResponse.getEntity());

                if (!result.equals("") || !result.equals(null)) {

                    resultCode = 0;

                    JSONObject all = new JSONObject(result);
                    JSONArray waypoints = all.getJSONArray(Params.TAG_WAYPOINTS);

                    mLobbyDataList = getResult(waypoints);

                } else {
                    resultCode = -1;
                }
            } else {
                resultCode = -4;
            }

        } catch (IOException e) {
            e.printStackTrace();
            resultCode = -4;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultCode;
    }

    private List<JourneyLobbyData> getResult(JSONArray response) throws JSONException {

        List<JourneyLobbyData> lobbyList = new ArrayList<JourneyLobbyData>();
        Log.d(TAG, "Get journeylobby data success");

        for (int i = 0; i < response.length(); i++) {

            JSONObject jsObject = (JSONObject) response.get(i);
            JourneyLobbyData lobbyData = new JourneyLobbyData();

            if (!(jsObject.getString("thumbnail")).toString().equals("")) {
                lobbyData.journeyPic = Params.WAYPOINTS_PREFIX + (jsObject.getString("thumbnail")).toString();
            }else{
                lobbyData.journeyPic = Params.PREPARE_JOURNEYPICNULL;
            }

            if(jsObject.has("picture")) {

                if (!(jsObject.getString("picture")).toString().equals("")) {
                    lobbyData.accountPic = (jsObject.getString("picture")).toString();
                } else {
                    lobbyData.accountPic = Params.PREPARE_ACCOUNTPIVNULL;
                }
            }else{
                lobbyData.accountPic = Params.PREPARE_ACCOUNTPIVNULL;
            }

            if(jsObject.has("name")) {
                lobbyData.name = (jsObject.getString("name")).toString();
            }else{
                lobbyData.name = "Top secret";
            }

            lobbyData.viewsCount = (jsObject.getString("view")).toString();
            lobbyData.likesCount = (jsObject.getString("like")).toString();

            Log.d(TAG, "journey pic : " + lobbyData.journeyPic);
            Log.d(TAG, "account pic : " + lobbyData.accountPic);
            Log.d(TAG, "name : " + lobbyData.name);
            Log.d(TAG, "view : " + lobbyData.viewsCount);
            Log.d(TAG, "like : " + lobbyData.likesCount);

            lobbyList.add(lobbyData);
        }
        return lobbyList;
    }

//    private byte[] handleByte(Bitmap bitmap) {
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 20, stream);
//        byte[] byteArray = stream.toByteArray();
//
//        return byteArray;
//    }
//
//    private Bitmap handleURL(URL url){
//
//        try {
//            URLConnection conn = url.openConnection();
//            conn.connect();
//            InputStream isCover = conn.getInputStream();
//            Bitmap bmpCover = BitmapFactory.decodeStream(isCover);
//            isCover.close();
//            return bmpCover;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        int resultCode = (int) result;

        switch (resultCode) {

            case 0:
                mListener.onFinishListener(mLobbyDataList);
                break;
            case -1:
                mListener.onErrorListener(resultCode);
                break;
            case -4:
                mListener.onConnectionFailureListener();
                break;
        }
        mDialog.dismiss();
    }
}
