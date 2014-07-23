
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

  String mostRecentWebHistory;


  /**
   * Starts or stops the users browsing depending on time elapsed, depth in website
   * 
   * @return
   * @throws IOException
   */
  public String BrowseController(String url)
  {
    try
    {
      Element randomLink = getRandomLinkOnPage(url);

      if (randomLink == null || !checkLinkValid(randomLink, url))
      {
        if (history.size() < 2)
        {
          return null;
        }
        history.remove(getLastUrl(history));
        browse(getLastUrl(history));
      }
      else if (history.size() >= 9)
      {
        randomLink = getRandomLinkOnPage(getLastUrl(history));
        history.clear();
        browse(randomLink.attr("abs:href"));
      }
      else
      {
        browse(randomLink.attr("abs:href"));
      }

    }
    catch (IOException e)
    {
      System.out.println(e);
    }
    return getLastUrl(history);
  }


  /**
   * Randomly selects a link on the current page.
   * 
   * @param url
   * @return
   * @throws IOException
   */
  public Element getRandomLinkOnPage(String url)

  {
    Elements links = null;
    try
    {
      Document doc = Jsoup.connect(url).get();
      links = doc.select("a[href]");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

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
  public Document browse(String url)
  {
    Document doc = new Document("");
    try
    {
      doc = Jsoup.connect(url).get();
      setMostRecentWebHistory(url, System.currentTimeMillis());
    }
    catch (IOException e)
    {
      System.out.println(e);
      setMostRecentWebHistory("ERROR: failed to access URL", System.currentTimeMillis());
    }
    history.add(url);

    return doc;
  }


  private String getLastUrl(List<String> history)
  {
    return history.get(history.size() - 1);
  }


  public String getMostRecentWebHistory()
  {
    return mostRecentWebHistory;
  }


  public void setMostRecentWebHistory(String url, long currentTime)
  {
    mostRecentWebHistory = "url : " + url + " Time : " + currentTime;
  }
}
