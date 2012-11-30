package ch.akuhn.foreach;

import java.util.NoSuchElementException;

public class Result {

	protected static final Result ThreadLocal = new Result();

	private ThreadLocal $result = new ThreadLocal();
	private Object NONE = new Object();

	public Result() {
		this.reset();
	}

	public <T> T result() {
		if (!available()) throw new NoSuchElementException("Thou shall not break out of loops!");
		return (T) $result.get();
	}

	public void offer(Object result) {
		$result.set(result);
	}

	public void reset() {
		$result.set(NONE);
	}

	public boolean available() {
		return $result.get() != NONE;
	}

}
