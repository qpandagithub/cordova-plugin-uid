/*
 * Copyright (c) 2020 Darren
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 */
package cn.com.pandashop.cordova.uid;

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

public class UID extends CordovaPlugin {
    public static String uuid; // Device UUID
    public static String imei; // Device IMEI
    public static String imsi; // Device IMSI
    public static String iccid; // Sim IMSI
    public static String mac; // MAC address

    /**
     * Constructor.
     */
    public UID() {
    }

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Context context = cordova.getActivity().getApplicationContext();
        UID.uuid = getUuid(context);
        UID.imei = getImei(context);
        UID.imsi = getImsi(context);
        //UID.iccid = getIccid(context);
        UID.mac = getMac(context);
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
        if (action.equals("getUID")) {
            JSONObject r = new JSONObject();
            r.put("UUID", UID.uuid);
            r.put("IMEI", UID.imei);
            r.put("IMSI", UID.imsi);
            r.put("ICCID", UID.iccid);
            r.put("MAC", UID.mac);
            callbackContext.success(r);
        } else {
            return false;
        }
        return true;
    }

    /**
     * Get the device's Universally Unique Identifier (UUID).
     *
     * @param context The context of the main Activity.
     * @return
     */
    public String getUuid(Context context) {
        String uuid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return uuid;
    }

    /**
     * Get the device's International Mobile Station Equipment Identity (IMEI).
     *
     * @param context The context of the main Activity.
     * @return
     */
    public String getImei(Context context) {
        String deviceId;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }
        return deviceId;
    }

    /**
     * Get the device's International mobile Subscriber Identity (IMSI).
     *
     * @param context The context of the main Activity.
     * @return
     */
    public String getImsi(Context context) {

        String imsi = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<SubscriptionInfo> subscription = SubscriptionManager.from(context).getActiveSubscriptionInfoList();
            for (int i = 0; i < subscription.size(); i++) {
                SubscriptionInfo info = subscription.get(i);
                imsi = info.getNumber();
                /*Log.i("IMSI", "number " + info.getNumber());
                Log.d("IMSI", "network name : " + info.getCarrierName());
                Log.d("IMSI", "country iso " + info.getCountryIso());*/
            }
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imsi = mTelephony.getLine1Number();
        }

        return imsi;
    }

    /**
     * Get the sim's Integrated Circuit Card Identifier (ICCID).
     *
     * @param context The context of the main Activity.
     * @return
     */
    public String getIccid(Context context) {
        final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String iccid = mTelephony.getSimSerialNumber();
        return iccid;
    }

    /**
     * Get the Media Access Control address (MAC).
     *
     * @param context The context of the main Activity.
     * @return
     */
    public String getMac(Context context) {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    // res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "";
    }

}
