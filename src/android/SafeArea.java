/*
 * Copyright (c) 2020 Darren
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 */
package cn.com.pandashop.cordova.safearea;

import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.provider.Settings;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;

import java.util.Collections;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SafeArea extends CordovaPlugin {

    /**
     * Constructor.
     */
    public SafeArea() {
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action          The action to execute.
     * @param args            JSONArry of arguments for the plugin.
     * @param callbackContext The callback id used when calling back into JavaScript.
     * @return True if the action was valid, false if not.
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getSafeArea")) {
            JSONObject res = new JSONObject();
            DisplayCutout displayCutout = cordova.getActivity().getWindow().getDecorView().getRootWindowInsets().getDisplayCutout();
            Float density = cordova.getContext().getResources().getDisplayMetrics().density;

            Log.e("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + (displayCutout.getSafeInsetLeft() / density));
            Log.e("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + (displayCutout.getSafeInsetRight() / density));
            Log.e("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + (displayCutout.getSafeInsetTop() / density));
            Log.e("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + (displayCutout.getSafeInsetBottom() / density));
            Log.e("TAG", "像素密度:" + density);

            res.put("left", (displayCutout.getSafeInsetLeft() / density));
            res.put("right", (displayCutout.getSafeInsetRight() / density));
            res.put("top", (displayCutout.getSafeInsetTop() / density));
            res.put("bottom", (displayCutout.getSafeInsetBottom() / density));
            res.put("density", density);

            callbackContext.success(res);
        } else {
            return false;
        }
        return true;
    }

}
