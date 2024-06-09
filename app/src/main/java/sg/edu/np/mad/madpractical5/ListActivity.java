package sg.edu.np.mad.madpractical5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public static ArrayList<User> listOfUsers;
    public static SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.list_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.parent), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        DatabaseHandler dbHandler = DatabaseHandler.getInstance(ListActivity.this);
        db = dbHandler.getWritableDatabase();
        listOfUsers = dbHandler.getAllUsers();
        UserAdapter userAdapter = new UserAdapter(listOfUsers, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);






    }

    public void createDialog(User user){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Sets the title of the alert dialog
        builder.setTitle("Profile");

        //Sets the message of the alert dialog, block of text content (paragraph)

        builder.setMessage(user.getName());

        // Set the positive button

        builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Intents are used to start activity, broadcast services and more
                // navigate to the profile page using new Intent, from our source (ListActivity) -> (MainActivity) destination activity
                Intent ListToMain = new Intent(ListActivity.this,MainActivity.class);
                // pass data from ListActivity to MainActivity in a bundle
                Bundle extras = new Bundle();
                extras.putString("key1",user.getName());
                extras.putString("key2",user.getDescription());
                extras.putBoolean("key3",user.getFollowed());
                extras.putInt("key4",user.getId());

                ListToMain.putExtras(extras);
                startActivity(ListToMain);


            }
        });

        // set the negative button
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // show the dialog
        AlertDialog alert = builder.create();
        alert.show();




    }




}