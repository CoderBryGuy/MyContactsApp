package com.example.mycontactsapp;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mycontactsapp.adapter.ContactsAdapter;
import com.example.mycontactsapp.db.DataBaseHelper;
import com.example.mycontactsapp.db.entity.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   private ArrayList<Contact> mContactArrayList = new ArrayList<>();
   private RecyclerView mRecyclerView;
   private DataBaseHelper db;



    private ContactsAdapter mContactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getActionBar().setTitle("My Favorite Contacts");

        mRecyclerView = findViewById(R.id.recycler_view_contacts);
        db = new DataBaseHelper(this);

        mContactArrayList.addAll(db.getAllContacts());
        mContactsAdapter = new ContactsAdapter(this, mContactArrayList, MainActivity.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mContactsAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAndEditContacts(false, null, -1);
            }
        });


    }


    public void addAndEditContacts(boolean b, Contact contact, int positions) {

    }
}