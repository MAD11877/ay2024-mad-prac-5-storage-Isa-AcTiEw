package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseHandler extends SQLiteOpenHelper {

    // static member variables to be declared before initializing in constructor, final means immutable
    // specify name of table, name of columns etc

    public static final int DATABASE_VERSION = 1;
    public static final String DB_name = "my_users.db";

    // table name
    public static final String USERS = "users";

    public static final String COLUMN_ID = "id";

    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_DESCRIPTION = "description";

    public static final String COLUMN_FOLLOWED = "followed";
    private static final String TAG = "MyDBHandler";

    // Public method to provide access to the singleton instance
    public static synchronized DatabaseHandler getInstance(Context context) {
        DatabaseHandler instance = null; // Define instance variable locally

        if (instance == null) {
            instance = new DatabaseHandler(context.getApplicationContext());
        }
        return instance; // Return the local instance variable
    }



    private DatabaseHandler(Context context) {
        super(context, DB_name, null, DATABASE_VERSION);
    }



    // create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        // autoincrement will assign an integer to the column if no value is specified an increment by 1 based on the previous row
        Log.i("Database Operation", "Creating the databse");
        // Your DDL
        try {
            String CREATE_USER_TABLE = "CREATE TABLE " + USERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_FOLLOWED + " INTEGER " + ")";
            db.execSQL(CREATE_USER_TABLE); // execute creeation of table

            // insert the values of each column DML

            // GENERATE FOR 20 USERS

            for(int i = 0; i<20; i++){
                Random rand = new Random();
                String desc = "Description" + String.valueOf(rand.nextInt(9999));
                String name = "Name" + String.valueOf(rand.nextInt(9999));
                Boolean bfollowed = rand.nextBoolean();
                // returns value of 1 if expression true and 0 if expression is false (ternary operator)
                int followed = bfollowed ? 1 : 0;
                ContentValues values = new ContentValues();
                // no need to put id value as it is autoincremented by sqllite
                values.put(COLUMN_NAME,name);
                values.put(COLUMN_DESCRIPTION,desc);
                values.put(COLUMN_FOLLOWED,followed);
                db.insert(USERS,null,values);








            }
            Log.i("Database Operation","Users table Created successfully");






        } catch (SQLException e) {
            Log.i("Database Operation","Error" + e.getMessage());
        }
        finally {
//            db.close();
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // if the version of db is newer drop the whole table and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        onCreate(db);
    }

    // read all the user data from the DB and store it in an ArrayList

    public ArrayList<User> getAllUsers (){
        // returns the db in a read only (database snapshot)
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<User> userList = new ArrayList<>();
        // returns all tuples in the table
        String query = "SELECT * FROM " + USERS;
        // sql uses the bytecode engine not an executable and utilises pointers and cursors to refrence the tables and columns in sql
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt((int)cursor.getColumnIndex("id"));
            String name = cursor.getString((int)cursor.getColumnIndex("name"));
            String description = cursor.getString((int)cursor.getColumnIndex("description"));
            int followed = cursor.getInt((int) cursor.getColumnIndex("followed"));
            // returns true if followed == 1 and return false if followed == 0
            Boolean valfollowed = followed == 1 ? true:false;

            userList.add(new User(name,description,id,valfollowed));


        }
        cursor.close();

        return userList;

    }

    // pass the user's id and match the user's id witht he ids in the db using a WHERE clause
    // update the coresponding column in this case followed
    //  UPDATE user record

    // use the update function and specify the where argument and clause, cursors only for retieving data
    public void updateUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        int newfollow = user.getFollowed() ? 1 : 0;
        values.put(COLUMN_FOLLOWED,newfollow);
        // match the id specified in the where clause, ? means the value will be provided later in our wher clause argument
        String where_Clause = "id=?";
        String[] where_Args = {String.valueOf(user.getId())};
        int rowsUpdated = db.update(USERS,values,where_Clause,where_Args);
        if ( rowsUpdated> 0) {
            Log.d("DB_UPDATE", "User updated successfully");
        } else {
            Log.d("DB_UPDATE", "Failed to update user");
        }
    }

    @Override
    public void close() {
        Log.i("Database Operations", "Database is closed.");
        super.close();
    }


}
