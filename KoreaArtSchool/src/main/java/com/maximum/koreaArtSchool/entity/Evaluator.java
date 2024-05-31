package com.maximum.koreaArtSchool.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Table(name = "EVALUATOR")
@Data
@Entity
public class Evaluator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVL_NO", updatable = false)
    private int evlNo;
    @Column(name = "EVL_NM", nullable = false)
    private String evlNm;
    @Column(name = "GNDR_CD", nullable = false)
    private String gndrCd;
    @Column(name = "EVL_EML", nullable = false, unique = true)
    private String evlEml;
    @Column(name = "PWD", nullable = false)
    private String pwd;
    @Column(name = "EVL_TEL", nullable = false)
    private String evlTel;
    @Column(name = "EVL_OGDP", nullable = false)
    private String evlOgdp;
    @Column(name = "ADDR", nullable = false)
    private String addr;
    @Column(name = "ADDR_DETAIL", nullable = false)
    private String addrDetail;
    @Column(name = "EVL_BRDT", nullable = false)
    private String evlBrdt;
    @Column(name = "SLRY", nullable = true)
    private BigDecimal slry;
    @Column(name = "EVL_IMG", nullable = false)
    private String evlImg;
    @Column(name = "BANCK_NM", nullable = false)
    private String bankNm;
    @Column(name = "ACT_NO", nullable = false)
    private String actNo;
    @Column(name = "IS_SELECTED", nullable = false)
    private char isSelected;
    @Column(name = "IS_DELETED", nullable = false)
    private char isDeleted;
    @Column(name = "EVL_CRT_DT", nullable = false)
    private Timestamp evlCrtDt;
    @Column(name = "DEPT_CD", nullable = false)
    private String deptCd;
    @Column(name = "RCRT_CD", nullable = true)
    private String rcrtCd;
    @Column(name = "EVL_STG_CD", nullable = true)
    private String evlStgCd;
}
