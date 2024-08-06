    package com.example.besiktasdeneme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

    public class    MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomBar;
    private Fragment homeFragment= new HomeFragment();
    private Fragment teamFragment= new TeamFragment();
    private Fragment tableFragment= new TableFragment();
    private Fragment fixtureFragment=new FixtureFragment();
    private Fragment anthemFragment=new AnthemFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.besiktaslogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        mBottomBar = (BottomNavigationView) findViewById(R.id.main_activty_bottomNavigationView);
        setFragment(homeFragment);

        mBottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottombar_menu_home:
                        setFragment(homeFragment);
                    case R.id.bottombar_menu_team:
                        setFragment(teamFragment);
                        break;
                    case R.id.bottombar_menu_fixture:
                        setFragment(fixtureFragment);
                        break;
                    case R.id.bottombar_menu_table:
                        setFragment(tableFragment);
                        break;
                    case R.id.bottombar_menu_anthem:
                        setFragment(anthemFragment);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }
        private void setFragment(Fragment fragment){

            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_activty_frameLayout,fragment);
            transaction.commit();
        }
    }
