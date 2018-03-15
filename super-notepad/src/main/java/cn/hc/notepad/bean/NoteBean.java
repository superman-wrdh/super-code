package cn.hc.notepad.bean;

public class NoteBean {
    int id;
    String title=null;
    String content=null;
    String other=null;
    String otherkey=null;
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getOther() {
        return other;
    }
    public void setOther(String other) {
        this.other = other;
    }
    public String getOtherkey() {
        return otherkey;
    }
    public void setOtherkey(String otherkey) {
        this.otherkey = otherkey;
    }
    public NoteBean() {
        super();
    }

}

