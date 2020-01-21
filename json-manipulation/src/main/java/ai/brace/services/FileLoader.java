package ai.brace.services;

import ai.brace.models.InputFileData;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class FileLoader {
    Gson gson;

    public FileLoader() {
        this.gson = new Gson();
    }

    public InputFileData loadDataFromFile(String filename) throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(getFileFromResources(filename)));
        return gson.fromJson(reader, InputFileData.class);
    }

    private File getFileFromResources(String filename) {
        return new File(getClass().getClassLoader().getResource(filename).getFile());
    }

    public void writeFileWithData(InputFileData newData) throws IOException {
        FileWriter writer = new FileWriter(new File("output.json"));
        gson.toJson(newData, InputFileData.class, writer);
        writer.flush();
        writer.close();
    }
}
