package api.jsonplaceholder.models;

public class PostUpdate {
    private String title;
    private String body;

    public PostUpdate(String title, String body) {
        this.title = title;
        this.body = body;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
}