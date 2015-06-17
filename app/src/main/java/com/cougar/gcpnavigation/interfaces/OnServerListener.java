package com.cougar.gcpnavigation.interfaces;

/**
 * @author cougar0828
 * 
 *         處理Server回傳ResultCode for GCP Navigation
 * 
 *         0:網路連線成功&取回結果 -1:網路連線成功&結果為空 -2:參數傳送錯誤 -3:  -4:網路連線失敗
 */
public interface OnServerListener {

	final static int SUCCESS = 0;
	final static int NODATA = -1;
	final static int PARAMETERERROR = -2;
	final static int CONNECTIONFAILEED = -4;

	public void onFinishListener(Object result);// 0

	public void onErrorListener(int cancelCode);// -1,-2,-3

	public void onConnectionFailureListener();// -4
}
