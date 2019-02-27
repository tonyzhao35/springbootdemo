package demo.demoservice.impl;

import demo.demodao.Demo;
import demo.demodao.DemoRepository;
import demo.demoservice.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoRepository demoRepository;

    @Override
    public Demo addOne(Demo demo) {
        return this.demoRepository.save(demo);
    }
}
