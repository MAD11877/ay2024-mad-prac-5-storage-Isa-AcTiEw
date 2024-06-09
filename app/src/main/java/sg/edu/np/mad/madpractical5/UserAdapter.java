package sg.edu.np.mad.madpractical5;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter <UserAdapter.myViewHolder> {

    public static ArrayList<User> userList;
    public static ListActivity listActivity;

    public UserAdapter(ArrayList<User> userList, ListActivity activity) {
        this.userList = userList;
        this.listActivity = activity;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_activity_list,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.myViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.getName());
        holder.description.setText(user.getDescription());

        // Check if username ends with 7 (hide or display view respectively)
        if(user.getName().endsWith("7")){
            holder.bigImg.setVisibility(View.VISIBLE);
        }
        else{
            holder.bigImg.setVisibility(View.GONE);
        }

        // EventHandling when smallImg is clicked and then display an alertDialog
        holder.smallImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // use the createDialog() defined in ListActivity class
                listActivity.createDialog(user);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView description;

        ImageView smallImg;

        ImageView bigImg;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            //Locations of image, name and description found in custom_activity_list.xml
            name = itemView.findViewById(R.id.usName);
            description = itemView.findViewById(R.id.usDesc);
            smallImg = itemView.findViewById(R.id.smallImg);
            bigImg = itemView.findViewById(R.id.bigImg);
        }
    }
}
