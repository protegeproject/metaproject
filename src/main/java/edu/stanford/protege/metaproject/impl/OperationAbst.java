package edu.stanford.protege.metaproject.impl;

import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.*;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public abstract class OperationAbst implements Operation, Serializable {
    private static final long serialVersionUID = -6214079549750022529L;
    protected final OperationId id;
    protected final Name name;
    protected final Description description;
    protected final OperationType type;
    protected final Scope scope;

    /**
     * Constructor
     *
     * @param id   Operation identifier
     * @param name Operation name
     * @param description  Operation description
     * @param type Operation type
     * @param scope Operation scope
     */
    public OperationAbst(OperationId id, Name name, Description description, OperationType type, Scope scope) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.description = checkNotNull(description);
        this.type = checkNotNull(type);
        this.scope = checkNotNull(scope);
    }

    @Override
    public OperationId getId() {
        return id;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public OperationType getType() {
        return type;
    }

    @Override
    public Scope getScope() {
        return scope;
    }

    @Override
    public boolean isUser() {
        return false;
    }

    @Override
    public boolean isProject() {
        return false;
    }

    @Override
    public boolean isRole() {
        return false;
    }

    @Override
    public boolean isOperation() {
        return true;
    }

    @Override
    public int compareTo(@Nonnull Operation that) {
        return ComparisonChain.start()
                .compare(this.name.get(), that.getName().get())
                .result();
    }
}
