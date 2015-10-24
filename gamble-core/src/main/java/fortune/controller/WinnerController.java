package fortune.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lingda on 2015/10/24.
 */
@Scope("prototype")
@Controller
@RequestMapping("/winner")
public class WinnerController {
}
