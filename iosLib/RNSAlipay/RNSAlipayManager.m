//
//  RNSAlipayManager.m
//  example
//
//  Created by Arno on 2019/12/30.
//  Copyright © 2019 Facebook. All rights reserved.
//
#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(ReactNativeSAlipay, NSObject)


RCT_EXTERN_METHOD(
  pay:(NSString *)key
  :(RCTPromiseResolveBlock)resolve
  :(RCTPromiseRejectBlock)reject
)

+ (BOOL)requiresMainQueueSetup{
  return NO;
}

@end
