package jack.changgou.poster.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_content")
public class Content {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "title")
    private String title;// 内容标题
    @Column(name = "url")
    private String url;// 链接
    @Column(name = "pic")
    private String pic;// 图片路径
    @Column(name = "status")
    private String status;// 状态:0无效，1有效
    @Column(name = "sort_order")
    private Integer sortOrder;// 排序

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
