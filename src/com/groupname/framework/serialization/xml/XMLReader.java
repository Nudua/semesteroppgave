package com.groupname.framework.serialization.xml;

import com.groupname.framework.serialization.SerializationException;
import com.groupname.framework.serialization.Tuple;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.List;

/**
 * This class attempts to parse a XML file and instantiate the target class with
 * the specified values parsed from the XML file.
 */
public class XMLReader {

    /**
     * Creates a new instance of this class.
     */
    public XMLReader() {
    }

    // Gets the default constructor (no parameters) for the target class if it exists.
    // Returns null if it doesn't exist.
    private Constructor<?> getDefaultConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        for(Constructor<?> constructor : constructors) {
            if(constructor.getParameterCount() == 0) {
                return constructor;
            }
        }
        return null;
    }

    /**
     * Attempt to parse and instantiate the root node to target class.
     *
     * @param filePath the XML file to parse.
     * @param <T> the target class.
     * @return a new instance of the target class with the fields set to the values in the XML file.
     * @throws SerializationException if there was an issue while parsing the XML file or instantiating the new instance.
     */
    public <T> T read(Path filePath) throws SerializationException {
        XMLParser parser = new XMLParser();

        try {
            Tuple<String, List<XMLNode>> information = parser.getFields(filePath);

            return assemble(information.getItem1(), information.getItem2());
        } catch (XMLParseException exception) {
            throw new SerializationException();
        }
    }

    /**
     * Unchecked because we're creating a new instance of the target class at runtime.
     */
    @SuppressWarnings("unchecked")
    private  <T> T assemble(String className, List<XMLNode> nodes) throws SerializationException {

        try {
            Class<?> clazz = Class.forName(className);

            Constructor<?> defaultConstructor = getDefaultConstructor(clazz);

            if(defaultConstructor == null) {
                throw new SerializationException("Unsupported class");
            }

            T instance = (T)defaultConstructor.newInstance();

            for(XMLNode node : nodes) {
                Field field = instance.getClass().getDeclaredField(node.getName());

                boolean isAccessible = field.isAccessible();

                if(!isAccessible) {
                    field.setAccessible(true);
                }

                if(field.getType().isPrimitive()) {
                    setPrimitive(instance, field, node);
                } else {
                    // String
                    field.set(instance, node.getValue());
                }

                if(!isAccessible) {
                    field.setAccessible(false);
                }
            }

            return instance;
        } catch (ClassNotFoundException
                | InstantiationException
                | NoSuchFieldException
                | IllegalAccessException
                | InvocationTargetException
                | NumberFormatException exception) {
            throw new SerializationException();
        }

    }

    private <T> void setPrimitive(T instance, Field field, XMLNode node) throws IllegalAccessException, NumberFormatException {
        String typeName = field.getType().getName();
        String value = node.getValue();

        if(typeName.equals(long.class.getName())) {
            field.setLong(instance, Long.parseLong(value));
        } else if(typeName.equals(int.class.getName())) {
            field.setInt(instance, Integer.parseInt(value));
        } else if(typeName.equals(boolean.class.getName())) {
            field.setBoolean(instance, Boolean.parseBoolean(value));
        } else if(typeName.equals(double.class.getName())) {
            field.setDouble(instance, Double.parseDouble(value));
        } else if(typeName.equals(float.class.getName())) {
            field.setFloat(instance, Float.parseFloat(value));
        } else if(typeName.equals(short.class.getName())) {
            field.setShort(instance, Short.parseShort(value));
        } else if(typeName.equals(byte.class.getName())) {
            field.setByte(instance, Byte.parseByte(value));
        } else if(typeName.equals(char.class.getName())) {
            field.setChar(instance, value.length() == 0 ? ' ' : value.charAt(0));
        }
    }


}
