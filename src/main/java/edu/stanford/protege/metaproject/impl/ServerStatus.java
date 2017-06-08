package edu.stanford.protege.metaproject.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.metaproject.api.User;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;


@Immutable
public final class ServerStatus implements Serializable {
	private static final long serialVersionUID = -206877592564818670L;

	public final Optional<User> pausingUser;

	public ServerStatus(Optional<User> pausingUser) {
		this.pausingUser = pausingUser;
	}

	public boolean paused() {
		return pausingUser.isPresent();
	}
}
