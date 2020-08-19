package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.TemplateMapper;
import jack.changgou.goods.pojo.Para;
import jack.changgou.goods.pojo.Spec;
import jack.changgou.goods.pojo.Template;
import jack.changgou.service.ParaService;
import jack.changgou.service.SpecService;
import jack.changgou.service.TemplateService;
import jack.changgou.vo.TemplateVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    TemplateMapper templateMapper;

    @Autowired
    SpecService specService;

    @Autowired
    ParaService paraService;

    @Override
    public List<Template> getAllTemplate() {
        return templateMapper.selectAll();
    }

    @Override
    public Template getTemplateById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addTemplate(Template template) {
        templateMapper.insertSelective(template);
    }

    @Override
    public void modefyTemplate(Template template) {
        templateMapper.updateByPrimaryKeySelective(template);
    }

    @Override
    public void deleteTemplateById(Integer id) {
        templateMapper.deleteByPrimaryKey(id);
    }

    /**
     * 创建搜索条件
     *
     * @param template
     * @return
     */
    public Example createCriteria(Template template) {

        // 创建条件构造器
        Example example = new Example(Template.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照模板名查询
        if (!StringUtils.isEmpty(template.getName())) {
            criteria.andEqualTo("name", template.getName());
        }
        return example;
    }

    @Override
    public List<Template> searchTemplate(Template template) {
        return templateMapper.selectByExample(createCriteria(template));
    }

    @Override
    public PageInfo<Template> getAllTemplatePaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Template> templates = templateMapper.selectAll();
        return new PageInfo<>(templates);
    }

    @Override
    public PageInfo<Template> searchTemplatePaged(Template template, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Template> templates = templateMapper.selectByExample(createCriteria(template));
        return new PageInfo<>(templates);
    }

    /**
     * 保存模板信息
     *
     * @param templateVo
     */
    @Override
    public void saveTemplate(TemplateVo templateVo) {
        Template template = new Template();
        template.setName(templateVo.getName());
        template.setParaNum(templateVo.getParas().size());
        template.setSpecNum(templateVo.getSpecs().size());
        templateMapper.insertSelective(template);

        // 添加规格
        int k = 1;
        for (Spec spec : templateVo.getSpecs()) {
            spec.setSeq(k);
            spec.setTemplateId(template.getId());
            specService.addSpec(spec);
            k++;
        }

        // 添加参数
        k = 1;
        for (Para para : templateVo.getParas()) {
            para.setSeq(k);
            para.setTemplateId(template.getId());
            paraService.addPara(para);
            k++;
        }
    }

    /**
     * 获取所有的模板信息
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<TemplateVo> getAllTemplateVo(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Template> templates = templateMapper.selectAll();
        PageInfo<Template> templatePageInfo = new PageInfo<>(templates);

        List<TemplateVo> templateVos = new ArrayList<>();
        Spec spec = new Spec();
        Para para = new Para();
        templates.forEach(template -> {
            TemplateVo templateVo = new TemplateVo();
            templateVo.setId(template.getId());
            templateVo.setName(template.getName());

            spec.setTemplateId(template.getId());
            List<Spec> specs = specService.searchSpec(spec);
            templateVo.setSpecs(specs);

            para.setTemplateId(template.getId());
            List<Para> paras = paraService.searchPara(para);
            templateVo.setParas(paras);

            templateVos.add(templateVo);
        });

        PageInfo<TemplateVo> templateVoPageInfo = new PageInfo<>(templateVos);
        templateVoPageInfo.setPageNum(templatePageInfo.getPageNum());
        templateVoPageInfo.setTotal(templatePageInfo.getTotal());
        templateVoPageInfo.setIsFirstPage(templatePageInfo.isIsFirstPage());
        templateVoPageInfo.setIsLastPage(templatePageInfo.isIsLastPage());
        templateVoPageInfo.setPages(templatePageInfo.getPages());
        templateVoPageInfo.setPageSize(templatePageInfo.getPageSize());

        return templateVoPageInfo;
    }
}
