package com.example.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dashboard.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class CatTwoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public CatTwoFragment() {
        // Required empty public constructor
    }

    public static CatTwoFragment newInstance(String param1, String param2) {
        CatTwoFragment fragment = new CatTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

   private RecyclerView recyclerView;

    private DatabaseReference mCatTwoDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myview =  inflater.inflate(R.layout.fragment_cat_two, container, false);

        mCatTwoDatabase = FirebaseDatabase.getInstance().getReference().child("CatTwoDatabase");

        recyclerView = myview.findViewById(R.id.recycler_cat_two_data);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        return myview;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Data> option2 = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mCatTwoDatabase,Data.class).build();
        FirebaseRecyclerAdapter<Data, CatTwoFragment.CatTwoViewHolder> adapter2 = new FirebaseRecyclerAdapter<Data, CatTwoViewHolder>(option2) {
            @Override
            protected void onBindViewHolder(@NonNull CatTwoViewHolder holder, int position, @NonNull Data model) {

                holder.setTitle(model.getTitle());
                holder.setDescription(model.getDescription());
                holder.setImage(model.getImage());

            }

            @NonNull
            @Override
            public CatTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_item_data,parent,false);
                return new CatTwoViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter2);
        adapter2.startListening();
    }

    public static class CatTwoViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public CatTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String title){
            TextView mTitle=mView.findViewById(R.id.post_title);
            mTitle.setText(title);
        }
        public void setDescription (String description){
            TextView mTitle=mView.findViewById(R.id.post_details);
            mTitle.setText(description);
        }
        public void setImage(String image){
            ImageView imageView =mView.findViewById(R.id.post_image);

            Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                    Picasso.get().load(image).into(imageView);

                }
            });
        }
    }
}