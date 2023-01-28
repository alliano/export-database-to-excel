package com.export.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FormatterString {

    public static List<String> toCamelCase(List<String> listString) {
        listString = listString.stream().map( dl -> {
            String[] replaceSpace = dl.split(" ");
            String mergeString = Arrays.asList(replaceSpace).stream().reduce(( first, last) -> {
                char firstCharacter = last.charAt(0);
                String charToStrUppercase = Character.toString(firstCharacter).toUpperCase();
                String replace = last.replace(Character.toString(firstCharacter), charToStrUppercase);
                return first.concat(replace);
            }).get();
            return mergeString;
        }).collect(Collectors.toList());
        return listString;
    }

    public static String toCamelCase(String listString) {
        String arrayStr[] = listString.split(" ");
        listString = Arrays.asList(arrayStr).stream().reduce( ( first, last ) -> {
            char firstCharacter = last.charAt(0);
            String charToStrUppercase = Character.toString(firstCharacter).toUpperCase();
            String replace = last.replace(Character.toString(firstCharacter), charToStrUppercase);
            return first.concat(replace);
        }).get();
        return listString;
    }

    public static List<String> toSnakeCase(List<String> listString) {
        listString = listString.stream().map( dl -> {
            String[] replaceSpace = dl.split(" ");
            String mergeString = Arrays.asList(replaceSpace).stream().reduce(( first, last) -> {
               return first.concat("_").concat(last);
            }).get();
            return mergeString;
        }).collect(Collectors.toList());
        return listString;
    }

    public static String toSnakeCase(String listString) {
        String toArray[] = listString.split(" ");
        listString = Arrays.asList(toArray).stream().reduce( ( first, last ) -> {
            return first.concat("_").concat(last);
        }).get();
        return listString;
    }
}
