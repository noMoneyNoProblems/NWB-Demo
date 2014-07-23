
package nwb.demo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class User
{
  int totalBrowsingTime;

  CloseableHttpClient httpclient = HttpClients.createDefault();


  /**
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args)
      throws InterruptedException
  {
    UserBrowsingProfile ubp = new UserBrowsingProfile(2, 2, 20, TimeUnit.SECONDS);
    ubp.scheduleUserBrowsing();
  }


  public String AccessRandomWebsite()
      throws ClientProtocolException, IOException
  {
    HttpGet httpGet = new HttpGet(GetRandomSite());
    CloseableHttpResponse response = httpclient.execute(httpGet);
    try
    {
      return response.getStatusLine().toString();
    }
    finally
    {
      response.close();
    }
  }


  public String GetRandomSite()
  {
    return "http://www.google.com";
  }


  public int getTotalBrowsingTime()
  {
    return totalBrowsingTime;
  }

}
