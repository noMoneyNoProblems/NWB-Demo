
package nwb.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class UserBrowse
{
  List<String> history = new ArrayList<String>();

  int totalBrowsingTime;

  int elapsedBrowsingTime;

  boolean noLinksOnCurrentPage;


  /**
   * Starts or stops the users browsing depending on time elapsed, depth in website
   * 
   * @throws IOException
   */
  public void BrowseController(String url)
  {
    try
    {
      Element randomLink = getRandomLinkOnPage(url);

      if (randomLink == null || !checkLinkValid(randomLink, url))
      {
        if (history.size() < 2)
        {
          return;
        }
        history.remove(getLastUrl(history));
        Browse(getLastUrl(history));
      }
      else if (history.size() >= 9)
      {
        history.remove(getLastUrl(history));
        Browse(getLastUrl(history));
      }
      else
      {
        Browse(randomLink.attr("abs:href"));
      }

    }
    catch (IOException e)
    {
      System.out.println(e);
    }
  }


  /**
   * Randomly selects a link on the current page.
   * 
   * @param url
   * @return
   * @throws IOException
   */
  public Element getRandomLinkOnPage(String url)
      throws IOException
  {
    Document doc = Jsoup.connect(url).get();
    Elements links = doc.select("a[href]");

    if (links == null || links.size() == 0)
    {
      return null;
    }

    int index = (int)(Math.random() * links.size());
    return links.get(index);
  }


  /**
   * Returns whether link is valid or not. Tries to find a valid link 10 times before giving up and
   * going back.
   * 
   * @param element
   * @return
   * @throws IOException
   */
  public boolean checkLinkValid(Element randomLink, String url)
      throws IOException
  {
    int count = 0;

    while (count < 10)
    {
      if (!randomLink.attr("href").contains("javascript"))
      {
        return true;
      }
      else
      {
        randomLink = getRandomLinkOnPage(url);
        count++;
      }
    }
    return false;
  }


  /**
   * Clicks on a link in the page.
   * 
   * @return the document acquired by the link.
   * @throws IOException
   */
  public Document Browse(String url)
      throws IOException
  {
    Document doc = Jsoup.connect(url).get();
    history.add(url);

    return doc;
  }


  private String getLastUrl(List<String> history)
  {
    return history.get(history.size() - 1);
  }

}
