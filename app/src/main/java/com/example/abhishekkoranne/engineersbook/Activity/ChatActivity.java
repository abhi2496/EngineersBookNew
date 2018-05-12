package com.example.abhishekkoranne.engineersbook.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.abhishekkoranne.engineersbook.Adapter.ChatAdapter;
import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.Chat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class ChatActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    RecyclerView rvchat;
    int myUserId = 1, otherUserId = 2;
    EditText etmsg;
    ImageButton send;
    ChatAdapter adapt;
    final ArrayList<Chat> chatList = new ArrayList<>();
    long timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rvchat = findViewById(R.id.rvchat);

        adapt = new ChatAdapter(this, chatList, myUserId, otherUserId);
        rvchat.setAdapter(adapt);
        rvchat.setLayoutManager(new LinearLayoutManager(this));

        etmsg = (EditText) findViewById(R.id.etmsg);
        send = (ImageButton) findViewById(R.id.send);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        myRef.child(getUserIdNode(myUserId, otherUserId))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        chatList.clear();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Chat chat = postSnapshot.getValue(Chat.class);
                            Log.d("Error", "Value is: " + chat.getMessage());
                            chatList.add(chat);
                        }

                        adapt.notifyDataSetChanged();

                        if (chatList.size() > 0) {
                            rvchat.scrollToPosition(chatList.size() - 1);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Error", "Failed to read value.", error.toException());
                    }
                });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etmsg.getText().equals("")) {
                    Toast.makeText(ChatActivity.this, "Please Enter some Message!", Toast.LENGTH_SHORT).show();
                } else {
                    timestamp = Calendar.getInstance().getTimeInMillis();
                    Chat chat = new Chat(myUserId, etmsg.getText().toString().trim(), timestamp);

                    myRef.child(getUserIdNode(myUserId, otherUserId))
                            .child(timestamp + "-" + myUserId)
                            .setValue(chat);

                    etmsg.setText("");
                    chatList.clear();
                }
            }
        });
    }

    private String getUserIdNode(int myUserId, int otherUserId) {
        if (myUserId < otherUserId) {
            return myUserId + "-" + otherUserId;
        } else {
            return otherUserId + "-" + myUserId;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();

                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}