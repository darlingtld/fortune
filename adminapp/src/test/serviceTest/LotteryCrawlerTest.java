package serviceTest;

import fortune.crawler.LotteryMarkSixCrawler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by lingda on 2016/1/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class LotteryCrawlerTest {

    @Autowired
    private LotteryMarkSixCrawler lotteryMarkSixCrawler;

    @Test
    public void testCrawler(){
        lotteryMarkSixCrawler.fetchDrawingResult();
    }
}
