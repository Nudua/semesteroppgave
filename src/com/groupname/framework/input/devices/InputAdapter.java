package com.groupname.framework.input.devices;

import java.util.Set;

// https://en.wikipedia.org/wiki/Adapter_pattern
// Converge different input types into a simple unified interface
public interface InputAdapter {

    boolean isEnabled();
    void setEnabled(boolean enabled);

    void update(Set<String> digitalInput);
}
