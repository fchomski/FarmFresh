package com.example.farmfresh.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.enums.Key;
import com.example.farmfresh.model.data.State;
import com.example.farmfresh.model.data.enums.UserType;
import com.example.farmfresh.ui.maps.PinLocationMap;

public class ProfileFragment extends Fragment {

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);

        State s = State.getInstance();

        TextView userNameText = root.findViewById(R.id.userName);
        TextView usernameFull = root.findViewById(R.id.userFullName);
        TextView location = root.findViewById(R.id.locationText);
      
        //Display user's selected username
        System.out.println(s.getUser());
        userNameText.setText((String) s.getUser().get(Key.User.USER_NAME));

        //TODO: Change this to user's full name rather than username
        usernameFull.setText((String) s.getUser().get(Key.User.USER_NAME));

        //TODO: Set user's location to entered value, not default

        Button changePwdBtn = (Button) root.findViewById(R.id.changePwdBtn);
        Button pushItemBtn = (Button) root.findViewById(R.id.pushItemBtn);
        Button paymentDtlBtn = (Button) root.findViewById(R.id.paymentDtlsBtn);
        Button toCartBtn = (Button) root.findViewById(R.id.toCartBtn);
        Button locateFarmBtn = (Button) root.findViewById(R.id.locateFarmBtn);

       // set the visibility of button based on user type.
        System.out.println(s.getUser());
        if (s.getUserType() == UserType.BUYER) {
            pushItemBtn.setVisibility(View.INVISIBLE);
            locateFarmBtn.setVisibility(View.INVISIBLE);
            paymentDtlBtn.setVisibility(View.VISIBLE);
        } else {
            pushItemBtn.setVisibility(View.VISIBLE);
            locateFarmBtn.setVisibility(View.VISIBLE);
            paymentDtlBtn.setVisibility(View.INVISIBLE);
        }

        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });

        toCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                Intent intent = new Intent(getContext(), Cart.class);
                getContext().startActivity(intent);
            }
        });

        pushItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                startActivity(new Intent(getActivity(), AddItem.class));
            }
        });

        locateFarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                Intent pinMapIntent = new Intent(getContext(), PinLocationMap.class);
                getContext().startActivity(pinMapIntent);
            }
        });

        return root;
    }
}
