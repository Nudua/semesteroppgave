package com.groupname.framework.serialization.xml;

import com.groupname.framework.util.Tuple;
import com.groupname.framework.util.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A rudimentary XML parser.
 */
public class XMLParser {

    /**
     * Creates a new instance of this class.
     */
    public XMLParser() {
    }

    /**
     * Attempts to parse a valid XML file and returns the class name of the root node
     * as well as all of it's nodes that represents the fields of the target class.
     *
     * @param fileName the XML file to read.
     * @return a Tuple that contains the className (item1) and a List of XMLNodes (item2) that contains the field names and values.
     * @throws XMLParseException if there was an issue parsing the XML file.
     */
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
                        throw new XMLParseException("NewGame and end tag does not much for xml node");
                    }

                    String value = nodeMatcher.group("Value");

                    nodes.add(new XMLNode(startTag, value));
                }
            }

            return new Tuple<>(className, nodes);

        } catch (IOException exception) {
            throw new XMLParseException();
        }
    }
}
