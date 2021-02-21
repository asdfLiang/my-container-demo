package com.lagou.edu.pojo;

/**
 * @author liangzj
 * @date 2021/2/21 19:56
 */
public class LagouBean {

    private Long id;

    private DependBean dependBean;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DependBean getDependBean() {
        return dependBean;
    }

    public void setDependBean(DependBean dependBean) {
        this.dependBean = dependBean;
    }

    @Override
    public String toString() {
        return "LagouBean{" +
                "id=" + id +
                ", dependBean=" + dependBean +
                '}';
    }
}
