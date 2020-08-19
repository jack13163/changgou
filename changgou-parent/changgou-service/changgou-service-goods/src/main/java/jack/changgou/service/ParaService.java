package jack.changgou.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Para;

import java.util.List;

public interface ParaService {
    /**
     * 查询所有参数
     * @return
     */
    List<Para> getAllPara();
    /**
     * 根据Id查询参数
     * @return
     */
    Para getParaById(Integer id);
    /**
     * 添加参数
     * @return
     */
    void addPara(Para para);
    /**
     * 修改参数
     * @return
     */
    void modefyPara(Para para);
    /**
     * 根据Id删除参数
     * @return
     */
    void deleteParaById(Integer id);
    /**
     * 根据条件查询参数
     * @return
     */
    List<Para> searchPara(Para para);
    /**
     * 分页查询所有的参数
     * @return
     */
    PageInfo<Para> getAllParaPaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的参数
     * @param para
     * @param page
     * @param size
     * @return
     */
    PageInfo<Para> searchParaPaged(Para para, Integer page, Integer size);
    /**
     * 根据分类Id查询所有的参数
     * @param id
     * @return
     */
    List<Para> getParasByCategoryId(Integer id);
}
