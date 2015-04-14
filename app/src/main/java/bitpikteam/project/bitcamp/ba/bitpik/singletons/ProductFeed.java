package bitpikteam.project.bitcamp.ba.bitpik.singletons;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bitpikteam.project.bitcamp.ba.bitpik.Models.Product;
import bitpikteam.project.bitcamp.ba.bitpik.Services.ServiceRequests;

/**
 * Created by nedzadhamzic on 14/04/15.
 */
public class ProductFeed  {

    private static ProductFeed ourInstance = new ProductFeed();

    public static ProductFeed getInstance() {
        return ourInstance;
    }

    private List<Product> mProductList;

    private ProductFeed() {
        mProductList = new ArrayList<Product>();
    }

    /**
     * Our get request for the web service with the new created Callback;
     *
     * @param url
     */
    public void getFeed(String url) {
        ServiceRequests.get(url, parseResponse());
    }

    public List<Product> getTheListFeed() {
        return mProductList;
    }

    private Callback parseResponse() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                // treba staviti neki makeToastFromAnotherThread.... :)
                Log.e("RESPONSE", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //treba povjeriti da li si dobio odgovor od servera;
                // i onda kroz svaki clan ArrayJSON-a; izvuci svako OBject pojedinacno!
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject postObj = array.getJSONObject(i);
                        int id = postObj.getInt("id");
                        String name = postObj.getString("name");
                        String description = postObj.getString("description");
                        double price = postObj.getDouble("price");
                        boolean isSold = postObj.getBoolean("isSold");
                        boolean isSpecial = postObj.getBoolean("isSpecial");
                        String productImagePath = postObj.getString("productImagePath");
                        mProductList.add(new Product(id, name, description, price, isSold, isSpecial, productImagePath));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    // dodati ovdje ako padne!
                }
            }
        };
    }
}