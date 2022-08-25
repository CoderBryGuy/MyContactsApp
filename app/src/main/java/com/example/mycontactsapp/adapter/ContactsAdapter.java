package com.example.mycontactsapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mycontactsapp.MainActivity;
import com.example.mycontactsapp.R;
import com.example.mycontactsapp.db.entity.Contact;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Contact> mContactArrayList;
    private MainActivity mMainActivity;

    public ContactsAdapter(Context context, ArrayList<Contact> contacts, MainActivity mainActivity) {
        this.mContext = context;
        this.mContactArrayList = contacts;
        this.mMainActivity = mainActivity;
    }


    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.contact_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, @SuppressLint("RecyclerView") int positions) {
            final Contact contact = mContactArrayList.get(positions);
            holder.name.setText(contact.getName());
            holder.email.setText(contact.getEmail());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMainActivity.addAndEditContacts(true, contact, positions);
                }
            });
    }

    @Override
    public int getItemCount() {
        return mContactArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView email;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.email = itemView.findViewById(R.id.email);
        }


    }


}
