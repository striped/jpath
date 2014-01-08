package org.kot.tools;

import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 07/01/2014 23:21
 */
public class ConcurrentRunner extends Parameterized {

	public ConcurrentRunner(final Class<?> klass) throws Throwable {
		super(klass);

		final int threads = getChildren().size();
		final ExecutorService executor = Executors.newFixedThreadPool(threads);
		final CompletionService<Void> completionService = new ExecutorCompletionService<Void>(executor);

		setScheduler(
				new RunnerScheduler() {
					@Override
					public void schedule(final Runnable childStatement) {
						completionService.submit(childStatement, null);
					}

					@Override
					public void finished() {
						executor.shutdown();
						try {
							for (int i = 0; i < threads; i++) {
								completionService.take();
							}
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						} finally {
							executor.shutdownNow();
						}
					}
				}
		);
	}
}
