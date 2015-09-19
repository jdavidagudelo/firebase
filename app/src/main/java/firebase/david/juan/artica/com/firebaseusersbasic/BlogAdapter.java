package firebase.david.juan.artica.com.firebaseusersbasic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by interoperabilidad on 19/09/15.
 */
public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogPostViewHolder> {
    private List<BlogPost> posts = new ArrayList<>();
    private Firebase blogReference;
    private List<String> keys = new ArrayList<>();
    public BlogAdapter(List<BlogPost> blogPosts, List<String> keys,  Firebase blogReference){
        super();
        this.posts = blogPosts;
        this.keys = keys;
        this.blogReference = blogReference;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }
    public void setBlogReference(Firebase blogReference) {
        this.blogReference = blogReference;
    }

    public List<BlogPost> getPosts() {
        return posts;
    }

    public void setPosts(List<BlogPost> posts) {
        this.posts = posts;
    }

    public BlogAdapter() {
    }
    public class BlogPostViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewUserName;
        private TextView textViewPost;
        private TextView textViewDate;
        private Button buttonDelete;
        public BlogPostViewHolder(View itemView) {
            super(itemView);
            textViewDate = (TextView)itemView.findViewById(R.id.textViewDate);
            textViewPost = (TextView)itemView.findViewById(R.id.textViewMessage);
            textViewUserName = (TextView)itemView.findViewById(R.id.textViewUserName);
            buttonDelete = (Button)itemView.findViewById(R.id.buttonDelete);
        }
    }
    @Override
    public BlogPostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.blog_post_item, viewGroup, false);
        return new BlogPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BlogPostViewHolder blogPost, final int i) {
        BlogPost post = posts.get(i);
        blogPost.textViewUserName.setText(post.getUserName());
        blogPost.textViewDate.setText(post.getDate().toString());
        blogPost.textViewPost.setText(post.getMessage());
        blogPost.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase firebase = blogReference.child(keys.get(i));
                firebase.removeValue();
            }
        });
    }
    public void remove(BlogPost blogPost, String key){
        posts.remove(blogPost);
        keys.remove(key);
        notifyDataSetChanged();
    }
    public void add(BlogPost blogPost, String key){
        posts.add(blogPost);
        keys.add(key);
        notifyDataSetChanged();
    }
    public boolean contains(BlogPost blogPost){
        return posts.contains(blogPost);
    }
    @Override
    public int getItemCount() {
        return posts.size();
    }
}
