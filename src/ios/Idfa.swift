import Foundation
import AdSupport
import AppTrackingTransparency

@objc(UID) class UID : CDVPlugin {
  @objc(requestPermission:)
  func requestPermission(_ command: CDVInvokedUrlCommand) {
    var pluginResult = CDVPluginResult (status: CDVCommandStatus_OK, messageAs: "");

    if #available(iOS 14, *) {
      ATTrackingManager.requestTrackingAuthorization { status in
        switch status {
          case .authorized:
            pluginResult = CDVPluginResult (status: CDVCommandStatus_OK, messageAs: "authorized");
            print("UID: Authorized")
          case .denied:
            pluginResult = CDVPluginResult (status: CDVCommandStatus_OK, messageAs: "denied");
            print("UID: Denied")
          case .notDetermined:
            pluginResult = CDVPluginResult (status: CDVCommandStatus_OK, messageAs: "notDetermined");
            print("UID: Not Determined")
          case .restricted:
            pluginResult = CDVPluginResult (status: CDVCommandStatus_OK, messageAs: "restricted");
            print("UID: Restricted")
          @unknown default:
            pluginResult = CDVPluginResult (status: CDVCommandStatus_OK, messageAs: "unknown");
            print("UID: Unknown")
        }
      }
    }
    self.commandDelegate!.send(pluginResult, callbackId: command.callbackId);
  }

  @objc(getIdfa:)
  func getIdfa(_ command: CDVInvokedUrlCommand) {
    let id: String? = ASIdentifierManager.shared().advertisingIdentifier.uuidString
    let pluginResult = CDVPluginResult (status: CDVCommandStatus_OK, messageAs: id);
    self.commandDelegate!.send(pluginResult, callbackId: command.callbackId);
  }
}
