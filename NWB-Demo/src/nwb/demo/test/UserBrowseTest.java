
package nwb.demo.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import nwb.demo.UserBrowse;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;


public class UserBrowseTest
{
  UserBrowse userBrowse = new UserBrowse();


  @Test
  public void testTerminationWhenNoLinksOnPage()
      throws IOException
  {
    assertEquals("Link returned is null", userBrowse
        .getRandomLinkOnPage("http://www.dontclick.it/"), null);
  }


  @Test
  public void testBrowserControllerWebGet()
      throws IOException
  {
    userBrowse.BrowseController("http://www.trademe.co.nz/");
  }


  @Test
  public void testCheckLinkValid()
      throws IOException
  {
    userBrowse.checkLinkValid(createInvalidLink(), "http://www.trademe.co.nz/");
  }


  /**
   * Creates a link where the element has a reference to javascript in the href attribute.
   * 
   * @return
   * @throws IOException
   */
  private Element createInvalidLink()
      throws IOException
  {
    Document doc = userBrowse.Browse("http://www.trademe.co.nz/");
    Elements e = doc.select("a:contains(Show more featured listings)");
    return e.first();
  }
}
