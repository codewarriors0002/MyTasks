package com.gihan.dias.mytasks;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.gihan.dias.mytasks.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;



public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;
    private SignInButton signIn;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN =9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        context = getApplicationContext();
        signIn = (SignInButton) findViewById(R.id.sign_in_button);
        signIn.setOnClickListener(this);



        //Configure Google Sign-in object
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Configure Google Sign-in object
         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sign_in_button:
                // get internet conection status
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                //check internet conection availabitity
                if(isConnected){
                     signIn();
                }else{
                    Toast toast = Toast.makeText(context, getResources().getString(R.string.noInternetConection), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;            
        }

    }

    private void signIn() {
        //Starting the user to select a Google account to sign in and
        // prompted to grant access to the requested resources.
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);

        } catch (ApiException e) {

            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null){
            String name = account.getDisplayName();
            String emailAddress = account.getEmail();
            String profileImgUrl = account.getPhotoUrl().toString();

            User user = new User(name, emailAddress, profileImgUrl);
            user.save();
          /*  com.gihan.dias.mytasks.models.Task task1 = new com.gihan.dias.mytasks.models.Task("task1","2015-08-11 ","type1");
            task1.save();
            com.gihan.dias.mytasks.models.Task task2 = new com.gihan.dias.mytasks.models.Task("task2","2015-08-12 ","type1");
            task2.save();
            com.gihan.dias.mytasks.models.Task task3 = new com.gihan.dias.mytasks.models.Task("task3","2015-08-13 ","type1");
            task3.save();
            com.gihan.dias.mytasks.models.Task task4 = new com.gihan.dias.mytasks.models.Task("task4","2015-08-14 ","type1");
            task4.save();
            com.gihan.dias.mytasks.models.Task task5 = new com.gihan.dias.mytasks.models.Task("task5","2018-09-16 ","type1");
            task5.save();
                */


            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
         if(account != null){
             updateUI(account);
         }



    }
}
