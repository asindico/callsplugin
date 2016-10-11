var exec = require('cordova/exec');

function CallsPlugin() { 
 console.log("CallsPlugin.js: is created");
}

CallsPlugin.prototype.getCalls = function(callback,error){
        console.log("getCALLS");
        cordova.exec(function(winParam) {callback(winParam)},
             function(e) {error(e)},
             "CallsPlugin",
             "all",
             []);

};

 var callsPlugin = new CallsPlugin();
 module.exports = callsPlugin;


