package com.kson.mvpframework.model.entity;

import java.util.List;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/11
 * Description:
 */
public class ImageEntity {
    private int value = 1000;
    public String code;
    public String msg;
    public List<Image> data;
    public static class Image{
        public String _id;
        public String imgurl;

        public Image(String _id, String imgurl) {
            this._id = _id;
            this.imgurl = imgurl;
        }
    }
}
