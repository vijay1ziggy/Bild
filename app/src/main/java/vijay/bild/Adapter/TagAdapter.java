package vijay.bild.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import vijay.bild.R;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mTags;
    private List<String> mTagCount;

    public TagAdapter(Context mContext, List<String> mTags, List<String> mTagCount) {
        this.mContext = mContext;
        this.mTags = mTags;
        this.mTagCount = mTagCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item ,  parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tag.setText(" # " + mTags.get(position));
        holder.noOfPosts.setText(mTagCount.get(position) + "Posts");

    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tag;
        public TextView noOfPosts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag=itemView.findViewById(R.id.hash_tag);
            noOfPosts=itemView.findViewById(R.id.no_of_post);

        }
    }

    public void filter (List<String> filterTag , List<String> filterTagCount){
        this.mTags = filterTag;
        this.mTagCount = filterTagCount;

        notifyDataSetChanged();
    }
}
