package jack.changgou.poster.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_content_category")
public class ContentCategory {

    @Id
    @Column(name = "id")
    private Long id;//广告分类编号
    @Column(name="name")
    private String name;//广告分类名称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
