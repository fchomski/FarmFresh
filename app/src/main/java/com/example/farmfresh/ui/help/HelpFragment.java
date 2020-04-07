package com.example.farmfresh.ui.help;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.State;
import com.example.farmfresh.model.data.enums.UserType;

public class HelpFragment extends Fragment {

    private HelpViewModel mViewModel;
    private TextView helpText;
    private View root;

    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_help, container, false);
        helpText = (TextView) root.findViewById(R.id.helpText);
        if (State.getInstance().getUserType() == UserType.BUYER) {
            helpText.setText(
                    "Please browse items from search menu or from categories on the main page. " +
                    "To find farmers near by please go to the near by menu");
        }

        else {
            helpText.setText(
                    "To locate your farm please use the locate farm button in the profile. " +
                    "To add new items please use the add Item button.");
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HelpViewModel.class);
        // TODO: Use the ViewModel
    }

}
