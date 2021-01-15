# callsplugin
This the repository of a simple Cordova Plugin I made in order to fetch Android Calls Log from Javascript. 

In order to install the plugin open a shell in your Cordova project folder and type

`cordova plugin add callsplugin`

Then in your javascript 

```javascript

var firstCall=0;
var lastCall=20;
calls.getCalls(function(res){
                var calls = JSON.parse(res);
                for (i=0;i<calls.length;i++){
                    var tel = calls[i].phoneNumber;
                    var type = calls[i].type;
                    var date = calls[i].date;
                    var caller = calls[i].caller;
                    var duration = calls[i].duration;
                 }    
                }
                ,function(error){console.log(error);}
                ,""
                ,""
                ,[firstCall,lastCall]);

```

the range `[firstCall,lastCall]` represents the indexex of the calls to fetch starting from the latest call and proceding backward. 
This allows you to fetch buches of calls depending on the user need (i.e. scroll down, etc.) avoiding to slow down the whole app.

## Permissions

Please remembr to add at least this permission <uses-permission android:name="android.permission.READ_CALL_LOG" />
 to your app manifest. Otherwise the app will crash for a permission denied exception.
 
 Consider that for this permissions, if your targetSdkVersion is 23 or higher your app need to have the <uses-permission> element(s), and also to ask for that permissions at runtime. See https://developer.android.com/training/permissions/requesting.html for further information. 

As a temporary workaround you might want to drop your targetSdkVersion below 23.


