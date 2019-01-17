package com.beng.order;

import java.util.Date;
import java.util.List;

/**
 * 订单
 * 
 * @author apple
 */
public class Order {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 价格
     */
    private double price;

    /**
     * 商品
     */
    private List<Goods> goodsList;

    /**
     * 订单名称
     */
    private String orderName;

    private Date insertTime;

    private Date updateTime;

    private long st;
    private long ut;

    public Order() {
    }

    public Order(String orderId, double price, String orderName, List<Goods> goodsList, Date insertTime,
            Date updateTime, long st, long ut) {
        this.orderId = orderId;
        this.price = price;
        this.orderName = orderName;
        this.goodsList = goodsList;
        this.insertTime = insertTime;
        this.updateTime = updateTime;
        this.st = st;
        this.ut = ut;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        if (null == goodsList || goodsList.isEmpty()) {
            return price;
        } else {
            double result = 0.0;
            for (Goods good : goodsList) {
                result = result + good.getGoodsPrice();
            }
            return result;
        }
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
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
        return "Order [orderId=" + orderId + ", price=" + price + ", orderName=" + orderName + ", insertTime="
                + insertTime + ", updateTime=" + updateTime + ", st=" + st + ", ut=" + ut + "]";
    }
}
