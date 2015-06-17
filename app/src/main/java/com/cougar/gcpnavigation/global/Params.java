package com.cougar.gcpnavigation.global;

public class Params {

    // OAuth
    public static final String SCOPE = "oauth2: https://www.googleapis.com/auth/userinfo.email";

    public static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
    public static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;
    public static final int REQUEST_ACCOUNT_PICKER = 2;

    public static final String APP_CLIENT_ID = "server:client_id:885964767812-gqcmkvrgnvmotvve5a326es1ptljrqlj.apps.googleusercontent.com";
    public static final String WEB_CLIENT_ID = "server:client_id:885964767812-kfm1mj8b6e2f5rve6cr295cu66glr16p.apps.googleusercontent.com";
    public static final String ROOT_URL = "https://waldo-gcp.appspot.com/_ah/api/";
    public static final String APPLLICATION_NAME = "waldo-gcp";

    public static final String WAYPOINT_LIST = "https://waldo-gcp.appspot.com/_ah/api/waldo/v1/waypoints/list";
    public static final String WAYPOINT_ME = "https://waldo-gcp.appspot.com/_ah/api/waldo/v1/waypoints/me";
    public static final String WAYPOINT_INSERT = "https://waldo-gcp.appspot.com/_ah/api/waldo/v1/waypoints/insert";

    public static String OAUTHTOKEN = "";
    public static final int REQUEST_AUTHORIZATION = 1;
    public static int ISGETOAUTHTOKEN = 0;// OAuth Token:0-false,1-true

    public static final String AUTH_KEY = "authorization";
    public static final String AUTH_VALUE = "Bearer ";
    public static final String CONTENTTYPE_KEY = "Content-Type";
    public static final String CONTENTTYPE_VALUE = "application/json";

    //Async response
    public static final String WAYPOINTS_PREFIX = "https://storage.googleapis.com/";
    public static final String PREPARE_JOURNEYPICNULL = "http://procareinsuranceservices.com/wp-content/uploads/2013/10/4185389_1373369880.jpg";
    public static final String PREPARE_ACCOUNTPIVNULL = "https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg";
    public static final String TAG_WAYPOINTS = "waypoints";

    //Global
    public static final String JOURNEY_LOBBY = "JourneyLobby";
    public static final String NEW_JOURNEY = "NewJourney";
    public static final String JOURNEY_LIST = "JourneyList";
    public static final String JOURNEY_DETAIL = "JourneyDetail";

    // Journey
    public static final String TAG_JOURNEYRESULT = "journey_result";
    public static final String TAG_CONTENTTYPE_KEY = "content_type";
    public static final String TAG_CONTENTTYPE_VALUE = "text/plain";
    public static final String TAG_FILENAME_KEY = "file_name";
    public static final String TAG_BODY_KEY = "body";

}
