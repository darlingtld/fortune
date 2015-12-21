package fortune.service;

import fortune.dao.ActionTraceDao;
import fortune.pojo.ActionTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by tangl9 on 2015-12-21.
 */
@Service
public class ActionTraceService {

    @Autowired
    private ActionTraceDao actionTraceDao;

    @Transactional
    public void save(ActionTrace actionTrace) {
        actionTraceDao.save(actionTrace);
    }

    @Transactional
    public void save(String user, String action, HttpServletRequest request){
        ActionTrace actionTrace = new ActionTrace();
        actionTrace.setUser(user);
        actionTrace.setAction(action);
        actionTrace.setTimestamp(new Date());
        actionTrace.setRequestUrl(request.getRequestURI());
        actionTrace.setIp(request.getRemoteAddr());
        save(actionTrace);
    }

}
