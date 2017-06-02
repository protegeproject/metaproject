package edu.stanford.protege.metaproject.impl;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;


@Immutable
public final class ServerStatus implements Serializable {
	private static final long serialVersionUID = -206877592564818670L;
	public final boolean isPaused;

	public ServerStatus(boolean isPaused) {
		this.isPaused = isPaused;
	}
}
