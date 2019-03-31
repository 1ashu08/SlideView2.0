package com.app.ashu.slideview.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.app.ashu.slideview.Fragment.ContactsFragment;
import com.app.ashu.slideview.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText searchBoxMenu;

    ArrayAdapter<String> contactAdapter;
    public static ArrayList<String> contactList=new ArrayList<String>();
    ListView contactView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBoxMenu=(EditText)findViewById(R.id.searchBoxMenu);
        contactView=(ListView)findViewById(R.id.contactView);

        //contactList=ContactsFragment.permanentActieList;

        contactAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,contactList
        );

        contactView.setAdapter(contactAdapter);

        searchBoxMenu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //System.out.println("Before Chage");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(contactAdapter!=null)
                {
                    String text=searchBoxMenu.getText().toString();

                    if(text!=null && text.length()>0)
                    {
                        //System.out.println("In if ");
                        prepareSearchedList(text);
                        ContactsFragment.setDynamicHeight(contactView);
                    }
                    else
                    {
                        //System.out.println("In else block");
                        contactList.clear();
                        //System.out.println(permanentActieList.size());
                        for(int i=0;i<ContactsFragment.permanentActieList.size();i++)
                            contactList.add(ContactsFragment.permanentActieList.get(i));
                        contactAdapter.notifyDataSetChanged();

                        ContactsFragment.setDynamicHeight(contactView);
                    }
                }

                //System.out.println("After Chage");
            }
        });


        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        ContactsFragment.setDynamicHeight(contactView);
    }

    public static void refreshList()
    {
        for(int i=0;i<ContactsFragment.permanentActieList.size();i++)
            contactList.add(ContactsFragment.permanentActieList.get(i));
    }

    public void prepareSearchedList(String text)
    {
        ArrayList<String> newList=new ArrayList<String>();
        for(String data:ContactsFragment.permanentActieList)
        {
            text= text.toLowerCase();
            data=data.toLowerCase();
            if(data.contains(text))
            {
                newList.add(data);
            }
        }
        //System.out.println("Size is "+newList.size());
        contactList.clear();
        for(int i=0;i<newList.size();i++)
            contactList.add(newList.get(i));
        contactAdapter.notifyDataSetChanged();
    }

}
