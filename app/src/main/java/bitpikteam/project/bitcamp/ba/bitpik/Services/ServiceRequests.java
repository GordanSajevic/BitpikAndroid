package bitpikteam.project.bitcamp.ba.bitpik.Services;


import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import bitpikteam.project.bitcamp.ba.bitpik.singletons.UserData;
import okio.BufferedSink;

public class ServiceRequests {

    public static void get(String url, Callback callback) {
        request(url, null, callback, false);
    }

    public static void post(String url, String json, Callback callback) {
        request(url, json, callback, true);
    }

    private static void request(String url, String json, Callback callback, boolean isPost) {

        // Tip podatka JSON; (iz nase applikacije ide json tip)
        MediaType JSON = MediaType.parse("application/json");
        // Client je nesto kao browser - koji salje(call-a) zahtjev(request) prema servisu;
        OkHttpClient client = new OkHttpClient();
        // json - podatak;
        Request.Builder requestBuilder = new Request.Builder();

        if (isPost == true) {
            RequestBody requestBody = RequestBody.create(JSON, json);
            requestBuilder.post(requestBody);
        } else {
            requestBuilder.get();
        }

        Request request = requestBuilder
                .url(url)
                .addHeader("Authorization", UserData.getInstance().getBaseAuth())
                .addHeader("Accept", "application/json")
                .build();

        Call call = client.newCall(request);
        // kad dobijes odgovor pozovi moj Callback;
        call.enqueue(callback);

    }

}
