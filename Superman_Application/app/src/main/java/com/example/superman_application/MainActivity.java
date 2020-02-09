package com.example.superman_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.superman_application.Adapter.RecycleAdapter;
import com.example.superman_application.Controller.IBrowseController;

import com.example.superman_application.DTO.BrowseDTO;
import com.example.superman_application.DTO.IssueDTO;
import com.example.superman_application.RetrofitApi.APIClient;
import com.example.superman_application.TokenHandling.Token;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ArrayList<IssueDTO> issueDTOArrayList = new ArrayList<>();

    RecycleAdapter recycleViewAdapter;

    RecyclerView recyclerView;



    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textView = findViewById(R.id.viewComics);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_id1);


        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        getIssueResponse();

    }
        private void getIssueResponse(){

            IBrowseController iBrowseController = APIClient.createService(IBrowseController.class, Token.getInstance());

            Call<List<IssueDTO>> call = iBrowseController.getComics(0);

            call.enqueue(new Callback<List<IssueDTO>>() {
                @Override
                public void onResponse(Call<List<IssueDTO>> call, Response<List<IssueDTO>> response) {
                    issueDTOArrayList = new ArrayList<>(response.body());
                    recycleViewAdapter = new RecycleAdapter(MainActivity.this, issueDTOArrayList);
                    recyclerView.setAdapter(recycleViewAdapter);
//                    List<IssueDTO> browseDTOS = response.body();
//
//                    recycleViewAdapter.addAllItems(browseDTOS);



                }

                @Override
                public void onFailure(Call<List<IssueDTO>> call, Throwable t) {
                    textView.setText(t.getMessage());
                }
            });

//        recycleViewAdapter = new RecycleAdapter(this);
//
//        recyclerView.setAdapter(recycleViewAdapter);




        }
}
