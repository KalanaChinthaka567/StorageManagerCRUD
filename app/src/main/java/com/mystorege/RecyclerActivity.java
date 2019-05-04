package com.mystorege;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity{
    private RecyclerView postList;
    private DatabaseReference databaseStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        databaseStore = FirebaseDatabase.getInstance().getReference().child("Global");
        databaseStore.keepSynced(true);

        postList = (RecyclerView)findViewById(R.id.recyclerView);
        postList.setHasFixedSize(true);
        postList.setLayoutManager(new LinearLayoutManager(this));

    }

    //    Setting up title menu buttons and processes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
//                firebaseSearch2(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
//                firebaseSearch2(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addNew) {
            Intent intent = new Intent(RecyclerActivity.this, MainActivity.class);
            startActivity(intent);
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }

//    Fetching data on activity start
    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<PostAdd, AddViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PostAdd, AddViewHolder>
                (PostAdd.class, R.layout.layout_home, AddViewHolder.class, databaseStore) {
            @Override
            protected void populateViewHolder(AddViewHolder viewHolder, final PostAdd model, final int position) {

                viewHolder.setDetails(getApplicationContext(), model.getStoreType(), model.getDimensions(), model.getStoreFeatures(), model.getMonthlyRental(), model.getNotes(), model.getReportName(), model.getpId());
                final String s = model.getpId();



                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder= new AlertDialog.Builder(RecyclerActivity.this);
                        builder.setIcon(R.drawable.ic_action_exit);
                        builder.setTitle("Delete");
                        builder.setMessage("Do you want to Delete this post or Change the added data? \n\n" +
                                "Storage Type: "+model.getStoreType()+"\nDimensions: "+model.getDimensions()+"Sq. Meters"+"\nStorage Feature: "+model.getStoreFeatures()
                                +"\nMonthly Rental: "+model.getMonthlyRental()+"\nNotes: "+ model.getNotes()+"\nReporter's Name: "+model.getReportName());
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = model.getpId();
                                databaseStore.child(id).child("storeType").setValue(null);
                                databaseStore.child(id).child("storeFeatures").setValue(null);
                                databaseStore.child(id).child("notes").setValue(null);
                                databaseStore.child(id).child("reportName").setValue(null);
                                databaseStore.child(id).child("dimensions").setValue(null);
                                databaseStore.child(id).child("monthlyRental").setValue(null);
                                databaseStore.child(id).child("monthlyRental").setValue(null);
                                databaseStore.child(id).child("date").setValue(null);
                                databaseStore.child(id).child("time").setValue(null);
                                databaseStore.child(id).child("pId").setValue(null);

                                Toast.makeText(getApplicationContext(), "Post Deleted Successfully", Toast.LENGTH_LONG).show();
//
                            }

                        }).setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(RecyclerActivity.this, EditDetails.class);
                                intent.putExtra("storeType", model.getStoreType());
                                intent.putExtra("id", model.getpId());
                                intent.putExtra("dimention", model.getDimensions());
                                intent.putExtra("storeFeatures", model.getStoreFeatures());
                                intent.putExtra("notes", model.getNotes());
                                intent.putExtra("monthlyRental", model.getMonthlyRental());
                                intent.putExtra("reporter", model.getReportName());

                                startActivity(intent);
                            }
                        }).setCancelable(true);
                        builder.show();
                    }
                });

                System.out.println("........................................................."+s);


            }
        };
        postList.setAdapter(firebaseRecyclerAdapter);
    }

    //Search Data by Storage Type
    private void firebaseSearch(String searchText){
        Query searchQuery = databaseStore.orderByChild("storeType").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerAdapter<PostAdd, AddViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PostAdd, AddViewHolder>
                (PostAdd.class, R.layout.layout_home, AddViewHolder.class, searchQuery) {
            @Override
            protected void populateViewHolder(AddViewHolder viewHolder, final PostAdd model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getStoreType(), model.getDimensions(), model.getStoreFeatures(), model.getMonthlyRental(), model.getNotes(), model.getReportName(), model.getpId());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder= new AlertDialog.Builder(RecyclerActivity.this);
                        builder.setIcon(R.drawable.ic_action_exit);
                        builder.setTitle("Delete");
                        builder.setMessage("Do you want to Delete this post or Change the added data? \n\n" +
                                "Storage Type: "+model.getStoreType()+"\nDimensions: "+model.getDimensions()+"Sq. Meters"+"\nStorage Feature: "+model.getStoreFeatures()
                                +"\nMonthly Rental: "+model.getMonthlyRental()+"\nNotes: "+ model.getNotes()+"\nReporter's Name: "+model.getReportName());
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = model.getpId();
                                databaseStore.child(id).child("storeType").setValue(null);
                                databaseStore.child(id).child("storeFeatures").setValue(null);
                                databaseStore.child(id).child("notes").setValue(null);
                                databaseStore.child(id).child("reportName").setValue(null);
                                databaseStore.child(id).child("dimensions").setValue(null);
                                databaseStore.child(id).child("monthlyRental").setValue(null);
                                databaseStore.child(id).child("monthlyRental").setValue(null);
                                databaseStore.child(id).child("date").setValue(null);
                                databaseStore.child(id).child("time").setValue(null);
                                databaseStore.child(id).child("pId").setValue(null);
//
                            }

                        }).setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(RecyclerActivity.this, EditDetails.class);
                                intent.putExtra("storeType", model.getStoreType());
                                intent.putExtra("id", model.getpId());
                                intent.putExtra("dimention", model.getDimensions());
                                intent.putExtra("storeFeatures", model.getStoreFeatures());
                                intent.putExtra("notes", model.getNotes());
                                intent.putExtra("monthlyRental", model.getMonthlyRental());
                                intent.putExtra("reporter", model.getReportName());

                                startActivity(intent);
                            }
                        }).setCancelable(true);
                        builder.show();
                    }
                });

            }
        };
        postList.setAdapter(firebaseRecyclerAdapter);
    }



//    Exit application When back button pressed
    @Override
    public void onBackPressed() {
            new AlertDialog.Builder(this)

                    .setIcon(R.drawable.ic_action_exit)
                    .setTitle("EXIT")
                    .setMessage("Are you sure you want to EXIT?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent a = new Intent(Intent.ACTION_MAIN);
                            a.addCategory(Intent.CATEGORY_HOME);
                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(a);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }


    public void deleteIndex(View v) {
//Do whatever you want when user clicks on your ImageButton
        showDeleteDataDialog();
    }

    private void showDeleteDataDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(RecyclerActivity.this);
        builder.
                setIcon(R.drawable.ic_action_exit)
                .setTitle("Delete")
                .setMessage("Are you sure you want to DELETE this entry?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Global");
                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String name = ds.getKey();
                                    System.out.println(name);

                                    TextView id = (TextView)findViewById(R.id.textViewId);
                                    if(id.getText().toString() == name){
                                        DeleteData(name);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        };
                        ref.addListenerForSingleValueEvent(eventListener);
                    }

                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void DeleteData(String id) {
        databaseStore.child("pId")
                .child("storeType")
                .child("dimensions")
                .child("date")
                .child("time")
                .child("storeFeatures")
                .child("monthlyRental")
                .child("notes")
                .child("reportName")
                .setValue(null);
    }

}
