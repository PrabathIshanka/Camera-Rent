package com.example.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class CatOneFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;

   //Firebase Reference
    private DatabaseReference mCatOneDatabase;



    private String mParam1;
    private String mParam2;

    public CatOneFragment() {
        // Required empty public constructor
    }

    public static CatOneFragment newInstance(String param1, String param2) {
        CatOneFragment fragment = new CatOneFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View myview =  inflater.inflate(R.layout.fragment_cat_one, container, false);



       mCatOneDatabase= FirebaseDatabase.getInstance( ).getReference().child("CatOneDatabase");
       mCatOneDatabase.keepSynced(true);


       recyclerView = myview.findViewById(R.id.recycler_cat_one);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

       return myview;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Data> option1 = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mCatOneDatabase,Data.class).build();
        FirebaseRecyclerAdapter<Data,CatOneViewHolder>adapter1 = new FirebaseRecyclerAdapter<Data, CatOneViewHolder>(option1) {
            @Override
            protected void onBindViewHolder(@NonNull CatOneViewHolder holder, int position, @NonNull Data model) {

                holder.setTitle(model.getTitle());
                holder.setDescription(model.getDescription());
                holder.setImage(model.getImage());

            }

            @NonNull
            @Override
            public CatOneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_item_data,parent,false);
                return new CatOneViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter1);
        adapter1.startListening();

    }

    public static class CatOneViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public CatOneViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String title){
            TextView mTitle = mView.findViewById(R.id.post_title);
            mTitle.setText(title);
        }
        public void setDescription(String description){
            TextView mTitle = mView.findViewById(R.id.post_details);
            mTitle.setText(description);
        }
        public void setImage(String image){
            ImageView mImage = mView.findViewById(R.id.post_image);

            Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(mImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                    Picasso.get().load(image).into(mImage);

                }
            });


        }


    }
}