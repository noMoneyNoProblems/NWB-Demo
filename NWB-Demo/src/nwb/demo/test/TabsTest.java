
package nwb.demo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import nwb.demo.Tabs;

import org.junit.Test;


public class TabsTest
{

  @Test
  public void testTabCreation()
  {
    Tabs tabs = new Tabs();
    tabs.createNewTab();
    tabs.createNewTab();
    assertEquals("size of tab group", 3, tabs.size());
  }


  @Test
  public void testBrowsingProbabilitiesChange()
  {
    Tabs tabs = new Tabs();
    double[] probabilitiesOfUsingEachTab = tabs.getTabUsageProbabilities();
    tabs.createNewTab();
    double[] probabilityAfterAddingNewTab = tabs.getTabUsageProbabilities();

    assertFalse("Probabilities of using each tab are equal", Arrays.equals(
        probabilitiesOfUsingEachTab, probabilityAfterAddingNewTab));

    tabs.removeTab(1);
    double[] probabilityRemovingTab = tabs.getTabUsageProbabilities();

    assertTrue("Probabilities of using each tab are not equal", Arrays.equals(
        probabilitiesOfUsingEachTab, probabilityRemovingTab));
  }


  @Test
  public void testCalculateTabUsageProbabilities()
  {
    Tabs tabs = new Tabs();
    int testValue = 4;
    double[] expectedProbabilities = new double[testValue];
    double uniformProbability = (double)1 / testValue;

    for (int i = 0; i < testValue - 1; i++)
    {
      tabs.createNewTab();
    }

    for (int i = 0; i < testValue; i++)
    {
      expectedProbabilities[i] = uniformProbability * (i + 1);
    }

    double[] tabUsageProbabilities = tabs.calculateTabUsageProbabilities();

    assertTrue("Probabilities of using each tab are not equal", Arrays.equals(
        tabUsageProbabilities, expectedProbabilities));
  }


  @Test
  public void testWillNotRemoveFinalTab()
  {
    Tabs tabs = new Tabs();
    tabs.setCreationRemovalProbability(0.0, 1.0);
    tabs.chooseNextTabAction();
    assertTrue(tabs.size() == 1);

  }


  @Test
  public void testAssigningFirstUrlToNewTab()
  {
    Tabs tabs = new Tabs();
    assertTrue("First tab has no first entry", tabs.getUrlFromTabHistory(0, 0) != null);
  }


  @Test
  public void testSettingAndGettingUrlFromTabHistory()
  {
    Tabs tabs = new Tabs();
    tabs.createNewTab();
    String randomUrl = "http://www.randomsite.com";

    tabs.setUrlToTabHistory(1, randomUrl);

    assertEquals("The url is being set to the wrong position", randomUrl, tabs
        .getUrlFromTabHistory(1, 1));
  }


  @Test
  public void testGetUrlFromCurrentTab()
  {
    Tabs tabs = new Tabs();
    tabs.createNewTab();
    tabs.chooseNextTabAction();
    int lastTabUsed = tabs.getLastTabUsed();

    String expectedUrl =
        tabs.getUrlFromTabHistory(lastTabUsed, tabs.getLatestEntryIndex(lastTabUsed));

    assertEquals("The expected url differs from the url given by current tab", expectedUrl, tabs
        .getUrlFromCurrentTab());
  }
}
