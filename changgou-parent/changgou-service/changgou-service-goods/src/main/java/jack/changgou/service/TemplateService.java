package jack.changgou.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Template;
import jack.changgou.vo.TemplateVo;

import java.util.List;

public interface TemplateService {
    /**
     * 查询所有模板
     * @return
     */
    List<Template> getAllTemplate();
    /**
     * 根据Id查询模板
     * @return
     */
    Template getTemplateById(Integer id);
    /**
     * 添加模板
     * @return
     */
    void addTemplate(Template template);
    /**
     * 修改模板
     * @return
     */
    void modefyTemplate(Template template);
    /**
     * 根据Id删除模板
     * @return
     */
    void deleteTemplateById(Integer id);
    /**
     * 根据条件查询模板
     * @return
     */
    List<Template> searchTemplate(Template template);
    /**
     * 分页查询所有的模板
     * @return
     */
    PageInfo<Template> getAllTemplatePaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的模板
     * @param template
     * @param page
     * @param size
     * @return
     */
    PageInfo<Template> searchTemplatePaged(Template template, Integer page, Integer size);
    /**
     * 保存模板信息
     * @param templateVo
     */
    void saveTemplate(TemplateVo templateVo);
    /**
     * 分页查询所有的模板信息
     * @param page
     * @param size
     * @return
     */
    PageInfo<TemplateVo> getAllTemplateVo(Integer page, Integer size);
}
