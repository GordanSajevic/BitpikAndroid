package bitpikteam.project.bitcamp.ba.bitpik.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import bitpikteam.project.bitcamp.ba.bitpik.R;

public class ShowProductActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String productImagePath = intent.getStringExtra("productImagePath");


        EditText productName = (EditText) findViewById(R.id.productName);
        EditText productDesc = (EditText) findViewById(R.id.productDesc);
        EditText productPrice = (EditText) findViewById(R.id.productPrice);
        ImageView productImage = (ImageView) findViewById(R.id.productImage);
        productName.setText(name);
        productDesc.setText(description);
        productPrice.setText(price);
        Picasso.with(getBaseContext()).load("http://icons.iconarchive.com/icons/martin-berube/animal/256/lion-icon.png").into(productImage);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_product, menu);
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
