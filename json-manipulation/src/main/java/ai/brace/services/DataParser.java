package ai.brace.services;

import ai.brace.models.TextData;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DataParser {
    public List<String> sortByAscendingIds(List<TextData> textData) {
        return textData.stream()
                .sorted(Comparator.comparing(TextData::getId))
                .map(TextData::getTextdata)
                .collect(toList());
    }
}
