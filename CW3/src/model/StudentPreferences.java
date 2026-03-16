package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Preference bundle used to personalise search and recommendations.
 */
public class StudentPreferences {
    private final Set<String> preferredCategories;

    public StudentPreferences() {
        this(new HashSet<String>());
    }

    public StudentPreferences(Set<String> preferredCategories) {
        this.preferredCategories = new HashSet<>(preferredCategories);
    }

    public Set<String> getPreferredCategories() {
        return Collections.unmodifiableSet(preferredCategories);
    }
}
