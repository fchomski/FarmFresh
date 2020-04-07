package com.example.farmfresh.model.adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.data.Item;
import com.example.farmfresh.model.data.enums.Key;
import com.example.farmfresh.ui.search.SingleItem;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;

public class ItemCardAdaptor extends RecyclerView.Adapter<ItemCardAdaptor.PlaceHolder> {
    private Context context;
    private ArrayList<Item> items;

    public ItemCardAdaptor(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new PlaceHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
        Item item = items.get(position);
        holder.setUp(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PlaceHolder extends RecyclerView.ViewHolder {
        // todo convert image.
        private TextView itemName, itemPrice, itemSeller, itemCategory;
        private ImageView itemImage;

        PlaceHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.cardItemName);
            itemPrice = itemView.findViewById(R.id.cardItemPrice);
            itemSeller = itemView.findViewById(R.id.cardItemSeller);
            itemImage = itemView.findViewById(R.id.cardItemImage);
            itemCategory = itemView.findViewById(R.id.cardItemCategory);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void setUp(final Item item) {
            itemName.setText((String) item.get(Key.Item.ITEM_NAME));
            itemPrice.setText((String) item.get(Key.Item.PRICE));
            itemSeller.setText((String) item.get(Key.Item.SELLER_NAME));
            itemCategory.setText(item.get(Key.Item.CATEGORY).toString());

            String base64Image = (String) item.get(Key.Item.IMAGE_BASE64);
            byte[] byteArray = base64ImgToByteArray(base64Image);
            itemImage.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View e) {
                    Context context = e.getContext();
                    Intent intent = new Intent(context, SingleItem.class);
                    try {
                        intent.putExtra("item", item.toJson().toString());
                        context.startActivity(intent);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        private byte[] base64ImgToByteArray(String base64Image) {
            Decoder decoder = Base64.getDecoder();
            return decoder.decode(base64Image);
        }
    }
}
