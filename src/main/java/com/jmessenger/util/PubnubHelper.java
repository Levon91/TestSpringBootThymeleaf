package com.jmessenger.util;

import com.jmessenger.exception.PubnubError;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import org.json.JSONObject;

import java.util.List;

public class PubnubHelper {

    private static Pubnub pubnub = null;

    private static Pubnub getInstance() {
        if (pubnub == null) {
            pubnub = new Pubnub("demo", "demo");
        }
        return pubnub;
    }

    public static void publish(List<String> channels, JSONObject message) {
        for (String channel : channels) {
            getInstance().publish(channel, message, new Callback() {
                public void successCallback(String channel, Object response) {
                    System.out.println(response.toString());
                }

                public void errorCallback(String channel, PubnubError error) {
                    System.out.println(error.toString());
                }
            });
        }
    }
}
