/*
 * Copyright (c) 2014 HygieiaSoft
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 */
var exec = require('cordova/exec');

module.exports = {
	getUID: function (onSuccess, onError) {
		exec(onSuccess, onError, "UID", "getUID", []);
	}
}
