package cn.gb2260;

/**
 * The codes involve file name (without suffix .txt) in /data/GB2260_xx.txt
 * source: http://www.mca.gov.cn/article/sj/xzqh/1980/
 * @author quanql
 */
public enum Revision {
    V2020("2020"),
    V2019("2019"),
    V2018("2018"),
    V2017("2017"),
    V2016("2016"),
    V2015("2015"),
    V2014("2014"),
    V2013("2013"),
    V2012("2012"),
    V2011("2011"),
    V2010("2010"),
    V2009("2009"),
    V2008("2008"),
    V2007("2007"),
    V2006("2006"),
    V2005("2005"),
    V200506("200506"),
    V2004("2004"),
    V200409("200409"),
    V200403("200403"),
    V2003("2003"),
    V200306("200306"),
    V2002("2002");

    private final String code;

    Revision(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
