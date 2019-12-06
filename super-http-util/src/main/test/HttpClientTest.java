import cn.hc.http.Request;
import cn.hc.http.Response;
import com.google.gson.Gson;
import org.apache.http.entity.ContentType;


public class HttpClientTest {

    class Blog {
        private int id;
        private String title;
        private String releaseDate;
        private String clickHit;
        private String content;

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public void setClickHit(String clickHit) {
            this.clickHit = clickHit;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public String getClickHit() {
            return clickHit;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return "id:" + this.id + "Title:" + this.title;
        }
    }

    @org.junit.Test
    public void testHttps() {
        String url = "https://www.66super.com/api/blog/get/125.do";
        Request r = new Request(url);
        r.contentType(ContentType.APPLICATION_JSON);
        Response response = r.execute();
        assert response != null;
        String jsonString = response.string();
        Gson gson = new Gson();
        Blog blog = gson.fromJson(jsonString, Blog.class);
        System.out.println(blog);
    }
}
