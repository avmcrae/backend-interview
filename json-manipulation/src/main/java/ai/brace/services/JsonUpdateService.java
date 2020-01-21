package ai.brace.services;

import ai.brace.models.InputFileData;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class JsonUpdateService {
    private FileLoader fileLoader;
    private ValidationService validationService;

    public JsonUpdateService(FileLoader fileLoader, ValidationService validationService) {
        this.fileLoader = fileLoader;
        this.validationService = validationService;
    }

    public void createOutputFileWithLatestDateFromFiles(List<String> filenames) {
        List<InputFileData> inputDataList = filenames
                .stream()
                .map(filename -> {
                    {
                        try {
                            return fileLoader.loadDataFromFile(filename);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    }
                }).collect(toList());
        InputFileData newData = mergeInputFileDataIntoNewestData(inputDataList);
        try {
            fileLoader.writeFileWithData(newData);
        } catch (Exception e) {
            validationService.validate(e);
        }
    }

    private InputFileData mergeInputFileDataIntoNewestData(List<InputFileData> inputFileDataList) {
        UUID newUUID = UUID.randomUUID();
        InputFileData latestData = new InputFileData(newUUID);
        inputFileDataList.stream().forEach(newerRecord -> {
            if (isEpochDateAfterUtcTimestamp(latestData.getLastModified(), newerRecord.getLastModified())) {
                latestData.updateAll(newerRecord);
            } else {
                latestData.addText(newerRecord.getTextArray());
            }
        });
        return latestData;
    }

    private boolean isEpochDateAfterUtcTimestamp(String latestData, String latestRecord) {
        if (latestData == null && latestRecord != null) return true;
        if (latestRecord == null) return false;
        return DateService.getTimestampFromEpoch(Long.valueOf(latestRecord)).isAfter(DateService.getUtcFromString(latestData));
    }

}
