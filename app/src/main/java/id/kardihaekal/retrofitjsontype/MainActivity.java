package id.kardihaekal.retrofitjsontype;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.kardihaekal.retrofitjsontype.Interface.Api;
import id.kardihaekal.retrofitjsontype.model.RecyclerAdapter;
import id.kardihaekal.retrofitjsontype.model.RecyclerAdapter.RecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;
  private RecyclerAdapter adapter;
  private List<Photos> photosList;
  RecyclerAdapter.RecyclerViewClickListener listener;
  String URL = "https://jsonplaceholder.typicode.com";
  ProgressBar progressBar;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getSupportActionBar().setTitle("Welcome");

    Resources res = this.getResources();
    ImageView imgBackground = (ImageView) findViewById(R.id.imgBackgroundMain);
    TransitionDrawable trans1 = (TransitionDrawable) res.getDrawable(R.drawable.transition1);
    imgBackground.setImageDrawable(trans1);
    trans1.startTransition(10000);

    recyclerView = findViewById(R.id.recyclerView);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    photosList = new ArrayList<>();

    listener = new RecyclerViewClickListener() {
      @Override
      public void onRowClick(View view, int position) {

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
       // intent.putExtra("albumId", photosList.get(position).getAlbumId());
        intent.putExtra("title", photosList.get(position).getTitle());
        intent.putExtra("url", photosList.get(position).getUrl());
        intent.putExtra("thumbnailUrl", photosList.get(position).getThumbnailUrl());
        startActivity(intent);

      }
    };

    getPhotos();


  }

  @Override
  protected void onResume() {
    getPhotos();
    super.onResume();
  }

  private void getPhotos() {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    Api service = retrofit.create(Api.class);
    Call<List<Photos>> call=   service.getData();
    call.enqueue(new Callback<List<Photos>>() {
      @Override
      public void onResponse(Call<List<Photos>> call, Response<List<Photos>> response) {

        photosList = response.body();
        Log.i(MainActivity.class.getSimpleName(), response.body().toString());
        adapter = new RecyclerAdapter(photosList, MainActivity.this, listener);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        }

      @Override
      public void onFailure(Call<List<Photos>> call, Throwable t) {
        Toast.makeText(MainActivity.this, "rp :"+
                t.getMessage().toString(),
            Toast.LENGTH_SHORT).show();
      }
    });
  }
}
