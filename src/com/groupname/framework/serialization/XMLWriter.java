package com.groupname.framework.serialization;

import com.groupname.framework.core.Difficulty;
import com.groupname.game.editor.metadata.PowerupSpriteType;
import com.groupname.game.entities.EnemySpriteType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * XMLWriter takes an instance of a class and saves it to a file.
 * Saves as XML to file.
 */
public class XMLWriter {

    /**
     * Reads the fields of the instance of the class.
     * Saves it to "fileName" as XML.
     * @param fileName filename to the file you save to.
     * @param instance the instance of the class you want to store
     * @param <T> the class type you want to store
     * @throws IllegalAccessException if no access to the class fields.
     * @throws IOException issues writing the file to disk.
     */
    public <T> void write(Path fileName, T instance) throws IllegalAccessException, IOException {
        List<XMLNode> nodes = readFields(instance);
        writeXML(fileName, instance.getClass().getName(), nodes);
    }

    private List<String> getWhiteList(){
        List<String> validClasses = new ArrayList<>();

        // Classes
        validClasses.add(String.class.getName());

        validClasses.add(Class.class.getTypeName());
        validClasses.add(Difficulty.class.getName());
        validClasses.add(EnemySpriteType.class.getName());
        validClasses.add(PowerupSpriteType.class.getName());

        // Primitives
        validClasses.add(int.class.getName());
        validClasses.add(long.class.getName());
        validClasses.add(double.class.getName());
        validClasses.add(float.class.getName());
        validClasses.add(char.class.getName());
        validClasses.add(boolean.class.getName());

        return validClasses;
    }

    private <T> List<XMLNode> readFields(T instance) throws IllegalAccessException {
        List<String> validClasses = getWhiteList();

        List<Field> fields = getAllFields(instance);

        List<XMLNode> nodes = new ArrayList<>();

        for(Field field : fields) {
            if(Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if(validClasses.contains(field.getType().getName())) {

                boolean isAccessible = field.isAccessible();

                if(!isAccessible) {
                    field.setAccessible(true);
                }
                String fieldValue = field.get(instance).toString();
                if(field.getType().getName().equals(Class.class.getTypeName())) {
                    fieldValue = fieldValue.replace("class ", "");
                }

                XMLNode node = new XMLNode(field.getName(), fieldValue);

                if(!isAccessible) {
                    field.setAccessible(false);
                }

                nodes.add(node);
            }
        }
        return nodes;
    }

    private void getFieldsFromClass(List<Field> fields, Class objectOfClass) {
        if(objectOfClass == Object.class) {
            return;
        }

        fields.addAll(Arrays.asList(objectOfClass.getDeclaredFields()));

        // Recursively get all fields from subclass until we hit the Object class
        getFieldsFromClass(fields, objectOfClass.getSuperclass());
    }

    private <T> List<Field> getAllFields(T instance) {
        List<Field> fields = new ArrayList<>();
        Class objectOfClass = instance.getClass();

        /*
        while(objectOfClass != Object.class){
            fields.addAll(Arrays.asList(objectOfClass.getDeclaredFields()));
            objectOfClass = objectOfClass.getSuperclass();
        }*/
        getFieldsFromClass(fields, objectOfClass);

        return fields;
    }

    private void writeXML(Path fileName, String className, List<XMLNode> nodes) throws IOException {
        final String openTag = "<";
        final String openEndTag = ">";
        final String closingEndTag = "</";
        final String newLine = "\n";
        final String tab = "\t";

        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(fileName, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            //Writes the basenode in the following format: <classname>
            bufferedWriter.write(openTag + className + openEndTag + newLine);
            for(XMLNode node : nodes) {
                //Writes the field in the following format: <variableName>value</variableName>
                bufferedWriter.write(tab + openTag + node.getName() + openEndTag + node.getValue() + closingEndTag + node.getName() + openEndTag + newLine);
            }
            //Writes the basenode in the following format: </classname>
            bufferedWriter.write(closingEndTag + className + openEndTag + "");

        }
    }
}
