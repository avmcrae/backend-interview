package ai.brace.models;

public class TextData {
    private Integer id;
    private String textdata;

    public TextData(Integer id, String textdata) {
        this.id = id;
        this.textdata = textdata;
    }

    public String getTextdata() {
        return textdata;
    }

    public Integer getId() {
        return id;
    }
}
