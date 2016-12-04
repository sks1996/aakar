package com.example.a1405264.aakar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button  mSubmitBtn;

    private   Uri imageUri = null;

    private DatabaseReference mdatabase;
    private StorageReference mStoragereference;

    private static final int GALLERY_REQUEST=1;

    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mStoragereference= FirebaseStorage.getInstance().getReference();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("suraj");


        mPostTitle=(EditText)findViewById(R.id.editText);
        mPostDesc=(EditText)findViewById(R.id.editText2);
        mSubmitBtn=(Button)findViewById(R.id.button3);

        mProgress=new ProgressDialog(this);

        imageView=(ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startposting();
            }
        });

    }

    private void startposting()
    {
        mProgress.setMessage("Posting ...");


        final String title=mPostTitle.getText().toString().trim();
        final String desc=mPostDesc.getText().toString().trim();

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc) && imageUri!=null)
        {
            mProgress.show();

            StorageReference filepath=mStoragereference.child("suraj").child(imageUri.getLastPathSegment());//imageuri can be replaced  with random name generator

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downlodurl=taskSnapshot.getDownloadUrl();

                    DatabaseReference newpost=mdatabase.push();
                    newpost.child("title").setValue(title);
                    newpost.child("desc").setValue(desc);
                    newpost.child("image").setValue(downlodurl.toString());

                    mProgress.dismiss();

                    startActivity(new Intent(PostActivity.this,MainActivity.class));
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST  && resultCode==RESULT_OK)
        {
            imageUri =data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}