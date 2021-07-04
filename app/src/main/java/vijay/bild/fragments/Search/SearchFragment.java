package vijay.bild.fragments.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;


import vijay.bild.Adapter.TagAdapter;
import vijay.bild.Adapter.UserAdapter;

import vijay.bild.R;
import vijay.bild.model.User;

public class SearchFragment extends Fragment {
        private RecyclerView recyclerView;
        private SocialAutoCompleteTextView searchBar;
        private List<User> mUsers;
        private UserAdapter userAdapter;

        private RecyclerView recyclerViewTags;
        private List<String> mHashTags;
        private List<String> mHashTagCount;
        private TagAdapter tagAdapter;

        private SocialAutoCompleteTextView search_bar;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
                View view =  inflater.inflate(R.layout.fragment_search, container, false);

                recyclerView = view.findViewById(R.id.recycle_view_users);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                recyclerViewTags = view.findViewById(R.id.recycle_view_tags);
                recyclerViewTags.setHasFixedSize(true);
                recyclerViewTags.setLayoutManager(new LinearLayoutManager(getContext()));

                mHashTags = new ArrayList<>();
                mHashTagCount = new ArrayList<>();
                tagAdapter = new TagAdapter(getContext() , mHashTags , mHashTagCount);
                recyclerViewTags.setAdapter(tagAdapter);

                mUsers =  new ArrayList<>();
                userAdapter = new UserAdapter(getContext() , mUsers,true);
                recyclerView.setAdapter(userAdapter);

                search_bar = view.findViewById(R.id.search_bar);

                readUsers();
                readTags();

                search_bar.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                searchUsers(s.toString());

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                                filters(s.toString());

                        }
                });

                return view;
        }

        private void readTags() {
                FirebaseDatabase.getInstance().getReference().child("HashTags").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                mHashTags.clear();
                                mHashTagCount.clear();

                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        mHashTags.add(dataSnapshot.getKey());
                                        mHashTagCount.add(dataSnapshot.getChildrenCount() + "");
                                }
                                tagAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                });
        }

        private void readUsers() {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (TextUtils.isEmpty(search_bar.getText().toString())){
                                        mUsers.clear();
                                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                                User user = snapshot1.getValue(User.class);
                                                mUsers.add(user);
                                        }

                                        userAdapter.notifyDataSetChanged();
                                }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                });
        }

        private void searchUsers (String s){
                Query query = FirebaseDatabase.getInstance().getReference().child("Users")
                        .orderByChild("Username").startAt(s).endAt(s + "\uf8ff");

                query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                mUsers.clear();
                                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                        User user = snapshot1.getValue(User.class);
                                        mUsers.add(user);
                                }

                                userAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                });
        }

        private void filters (String text){
                List<String> mSearchTags = new ArrayList<>();
                List<String> mSearchTagCount = new ArrayList<>();

                for(String s : mHashTags){
                        if (s.toLowerCase().contains(text.toLowerCase())){
                                mSearchTags.add(s);
                                mSearchTagCount.add(mHashTagCount.get(mHashTags.indexOf(s)));
                        }
                }

                tagAdapter.filter(mSearchTags, mSearchTagCount);
        }

}

