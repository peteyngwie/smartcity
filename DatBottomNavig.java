package com.tra.loginscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class DatBottomNavig extends AppCompatActivity {

    BottomNavigationView bottomNavigationView ; // this is an instance of bottom navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dat_bottom_navig);

        bottomNavigationView=findViewById(R.id.bottom_navigation) ; // focus on the bottom navigation bar
        // The following was assigned to add fragment is first . You can also change other one.
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new AddFragment()).commit() ;
        bottomNavigationView.setSelectedItemId(R.id.nav_add);  // Add item on navigation bar

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
            // Bottom navigation var on click event handling
                Fragment fragment = null ;   // Given a null fragment , then
                // The following will show all fragments you clicked .

                if (item.getItemId() == R.id.nav_add ) {
                    Toast.makeText(DatBottomNavig.this, "新增", Toast.LENGTH_SHORT).show();
                    fragment = new AddFragment();
                        assert(fragment!=null);



                    bottomNavigationView.getOrCreateBadge(R.id.nav_add).setVisible(true);

                    bottomNavigationView.getOrCreateBadge(R.id.nav_del).setVisible(false);
                    bottomNavigationView.getOrCreateBadge(R.id.nav_update).setVisible(false);
                    bottomNavigationView.getOrCreateBadge(R.id.nav_query).setVisible(false);


                }
                else if (item.getItemId() == R.id.nav_del ) {
                    Toast.makeText(DatBottomNavig.this, "刪除", Toast.LENGTH_SHORT).show();
                    fragment = new DelFragment() ;
                    assert(fragment!=null);

                    bottomNavigationView.getOrCreateBadge(R.id.nav_del).setVisible(true);

                    bottomNavigationView.getOrCreateBadge(R.id.nav_add).setVisible(false);
                    bottomNavigationView.getOrCreateBadge(R.id.nav_update).setVisible(false);
                    bottomNavigationView.getOrCreateBadge(R.id.nav_query).setVisible(false);

                }
                else if (item.getItemId() == R.id.nav_query) {
                    Toast.makeText(DatBottomNavig.this, "查詢", Toast.LENGTH_SHORT).show();
                    fragment = new QueryFragment() ;
                    assert(fragment!=null);
                    bottomNavigationView.getOrCreateBadge(R.id.nav_del).setVisible(false);

                    bottomNavigationView.getOrCreateBadge(R.id.nav_add).setVisible(false);
                    bottomNavigationView.getOrCreateBadge(R.id.nav_update).setVisible(false);
                    bottomNavigationView.getOrCreateBadge(R.id.nav_query).setVisible(true);
                }
                else {
                    Toast.makeText(DatBottomNavig.this, "更新", Toast.LENGTH_SHORT).show();

                    fragment = new UpdateFragment() ;
                    assert(fragment!=null);
                    bottomNavigationView.getOrCreateBadge(R.id.nav_del).setVisible(false);

                    bottomNavigationView.getOrCreateBadge(R.id.nav_add).setVisible(false);
                    bottomNavigationView.getOrCreateBadge(R.id.nav_update).setVisible(true);
                    bottomNavigationView.getOrCreateBadge(R.id.nav_query).setVisible(false);
                }
                // the following code is responsible for showing the fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragment).commit();

                return true ;
            }
        });


    }
}