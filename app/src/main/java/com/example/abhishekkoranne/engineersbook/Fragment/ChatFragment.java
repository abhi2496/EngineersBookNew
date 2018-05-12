package com.example.abhishekkoranne.engineersbook.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishekkoranne.engineersbook.Activity.ChatActivity;
import com.example.abhishekkoranne.engineersbook.Activity.ContactsInNetworkActivity;
import com.example.abhishekkoranne.engineersbook.Adapter.ContactsAdapter;
import com.example.abhishekkoranne.engineersbook.R;
import com.example.abhishekkoranne.engineersbook.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    /*FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();*/
    RecyclerView rv_contacts;
    /*int myUserId = 1, otherUserId = 2;
    EditText etmsg;
    ImageButton send;
    ChatAdapter adapt;
    final ArrayList<Chat> chatList = new ArrayList<>();
    long timestamp;
    Button btn;*/ ArrayList<User> usersList = new ArrayList<>();
    FloatingActionButton fab_start_chat;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    ContactsAdapter adapt;

    public ChatFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        /*btn=(Button)getActivity().findViewById(R.id.call);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChatActivity.class));
            }
        });*/

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fab_start_chat = (FloatingActionButton) getActivity().findViewById(R.id.fab_start_chat);
     //   fab_start_chat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFabBackground)));
        fab_start_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContactsInNetworkActivity.class));
                //startActivity(new Intent(getActivity(), ChatActivity.class));
            }
        });

        final SharedPreferences shad = getActivity().getSharedPreferences("cookie", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=shad.edit();
        edit.putString("userId",1+"");

        usersList.clear();
        rv_contacts = (RecyclerView) getActivity().findViewById(R.id.rv_contacts);
//        User user1 = new User(1, "xx@xx.com", "abc@gmail.com", "Abhi", "Koranne", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
//        User user2 = new User(2, "abc@gmail.com", "abc@gmail.com", "JIGGY", "VYAS", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
//        User user3 = new User(3, "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8", "abc@gmail.com", "Shabbi", "SRK", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
//        User user4 = new User(4, "ac@gmail.com", "abc@gmail.com", "GABRU", "JAVA", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
//        User user5 = new User(5, "bc@gmail.com", "abc@gmail.com", "NABDU", "Dot NET", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
//        usersList.add(user1);
//        usersList.add(user2);
//        usersList.add(user3);
//        usersList.add(user4);
//        usersList.add(user5);

        adapt = new ContactsAdapter(getActivity(), usersList);
        rv_contacts.setAdapter(adapt);
        rv_contacts.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        databaseReference
                .child("Chatting")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        usersList.clear();

                        Log.d("Error", "userId: " + 1 + "");

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //ChatMessageModel post = postSnapshot.getValue(ChatMessageModel.class);
                            String[] arrayIds = postSnapshot.getKey().split("-");
                            if (arrayIds[0].equals(1 + "")) {
                                for (DataSnapshot snapshot : postSnapshot.child("chatInfo").getChildren()) {
                                    if (!snapshot.getKey().equals(1 + "")) {

                                        User user = new User(Integer.parseInt(snapshot.getKey()),
                                                "", "",
                                                (String) snapshot.getValue(), "", "");
                                        usersList.add(user);

                                        break;
                                    }
                                }
                            } else if (arrayIds[1].equals(1 + "")) {
                                for (DataSnapshot snapshot : postSnapshot.child("chatInfo").getChildren()) {
                                    if (!snapshot.getKey().equals(1 + "")) {

                                        usersList.add(new User(Integer.parseInt(snapshot.getKey()),
                                                "","",(String) snapshot.getValue(),"",""));

                                        break;
                                    }
                                }
                            }
                            //Log.d("Error", "Value is: " + post.getMessage() + " : " + post.getTimeStamp());
                        }
//                      chatAdapter = new ChatAdapter(ActivityChatListRecycler.this, chatList, sharedPref.gettingId(),  Integer.parseInt(snapshot.getKey()),sharedPref.gettingName(),);
//                        recyclerView.setAdapter(chatAdapter);


                        for (User chatInfoModel : usersList) {
                            Log.d("Error", chatInfoModel.getUserId() + ": " + chatInfoModel.getFirstName());

                        }

                        adapt = new ContactsAdapter(getActivity(), usersList);
                        rv_contacts.setAdapter(adapt);

                        //adapt.notifyDataSetChanged();
//                        if (chatList.size() > 0) {
//                            recyclerView.scrollToPosition(us.size() - 1);
//                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("error", "Failed to read value.", error.toException());
                    }
                });
    }
}