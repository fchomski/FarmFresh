package com.example.farmfresh.ui.additem;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.sax.TextElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.Item;
import com.example.farmfresh.model.data.Key;
import com.example.farmfresh.model.data.State;
import com.example.farmfresh.model.data.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddItem extends Fragment {

    private AddItemViewModel mViewModel;

    public static AddItem newInstance() {
        return new AddItem();
    }

    private String itemName;
    private Double price;
    private Double quantity;
    private String imageBase64;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        State s = State.getInstance();
        final String username = (String) s.getUser().get(Key.User.USER_NAME);

        final View root = inflater.inflate(R.layout.add_item_fragment, container, false);
        final TextInputEditText itemNameText = (TextInputEditText) root.findViewById(R.id.itemName);
        final TextInputEditText priceText = (TextInputEditText) root.findViewById(R.id.price);
        final TextInputEditText quantityText = (TextInputEditText) root.findViewById(R.id.quantity);
        Button imgSelectBtn = (Button) root.findViewById(R.id.imageSelectBtn);
        Button addItemBtn = (Button) root.findViewById(R.id.addItemBtn);

        imgSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {

            }
        });

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                try {
                    Connect c = new Connect(root.getContext());
                    Item newItem = new Item();
                    newItem.put(Key.Item.SELLER_NAME, username);

                    if (itemName    != null &&
                        price       != null &&
                        quantity    != null &&
                        imageBase64 != null) {
                        newItem.put(Key.Item.ITEM_NAME, itemName);
                        newItem.put(Key.Item.PRICE, Double.toString(price));
                        newItem.put(Key.Item.QUANTITY, Double.toString(price));
                        newItem.put(Key.Item.IMAGE_BASE64, imageBase64);
                    } else {
                        String toasterMsg = "invalid input, try again";
                        Toast toast = Toast.makeText(root.getContext(), toasterMsg, Toast.LENGTH_SHORT);
                        toast.setMargin(50, 50);
                        toast.show();
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
        // TODO: Use the ViewModel
    }

}
