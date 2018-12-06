package cn.aegisa.bloomfilter.controller;

import cn.aegisa.bloomfilter.bitset.JavaBitSet;
import cn.aegisa.bloomfilter.common.BloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2018/12/6 10:45
 */
@Controller
@Slf4j
public class TestController {

    BloomFilter<String> filter;

    @PostConstruct
    public void doinit() {
        filter = new BloomFilter<>(0.0000001, 10000000);
        filter.bind(new JavaBitSet());
    }

    @RequestMapping("/init")
    @ResponseBody
    public String init() {
        String one = "";
        for (int i = 0; i < 10000000; i++) {
            String uuid = getUUID();
            filter.add(uuid);
            if (i == 555555) {
                one = uuid;
            }
        }
        return one;
    }

    @RequestMapping("/contains/{id}")
    @ResponseBody
    public String contains(@PathVariable String id) {
        return String.valueOf(filter.contains(id));
    }

    @RequestMapping("/add/{content}")
    @ResponseBody
    public String add(@PathVariable String content) {
        filter.add(content);
        return "added";
    }

    private String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }
}
