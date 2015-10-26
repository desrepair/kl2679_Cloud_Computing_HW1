package hw1;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BackgroundJobManager implements ServletContextListener {
	
	private ScheduledExecutorService scheduler;
	private Thread thread;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		scheduler.shutdownNow();
		thread.interrupt();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new UpdateStreams(), 0, 1, TimeUnit.MINUTES);
		thread = new Thread(new TweetFetcher());
		thread.start();
	}

}
