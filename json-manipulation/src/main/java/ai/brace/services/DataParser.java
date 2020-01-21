package ai.brace.services;

import ai.brace.models.TextData;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class DataParser {
    public List<String> sortByAscendingIds(List<TextData> textData) {
        return textData.stream()
                .sorted(Comparator.comparing(TextData::getId))
                .map(TextData::getTextdata)
                .collect(toList());
    }

    public Map<String, Integer> mapWordsByFrequency(List<TextData> textDataList) {
        return convertTextDataToListOfAllWordsPresent(textDataList)
                .collect(groupingBy(Function.identity(), summingInt(e -> 1)));
    }

    private Stream<String> convertTextDataToListOfAllWordsPresent(List<TextData> textDataList) {
        return textDataList
                .stream()
                .flatMap(textData -> Arrays.stream(textData.getTextdata().split(" ")));
    }
}
