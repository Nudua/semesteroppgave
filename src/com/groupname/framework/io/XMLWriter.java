package com.groupname.framework.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

class XMLNode {
    private final String name;
    private final String value;


    public XMLNode(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "XMLNode{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

public class XMLWriter {

    public <T> void write(Path fileName, T instance) throws IllegalAccessException, IOException {
        List<XMLNode> nodes = readFields(fileName, instance);
        writeXML(fileName, instance.getClass().getName(), nodes);

    }

    private <T> List<XMLNode> readFields(Path fileName, T instance) throws IllegalAccessException {

        List<String> validClasses = new ArrayList<>();

        validClasses.add(String.class.getName());
        //Primitives
        validClasses.add(int.class.getName());
        validClasses.add(double.class.getName());
        validClasses.add(float.class.getName());
        validClasses.add(char.class.getName());
        validClasses.add(boolean.class.getName());

        Field[] fields = instance.getClass().getDeclaredFields();
        String className = instance.getClass().getName();

        List<XMLNode> nodes = new ArrayList<>();

        for(Field field : fields) {
            if(!validClasses.contains(field.getType().getName())) {
                continue;
            }
            boolean isAccessible = field.isAccessible();

            if(!isAccessible) {
                field.setAccessible(true);
            }

            XMLNode node = new XMLNode(field.getName(), field.get(instance).toString());

            if(!isAccessible) {
                field.setAccessible(false);
            }

            nodes.add(node);
        }
        return nodes;
    }

    private void writeXML(Path fileName, String className, List<XMLNode> nodes) throws IOException {
        final String openTag = "<";
        final String openEndTag = ">";
        final String closingEndTag = "</";

        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(fileName, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            //Writes the basenode in the following format: <classname>
            bufferedWriter.write(openTag + className + openEndTag);

            for(XMLNode node : nodes) {
                bufferedWriter.write(openTag + node.getName() + openEndTag + node.getValue() + closingEndTag + node.getName() + openEndTag);
            }
        }
    }
}
