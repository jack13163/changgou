package jack.changgou.search.dao;

import jack.changgou.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuInfoMapper  extends ElasticsearchRepository<SkuInfo, Long> {
}
