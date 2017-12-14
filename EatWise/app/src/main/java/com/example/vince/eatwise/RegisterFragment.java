package com.example.vince.eatwise;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Display when hit "Register" to set up user's display name.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private View myView;
    private EditText displayName;
    private Button doneButton;
    private FirebaseAuth mAuth;
    private  FirebaseUser mUser;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.register_layout, container, false);
        getActivity().setTitle("Enter your user name");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        displayName = (EditText) myView.findViewById(R.id.display_name);
        doneButton = (Button) myView.findViewById(R.id.displayname_button);
        doneButton.setOnClickListener(this);

        return myView;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.displayname_button) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName.getText().toString()).build();
            mUser.updateProfile(profileUpdates);
            Intent intent = new Intent(getActivity(), NavigationDrawerActivity.class);
            startActivity(intent);
        }
    }
}
