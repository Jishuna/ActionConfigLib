package me.jishuna.actionconfiglib;

import java.util.logging.Logger;

public class ParsingException extends Exception {
	private static final long serialVersionUID = 738886171934348091L;
	
	private final ParsingException cause;

	public ParsingException(String msg) {
		super(msg);
		this.cause = null;
	}

	public ParsingException(String msg, ParsingException cause) {
		super(msg);
		this.cause = cause;
	}

	public void log(Logger logger, int spaces) {
		logger.severe(" ".repeat(spaces) + this.getMessage());

		if (this.cause != null)
			this.cause.log(logger, spaces + 2);
	}

}
