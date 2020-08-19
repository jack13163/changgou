package jack.changgou.search.service;

import java.io.IOException;
import java.util.Map;

public interface SkuInfoService {
    // 导入SkuInfo
    void importSkuInfo();
    // 按照关键词对Sku中name中的进行搜索
    Map<String, Object> searchByKeyword(Map<String, String> conditionMap) throws IOException;
}
