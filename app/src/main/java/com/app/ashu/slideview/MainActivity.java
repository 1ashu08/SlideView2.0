package com.app.ashu.slideview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.app.ashu.slideview.Fragment.CallsFragment;
import com.app.ashu.slideview.Fragment.ChatFragment;
import com.app.ashu.slideview.Fragment.ContactsFragment;

import com.app.ashu.slideview.activity.SearchActivity;
import com.app.ashu.slideview.adapter.ViewPagerAdapter;
import com.app.ashu.slideview.database_utility.BasicUtility;
import com.app.ashu.slideview.utility.CreateContactList;
import com.app.ashu.slideview.utility.User;

import java.util.ArrayList;
import java.util.HashMap;

import static com.app.ashu.slideview.Fragment.ContactsFragment.RequestPermissionCode;


public class MainActivity extends AppCompatActivity {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    ViewPagerAdapter adapter;

    //EditText searchBoxMenu;

    //Fragments

    ChatFragment chatFragment;
    CallsFragment callsFragment;
    ContactsFragment contactsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        //searchBoxMenu=(EditText)findViewById(R.id.searchBoxMenu);
        //searchBoxMenu.setVisibility(View.GONE);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        EnableRuntimePermission();
        //searchBoxMenu.getOnFocusChangeListener();

        try
        {
            if(flag==1)
            {

            }

        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }



    }

    public int flag=0;
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        // Associate searchable configuration with the SearchView
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_status:
                Toast.makeText(this, "Home Status Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_with_icon:
                Intent withouticon=new Intent(this,this.getClass());
                startActivity(withouticon);
                finish();
                return true;
            case R.id.action_search:
                System.out.println("Search Icon Clicked");
                Intent search_tab=new Intent(this,SearchActivity.class);
                startActivity(search_tab);
                //finish();
                return true;
                //searchBoxMenu.setVisibility(View.VISIBLE);
            /*case R.id.action_customtab:
                Intent custom_tab=new Intent(this,CustomTabActivity.class);
                startActivity(custom_tab);
                finish();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        callsFragment=new CallsFragment();
        chatFragment=new ChatFragment();
        contactsFragment=new ContactsFragment();
        adapter.addFragment(callsFragment,"CALLS");
        adapter.addFragment(chatFragment,"CHAT");
        adapter.addFragment(contactsFragment,"CONTACTS");
        viewPager.setAdapter(adapter);
    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

            flag=1;
            //createDB();
        } else {

            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,"Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();
                    flag=1;
                    //createDB();
                } else {

                    Toast.makeText(this,"Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void createDB()
    {
        CreateContactList createList=new CreateContactList();
        HashMap<String,ArrayList<String>> list=createList.getContactList(this);
        ArrayList<String> activeContacts=list.get("Active");

        ArrayList<User> userList=new ArrayList<User>();

        BasicUtility dbObject=new BasicUtility(this);
        int count=dbObject.getContactCount();

        for(String num:activeContacts)
        {
            User usr=new User();
            usr.setName(num);
            usr.setPhoneNumber(num);
            usr.setUnique_id(num);
            usr.setId(++count);
            userList.add(usr);
        }


        dbObject.insertData(userList);

        System.out.println("Insertion Complete");
    }
}