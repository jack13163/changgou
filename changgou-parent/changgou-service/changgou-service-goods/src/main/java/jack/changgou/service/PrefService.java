package jack.changgou.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Pref;

import java.util.List;

public interface PrefService {
    /**
     * 查询所有相册
     * @return
     */
    List<Pref> getAllPref();
    /**
     * 根据Id查询相册
     * @return
     */
    Pref getPrefById(Integer id);
    /**
     * 添加相册
     * @return
     */
    void addPref(Pref Pref);
    /**
     * 修改品牌
     * @return
     */
    void modefyPref(Pref Pref);
    /**
     * 根据Id删除相册
     * @return
     */
    void deletePrefById(Integer id);
    /**
     * 根据条件查询相册
     * @return
     */
    List<Pref> searchPref(Pref Pref);
    /**
     * 分页查询所有的相册
     * @return
     */
    PageInfo<Pref> getAllPrefPaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的品牌
     * @param Pref
     * @param page
     * @param size
     * @return
     */
    PageInfo<Pref> searchPrefPaged(Pref Pref, Integer page, Integer size);
}
