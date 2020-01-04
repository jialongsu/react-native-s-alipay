//
//  RNSAlipay.swift
//  example
//
//  Created by Arno on 2019/12/30.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation

@objc(ReactNativeSAlipay)
class RNSAlipayModule: NSObject{
  
  var promiseRej: RCTPromiseRejectBlock!
  var promiseRes: RCTPromiseResolveBlock!
  
  
  @objc func pay(_ orderInfo: String, _ resolve: @escaping RCTPromiseResolveBlock, _ reject: @escaping RCTPromiseRejectBlock) {

    let urlTypes = Bundle.main.infoDictionary!["CFBundleURLTypes"] as! [AnyObject]
    var alipayScheme = "";
    for item in urlTypes {
      let data: [String : Any] = item as! [String : Any];
      let URLName: String = data["CFBundleURLName"] as! String;
      if(URLName == "alipay") {
        let CFBundleURLSchemes: NSArray =  data["CFBundleURLSchemes"] as! NSArray
        alipayScheme = CFBundleURLSchemes[0] as! String;
      }
    }
    if(alipayScheme == "") {
      let error = "scheme cannot be empty"
      reject("-1", error, NSError(domain: error, code: -1, userInfo: nil))
      return
    }
    AlipaySDK.defaultService().payOrder(orderInfo, fromScheme: alipayScheme, callback: {  resultDic in
        if let dic = resultDic as? [String:Any] {
          let resultStatus: String = dic["resultStatus"] as! String
          var resultMap = Dictionary<String, Any>();
          resultMap["code"] = resultStatus;
          if(resultStatus == "9000") {
            resolve(resultMap);
          }else{
            let data = try? JSONSerialization.data(withJSONObject: dic, options: [])
            let error = String(data: data!, encoding: String.Encoding.utf8)
            reject(resultStatus, error, NSError(domain: error ?? "", code: -1, userInfo: nil))
          }
        }
    })
  }
  

  
}
