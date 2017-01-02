/**
Copyright 2017 Andrea Sindico

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**/
    

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
    }

    @Override
    public boolean execute(String action, JSONArray args,  final CallbackContext callbackContext) throws JSONException {
        class CallsRetriever implements Runnable {
            private int firstCall = 0;
            private int lastCall = 20;
            CallsRetriever(int _firstCall,int _lastCall){
                firstCall = _firstCall;
                lastCall = _lastCall;
            }

            @Override
            public void run() {
                final Context context =  cordova.getActivity().getApplicationContext();

                JSONArray res = Calls.this.getCallDetails(context,firstCall,lastCall);
                callbackContext.success(res.toString());
            }
        }
        cordova.getThreadPool().execute(new CallsRetriever(args.getInt(0),args.getInt(1)));
        return true;

    }

    private JSONArray getCallDetails(Context context,int firstCall,int lastCall) {

        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, CallLog.Calls.DATE + " DESC");
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int callerIndex = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        JSONArray calls = new JSONArray();
        int count = 0;
        while (managedCursor.moveToNext()) {
            if(count>=firstCall && count<lastCall) {
                JSONObject call = new JSONObject();
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                String prettyDate = callDate;
                String caller = managedCursor.getString(callerIndex);
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
                try {
                    call.put("phoneNumber", phNumber);
                    call.put("date", prettyDate);
                    call.put("type", dir);
                    call.put("caller", caller);
                    calls.put(call);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if(count>lastCall)break;
            count++;
        }
        managedCursor.close();
        return calls;

    }
}