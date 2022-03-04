package com.example.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private AllFragment allFragment;
    private CatOneFragment catOneFragment;
    private CatTwoFragment catTwoFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allFragment=new AllFragment();
        catOneFragment=new CatOneFragment();
        catTwoFragment=new CatTwoFragment();

        setAllFragment(allFragment);
        ActionBar toolbar=getSupportActionBar();


        bottomNavigationView = findViewById(R.id.bottombar);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.all:
                        bottomNavigationView.setItemBackgroundResource(R.color.allbottom);
                        setAllFragment(allFragment);
                        return true;
                    case R.id.catone:
                        bottomNavigationView.setItemBackgroundResource(R.color.catone);
                        setAllFragment(catOneFragment);
                        return true;
                    case R.id.cattwo:
                        bottomNavigationView.setItemBackgroundResource(R.color.cattwo);
                        setAllFragment(catTwoFragment);
                        return true;

                    default:return false;
                }

            }
        });
    }
    public void setAllFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

         getMenuInflater().inflate(R.menu.main_menu,menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.idNotes:
                startActivity(new Intent(this,AddToDo.class));
                return  true;
            case R.id.idSignOut:
                startActivity(new Intent(this,SignIn.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}