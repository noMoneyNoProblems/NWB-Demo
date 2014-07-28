
package nwb.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Tabs
{
  Map<Integer, List<String>> tabGroup;

  int lastUsedTab;

  double[] tabUsageProbabilities;

  double creationProbability;

  double removalProbability;

  static final Logger logger = LogManager.getLogger(UserBrowse.class.getName());


  public Tabs()
  {
    tabGroup = new HashMap<Integer, List<String>>();
    creationProbability = 0.3;
    removalProbability = 0.1;
    createNewTab();
    logger.debug("Entering application.");

  }


  public void createNewTab()
  {
    if (tabGroup.size() == 0)
    {
      tabGroup.put(0, new ArrayList<String>());
    }
    else
    {
      tabGroup.put(tabGroup.size(), new ArrayList<String>());
    }
    assignFirstUrlToNewTab();
    calculateTabUsageProbabilities();
  }


  private void assignFirstUrlToNewTab()
  {
    String url = RandomSiteGenerator.chooseUrl();
    setUrlToTabHistory(tabGroup.size() - 1, url);
  }


  public void removeTab(int i)
  {
    tabGroup.remove(i);
    calculateTabUsageProbabilities();
  }


  /**
   * Chooses the tab to use next and then browses using it.
   */
  public void chooseTabToUseNext()
  {
    int count = 0;
    boolean chosen = false;
    double selectedTab = Math.random();

    while (chosen == false)
    {
      if (selectedTab <= tabUsageProbabilities[count])
      {
        lastUsedTab = count;
        chosen = true;
      }
      count++;
    }
    // lastUsedTab = (int)(Math.random() * (tabGroup.size() - 1));
  }


  /**
   * Chooses whether to add a tab, delete one, or use a tab to browse.
   */
  public void chooseNextTabAction()
  {
    tabUsageProbabilities = new double[tabGroup.size()];
    double selectedTab = Math.random();

    if (selectedTab < creationProbability)
    {
      createNewTab();
    }
    else if (selectedTab < removalProbability && tabGroup.size() != 1)
    {
      int indexOfTabToRemove = (int)(Math.random() * tabGroup.size() - 1);
      removeTab(indexOfTabToRemove);
    }
    calculateTabUsageProbabilities();
    chooseTabToUseNext();
  }


  public int getLastTabUsed()
  {
    return lastUsedTab;
  }


  public String getUrlFromCurrentTab()
  {
    int lastUrl = getLatestEntryIndex(lastUsedTab);
    return tabGroup.get(lastUsedTab).get(lastUrl);
  }


  public double[] getTabUsageProbabilities()
  {
    return tabUsageProbabilities;
  }


  /**
   * @param newCreateProb
   * @param newRemoveProb
   */
  public void setCreationRemovalProbability(double newCreateProb, double newRemoveProb)
  {
    creationProbability = newCreateProb;
    removalProbability = newRemoveProb;
  }


  /**
   * Assigns uniform probabilities for each tab in the tabgroup. Each probability is the sum of
   * those that came before them. For example: tabgroup size is 4, uniform probability is 0.25,
   * assigned probabilities are [0.25, 0.5, 0.75, 1]
   */
  public double[] calculateTabUsageProbabilities()
  {
    tabUsageProbabilities = new double[tabGroup.size()];
    double uniformProbability = (double)1 / tabGroup.size();

    for (int i = 0; i < tabGroup.size(); i++)
    {
      tabUsageProbabilities[i] = uniformProbability * (i + 1);
    }
    return tabUsageProbabilities;
  }


  public String getNewUrlFromCurrentTab()
  {
    return getUrlFromTabHistory(lastUsedTab, tabGroup.get(lastUsedTab).size() - 1);
  }


  /**
   * Assigns the url to the end of the history of a tab
   * 
   * @param tabIndex
   * @param url
   */
  public void setUrlToTabHistory(int tabIndex, String url)
  {
    tabGroup.get(tabIndex).add(url);
  }


  /**
   * Gets the url from a given point in the history of a tab
   * 
   * @param tabIndex
   * @param historyIndex
   * @return
   */
  public String getUrlFromTabHistory(int tabIndex, int historyIndex)
  {
    return tabGroup.get(tabIndex).get(historyIndex);

  }


  /**
   * Returns the amount of tabs in the Tab group
   * 
   * @return
   */
  public int size()
  {
    return tabGroup.size();
  }


  /**
   * Returns the amount of urls in a single tab's history
   * 
   * @return
   */
  public int getLatestEntryIndex(int tabIndex)
  {
    return tabGroup.get(tabIndex).size() - 1;
  }


  /**
   * Removes the history entry from the specified tab index. If afterwards the tab has zero entries,
   * it adds a single new one.
   * 
   * @param tabIndex
   * @param historyIndex
   */
  public void removeHistoryEntry(int tabIndex, int historyIndex)
  {
    tabGroup.get(tabIndex).remove(historyIndex);
    if (tabGroup.get(tabIndex).size() == 0)
    {
      tabGroup.get(tabIndex).add(RandomSiteGenerator.chooseUrl());
    }
  }
}
