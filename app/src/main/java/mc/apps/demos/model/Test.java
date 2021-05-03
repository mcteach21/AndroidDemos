package mc.apps.demos.model;

import java.util.Date;

public class Test {
    private int id;
    private String title;
    private String start2;
    private String start3;


    public Test(int id, String title, String start2, String start3) {
        this.id = id;
        this.title = title;
        this.start2 = start2;
        this.start3 = start3;
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

    public String getStart2() {
        return start2;
    }

    public void setStart2(String start2) {
        this.start2 = start2;
    }

    public String getStart3() {
        return start3;
    }

    public void setStart3(String start3) {
        this.start3 = start3;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", start2='" + start2 + '\'' +
                ", start3='" + start3 + '\'' +
                '}';
    }
}
