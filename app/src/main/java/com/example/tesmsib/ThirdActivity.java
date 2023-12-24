package com.example.tesmsib;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThirdActivity extends AppCompatActivity implements UserAdapter.OnUserItemClickListener {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> userList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPage = 1;
    private final int perPage = 10;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        userList = new ArrayList<>();
        adapter = new UserAdapter(this, userList, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Initialize ApiService
        apiService = RetrofitClient.getInstance().create(ApiService.class);

        // Set up EndlessRecyclerViewScrollListener for infinite scrolling
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextPage();
            }
        });

        // Set up pull to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        // Load initial data
        loadData();
    }

    private void loadNextPage() {
        currentPage++;
        loadData();
    }

    private void refreshData() {
        currentPage = 1;
        userList.clear();
        adapter.notifyDataSetChanged();
        loadData();
    }

    private void loadData() {
        Call<UserResponse> call = apiService.getUsers(currentPage, perPage);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> newUserList = response.body().getData();
                    userList.addAll(newUserList);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ThirdActivity.this, "Gagal mendapatkan data pengguna", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(ThirdActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onUserClick(User user) {
        // Menggunakan Intent untuk memberikan hasil kembali ke SecondActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selectedUserName", user.getFirstName() + " " + user.getLastName());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}