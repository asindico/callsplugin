package android.plugin.calls;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Calls extends CordovaPlugin {

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        // your init code here
        LOG.d("CALLS","plugin initialization");
    }

    @Override
    public boolean execute(String action, JSONArray args,  CallbackContext callbackContext) throws JSONException {
        final long duration = args.getLong(0);
        JSONArray jar = new JSONArray();
        callbackContext.success();
        return true;

    }

    private JSONArray getCallDetails(Context context) {
        Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        JSONArray calls = new JSONArray();
        while (managedCursor.moveToNext()) {
            JSONObject call = new JSONObject();
            try {
                call.put("phoneNumber",managedCursor.getString(number));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            
        }
        managedCursor.close();
        return calls;

    }
}
