
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
  public String BrowseController(Tabs tabs)
  {
    String url = tabs.getUrlFromCurrentTab();
    tabs.chooseNextTabAction();
    int currentTab = tabs.getLastTabUsed();
    int latestEntryIndex = tabs.getLatestEntryIndex(tabs.getLastTabUsed());

    try
    {
      Element randomLink = getRandomLinkOnPage(url);

      if (randomLink == null || !checkLinkValid(randomLink, url))
      {
        if (latestEntryIndex < 2)
        {
          return null;
        }
        tabs.removeHistoryEntry(currentTab, latestEntryIndex);
        browse(tabs.getUrlFromTabHistory(currentTab, latestEntryIndex), tabs);
      }
      else
      {
        browse(randomLink.attr("abs:href"), tabs);
      }
    }
    catch (IOException e)
    {
      System.out.println(e);
    }
    return tabs.getNewUrlFromCurrentTab();
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
  public Document browse(String url, Tabs tabs)
  {
    Document doc = new Document("");

    try
    {
      doc = Jsoup.connect(url).get();
      setMostRecentWebHistory(url + " tab used : " + tabs.getLastTabUsed());
    }
    catch (IOException e)
    {
      System.out.println(e);
      setMostRecentWebHistory("ERROR: failed to access URL");
    }
    tabs.setUrlToTabHistory(tabs.getLastTabUsed(), url);

    return doc;
  }


  public String getMostRecentWebHistory()
  {
    return mostRecentWebHistory;
  }


  public void setMostRecentWebHistory(String url)
  {
    mostRecentWebHistory = "url : " + url;
  }

}
