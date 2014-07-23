
package nwb.demo;

import java.io.BufferedReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class RandomSiteGenerator
{
  private HttpGet mRequest;

  private HttpClient mClient;

  private BufferedReader mReader;

  private DocumentBuilder mBuilder;

  private StringBuffer mBuffer;

  private String mNewLine;

  private static String[] popularSites = {//
      "http://www.stuff.co.nz", //
          "http://www.youtube.com", //
          "http://www.trademe.co.nz", //
          "http://en.wikipedia.org/wiki/Main_Page", //
          "https://nz.yahoo.com/?p=us", //
          "http://www.nzherald.co.nz/", //
          "http://www.google.co.nz", //
          "http://msn.co.nz" //
      };


  public void getRandomSite(String url)
      throws IOException
  {
    Document doc = Jsoup.connect(url).get();
    // Element content = doc.getElementById("content");

    Elements links = doc.select("a[href]"); // a with href

    for (Element link : links)
    {
      System.out.println("Link href :" + link.text());
      String linkHref = link.attr("href");
      String linkText = link.text();
    }
  }


  public static String chooseUrl()
  {
    int index = (int)(Math.random() * popularSites.length);
    return popularSites[index];
  }
}
