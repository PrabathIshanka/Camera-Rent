package com.example.dashboard;

import android.content.Intent;
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
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import okhttp3.Call;


public class AllFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static AllFragment newInstance(String param1, String param2) {
        AllFragment fragment = new AllFragment();
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

    private RecyclerView allRecycler;
    private RecyclerView recyclerViewCatTwo;
    private RecyclerView recyclerViewCatThree;

    //Firebase Database
    private DatabaseReference mCatOneDatabase;
    private DatabaseReference mCatTwoDatabase;
    private DatabaseReference mCatThreeDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_all, container, false);


        mCatOneDatabase = FirebaseDatabase.getInstance("https://uvindu-a73ab-default-rtdb.firebaseio.com").getReference("CatOneDatabase");
        mCatOneDatabase.keepSynced(true);
        mCatTwoDatabase = FirebaseDatabase.getInstance("https://uvindu-a73ab-default-rtdb.firebaseio.com").getReference("CatTwoDatabase");
        mCatTwoDatabase.keepSynced(true);
        mCatThreeDatabase = FirebaseDatabase.getInstance("https://uvindu-a73ab-default-rtdb.firebaseio.com").getReference("CatThreeDatabase");
        mCatThreeDatabase.keepSynced(true);


        //catOne Recycler
        allRecycler = myView.findViewById(R.id.recycler_all);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        allRecycler.setHasFixedSize(true);
        allRecycler.setLayoutManager(layoutManager);

        //catTwo Recycler
        recyclerViewCatTwo = myView.findViewById(R.id.recycler_two);

        LinearLayoutManager layoutManagerCatTwo = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerCatTwo.setStackFromEnd(true);
        layoutManagerCatTwo.setReverseLayout(true);

        recyclerViewCatTwo.setHasFixedSize(true);
        recyclerViewCatTwo.setLayoutManager(layoutManagerCatTwo);

        //catThree Recycler
        recyclerViewCatThree = myView.findViewById(R.id.recycler_three);

        LinearLayoutManager layoutManagerCatThree = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerCatTwo.setStackFromEnd(true);
        layoutManagerCatTwo.setReverseLayout(true);

        recyclerViewCatThree.setHasFixedSize(true);
        recyclerViewCatThree.setLayoutManager(layoutManagerCatThree);


        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Data> option1 = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mCatOneDatabase, Data.class).build();
        FirebaseRecyclerAdapter<Data, CatOneViewHolder> adapterOne =
                new FirebaseRecyclerAdapter<Data, CatOneViewHolder>(option1) {
                    @Override
                    protected void onBindViewHolder(@NonNull CatOneViewHolder holder, int position, @NonNull Data model) {

                        holder.setTitle(model.getTitle());
                        holder.setDescription(model.getDescription());
                        holder.setImage(model.getImage());

                        holder.myview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), CatOneDetailsActivity.class);
                                intent.putExtra("title", model.getTitle());
                                intent.putExtra("description", model.getDescription());
                                intent.putExtra("image", model.getImage());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CatOneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
                        return new CatOneViewHolder(view);
                    }

                };

        allRecycler.setAdapter(adapterOne);
        adapterOne.startListening();


        FirebaseRecyclerOptions<Data> option2 = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mCatTwoDatabase, Data.class).build();
        FirebaseRecyclerAdapter<Data, CatTwoViewHolder> adapterTwo =
                new FirebaseRecyclerAdapter<Data, CatTwoViewHolder>(option2) {
                    @Override
                    protected void onBindViewHolder(@NonNull CatTwoViewHolder holder, int position, @NonNull Data model) {

                        holder.msetTitle(model.getTitle());
                        holder.msetDescription(model.getDescription());
                        holder.msetImage(model.getImage());

                        holder.mView.setOnClickListener(view -> {
                            Intent intent = new Intent(getActivity(), CatTwoDetailsActivity.class);
                            intent.putExtra("title", model.getTitle());
                            intent.putExtra("description", model.getDescription());
                            intent.putExtra("image", model.getImage());

                            startActivity(intent);
                        });
                    }

                    @NonNull
                    @Override
                    public CatTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
                        return new CatTwoViewHolder(view);
                    }
                };


        recyclerViewCatTwo.setAdapter(adapterTwo);
        adapterTwo.startListening();


        FirebaseRecyclerOptions<Data> option3 = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mCatThreeDatabase, Data.class).build();
        FirebaseRecyclerAdapter<Data, CatThreeViewHolder> adapterThree =
                new FirebaseRecyclerAdapter<Data, CatThreeViewHolder>(option3) {
                    @Override
                    protected void onBindViewHolder(@NonNull CatThreeViewHolder holder, int position, @NonNull Data model) {

                        holder.tsetTitle(model.getTitle());
                        holder.tsetDescription(model.getDescription());
                        holder.tsetImage(model.getImage());

                        holder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), CatThreeDetailsActivity.class);
                                intent.putExtra("title", model.getTitle());
                                intent.putExtra("description", model.getDescription());
                                intent.putExtra("image", model.getImage());
                                startActivity(intent);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CatThreeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
                        return new CatThreeViewHolder(view);
                    }
                };
        recyclerViewCatThree.setAdapter(adapterThree);
        adapterThree.startListening();

    }

    public static class CatOneViewHolder extends RecyclerView.ViewHolder {

        View myview;

        public CatOneViewHolder(@NonNull View itemView) {
            super(itemView);
            myview = itemView;
        }

        public void setTitle(String title) {
            TextView mTitle = myview.findViewById(R.id.title);
            mTitle.setText(title);
        }

        public void setDescription(String description) {
            TextView mDescription = myview.findViewById(R.id.description);
            mDescription.setText(description);
        }

        public void setImage(String image) {
            ImageView mImage = myview.findViewById(R.id.imageView);

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

    public static class CatTwoViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public CatTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void msetTitle(String title) {
            TextView mTitle = mView.findViewById(R.id.title);
            mTitle.setText(title);
        }

        public void msetDescription(String description) {
            TextView mDescription = mView.findViewById(R.id.description);
            mDescription.setText(description);
        }

        public void msetImage(String image) {
            ImageView mImageView = mView.findViewById(R.id.imageView);

            Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(mImageView, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                    Picasso.get().load(image).into(mImageView);
                }
            });

        }
    }

    public static class CatThreeViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public CatThreeViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void tsetTitle(String title) {
            TextView mTitle = mView.findViewById(R.id.title);
            mTitle.setText(title);
        }

        public void tsetDescription(String description) {
            TextView mDescription = mView.findViewById(R.id.description);
            mDescription.setText(description);
        }

        public void tsetImage(String image) {
            ImageView mImageView = mView.findViewById(R.id.imageView);

            Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(mImageView, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                    Picasso.get().load(image).into(mImageView);
                }
            });
        }
    }
}