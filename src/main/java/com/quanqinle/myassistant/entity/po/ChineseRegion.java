package com.quanqinle.myassistant.entity.po;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author quanql
 * @version 2021/7/26
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "chinese_region")
public class ChineseRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 编码。GB2260
     */
    private String code;
    /**
     * 名称。GB2260
     */
    private String name;
    /**
     * 类型：1省 2市 3区县
     */
    private int type;
    /**
     * 修订版本号
     */
    private String revision;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 完整名称
     */
    private String fullName;
    /**
     * 状态：0删除，1有效
     */
    private int state;

    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public String getFullName() {
        return (province == null ? "" : province + " ") + (city == null ? "" : city + " ") + (name == null ? "" : name);
//        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ChineseRegion that = (ChineseRegion) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 911724329;
    }
}
