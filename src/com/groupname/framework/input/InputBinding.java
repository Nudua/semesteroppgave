package com.groupname.framework.input;

import com.groupname.framework.util.EmptyStringException;
import com.groupname.framework.util.Strings;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to bind one String to an arbitrary amount of other bindings.
 */
public class InputBinding {
    private String name;
    private Set<String> bindings;

    /**
     * Creates a new instance of this class with the specified name for this inputBinding,
     * with no initial bindings to bind to.
     *
     * @param name the name to use for this inputbinding.
     * @throws NullPointerException if the name is null.
     * @throws EmptyStringException if the name is empty (equal to the String "")
     */
    public InputBinding(String name) {
        this.name = Strings.requireNonNullAndNotEmpty(name);
        this.bindings = new HashSet<>();
    }

    /**
     * Creates a new instance of this class with the specified name and inputbindings.
     *
     * @param name the name to use for this inputbinding.
     * @param bindings the bindings to add to this inputbinding.
     * @throws NullPointerException if the name is null, or the bindings are null or empty.
     * @throws EmptyStringException if the name is empty (equal to the String "")
     */
    public InputBinding(String name, String ... bindings) {
        this.name = Strings.requireNonNullAndNotEmpty(name);

        // varargs can be null, they're just an array
        if(bindings == null || bindings.length == 0) {
            throw new NullPointerException();
        }

        this.bindings = new HashSet<>();
        this.bindings.addAll(Arrays.asList(bindings));
    }

    /**
     * Returns the name of this inputbinding.
     *
     * @return the name of this inputbinding.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a new binding to this inputbinding.
     * @param button the String binding to add.
     */
    public void addBinding(String button) {
        Strings.requireNonNullAndNotEmpty(button);
        bindings.add(button);
    }

    /**
     * Returns the bindings associated by this instance.
     *
     * @return the bindings associated by this instance.
     */
    public Set<String> getBindings() {
        return bindings;
    }

    /**
     * Returns the String representation of this object, the name and all of the bindings used for this instance.
     *
     * @return the name and all of the bindings used for this instance.
     */
    @Override
    public String toString() {
        return "InputBinding{" +
                "name='" + name + '\'' +
                ", bindings=" + bindings +
                '}';
    }
}
