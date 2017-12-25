package app.codekiller.com.newsapp.bean;

import java.util.ArrayList;

/**
 * Created by Lollipop on 2017/12/19.
 */

public class ZhihuDailyNews{
    private String date;
    private ArrayList<Question> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Question> getStories() {
        return stories;
    }

    public void setStories(ArrayList<Question> stories) {
        this.stories = stories;
    }

    public class Question extends BaseBean{
        private int type;
        private String ga_prefix;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

    }
}
