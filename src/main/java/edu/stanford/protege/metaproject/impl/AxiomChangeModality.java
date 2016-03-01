package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.ChangeModality;

/**
 * An axiom change restriction modality specifies whether the axiom type can be added or removed
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public enum AxiomChangeModality implements ChangeModality {

    ADDITION {
        @Override
        public String get() {
            return getName(name());
        }
    },
    REMOVAL {
        @Override
        public String get() {
            return getName(name());
        }
    };

    String getName(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
    }
}