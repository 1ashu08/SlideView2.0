package com.app.ashu.slideview.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ashu.slideview.MainActivity;
import com.app.ashu.slideview.R;
import com.app.ashu.slideview.utility.CreateContactList;
import com.app.ashu.slideview.utility.SpecificUserMessage;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {


    ListView inActiveMember,activeMember ;
    //RecyclerView activeMember;
    ArrayList<String> activeContacts,inActiveContacts ;
    ArrayAdapter<String> inActiveContactAdapter,activeContactAdapter;
    //ContactListAdapter activeContactAdapter;

    Cursor cursor ;
    String name, phonenumber ;
    public  static final int RequestPermissionCode  = 1 ;
    Button button;
    TextView txtView;
    EditText searchBox;

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inActiveMember=(ListView)view.findViewById(R.id.inActiveMember);
        activeMember=(ListView)view.findViewById(R.id.activeMember);
        searchBox=(EditText)view.findViewById(R.id.searchBox);


        button = (Button)view.findViewById(R.id.button1);

        activeContacts = new ArrayList<String>();
        inActiveContacts=new ArrayList<String>();

        EnableRuntimePermission();

        final CreateContactList createList=new CreateContactList();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String,ArrayList<String>> list=createList.getContactList(getContext());
                activeContacts=list.get("Active");
                permanentActieList=createList.getContactList(getContext()).get("Active");

                inActiveContacts=list.get("InActive");
                //GetContactsIntoArrayList();
                //new ArrayAdapter<String>();
                /*txtView=(TextView)findViewById(R.id.textView);
                txtView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        System.out.println("In the text View");
                    }
                });*/

                //activeContactAdapter = new ContactListAdapter(activeContacts);
                activeContactAdapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1,activeContacts
                );

                inActiveContactAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1,inActiveContacts
                );

                activeMember.setAdapter(activeContactAdapter);
                inActiveMember.setAdapter(inActiveContactAdapter);

                setDynamicHeight(activeMember);
                setDynamicHeight(inActiveMember);

                /*activeMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("In the text View ");

                        GotoMessage(v.getId());
                    }
                });*/
                activeMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("In the text View "+position+"  "+id+" "+view.toString());

                        GotoMessage(id);
                    }

                });

            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //System.out.println("Before Chage");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text=searchBox.getText().toString();

                /*if(text!=null)
                {
                    prepareSearchedList(text);
                }
                else
                {
                    activeContacts=permanentActieList;
                    activeContactAdapter.notifyDataSetChanged();
                }*/
                //System.out.println("On Chage");
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(activeContactAdapter!=null)
                {
                    String text=searchBox.getText().toString();

                    if(text!=null && text.length()>0)
                    {
                        //System.out.println("In if ");
                        prepareSearchedList(text);
                        setDynamicHeight(activeMember);
                    }
                    else
                    {
                        //System.out.println("In else block");
                        activeContacts.clear();
                        //System.out.println(permanentActieList.size());
                        for(int i=0;i<permanentActieList.size();i++)
                            activeContacts.add(permanentActieList.get(i));
                        activeContactAdapter.notifyDataSetChanged();

                        setDynamicHeight(activeMember);
                    }
                }

                //System.out.println("After Chage");
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setHasOptionsMenu(true);
    }

    ArrayList<String> permanentActieList=new ArrayList<String>();
    public void prepareSearchedList(String text)
    {
        ArrayList<String> newList=new ArrayList<String>();
        for(String data:permanentActieList)
        {
            text= text.toLowerCase();
            data=data.toLowerCase();
            if(data.contains(text))
            {
                newList.add(data);
            }
        }
        //System.out.println("Size is "+newList.size());
        activeContacts.clear();
        for(int i=0;i<newList.size();i++)
            activeContacts.add(newList.get(i));
        activeContactAdapter.notifyDataSetChanged();
    }


    public static void setDynamicHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        //check adapter if null
        if (adapter == null) {
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = height + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }


    public void GotoMessage(long id)
    {
        int i=(int)id;
        SpecificUserMessage.dName=activeContacts.get(i).split(":")[0];

        Intent msg=new Intent(getContext(),SpecificUserMessage.class);
        this.startActivity(msg);

        //finish();

    }

    /*public void GetContactsIntoArrayList(){

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            StoreContacts.add(name + " "  + ":" + " " + phonenumber);
        }

        cursor.close();

    }*/

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                getActivity(),
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(getContext(),"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getContext(),"Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getContext(),"Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contacts_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
