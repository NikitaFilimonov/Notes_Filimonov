package com.example.notes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.notes.observe.Publisher;
import com.example.notes.ui.SocialNetworkFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private String ABOUT_DIALOG_FRAGMENT_TAG = "about_dialog_fragment_tag";
    private DialogFragment aboutDialogFragment;

    private Navigation navigation;
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = new Navigation(getSupportFragmentManager());
        initView();
        getNavigation().addFragment(SocialNetworkFragment.newInstance(), false);
    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        initAboutDialog();
    }

    private void initAboutDialog() {
        aboutDialogFragment = new AboutDialogFragment();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return (toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });

    }

    private boolean navigateFragment(int id) {
        if (id == R.id.md_action_about) {
            showAboutDialog();
        }
        return false;
    }

    private void showAboutDialog() {
        aboutDialogFragment.show(getSupportFragmentManager(), ABOUT_DIALOG_FRAGMENT_TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        initSearchView(menu);
        initAboutButton(menu);

        return true;
    }

    private void initSearchView(Menu menu) {
        MenuItem search = menu.findItem(R.id.m_action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void initAboutButton(Menu menu) {
        MenuItem menuItemAbout = menu.findItem(R.id.m_action_about);

        menuItemAbout.setOnMenuItemClickListener(item -> {
            showAboutDialog();
            return false;
        });
    }
}