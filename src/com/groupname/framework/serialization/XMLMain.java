package com.groupname.framework.serialization;

import com.groupname.framework.io.Content;
import com.groupname.framework.util.Strings;
import com.groupname.game.levels.Credits;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// Immutable
class SaveData {
    private final String currentLevel;
    private final long score;

    public SaveData(String currentLevel, long score) {
        this.currentLevel = Strings.requireNonNullAndNotEmpty(currentLevel);
        if(score < 0) {
            throw new InvalidParameterException("Score cannot be negative");
        }
        this.score = score;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public long getScore() {
        return score;
    }
}


class XMLParseException extends Exception {
    public XMLParseException() {
    }

    public XMLParseException(String message) {
        super(message);
    }

}


class Tuple<T, T2> {

    private T item1;
    private T2 item2;

    public Tuple(T item1, T2 item2) {
        this.item1 = Objects.requireNonNull(item1);
        this.item2 = Objects.requireNonNull(item2);
    }

    public T getItem1() {
        return item1;
    }

    public T2 getItem2() {
        return item2;
    }
}


class XMLParser {
    public XMLParser() {

    }

    public Tuple<String, List<XMLNode>> getFields(Path fileName) throws XMLParseException {

        List<XMLNode> nodes = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(fileName)) {

            String line;

            Pattern rootNodeStart = Pattern.compile("^<(?<RootStart>[a-zA-Z0-9.]+)>$");


            String firstLine = reader.readLine();

            if(Strings.isNullOrEmpty(firstLine)) {
                throw new XMLParseException();
            }

            Matcher rootMatcher = rootNodeStart.matcher(firstLine);

            if(!rootMatcher.matches()) {
                throw new XMLParseException();
            }

            String className = rootMatcher.group("RootStart");

            // Each node (field) within every
            Pattern nodePattern = Pattern.compile("^<(?<StartTag>[a-zA-Z0-9]+)>(?<Value>.+)</(?<EndTag>[a-zA-Z0-9]+)>$"); //^<[a-zA-Z.]+>$


            while ((line = reader.readLine()) != null) {
                line = line.trim();

                Matcher nodeMatcher = nodePattern.matcher(line);

                if(nodeMatcher.matches()) {
                    String startTag = nodeMatcher.group("StartTag");
                    String endTag = nodeMatcher.group("EndTag");

                    if(!startTag.equals(endTag)) {
                        throw new XMLParseException("Start and end tag does not much for xml node");
                    }

                    String value = nodeMatcher.group("Value");

                    nodes.add(new XMLNode(startTag, value));
                } else {
                    System.err.println(line + " does not match");
                }
            }

            /*
            // Check last node
            Pattern rootNodeEnd = Pattern.compile("^</(?<RootEnd>[a-zA-Z0-9.]+)>$");

            line = reader.readLine();

            if(Strings.isNullOrEmpty(line)) {
                throw new XMLParseException("End node is empty or null");
            }

            Matcher endRootMatcher = rootNodeEnd.matcher(reader.readLine());

            if(!endRootMatcher.matches()) {
                throw new XMLParseException("End node does not match");
            }
            */

            return new Tuple<>(className, nodes);

        } catch (IOException exception) {
            throw new XMLParseException();
        }
    }
}




public class XMLMain {

    public static void main (String[] args) throws Exception {
        Content.setContentBaseFolder("/com/groupname/game/resources");

        XMLWriter xmlWriter = new XMLWriter();

        SaveData saveData = new SaveData(Credits.LEVEL_ID, 0);
        xmlWriter.write(Paths.get("saveFile1.xml"), saveData);

        XMLParser parser = new XMLParser();

        Tuple<String, List<XMLNode>> fields = parser.getFields(Paths.get("saveFile1.xml"));

        String className = fields.getItem1();

        List<XMLNode> nodes = fields.getItem2();

        System.out.println(className);
    }
}
