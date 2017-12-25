package app.codekiller.com.newsapp.bean;

import java.util.ArrayList;

/**
 * 只是为了复用NewsRecyclerAdapter少写点代码
 * Created by R2D2 on 2017/12/25.
 */

public class BaseBean {
    private ArrayList<String> images;
    private int id;
    private String title;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
