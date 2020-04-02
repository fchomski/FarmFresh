package com.example.farmfresh.ui.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.farmfresh.R;
import com.example.farmfresh.model.data.Key;
import com.example.farmfresh.model.data.State;
import com.example.farmfresh.model.data.UserType;

import java.io.BufferedReader;

public class ProfileFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        State s = State.getInstance();

        //Display user's selected username
        TextView userNameText = root.findViewById(R.id.userName);
        userNameText.setText((String) s.getUser().get(Key.User.USER_NAME));

        //TODO: Change this to user's full name rather than username
        TextView usernameFull = root.findViewById(R.id.userFullName);
        usernameFull.setText((String) s.getUser().get(Key.User.USER_NAME));

        //TODO: Set user's location to entered value, not default

        Button changePwdBtn = (Button) root.findViewById(R.id.changePwdBtn);
        Button pushItemBtn = (Button) root.findViewById(R.id.pushItemBtn);
        Button paymentDtlBtn = (Button) root.findViewById(R.id.paymentDtlsBtn);

        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });

        // set the visibility of button based on user type.
        if (s.getUserType() == UserType.BUYER) {
            pushItemBtn.setVisibility(View.INVISIBLE);
            paymentDtlBtn.setVisibility(View.VISIBLE);
        } else {
            pushItemBtn.setVisibility(View.VISIBLE);
            paymentDtlBtn.setVisibility(View.INVISIBLE);
        }
        return root;
    }
}
