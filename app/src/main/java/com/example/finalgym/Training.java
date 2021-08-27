
package com.example.finalgym;


import android.os.Parcel;
import android.os.Parcelable;

//as we want to put Training object inside intent, in the TrainingRecViewAdapter, we can put that object as
//a Parcelable, and for that we have to implement Parcelable and it's methods to this Training.Java (POJO class), so do that
public class Training implements Parcelable {




    //fields
    private String name, shortDescription, longDescription, imageUrl;
    private int id;

    //constructor for the fields
    public Training(String name, String shortDescription, String longDescription, String imageUrl, int id)
    {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    //creator field of Parcelable: Add Parcelable implementation: below three sections
    protected Training(Parcel in) {
        name = in.readString();
        shortDescription = in.readString();
        longDescription = in.readString();
        imageUrl = in.readString();
        id = in.readInt();
    }

    public static final Creator<Training> CREATOR = new Creator<Training>() {
        @Override
        public Training createFromParcel(Parcel in) {
            return new Training(in);
        }

        @Override
        public Training[] newArray(int size) {
            return new Training[size];
        }
    };

    //getters and setters for all the fields
    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    public void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }
    public String getShortDescription()
    {
        return shortDescription;
    }

    public void setLongDescription(String longDescription)
    {
        this.longDescription = longDescription;
    }
    public String getLongDescription()
    {
        return longDescription;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl=imageUrl;
    }
    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return id;
    }

    //the to String method:
    @Override
    public String toString() {
        return "Training{" +
                "name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", id=" + id +
                '}';
    }

    //implementing methods of Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(shortDescription);
        dest.writeString(longDescription);
        dest.writeString(imageUrl);
        dest.writeInt(id);
    }
}
