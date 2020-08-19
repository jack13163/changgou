package jack.changgou.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Spec;

import java.util.List;

public interface SpecService {
    /**
     * 查询所有规格
     * @return
     */
    List<Spec> getAllSpec();
    /**
     * 根据Id查询规格
     * @return
     */
    Spec getSpecById(Integer id);
    /**
     * 添加规格
     * @return
     */
    void addSpec(Spec spec);
    /**
     * 修改规格
     * @return
     */
    void modefySpec(Spec spec);
    /**
     * 根据Id删除规格
     * @return
     */
    void deleteSpecById(Integer id);
    /**
     * 根据条件查询规格
     * @return
     */
    List<Spec> searchSpec(Spec spec);
    /**
     * 分页查询所有的规格
     * @return
     */
    PageInfo<Spec> getAllSpecPaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的规格
     * @param spec
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spec> searchSpecPaged(Spec spec, Integer page, Integer size);
    /**
     * 根据分类查询所有的规格
     * @param cid
     * @return
     */
    List<Spec> getSpecByCategoryId(Integer cid);
}
