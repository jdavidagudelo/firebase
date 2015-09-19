package firebase.david.juan.artica.com.firebaseusersbasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Firebase firebase;
    private Firebase blogReference;
    private EditText editTextUserName;
    private EditText editTextPost;
    private BlogAdapter blogAdapter;
    private HashMap<String, BlogPost> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebase = new Firebase("https://glowing-inferno-3868.firebaseio.com/");
        blogReference = firebase.child("blogs");
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextPost = (EditText)findViewById(R.id.editTextPost);
        blogAdapter = new BlogAdapter();
        blogReference.keepSynced(true);
        RecyclerView view =(RecyclerView)findViewById(R.id.listViewPosts);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(blogAdapter);
        findViewById(R.id.buttonCreatePost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlogPost blogPost = new BlogPost();
                blogPost.setDate(new Date());
                blogPost.setMessage(editTextPost.getText().toString());
                blogPost.setUserName(editTextUserName.getText().toString());
                blogReference.push().setValue(blogPost);
            }
        });

        blogReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey() != null) {
                    BlogPost current = map.get(dataSnapshot.getKey());
                    if(!blogAdapter.contains(current)){
                       blogAdapter.add(dataSnapshot.getValue(BlogPost.class), dataSnapshot.getKey());
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey() != null) {
                    BlogPost current = map.get(dataSnapshot.getKey());
                    BlogPost updated = dataSnapshot.getValue(BlogPost.class);
                    current.setUserName(updated.getUserName());
                    current.setDate(updated.getDate());
                    current.setMessage(updated.getMessage());
                    blogAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getKey() != null) {
                    BlogPost current = map.get(dataSnapshot.getKey());
                    if(blogAdapter.contains(current)){
                        blogAdapter.remove(current, dataSnapshot.getKey());
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("TAG", firebaseError.getDetails());
                Toast.makeText(MainActivity.this, firebaseError.getDetails(), Toast.LENGTH_LONG).show();
            }
        });
        blogReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<BlogPost> posts = new ArrayList<>();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot current : dataSnapshot.getChildren()){
                    BlogPost blogPost = current.getValue(BlogPost.class);
                    map.put(current.getKey(), blogPost);
                    posts.add(blogPost);
                    keys.add(current.getKey());
                }
                blogAdapter.setPosts(posts);
                blogAdapter.setKeys(keys);
                blogAdapter.setBlogReference(blogReference);
                blogAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(MainActivity.this, firebaseError.getDetails(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
