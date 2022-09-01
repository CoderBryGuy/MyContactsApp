package com.example.mycontactsapp;

import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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


    public void addAndEditContacts(final boolean isUpdated, final Contact contact, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.layout_add_contact, null);

        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alerDialogBuilder.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final EditText newContact = view.findViewById(R.id.name);
        final EditText contactEmail = view.findViewById(R.id.email);

        contactTitle.setText(!isUpdated ? "Add New Contact" : "Edit Contact");

        if (isUpdated && contact != null) {
            newContact.setText(contact.getName());
            contactEmail.setText(contact.getEmail());
        }

        alerDialogBuilder.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (isUpdated) {
                                    DeletedContact(contact, position);
                                } else {
                                    dialog.cancel();
                                }
                            }
                        });

        final AlertDialog alertDialog = alerDialogBuilder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(newContact.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please Enter a Name", Toast.LENGTH_SHORT).show();

                    return;
                } else {
                    alertDialog.dismiss();
                }

                if (isUpdated && contact != null) {
                    UpdateContact(newContact.getText().toString(), contactEmail.getText().toString(), position);
                } else {
                    CreateContact(newContact.getText().toString(), contactEmail.getText().toString());
                }

            }
        });
    }

    private void DeletedContact(Contact contact, int position) {
        mContactArrayList.remove(position);
        db.deleteContact(contact);
        mContactsAdapter.notifyDataSetChanged();
    }

    private void UpdateContact(String name, String email, int position) {
        Contact contact = mContactArrayList.get(position);

        contact.setEmail(email);
        contact.setName(name);

        db.updateContact(contact);
        mContactArrayList.set(position, contact);
        mContactsAdapter.notifyDataSetChanged();
    }

    private void CreateContact(String name, String email) {
        long id = db.insertContact(name, email);
        Contact contact = db.getContact(id);

        if (contact != null) {
            mContactArrayList.add(0, contact);
            mContactsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull @NotNull Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
