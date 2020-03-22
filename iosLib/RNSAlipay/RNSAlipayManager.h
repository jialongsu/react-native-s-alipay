//
//  RNSAlipayManager.h
//  project
//
//  Created by Arno on 2020/3/10.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "React/RCTBridgeModule.h"

@interface RNSAlipayManager : NSObject<RCTBridgeModule>

+(void) handleCallback:(NSURL *)url;

@end
