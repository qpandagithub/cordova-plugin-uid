/*
 * Copyright (c) 2020 Darren
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 */
let cordova = require('cordova');

module.exports = {
	getSafeArea: function (onSuccess, onError) {
		cordova.exec(onSuccess, onError, "SafeArea", "getSafeArea", []);
	}
}
