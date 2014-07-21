
package nwb.demo.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import nwb.demo.User;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;


public class UserTest extends AbstractTest
{
  User user = new User();


  @Test
  public void testAccessRandomWebsite()
      throws ClientProtocolException, IOException
  {
    assertEquals("Status Line:", user.AccessRandomWebsite(), "HTTP/1.1 200 OK");
  }

}
