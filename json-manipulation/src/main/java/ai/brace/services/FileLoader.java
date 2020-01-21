package ai.brace.services;

import ai.brace.models.InputData;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import ai.brace.models.TextData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class FileLoader {
    public List<TextData> loadDataFromFile(String filename) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(getFileFromResources(filename)));
        InputData inputData = gson.fromJson(reader, InputData.class);
        return inputData.getTextArray();
    }

    private File getFileFromResources(String filename) {
        return new File(getClass().getClassLoader().getResource(filename).getFile());
    }
}
