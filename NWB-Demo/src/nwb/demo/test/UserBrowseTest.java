
package nwb.demo.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nwb.demo.Tabs;
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
    List<String> history = new ArrayList<String>();
    history.add("http://www.trademe.co.nz/");
    Tabs tabs = new Tabs();

    for (int i = 0; i < 5; i++)
    {
      history.add(userBrowse.BrowseController(tabs)); // history.get(i), tabs));
      System.out.println(userBrowse.getMostRecentWebHistory());
      tabs.chooseNextTabAction();
    }
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
    Tabs tabs = new Tabs();
    Document doc = userBrowse.browse("http://www.trademe.co.nz/", tabs);
    Elements e = doc.select("a:contains(Show more featured listings)");
    return e.first();
  }

}
