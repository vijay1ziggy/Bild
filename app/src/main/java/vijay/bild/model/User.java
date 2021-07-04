package vijay.bild.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String name;
    private String email;
    private String Username;
    private String bio;
    private String imageurl;
    private String id;


    public User() {
    }

    public User(String name, String email, String Username, String bio, String imageurl, String id) {
        this.name = name;
        this.email = email;
        this.Username = Username;
        this.bio = bio;
        this.imageurl = imageurl;
        this.id = id;
    }

    protected User(Parcel in) {
        email = in.readString();
        id = in.readString();
        Username = in.readString();
        name = in.readString();
        bio = in.readString();
        imageurl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", user_id='" + id + '\'' +
                ", username='" + Username + '\'' +
                ", avatar='" + imageurl + '\'' +
                ", bio= '" + bio + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(id);
        dest.writeString(Username);
        dest.writeString(imageurl);
        dest.writeString(bio);
    }
}
