var exec = require('cordova/exec');

function CallsPlugin() { 
 console.log("CallsPlugin.js: is created");
}

CallsPlugin.prototype.getCalls = function(callback,error,service,action,parameters){
        console.log("getCALLS");
        cordova.exec(function(winParam) {callback(winParam)},
             function(e) {error(e)},
             "CallsPlugin",
             action,
             parameters);

};

 var callsPlugin = new CallsPlugin();
 module.exports = callsPlugin;


