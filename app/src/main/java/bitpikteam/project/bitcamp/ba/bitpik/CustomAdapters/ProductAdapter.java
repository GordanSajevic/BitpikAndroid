package bitpikteam.project.bitcamp.ba.bitpik.CustomAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bitpikteam.project.bitcamp.ba.bitpik.Models.Product;
import bitpikteam.project.bitcamp.ba.bitpik.R;
import bitpikteam.project.bitcamp.ba.bitpik.singletons.ProductFeed;

/**
 * Created by nedzadhamzic on 14/04/15.
 */
public class ProductAdapter extends ArrayAdapter<Product> {

    private List<Product> items;
    private Filter mFilter;

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

        String currentImagePath = currentProduct.getProductImagePath();
         //   String asdasd = R.string.service_base_noport
         //         + currentImagePath;
        // currentImagePath.replace("http://localhost:9000", String.valueOf(R.string.service_base_ip));
         // String img =getString(R.string.image_path) + currentProduct.getProductImagePath();
         //  img = img.replaceAll("\\\\","/");
         //   Picasso.with(getContext()).load(img).into(slika);

        Picasso.with(getContext()).load("http://icons.iconarchive.com/icons/martin-berube/animal/256/lion-icon.png").into(slika);

        ime.setText(currentProduct.getName());
        opis.setText(currentProduct.getDescription());
        cijena.setText(String.valueOf(currentProduct.getPrice()));


        return convertView;
    }

    @Override
    public Filter getFilter(){
        if(mFilter == null){
            mFilter = new ProductFilter();
        }
        return mFilter;
    }

   @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Product getItem(int position) {
        return items.get(position);
    }


    /**
     * Filter Engine for String Names of Product and Price of Products;
     */
    public class ProductFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint == null || constraint.length() == 0){
                List<Product> origin = ProductFeed.getInstance().getTheListFeed();
                results.values = origin;
                results.count = origin.size();
            } else{
                String searchString = constraint.toString().toLowerCase();
                List<Product> filteredList = new ArrayList<Product>();
                for(int i=0; i<items.size(); i++){
                    Product p = items.get(i);
                    String productName = p.getName().toLowerCase();
                    String productPrice = String.valueOf(p.getPrice());
                    if(productName.contains(searchString) || productPrice.contains(searchString)){
                        filteredList.add(p);
                    }
                }
                results.values = filteredList;
                results.count = filteredList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (ArrayList<Product>)results.values;
            notifyDataSetChanged();
        }
    }

}


