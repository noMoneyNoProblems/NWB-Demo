
package nwb.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UserBrowse
{
  List<String> history = new ArrayList<String>();


  /**
   * Starts or stops the users browsing depending on time elapsed, depth in website
   */
  public void BrowseController()
  {

  }


  /**
   * Clicks on a link in the page.
   * @throws IOException 
   */
  public void Browse(String url) throws IOException
  {
    Document doc = Jsoup.connect(url).get();
  }

}
