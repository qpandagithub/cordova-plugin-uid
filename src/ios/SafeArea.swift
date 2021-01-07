import Foundation

@objc(SafeArea) class SafeArea : CDVPlugin {
  @objc(getSafeArea:)
  func getSafeArea(_ command: CDVInvokedUrlCommand) {
    var response : [String:CGFloat] = [String:CGFloat]()
    let density: CGFloat = UIScreen.main.scale
    response["density"] = density
    response["left"] = 0
    response["right"] = 0
    
    if #available(iOS 13.0, *) {
      let window = UIApplication.shared.windows[0]
      response["top"] = window.safeAreaInsets.top
      response["bottom"] = window.safeAreaInsets.bottom
      response["left"] = 0
      response["right"] = 0
    }
    else if #available(iOS 11.0, *) {
      let window = UIApplication.shared.keyWindow
        if (window != nil) {
          response["top"] = window!.safeAreaInsets.top
          response["bottom"] = window!.safeAreaInsets.bottom
        } else {
          response["top"] = 0
          response["bottom"] = 0
        }
    }
    print("获取安全区域:")
    print(response)

    let pluginResult = CDVPluginResult (status: CDVCommandStatus_OK, messageAs: response);
    self.commandDelegate!.send(pluginResult, callbackId: command.callbackId);
  }
}
