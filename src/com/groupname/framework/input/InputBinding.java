package com.groupname.framework.input;

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

    public InputBinding(String name) {
        this.name = Strings.requireNonNullAndNotEmpty(name);

        this.bindings = new HashSet<>();
    }

    public InputBinding(String name, String ... bindings) {
        this.name = Strings.requireNonNullAndNotEmpty(name);

        // varargs can be null, they're just an array
        if(bindings == null || bindings.length == 0) {
            throw new NullPointerException();
        }

        this.bindings = new HashSet<>();
        this.bindings.addAll(Arrays.asList(bindings));
    }

    public String getName() {
        return name;
    }

    public void addBinding(String button) {
        Strings.requireNonNullAndNotEmpty(button);
        bindings.add(button);
    }

    public Set<String> getBindings() {
        return bindings;
    }


}
