package fortune.crawler;

import common.Utils;
import fortune.pojo.LotteryMarkSix;
import fortune.service.LotteryService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lingda on 2015/12/13.
 */
@Component
public class LotteryMarkSixCrawler {

    private static final String URL = "http://www.cpzhan.com/liu-he-cai/all-results";

    @Autowired
    private LotteryService lotteryService;

    public void fetchDrawingResult() {
        try {
            Utils.logger.info("crawl lottery mark six result");
            Document doc = Jsoup.connect(URL).userAgent("Mozilla").timeout(30000).get();
            Elements eles = doc.select("div.mytable tbody>tr");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Element ele : eles) {
                Elements tdEles = ele.select("td");
                int year = Integer.parseInt(tdEles.get(0).text());
                int issue = Integer.parseInt(tdEles.get(1).text());
                Date date = sdf.parse(tdEles.get(2).text());
                int one = Integer.parseInt(tdEles.get(3).text());
                int two = Integer.parseInt(tdEles.get(4).text());
                int three = Integer.parseInt(tdEles.get(5).text());
                int four = Integer.parseInt(tdEles.get(6).text());
                int five = Integer.parseInt(tdEles.get(7).text());
                int six = Integer.parseInt(tdEles.get(8).text());
                int special = Integer.parseInt(tdEles.get(9).text());
                System.out.println(year + "_" + issue + "_" + date + "_" + one + "_" + two + "_" + three + "_" + four + "_" + five + "_" + six + "_" + special);

//                save to database
                LotteryMarkSix lotteryMarkSix = new LotteryMarkSix();
                lotteryMarkSix.setIssue(Integer.parseInt(year + Utils.formatNumber000(issue)));
                lotteryMarkSix.setOne(one);
                lotteryMarkSix.setTwo(two);
                lotteryMarkSix.setThree(three);
                lotteryMarkSix.setFour(four);
                lotteryMarkSix.setFive(five);
                lotteryMarkSix.setSix(six);
                lotteryMarkSix.setSpecial(special);
                lotteryMarkSix.setTimestamp(date);
                if (lotteryService.getLotteryMarkSix(Integer.parseInt(year + Utils.formatNumber000(issue))) == null) {
                    lotteryService.saveLotteryMarkSix(lotteryMarkSix);
                }

            }

        } catch (Exception e) {
            Utils.logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LotteryMarkSixCrawler().fetchDrawingResult();
    }
}
