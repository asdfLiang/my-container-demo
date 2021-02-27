package com.lagou.edu.service;

import com.lagou.factory.annotation.Service;
import com.lagou.factory.annotation.Transactional;

/**
 * @author liangzj
 * @date 2021/2/26 21:48
 */
@Service
@Transactional
public class TransferService {

    public String transfer() {
        System.out.println("transfer success");
        return "transfer success";
    }

}
