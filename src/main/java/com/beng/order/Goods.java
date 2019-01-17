package com.beng.order;

import java.util.Date;

/**
 * 商品
 * 
 * @author apple
 */
public class Goods {

    /**
     * 商品Id
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品价格
     */
    private double goodsPrice;

    /**
     * 商品描述
     */
    private String goodsDesc;

    private Date insertTime;

    private Date updateTime;

    private long st;
    private long ut;

    public Goods(String goodsId, String goodsName, double goodsPrice, String goodsDesc, Date insertTime,
            Date updateTime, long st, long ut) {
        super();
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsDesc = goodsDesc;
        this.insertTime = insertTime;
        this.updateTime = updateTime;
        this.st = st;
        this.ut = ut;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getSt() {
        return st;
    }

    public void setSt(long st) {
        this.st = st;
    }

    public long getUt() {
        return ut;
    }

    public void setUt(long ut) {
        this.ut = ut;
    }

    @Override
    public String toString() {
        return "Goods [goodsId=" + goodsId + ", goodsName=" + goodsName + ", goodsPrice=" + goodsPrice + ", goodsDesc="
                + goodsDesc + ", insertTime=" + insertTime + ", updateTime=" + updateTime + ", st=" + st + ", ut=" + ut
                + "]";
    }

}
