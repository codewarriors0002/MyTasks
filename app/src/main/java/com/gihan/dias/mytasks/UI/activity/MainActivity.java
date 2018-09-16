package com.gihan.dias.mytasks.UI.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gihan.dias.mytasks.UI.fragment.AddEditTaskFragment;
import com.gihan.dias.mytasks.UI.fragment.ProfileFragment;
import com.gihan.dias.mytasks.R;
import com.gihan.dias.mytasks.UI.fragment.TasksFragment;
import com.gihan.dias.mytasks.models.User;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView mivProfile;
    private TextView mtvName;
    private TextView mtvEmail;
    private User user;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        // get user details from database
        user = User.findById(User.class, (long) 1);

        mivProfile = (ImageView) headerView.findViewById(R.id.imgProfile);
        mtvName = (TextView) headerView.findViewById(R.id.txtName);
        mtvEmail = (TextView) headerView.findViewById(R.id.txtEmail);

        // call to displayProfileDetails for display profile
        displayProfileDetails();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadAddEditeFragment();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        loadTasksFragment();
    }

    private void displayProfileDetails() {
        if(user != null){
            String name = user.getName();
            String email = user.getEmailAddress();
            String profileImg = user.getProfileImgUrl();

            //img loading in to Image View using picasso image downloading and caching library
            Picasso.get().load(profileImg).into(mivProfile);
            mtvName.setText(name);
            mtvEmail.setText(email);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }







    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_profille :
                loadprofileFragment();
                break;

            case R.id.nav_task :
                loadTasksFragment();
                break;

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadprofileFragment(){
        ProfileFragment profileFragment = ProfileFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame,profileFragment,"messagesFragment");
        transaction.commit();
    }

    private void loadTasksFragment(){
        TasksFragment tasksFragment = TasksFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame,tasksFragment,"tasksFragment");
        transaction.commit();
    }

    private void loadAddEditeFragment(){
        AddEditTaskFragment addEditTaskFragment = AddEditTaskFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame,addEditTaskFragment,"addEditTaskFragment");
        transaction.commit();
    }

    // set AvtionBar title
    public void setActionBarTitle(int title) {
        getSupportActionBar().setTitle(title);
    }


}
