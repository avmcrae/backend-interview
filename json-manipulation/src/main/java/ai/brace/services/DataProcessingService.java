package ai.brace.services;

import ai.brace.models.TextData;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class DataProcessingService {
    public List<String> sortByAscendingIds(List<TextData> textData) {
        return textData.stream()
                .sorted(Comparator.comparing(TextData::getId))
                .map(TextData::getTextdata)
                .collect(toList());
    }

    public Map<String, Integer> mapWordsByFrequency(List<TextData> textDataList) {
        return convertToListOfAllLowercaseWordsPresent(textDataList)
                .collect(groupingBy(Function.identity(), summingInt(e -> 1)));
    }

    private Stream<String> convertToListOfAllLowercaseWordsPresent(List<TextData> textDataList) {
        return textDataList
                .stream()
                .flatMap(textData -> Arrays.stream(textData.getTextdata().split(" ")))
                .map(String::toLowerCase)
                .map(word -> word.replaceAll("[^a-zA-Z]", ""));
    }
}
