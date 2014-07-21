
package nwb.demo.test;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;


public abstract class AbstractTest
{
  CloseableHttpClient httpclient = HttpClients.createDefault();


  @Before
  public void SetUp()
  {

  }


  @After
  public void TearDown()
  {

  }

  // public String AccessRandomSite()
  // throws ClientProtocolException, IOException
  // {
  // HttpGet httpGet = new HttpGet(GetRandomSite());
  // CloseableHttpResponse response = httpclient.execute(httpGet);
  // try
  // {
  // return response.getStatusLine().toString();
  // }
  // finally
  // {
  // response.close();
  // }
  // }
  //
  //
  // public String GetRandomSite()
  // {
  // return "http://www.google.com";
  // }

}
