package cache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by apple on 15/10/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class MemcacheTest {

    @Autowired
    private MemcachedClient memcachedClient;

    @Test
    public void testMemcache() {
        try {
            // set, get
            memcachedClient.set("foo", 36000, "lingda");
            assertEquals("lingda", memcachedClient.get("foo"));
            System.out.println(memcachedClient.get("foo"));

            // replace
            memcachedClient.replace("foo", 36000, "tang");
            assertEquals("tang", memcachedClient.get("foo"));
            System.out.println(memcachedClient.get("foo"));

            // remove
            memcachedClient.delete("foo");
            assertNull(memcachedClient.get("foo"));
            System.out.println(memcachedClient.get("foo"));
        } catch (TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MemcachedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
