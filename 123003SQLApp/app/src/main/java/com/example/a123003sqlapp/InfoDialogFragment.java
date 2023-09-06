package com.example.a123003sqlapp;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * InfoDialogFragmnet that displays animal info after select button pressed
 *
 * @author 123003
 * @version 05/13/22
 */
public class InfoDialogFragment extends DialogFragment {

    private String name;
    private String email;
    private int index;

    public InfoDialogFragment() {
        // Required empty public constructor
    }

    public static InfoDialogFragment newInstance(String name, String email, int index) {
        InfoDialogFragment fragment = new InfoDialogFragment();

        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("email", email);
        args.putInt("index", index);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            name = getArguments().getString("name");
            email = getArguments().getString("email");
            index = getArguments().getInt("index");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView name1 = view.findViewById(R.id.name);
        TextView email1 = view.findViewById(R.id.email);
        ImageView image = view.findViewById(R.id.image);

        name1.setText("Name searched for: "+ name);
        email1.setText("Email for this name: "+ email);

        TypedArray images = getResources().obtainTypedArray(R.array.animals);

        image.setImageDrawable(images.getDrawable(index - 1));

        view.findViewById(R.id.ok).setOnClickListener(v -> dismiss());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_info_dialog_fragment, container, false);
    }
}