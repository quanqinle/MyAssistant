package com.quanqinle.myworld.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * note:
 * 默认的自动对应
 *  Class(TaxRate) --> DB(tax_rate)
 *  field(rangeHighest) --> DB(range_highest)
 */
@Entity
public class TaxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float rate;

    private float rangeLowest;

    private float rangeHighest;

    private float quickDeduction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getRangeLowest() {
        return rangeLowest;
    }

    public void setRangeLowest(float rangeLowest) {
        this.rangeLowest = rangeLowest;
    }

    public float getRangeHighest() {
        return rangeHighest;
    }

    public void setRangeHighest(float rangeHighest) {
        this.rangeHighest = rangeHighest;
    }

    public float getQuickDeduction() {
        return quickDeduction;
    }

    public void setQuickDeduction(float quickDeduction) {
        this.quickDeduction = quickDeduction;
    }

    @Override
    public String toString() {
        return "TaxRate{" +
                "id=" + id +
                ", rate=" + rate +
                ", rangeLowest=" + rangeLowest +
                ", rangeHighest=" + rangeHighest +
                ", quickDeduction=" + quickDeduction +
                '}';
    }
}
