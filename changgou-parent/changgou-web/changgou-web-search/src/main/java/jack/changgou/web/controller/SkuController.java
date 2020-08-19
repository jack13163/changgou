package jack.changgou.web.controller;

import jack.changgou.search.feign.SkuFeign;
import jack.changgou.vo.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class SkuController {

    // 自动注入调用Feign
    @Autowired
    private SkuFeign skuFeign;

    @GetMapping("/list")
    public String search(@RequestParam(required = false) Map<String, String> map, Model model) throws IOException {
        Map<String, Object> resultMap = skuFeign.search(map);
        model.addAttribute("resultMap", resultMap);
        // 页面回显数据
        model.addAttribute("searchCondition", map);
        // 拼接URL
        String url = "/search/list";
        String sortUrl = "/search/list";
        if (!map.isEmpty()) {
            url += "?";
            sortUrl += "?";

            for (String key : map.keySet()) {
                if (key.equals("pageNum")) {
                    continue;
                }
                url += key + "=" + map.get(key) + "&";
                if (StringUtils.equalsIgnoreCase(key, "sortField")
                        || StringUtils.equalsIgnoreCase(key, "sortRule")) {
                    continue;
                }
                sortUrl += key + "=" + map.get(key) + "&";
            }
            url = url.substring(0, url.length() - 1);
            sortUrl = sortUrl.substring(0, sortUrl.length() - 1);
        }
        model.addAttribute("url", url);
        model.addAttribute("sortUrl", sortUrl);
        // 计算分页的起始页和结束页
        int total = Integer.parseInt(resultMap.get("total").toString());
        int totalPages = Integer.parseInt(resultMap.get("totalPages").toString());
        int currentPage = Integer.parseInt(resultMap.get("currentPage").toString());
        Page page = new Page(total, currentPage, 10);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("lpage", page.getLpage());
        model.addAttribute("rpage", page.getRpage());
        model.addAttribute("cpage", currentPage);
        return "search";
    }

    /**
     * 商品详情页
     * @param map
     * @param model
     * @return
     */
    @GetMapping("/item")
    public String detail(@RequestParam(required = false) Map<String, String> map, Model model) {
        return "item";
    }
}
