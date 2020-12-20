/*
 * Copyright (c) 2014 HygieiaSoft
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 */
let cordova = require('cordova');

module.exports = {
	getUID: function (onSuccess, onError) {
		cordova.exec(onSuccess, onError, "UID", "getUID", []);
	},
	getIdfa: function (onSuccess, onError) {
		cordova.exec(onSuccess, onError, "UID", "getIdfa", []);
	},
	requestPermission: function (onSuccess, onError) {
		cordova.exec(onSuccess, onError, "UID", "requestPermission", []);
	}
}
