package com.gihan.dias.mytasks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gihan.dias.mytasks.models.User;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment  {

    private User user;
    private ImageView mivProfileImg;
    private EditText metProfileName;
    private EditText metEmail;
    private Button mbtnUpdate;


    public ProfileFragment() {
        // Required empty public constructor
    }



    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = User.findById(User.class, (long) 1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        mivProfileImg = rootView.findViewById(R.id.imgProfile);
        metProfileName = rootView.findViewById(R.id.txtProfileName);
        metEmail = rootView.findViewById(R.id.txtEmail);
        mbtnUpdate = rootView.findViewById(R.id.btnUpdate);

        mbtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //call update user methode for update user details
                updateUser();
            }
        });

        displayProfileDetails();


        return rootView;
    }

    private void displayProfileDetails() {
        if(user != null){
            String name = user.getName();
            String email = user.getEmailAddress();
            String profileImg = user.getProfileImgUrl();

            //img loading in to Image View using picasso image downloading and caching library
            Picasso.get().load(profileImg).into(mivProfileImg);
            metProfileName.setText(name);
            metEmail.setText(email);
        }

    }

    private void updateUser() {
        if(user != null){

            String name = String.valueOf(metProfileName.getText());
            String email = String.valueOf(metEmail.getText());
            user.setName(name);
            user.setEmailAddress(email);
            user.save();
            Toast toast = Toast.makeText(getActivity(), "Updated!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
