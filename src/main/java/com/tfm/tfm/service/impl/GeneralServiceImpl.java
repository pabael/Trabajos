package com.tfm.tfm.service.impl;

import org.springframework.stereotype.Service;

import com.tfm.tfm.service.GeneralService;

@Service
public class GeneralServiceImpl implements GeneralService{

	public String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
