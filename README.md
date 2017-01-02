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
                 }    
                }
                ,function(error){console.log(error);}
                ,""
                ,""
                ,[firstCall,lastCall]);

```

the range `[firstCall,lastCall]` represents the indexex of the calls to fetch starting from the latest call and proceding backward. 
This allows you to fetch buches of calls depending on the user need (i.e. scroll down, etc.) avoiding to slow down the whole app.

