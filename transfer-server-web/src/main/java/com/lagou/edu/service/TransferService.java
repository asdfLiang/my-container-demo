package com.lagou.edu.service;

import com.lagou.edu.dao.AccountDAO;
import com.lagou.edu.spring.factory.annotation.Autowired;
import com.lagou.edu.spring.factory.annotation.Service;
import com.lagou.edu.spring.factory.annotation.Transactional;

/**
 * @author liangzj
 * @date 2021/2/26 21:48
 */
@Service
public class TransferService {

    @Autowired
    AccountDAO accountDAO;

    public boolean transfer(String fromCardNo, String toCardNo, Double money) {
        accountDAO.update(fromCardNo, -money);
        int c = 1/0;
        accountDAO.update(toCardNo, money);
        return true;
    }

}
