package ai.brace.models;

import ai.brace.services.DateService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InputFileData {
    private String version;
    private UUID uuid;
    private String lastModified;
    private String title;
    private String author;
    private String translator;
    private String releaseDate;
    private String language;
    private List<TextData> textArray;

    public InputFileData(UUID uuid) {
        this.uuid = uuid;
        this.textArray = new ArrayList<>();
    }

    public InputFileData(List<TextData> textArray) {
        this.textArray = textArray;
    }

    public List<TextData> getTextArray() {
        return textArray;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void updateAll(InputFileData newerRecord) {
        this.lastModified = DateService.getTimestampFromEpoch(Long.valueOf(newerRecord.getLastModified())).toString();
        this.version = updateIfNonNull(newerRecord.version, version);
        this.author = updateIfNonNull(newerRecord.author, author);
        this.translator = updateIfNonNull(newerRecord.translator, translator);
        this.language = updateIfNonNull(newerRecord.language, language);
        this.title = updateIfNonNull(newerRecord.title, title);
        this.releaseDate = updateIfNonNull(newerRecord.releaseDate, releaseDate);
        this.textArray.addAll(newerRecord.textArray);
    }

    public void addText(List<TextData> newTextData) {
        this.textArray.addAll(newTextData);
    }

    private String updateIfNonNull(String newer, String older) {
        return newer != null ? newer : older;
    }
}
