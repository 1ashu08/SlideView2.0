package com.app.ashu.slideview.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.ashu.slideview.R;
import com.app.ashu.slideview.utility.SpecificUserMessage;
import com.app.ashu.slideview.utility.UserLog;

import java.util.ArrayList;


public class ChatFragment extends Fragment {

    ListView chatLog;

    ArrayAdapter<String> chatLogAdapter;
    ArrayList<String> chatLogList=new ArrayList<String>();
    public ChatFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chatLog=(ListView)view.findViewById(R.id.chatLog);

        chatLogAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,chatLogList
        );

        refreshChatLog();
        chatLog.setAdapter(chatLogAdapter);

        chatLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("In the text View "+position+"  "+id+" "+view.toString());

                GotoMessage(id);
            }

        });

        ContactsFragment.setDynamicHeight(chatLog);

    }

    public void GotoMessage(long id)
    {
        int i=(int)id;
        SpecificUserMessage spUser=new SpecificUserMessage();
        String name=chatLogList.get(i);

        int ret=checkUniqueChat(name);

        if(ret==-1)
        {
            UserLog newUser=new UserLog();
            newUser.setdName(name);

            SpecificUserMessage.user.add(newUser);
            SpecificUserMessage.index=SpecificUserMessage.user.size()-1;
        }
        else
        {
            SpecificUserMessage.index=ret;
        }

        Intent msg=new Intent(getContext(),SpecificUserMessage.class);
        this.startActivity(msg);

        //finish();

    }

    public int checkUniqueChat(String dname)
    {
        for(int i=0;i<SpecificUserMessage.user.size();i++)
        {
            if(SpecificUserMessage.user.get(i).getdName().equalsIgnoreCase(dname))
            {
                return i;
            }
        }
        return -1;
    }

    public void refreshChatLog()
    {
        chatLogList.clear();

        for(int i=0;i<SpecificUserMessage.user.size();i++)
        {
            chatLogList.add(SpecificUserMessage.user.get(i).getdName());
        }

        System.out.println("Size of chatLogList "+chatLogList.size());

        chatLogAdapter.notifyDataSetChanged();
        ContactsFragment.setDynamicHeight(chatLog);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //System.out.println("Selected Menu "+item);

        if(item.toString().equalsIgnoreCase("Refresh"))
        {
           refreshChatLog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chat_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
