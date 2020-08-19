package jack.changgou.vo;

import java.util.List;

public class MenuVo {
    private Integer value;
    private String label;
    private List<MenuVo> children;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }

    public MenuVo(Integer value, String label, List<MenuVo> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }
}
