package bitpikteam.project.bitcamp.ba.bitpik.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import bitpikteam.project.bitcamp.ba.bitpik.CustomAdapters.ProductAdapter;
import bitpikteam.project.bitcamp.ba.bitpik.Models.Product;
import bitpikteam.project.bitcamp.ba.bitpik.R;
import bitpikteam.project.bitcamp.ba.bitpik.singletons.ProductFeed;

public class IndexPage extends ActionBarActivity {

    private EditText editTextPretraga;
    private ListView mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_page);

        editTextPretraga = (EditText) findViewById(R.id.edit_text_filter);
        mProductList = (ListView) findViewById(R.id.list_view_products);

        ProductFeed productFeed = ProductFeed.getInstance();

        String urlPart1 = getString(R.string.service_base);
        String urlPart2 = getString(R.string.service_allproducts);
        String url = urlPart1 + urlPart2;
        // OUR CONNECTION AND REQUEST HAPPENS HERE!!! and fills our mProductList with a lot of Objects <Products>;
        productFeed.getFeed( url );

        ProductAdapter productAdapter = new ProductAdapter(this, productFeed.getTheListFeed());
        mProductList.setAdapter(productAdapter);
  //      ArrayAdapter<Product> listAdapter = new ArrayAdapter<Product>(this, R.layout.our_product_list_layout, productFeed.getTheListFeed());
  //      mProductList.setAdapter(listAdapter);

        // The search Engine is here!

        editTextPretraga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // s - is the String that has been entered by the user into the search EditText widget;
                (  (ArrayAdapter<Product>) mProductList.getAdapter() ).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
