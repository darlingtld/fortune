package serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fortune.dao.TuishuiDao;
import fortune.service.TuishuiService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class TuishuiTest {

    @Autowired
    private TuishuiService tuishuiService;
    
    @Autowired
    TuishuiDao tuishuiDao;
    
    @Test
    public void cleanAndPopulateTuishui() {
        tuishuiDao.removeAll();
        tuishuiService.populateTuishui4AllUser();
    }
    
    @Test
    public void populateTuishui() {
        tuishuiService.populateTuishui4AllUser();
    }
    
}
