
package nwb.demo.test;

import java.util.concurrent.TimeUnit;

import nwb.demo.UserBrowsingProfile;

import org.junit.Test;


public class UserBrowsingProfileTest
{

  @Test
  public void test()
      throws InterruptedException
  {
    UserBrowsingProfile ubp = new UserBrowsingProfile(2, 2, 10, TimeUnit.SECONDS);
    ubp.scheduleUserBrowsing();
  }

}
