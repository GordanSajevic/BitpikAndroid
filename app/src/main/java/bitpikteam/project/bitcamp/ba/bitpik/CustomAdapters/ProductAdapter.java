package bitpikteam.project.bitcamp.ba.bitpik.CustomAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bitpikteam.project.bitcamp.ba.bitpik.Models.Product;
import bitpikteam.project.bitcamp.ba.bitpik.R;

/**
 * Created by nedzadhamzic on 14/04/15.
 */
public class ProductAdapter extends ArrayAdapter<Product> {

    private List<Product> items;

    public ProductAdapter(Context context, List<Product> items) {
        super(context, 0, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product currentProduct = items.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.our_product_list_layout, parent, false);
        }

        ImageView slika = (ImageView)convertView.findViewById(R.id.slika);
        TextView ime = (TextView)convertView.findViewById(R.id.ime);
        TextView opis = (TextView)convertView.findViewById(R.id.opis);
        TextView cijena = (TextView)convertView.findViewById(R.id.cijena);
        Picasso.with(getContext()).load("http://www.dexternet.info/images/pik.bablack.png").into(slika);

        ime.setText(currentProduct.getName());
        opis.setText(currentProduct.getDescription());
        cijena.setText(String.valueOf(currentProduct.getPrice()));


        return convertView;
    }


}


