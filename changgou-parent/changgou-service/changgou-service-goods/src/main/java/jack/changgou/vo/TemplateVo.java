package jack.changgou.vo;

import jack.changgou.goods.pojo.Para;
import jack.changgou.goods.pojo.Spec;

import java.io.Serializable;
import java.util.List;

public class TemplateVo implements Serializable {
    private Integer id;
    private String name;
    private List<Para> paras;
    private List<Spec> specs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Para> getParas() {
        return paras;
    }

    public void setParas(List<Para> paras) {
        this.paras = paras;
    }

    public List<Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Spec> specs) {
        this.specs = specs;
    }
}
