package sg.edu.np.mad.madpractical5;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("key1");
        String description = bundle.getString("key2");
        boolean followed = bundle.getBoolean("key3");
        int uid = bundle.getInt("key4");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvName = findViewById(R.id.tvName);

        TextView tvDescription = findViewById(R.id.tvDescription);

        Button buttonFollow = findViewById(R.id.btnFollow);

        tvName.setText(name);

        tvDescription.setText(description);


        if(!followed){
            buttonFollow.setText("Follow");
        }
        else{
            buttonFollow.setText("Unfollow");
        }


        buttonFollow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get the text from button
                DatabaseHandler dbHandler = DatabaseHandler.getInstance(MainActivity.this);
                db = dbHandler.getWritableDatabase();
                // logic to handle different text (follow/unfollow), follow -> unfollow vice versa
                String text = buttonFollow.getText().toString();
                if (text.equals("Follow")) {
                    buttonFollow.setText("Unfollow");
                    User user = new User(name,description,uid,true);
                    dbHandler.updateUser(user);
                    db.close();
                    Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();
                } else if (text.equals("Unfollow")) {
                    buttonFollow.setText("Follow");
                    User user = new User(name,description,uid,false);
                    dbHandler.updateUser(user);
                    db.close();
                    Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}