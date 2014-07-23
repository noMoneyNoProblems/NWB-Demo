
package nwb.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class UserBrowsingProfile
{
  private long initialDelay;

  private long delay;

  private TimeUnit timeUnit;

  private String url;

  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  private long stopAfter;

  private UserBrowse userBrowse;


  public UserBrowsingProfile(long initialDelay, long delay, long stopAfter, TimeUnit timeUnit)
  {
    this.initialDelay = initialDelay;
    this.delay = delay;
    this.stopAfter = stopAfter;
    this.timeUnit = timeUnit;
    userBrowse = new UserBrowse();

  }


  public void scheduleUserBrowsing()
  {
    url = RandomSiteGenerator.chooseUrl();
    final Runnable randomBrowser = new Runnable()
    {
      public void run()
      {
        url = userBrowse.BrowseController(url);
        updateWebHistoryFile();
      }
    };

    final ScheduledFuture<?> randomBrowserHandle =
        scheduler.scheduleWithFixedDelay(randomBrowser, initialDelay, delay, timeUnit);

    scheduler.schedule(new Runnable()
    {
      public void run()
      {
        randomBrowserHandle.cancel(true);
      }
    }, 60, TimeUnit.SECONDS);
  }


  private void updateWebHistoryFile()
  {
    System.out.println(userBrowse.getMostRecentWebHistory());
  }
}
