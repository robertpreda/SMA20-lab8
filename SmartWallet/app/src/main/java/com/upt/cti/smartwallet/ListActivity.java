package com.upt.cti.smartwallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

// lab is deprecated

public class ListActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private int currentMonth;
    private List<Payment> paymentList = new ArrayList<>();

    private TextView tStatus;
    private Button bPrevious, bNext;
    private FloatingActionButton fabAdd;
    private ListView listPayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        tStatus = findViewById(R.id.tStatus);
        bPrevious = findViewById(R.id.bPrevious);
        bNext = findViewById(R.id.bNext);
        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddPaymentActivity.class);
                startActivity(intent);
            }
        });


        listPayments = findViewById(R.id.listPayments);
        final PaymentAdapter adapter = new PaymentAdapter(this, R.layout.item_payment, paymentList); // crapa asta, copy paste din lab
        listPayments.setAdapter(adapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        databaseReference = database.getReference();
        databaseReference.child("wallet").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String name = (String)ds.child("name").getValue();
                    String type = (String)ds.child("type").getValue();
                    double cost = (double)ds.child("cost").getValue();
                    String timestamp = (String)ds.getKey();
                    Payment payment = new Payment();
                    payment.setName(name);
                    payment.setCost(cost);
                    payment.setType(type);
                    payment.setTimestamp(timestamp);

                    paymentList.add(payment);


                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    public void fabClicked(View view){
//
//    }

    public void clicked(View view){

    }
}