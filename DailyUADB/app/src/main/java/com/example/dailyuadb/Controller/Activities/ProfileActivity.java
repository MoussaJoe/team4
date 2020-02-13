package com.example.dailyuadb.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyuadb.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser, user;
    String uid;
    private FirebaseAuth firebaseAuth;
    private Uri imageUri;
    private StorageTask uploadTask;
    String myUrl = "";
    String profile ;
    StorageReference storageReference;
    CircleImageView img;
    TextView nom,prenom,carte,email,btnEdit;
    Button btndisconnect, buttonModifParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        img = findViewById(R.id.imageProfile);
        nom = findViewById(R.id.profilNom);
        prenom = findViewById(R.id.profilPrenom);
        carte = findViewById(R.id.profilCarte);
        email = findViewById(R.id.profilEmail);
        btndisconnect = findViewById(R.id.buttonDisconnect);
        btnEdit = findViewById(R.id.buttonEditProfil);
        buttonModifParam = findViewById(R.id.buttonModifParam);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth= FirebaseAuth.getInstance();

        String uid= firebaseUser.getUid();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Affichage du profile de l'utilisateur connecter
                user = FirebaseAuth.getInstance().getCurrentUser();
                profile = dataSnapshot.child("profil").getValue().toString();

                nom.setText(dataSnapshot.child("nom").getValue().toString());
                prenom.setText(dataSnapshot.child("prenom").getValue().toString());
                carte.setText(dataSnapshot.child("numCarte").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                //     Glide.with(mContext).load(dataSnapshot.child("imageurl").getValue()).into(img);
                Picasso.get().load(dataSnapshot.child("imageurl").getValue().toString()).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btndisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Redirection en fonction du profile de l'utilisateur
                if (profile.equalsIgnoreCase("Etudiant")){
                    firebaseAuth.signOut();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else if (profile.equalsIgnoreCase("Delegue")){
                    firebaseAuth.signOut();
                    startActivity(new Intent(getApplicationContext(), DelegueActivity.class));
                }else if (profile.equalsIgnoreCase("Codifiant")){
                    firebaseAuth.signOut();
                    startActivity(new Intent(getApplicationContext(), AccueilCodifiantActivity.class));
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .start(ProfileActivity.this);

                uploadImage();
            }
        });

        buttonModifParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    //Retrouver l'extension du fichier
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void uploadImage(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Téléchargement en cours ...");
        progressDialog.show();

        if (imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+
                    "."+ getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isComplete()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        uid = user.getUid();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

                        //String postid = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("imageurl", myUrl);

                        reference.updateChildren(hashMap);
                        progressDialog.dismiss();

                        //Redirection en fonction du profile de l'utilisateur
                        if (profile.equalsIgnoreCase("Etudiant")){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }else if (profile.equalsIgnoreCase("Delegue")){
                            startActivity(new Intent(getApplicationContext(), DelegueActivity.class));
                            finish();
                        }else if (profile.equalsIgnoreCase("Codifiant")){
                            //selectedFrangment = new CodifiantFragment();
                            startActivity(new Intent(getApplicationContext(), AccueilCodifiantActivity.class));
                            finish();
                        }

                    }else {
                        Toast.makeText(ProfileActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this, " "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }else{
            Toast.makeText(this, "Aucune image sélectionnée", Toast.LENGTH_LONG).show();
        }
    }
    //Ctrl + O

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            img.setImageURI(imageUri);
        }else{
            Toast.makeText(this, "Une erreur s'est produite. Réessayer", Toast.LENGTH_LONG).show();
            //Redirection en fonction du profile de l'utilisateur
            if (profile.equalsIgnoreCase("Etudiant")){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }else if (profile.equalsIgnoreCase("Delegue")){
                startActivity(new Intent(getApplicationContext(), DelegueActivity.class));
                finish();
            }else if (profile.equalsIgnoreCase("Codifiant")){
                //selectedFrangment = new CodifiantFragment();
                startActivity(new Intent(getApplicationContext(), AccueilCodifiantActivity.class));
                finish();
            }
        }
    }
}
