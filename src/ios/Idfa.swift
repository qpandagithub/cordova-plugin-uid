import Foundation
import AdSupport
import AppTrackingTransparency

@objc(UID) class UID : CDVPlugin {
  @objc(getUID:)
  func getUID(_ command: CDVInvokedUrlCommand) {
    var id: String?
    var pluginResult = CDVPluginResult (status: CDVCommandStatus_ERROR, messageAs: "The Plugin Failed");
    
    if #available(iOS 14, *) {
      ATTrackingManager.requestTrackingAuthorization { status in
        switch status {
          case .authorized:
            id = ASIdentifierManager.shared().advertisingIdentifier.uuidString
          case .denied:
            // Tracking authorization dialog was
            // shown and permission is denied
            print("Denied")
          case .notDetermined:
            // Tracking authorization dialog has not been shown
            print("Not Determined")
          case .restricted:
            print("Restricted")
          @unknown default:
            print("Unknown")
        }
      }
    } else {
      id = ASIdentifierManager.shared().advertisingIdentifier.uuidString
    }

    if id != nil {
      pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: id);
    }
      
    self.commandDelegate!.send(pluginResult, callbackId: command.callbackId);
  }
}