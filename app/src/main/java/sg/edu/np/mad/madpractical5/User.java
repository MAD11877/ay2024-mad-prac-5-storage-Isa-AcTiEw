package sg.edu.np.mad.madpractical5;

public class User {
    public String name;
    public String description;

    public int id;

    public boolean followed;

    public User(String name, String description, int id, boolean followed){
        this.name = name;
        this.description = description;
        this.id = id;
        this.followed = followed;
    }


    public String getName() {
        return name;
    }

    public String setName(){
        return this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(){
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public boolean getFollowed(){
        return followed;
    }


}