package id.kardihaekal.retrofitjsontype.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;
import id.kardihaekal.retrofitjsontype.Photos;
import id.kardihaekal.retrofitjsontype.R;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {

  public List<Photos> list;
  private Context context;
  private RecyclerViewClickListener mListener;






  public RecyclerAdapter(List<Photos> list, Context context, RecyclerViewClickListener listener) {
    this.list = list;
    this.context = context;
    this.mListener = listener;
  }


  @NonNull
  @Override
  public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view,parent,false);
    MyHolder myHolder = new MyHolder(view, mListener);
    return myHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull MyHolder holder, int position) {

    holder.title.setText(list.get(position).getTitle());
    holder.url.setText(list.get(position).getUrl());

    RequestOptions requestOptions = new RequestOptions();
    requestOptions.skipMemoryCache(true);
    requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
    requestOptions.placeholder(R.drawable.ic_launcher_background);
    requestOptions.error(R.drawable.ic_launcher_background);

    Glide.with(context)
        .load(list.get(position).getThumbnailUrl())
        .apply(requestOptions)
        .into(holder.thumbnailUrl);


  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView title, url;
    private CircleImageView thumbnailUrl;
    private RecyclerViewClickListener mListener;
    private LinearLayout mRowContaioner;


    public MyHolder(@NonNull View itemView,  RecyclerViewClickListener listener) {
      super(itemView);
      thumbnailUrl= (CircleImageView) itemView.findViewById(R.id.thumbnail);
      url = (TextView) itemView.findViewById(R.id.tv_url);
      title = (TextView) itemView.findViewById(R.id.tv_tittle);
      mRowContaioner = itemView.findViewById(R.id.row_container);

      mListener = listener;
      mRowContaioner.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.row_container:
          mListener.onRowClick(mRowContaioner, getAdapterPosition());
          break;
        default:
          break;
      }

    }
  }

  public interface RecyclerViewClickListener {
    void onRowClick(View view, int position);

  }
}
