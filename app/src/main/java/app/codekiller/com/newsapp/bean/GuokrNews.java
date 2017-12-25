package app.codekiller.com.newsapp.bean;

import java.util.List;

/**
 * Created by R2D2 on 2017/12/25.
 */

public class GuokrNews {

    /**
     * now : 2017-12-25T10:24:00.755683+08:00
     * ok : true
     * result : [{"link_v2_sync_img":"http://jingxuan.guokr.com/pick/v2/85104/sync/","source_name":"十五言","video_url":"","current_user_has_collected":false,"likings_count":160,"images":["http://2-im.guokr.com/VG9CuEne1Ywmi7pEwwPGUeP6cflM1tvCrP4BTz8AdqMABQAA0AIAAEpQ.jpg?imageView2/1/w/1280/h/720","http://3-im.guokr.com/qXLZ5h-DykR5fZAr0xqehQwkXJkekkRKsw6yAPt-GC_bAQAAPwEAAEpQ.jpg?imageView2/1/w/475/h/319","http://1-im.guokr.com/g_T5w6_8mZVAoD26fjobzjPeNxVQAzFgoS5A_4PpzlkGAgAALAEAAEpQ.jpg?imageView2/1/w/518/h/300","http://1-im.guokr.com/rDlQjpVV2p3v6a28SAaX6irerOlKs867f7ESE11A3btYAgAAdgEAAEpQ.jpg?imageView2/1/w/600/h/374","http://2-im.guokr.com/gjWiyUdMMpczUp_NPsnyZ5h3Y2SizBaChth1p8DqDoAABQAA0AIAAEpQ.jpg?imageView2/1/w/1280/h/720"],"video_duration":null,"id":85104,"category":"humanities","style":"article","title":"虎啸龙吟：那个花美男大内官是什么来头？","source_data":{"image":"http://3.im.guokr.com/u6lVLjVH16EVdBExd7fbyOZQYwTcMAyPYxSWdj03gtPoAwAA6AMAAEpQ.jpg","summary":"果壳旗下高质量写作/内容社区。有一群关注点崎岖的作者，没事儿陪你唠唠美食的渊源、历史政治的趣味边角，晚上陪你考据鬼神邪典、美少女和小电影\u2026\u2026总之就是「知识的文艺范儿」，好看又涨知识。","id":6,"key":"十五言","title":"十五言"},"headline_img_tb":"https://ob7zbqpa6.qnssl.com/fa2orzy8pji7c0m70lroz8oif7sdm8r7.jpg!content","link_v2":"http://jingxuan.guokr.com/pick/v2/85104/","date_picked":1.514121541E9,"is_top":false,"link":"http://jingxuan.guokr.com/pick/85104/","headline_img":"https://ob7zbqpa6.qnssl.com/fa2orzy8pji7c0m70lroz8oif7sdm8r7.jpg!content","replies_count":null,"current_user_has_liked":false,"page_source":"http://jingxuan.guokr.com/pick/85104/?ad=1","author":"子曰少怀","summary":"前不久，《大军师司马懿之军师联盟》的第二季《虎啸龙吟》开播，在这新的一季中，疑似魏明帝男宠的大内官\u201c辟邪\u201d，一出场便成为了一大亮点","source":"liyan","reply_root_id":0,"date_created":1.51321482E9}]
     */

    private String now;
    private boolean ok;
    private List<ResultBean> result;

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean extends BaseBean{
        /**
         * link_v2_sync_img : http://jingxuan.guokr.com/pick/v2/85104/sync/
         * source_name : 十五言
         * video_url :
         * current_user_has_collected : false
         * likings_count : 160
         * images : ["http://2-im.guokr.com/VG9CuEne1Ywmi7pEwwPGUeP6cflM1tvCrP4BTz8AdqMABQAA0AIAAEpQ.jpg?imageView2/1/w/1280/h/720","http://3-im.guokr.com/qXLZ5h-DykR5fZAr0xqehQwkXJkekkRKsw6yAPt-GC_bAQAAPwEAAEpQ.jpg?imageView2/1/w/475/h/319","http://1-im.guokr.com/g_T5w6_8mZVAoD26fjobzjPeNxVQAzFgoS5A_4PpzlkGAgAALAEAAEpQ.jpg?imageView2/1/w/518/h/300","http://1-im.guokr.com/rDlQjpVV2p3v6a28SAaX6irerOlKs867f7ESE11A3btYAgAAdgEAAEpQ.jpg?imageView2/1/w/600/h/374","http://2-im.guokr.com/gjWiyUdMMpczUp_NPsnyZ5h3Y2SizBaChth1p8DqDoAABQAA0AIAAEpQ.jpg?imageView2/1/w/1280/h/720"]
         * video_duration : null
         * id : 85104
         * category : humanities
         * style : article
         * title : 虎啸龙吟：那个花美男大内官是什么来头？
         * source_data : {"image":"http://3.im.guokr.com/u6lVLjVH16EVdBExd7fbyOZQYwTcMAyPYxSWdj03gtPoAwAA6AMAAEpQ.jpg","summary":"果壳旗下高质量写作/内容社区。有一群关注点崎岖的作者，没事儿陪你唠唠美食的渊源、历史政治的趣味边角，晚上陪你考据鬼神邪典、美少女和小电影\u2026\u2026总之就是「知识的文艺范儿」，好看又涨知识。","id":6,"key":"十五言","title":"十五言"}
         * headline_img_tb : https://ob7zbqpa6.qnssl.com/fa2orzy8pji7c0m70lroz8oif7sdm8r7.jpg!content
         * link_v2 : http://jingxuan.guokr.com/pick/v2/85104/
         * date_picked : 1.514121541E9
         * is_top : false
         * link : http://jingxuan.guokr.com/pick/85104/
         * headline_img : https://ob7zbqpa6.qnssl.com/fa2orzy8pji7c0m70lroz8oif7sdm8r7.jpg!content
         * replies_count : null
         * current_user_has_liked : false
         * page_source : http://jingxuan.guokr.com/pick/85104/?ad=1
         * author : 子曰少怀
         * summary : 前不久，《大军师司马懿之军师联盟》的第二季《虎啸龙吟》开播，在这新的一季中，疑似魏明帝男宠的大内官“辟邪”，一出场便成为了一大亮点
         * source : liyan
         * reply_root_id : 0
         * date_created : 1.51321482E9
         */

        private String link_v2_sync_img;
        private String source_name;
        private String video_url;
        private boolean current_user_has_collected;
        private int likings_count;
        private Object video_duration;
        private String category;
        private String style;
        private SourceDataBean source_data;
        private String headline_img_tb;
        private String link_v2;
        private double date_picked;
        private boolean is_top;
        private String link;
        private String headline_img;
        private Object replies_count;
        private boolean current_user_has_liked;
        private String page_source;
        private String author;
        private String summary;
        private String source;
        private int reply_root_id;
        private double date_created;

        public String getLink_v2_sync_img() {
            return link_v2_sync_img;
        }

        public void setLink_v2_sync_img(String link_v2_sync_img) {
            this.link_v2_sync_img = link_v2_sync_img;
        }

        public String getSource_name() {
            return source_name;
        }

        public void setSource_name(String source_name) {
            this.source_name = source_name;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public boolean isCurrent_user_has_collected() {
            return current_user_has_collected;
        }

        public void setCurrent_user_has_collected(boolean current_user_has_collected) {
            this.current_user_has_collected = current_user_has_collected;
        }

        public int getLikings_count() {
            return likings_count;
        }

        public void setLikings_count(int likings_count) {
            this.likings_count = likings_count;
        }

        public Object getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(Object video_duration) {
            this.video_duration = video_duration;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public SourceDataBean getSource_data() {
            return source_data;
        }

        public void setSource_data(SourceDataBean source_data) {
            this.source_data = source_data;
        }

        public String getHeadline_img_tb() {
            return headline_img_tb;
        }

        public void setHeadline_img_tb(String headline_img_tb) {
            this.headline_img_tb = headline_img_tb;
        }

        public String getLink_v2() {
            return link_v2;
        }

        public void setLink_v2(String link_v2) {
            this.link_v2 = link_v2;
        }

        public double getDate_picked() {
            return date_picked;
        }

        public void setDate_picked(double date_picked) {
            this.date_picked = date_picked;
        }

        public boolean isIs_top() {
            return is_top;
        }

        public void setIs_top(boolean is_top) {
            this.is_top = is_top;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getHeadline_img() {
            return headline_img;
        }

        public void setHeadline_img(String headline_img) {
            this.headline_img = headline_img;
        }

        public Object getReplies_count() {
            return replies_count;
        }

        public void setReplies_count(Object replies_count) {
            this.replies_count = replies_count;
        }

        public boolean isCurrent_user_has_liked() {
            return current_user_has_liked;
        }

        public void setCurrent_user_has_liked(boolean current_user_has_liked) {
            this.current_user_has_liked = current_user_has_liked;
        }

        public String getPage_source() {
            return page_source;
        }

        public void setPage_source(String page_source) {
            this.page_source = page_source;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getReply_root_id() {
            return reply_root_id;
        }

        public void setReply_root_id(int reply_root_id) {
            this.reply_root_id = reply_root_id;
        }

        public double getDate_created() {
            return date_created;
        }

        public void setDate_created(double date_created) {
            this.date_created = date_created;
        }

        public static class SourceDataBean {
            /**
             * image : http://3.im.guokr.com/u6lVLjVH16EVdBExd7fbyOZQYwTcMAyPYxSWdj03gtPoAwAA6AMAAEpQ.jpg
             * summary : 果壳旗下高质量写作/内容社区。有一群关注点崎岖的作者，没事儿陪你唠唠美食的渊源、历史政治的趣味边角，晚上陪你考据鬼神邪典、美少女和小电影……总之就是「知识的文艺范儿」，好看又涨知识。
             * id : 6
             * key : 十五言
             * title : 十五言
             */

            private String image;
            private String summary;
            private int id;
            private String key;
            private String title;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
