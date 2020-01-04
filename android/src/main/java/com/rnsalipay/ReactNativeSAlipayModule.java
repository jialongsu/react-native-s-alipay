package com.rnsalipay;

import com.alipay.sdk.app.PayTask;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import java.util.Map;

public class ReactNativeSAlipayModule extends ReactContextBaseJavaModule {

    private ReactApplicationContext mReactContext;
    private static final int SDK_PAY_FLAG = 1;
    private Promise promise;

    public ReactNativeSAlipayModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ReactNativeSAlipay";
    }

    @ReactMethod
    public void pay(final String orderInfo, Promise promiseMethod) {
        promise = promiseMethod;
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getCurrentActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(getReactApplicationContext().getMainLooper()) {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
        switch (msg.what) {
            case SDK_PAY_FLAG: {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                WritableMap resultMap = Arguments.createMap();
                /**
                 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                String memo = payResult.getMemo();

                // 判断resultStatus 为9000则代表支付成功,该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                if (TextUtils.equals(resultStatus, "9000")) {
                    resultMap.putString("code", resultStatus);
                    resultMap.putString("memo", memo);
                    resultMap.putString("result", payResult.getResult());
                    promise.resolve(resultMap);
                } else {
                    promise.reject(resultStatus, payResult.toString());
                }
                break;
            }
            default:
                break;
        }
    };
};
}
