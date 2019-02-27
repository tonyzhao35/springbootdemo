package demo.demoweb;

import demo.demodao.Demo;
import demo.demoservice.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @Autowired
    private DemoService demoService;

    @ResponseBody  // 返回 Json 数据
    @GetMapping("add")
    private Demo add(){
        Demo demo = new Demo();
        demo.setName("zhaolin");
        demo.setId(1);
        return demoService.addOne(demo); // 成功返回 保存后的 demo
    }

}
