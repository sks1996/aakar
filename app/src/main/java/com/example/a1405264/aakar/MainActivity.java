package com.example.a1405264.aakar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mBolglist;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase=FirebaseDatabase.getInstance().getReference().child("suraj");

        mBolglist=(RecyclerView)findViewById(R.id.blog_list);
        mBolglist.hasFixedSize();
        mBolglist.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Detail,Holder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Detail, Holder>(
                Detail.class,
                R.layout.blog_row,
                Holder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(Holder viewHolder, Detail model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setTitle(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());
            }
        };
        mBolglist.setAdapter(firebaseRecyclerAdapter);
    }

    public  static class Holder extends RecyclerView.ViewHolder
    {
        View mView;

        public Holder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public  void setTitle(String title)
        {
            TextView post_title=(TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public  void setdesc(String desc)
        {
            TextView post_desc=(TextView)mView.findViewById(R.id.post_text);
            post_desc.setText(desc);
        }

        public  void    setImage(Context ctx, String image)
        {
            ImageView postImage=(ImageView)mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(postImage);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_add)
        {
            startActivity(new Intent(MainActivity.this,PostActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
