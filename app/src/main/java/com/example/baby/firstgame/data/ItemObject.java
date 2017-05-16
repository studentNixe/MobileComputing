package com.example.baby.firstgame.data;

/**
 * Created by Denise on 09.05.2017.
 */

public abstract class ItemObject {
    private String id;
    private String image;
    private int quanity;

    public ItemObject(String id, String image, int quanity) {
        this.id = id;
        this.image = image;
        this.quanity = quanity;
    }

    //getter & Setter
    public String getImage() { return image;    }
    public String getId() {  return id; }
    public int getQuanity() { return quanity;   }

    public void setImage(String image) {this.image = image; }
    public void setId(String id) {
        this.id = id;
    }
    public void setQuanity(int quanity) {  this.quanity = quanity;  }


}
