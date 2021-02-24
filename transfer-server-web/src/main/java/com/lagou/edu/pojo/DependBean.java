package com.lagou.edu.pojo;

import com.lagou.factory.annotation.Component;

/**
 * @author liangzj
 * @date 2021/2/21 23:41
 */
@Component
public class DependBean {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
