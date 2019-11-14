package id.kardihaekal.retrofitjsontype;

import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

  private TextView tvTitle, tvUrl;
  private CircleImageView ivCircle;
  private int albumId, id;

  ImageView imgBackground;
  TransitionDrawable trans1;
  TransitionDrawable trans2;
  TransitionDrawable trans3;
  TransitionDrawable trans4;
  Resources res;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    getSupportActionBar().setTitle("Detail Colour");

    res = this.getResources();
    imgBackground = (ImageView) findViewById(R.id.imgBackground);
    trans1 = (TransitionDrawable) res.getDrawable(R.drawable.trantition_a);
    trans2 = (TransitionDrawable) res.getDrawable(R.drawable.trantition_b);
    trans3 = (TransitionDrawable) res.getDrawable(R.drawable.trantition_c);
    trans4 = (TransitionDrawable) res.getDrawable(R.drawable.trantition_d);
    load();


    tvTitle = findViewById(R.id.tv_intent_title);
    tvUrl = findViewById(R.id.tv_intent_url);
    ivCircle = findViewById(R.id.iv_intent_thumbnail);

    String id = getIntent().getStringExtra("id");
    final String title = getIntent().getStringExtra("title");
    String url = getIntent().getStringExtra("url");
    String thumbnail = getIntent().getStringExtra("thumbnailUrl");

    ivCircle.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        Toast.makeText(DetailActivity.this,"Judul warna yang kamu pilih adalah :" +title, Toast.LENGTH_SHORT).show();

      }
    });

    tvTitle.setText("Judul :" +title);
    tvUrl.setText("Alamat :" +url);

    RequestOptions requestOptions = new RequestOptions();
    requestOptions.skipMemoryCache(true);
    requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
    requestOptions.placeholder(R.drawable.ic_launcher_background);
    requestOptions.error(R.drawable.ic_launcher_background);

    Glide.with(DetailActivity.this)
        .load(thumbnail)
        .apply(requestOptions)
        .into(ivCircle);

  }

  private void load() {

    new CountDownTimer(8000, 7000) {
      public void onTick(long millisUntilFinished) {

      }

      public void onFinish() {
        repeat();
      }

    }.start();

  }

  private void repeat() {
    new CountDownTimer(7000, 6000) {
      public void onTick(long millisUntilFinished) {
        trans1.startTransition(4000);
        imgBackground.setImageDrawable(trans1);
      }

      public void onFinish() {
        imgBackground.setImageDrawable(trans2);
        trans2.startTransition(4000);

        new CountDownTimer(7000, 6000) {
          public void onTick(long millisUntilFinished) {

          }

          public void onFinish() {
            imgBackground.setImageDrawable(trans3);
            trans3.startTransition(4000);

            new CountDownTimer(7000, 6000) {
              public void onTick(long millisUntilFinished) {

              }

              public void onFinish() {
                imgBackground.setImageDrawable(trans4);
                trans4.startTransition(4000);

                new CountDownTimer(7000, 6000) {
                  public void onTick(long millisUntilFinished) {

                  }

                  public void onFinish() {
                    repeat();
                  }
                }.start();
              }
            }.start();
          }
        }.start();
      }
    }.start();

  }
}
