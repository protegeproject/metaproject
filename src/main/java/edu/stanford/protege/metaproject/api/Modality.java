package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Modality extends TextProperty {

    /**
     * An axiom change restriction modality specifies whether the axiom type can be
     * added, removed, or both.
     */
    enum AxiomChange implements Modality {
        ADDITION {
            public String get() {
                return getName(name());
            }
        },
        REMOVAL {
            public String get() {
                return getName(name());
            }
        },
        ADDITION_OR_REMOVAL {
            public String get() {
                return getName(name());
            }
        };

        String getName(String name) {
            name = name.replaceAll("_", " ");
            return Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
        }
    }
}
