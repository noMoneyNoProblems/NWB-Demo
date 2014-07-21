
package nwb.demo.test;

import java.io.IOException;
import java.net.URISyntaxException;

import nwb.demo.RandomSiteGenerator;

import org.junit.Test;
import org.xml.sax.SAXException;


public class RandomSiteGeneratorTest
{
  RandomSiteGenerator rsg = new RandomSiteGenerator();


  @Test
  public void test()
      throws IOException, SAXException, URISyntaxException
  {
    rsg.getRandomSite("http://www.nzherald.co.nz/");
  }
}
