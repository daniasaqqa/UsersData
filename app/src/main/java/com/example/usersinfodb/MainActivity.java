package com.example.usersinfodb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //  private static FirebaseFirestore fb;
    EditText etUserName;
    EditText etNumber;
    EditText etAddress;
    private static RecyclerView rcUsersData;
    private static Context ctx;
    private static FirebaseFirestore fb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserName = findViewById(R.id.et_username);
        etNumber = findViewById(R.id.et_number);
        etAddress = findViewById(R.id.et_address);
        rcUsersData = findViewById(R.id.rc_users_data);
        ctx = this;
        getDataU();
    }
// add data on firebase and get
    public void saveData(View view) {
        String userName = etUserName.getText().toString();
        String number = etNumber.getText().toString();
        String address = etAddress.getText().toString();

        Map<String, Object> users = new HashMap<>();
        users.put("UserName", userName);
        users.put("MobileNumber", number);
        users.put("Address", address);
        fb.collection("Users").add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("dna", "Success");
                        etUserName.setText("");
                        etNumber.setText("");
                        etAddress.setText("");
                        getDataU();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("dna", "Failure");
                    }
                });

    }


//get data function
    public static void getDataU() {
        ArrayList<UserDataModel> usersDataArr = new ArrayList<UserDataModel>();
        fb.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()
                            ) {
                                String id = doc.getId();
                                Map<String, Object> data = doc.getData();
                                UserDataModel uModel = new UserDataModel();
                                String userNameF = data.get("UserName").toString();
                                String mobileF = data.get("MobileNumber").toString();
                                String addressF = data.get("Address").toString();
                                uModel.setId(id);
                                uModel.setUserNameM(userNameF);
                                uModel.setMobileNumberM(mobileF);
                                uModel.setAddressM(addressF);
                                usersDataArr.add(uModel);

                            }

                            rcUsersData.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false));
                            rcUsersData.setHasFixedSize(true);

                            UserDataAdapter myAdapt = new UserDataAdapter(usersDataArr, ctx);

                            rcUsersData.setAdapter(myAdapt);

                        }

                    }
                });
    }
}