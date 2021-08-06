package cn.gb2260;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author quanql
 */
public class GB2260 {
    private final int CODE_LENGTH = 6;
    public final String PROVINCE_END_WITH = "0000";
    public final String PREFECTURE_END_WITH = "00";

    private final Revision revision;
    /**
     * code->name
     */
    private LinkedHashMap<String, String> data;
    /**
     * province list
     */
    private final ArrayList<Division> provinces;

    public static void main(String[] args) {
        GB2260 gb2260 = new GB2260();
        String []codes = {"110000","110111","130000","130100","130102"};
        for (String code : codes) {
            System.out.println("code exists: " + gb2260.exists(code));
            System.out.println("code=" + gb2260.getDivision(code));
        }

        List<Division> list = gb2260.getProvinces();
        System.out.println("province=" + list);
        List<Division> list1 = gb2260.getPrefectures("320000");
        System.out.println("prefecture=" + list1);
        list1 = gb2260.getPrefectures("110000");
        System.out.println("prefecture=" + list1);
        List<Division> list2 = gb2260.getCounties("330101");
        System.out.println("county=" + list2);
        list2 = gb2260.getCounties("330100");
        System.out.println("county=" + list2);
        list2 = gb2260.getCounties("330000");
        System.out.println("county=" + list2);
    }

    public GB2260() {
        this(Revision.V2020);
    }

    /**
     * read data based on revision object
     * @param revision such as Revision.V2020
     */
    public GB2260(Revision revision) {
        this.revision = revision;
        String sRevision = revision.getCode();

        data = new LinkedHashMap<>();
        provinces = new ArrayList<>();
        InputStream inputStream = getClass().getResourceAsStream("/data/GB2260_" + sRevision + ".txt");
        assert inputStream != null;
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while (r.ready()) {
                String line = r.readLine();

                // blank line or line comments
                if (line.isBlank() || line.startsWith("#") || line.startsWith("//")) {
                    continue;
                }

                String[] split = line.split("\t");
                String code = split[0].trim();
                String name = split[1].trim();

                if (CODE_LENGTH != code.length() || !Pattern.matches("^\\d{6}$", code)) {
                    continue;
                }
                data.put(code, name);

                /*
                 * province list
                 */
                if (code.endsWith(PROVINCE_END_WITH)) {
                    Division division = new Division();
                    division.setCode(code);
                    division.setName(name);
                    provinces.add(division);
                }
            }
        } catch (IOException e) {
            System.err.println("Error in loading GB2260 data!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Verify code exists
     * @param code  code in GB2260 with length is 6
     * @return -
     */
    public boolean exists(String code) {
        if (code == null || code.isBlank()) {
            return false;
        }

        if (code.trim().length() != CODE_LENGTH) {
            throw new RuntimeException("Invalid code", new Throwable());
        }

        return data.containsKey(code);
    }

    /**
     * Get division object based on code, if code doesn't exist, get null
     * @param code code in GB2260 with length is 6
     * @return -
     */
    public Division getDivision(String code) {
        if (!exists(code)) {
            return null;
        }

        Division division = new Division();
        division.setCode(code);
        division.setName(data.get(code));
        division.setRevision(getRevision().getCode());

        if (code.endsWith(PROVINCE_END_WITH)) {
            return division;
        }

        String provinceCode = code.substring(0, 2) + PROVINCE_END_WITH;
        division.setProvince(data.get(provinceCode));

        if (code.endsWith(PREFECTURE_END_WITH)) {
            return division;
        }

        String prefectureCode = code.substring(0, 4) + PREFECTURE_END_WITH;
        division.setPrefecture(data.get(prefectureCode));

        division.setRevision(this.revision.getCode());
        return division;
    }

    public Revision getRevision() {
        return revision;
    }

    public ArrayList<Division> getProvinces() {
        return provinces;
    }

    public LinkedHashMap<String, String> getData() {
        return data;
    }

    public void setData(LinkedHashMap<String, String> data) {
        this.data = data;
    }

    public ArrayList<Division> getPrefectures(String code)  {
        ArrayList<Division> rv = new ArrayList<>();

        if (!code.endsWith(PROVINCE_END_WITH)) {
            throw new RuntimeException("Invalid province code", new Throwable());
        }

        if (!data.containsKey(code)) {
            throw new RuntimeException("Province code not found", new Throwable());
        }

        Division province = getDivision(code);

        for (String key : data.keySet()) {
            if (key.startsWith(code.substring(0, 2)) && key.endsWith(PREFECTURE_END_WITH)) {
                Division division = getDivision(key);
                division.setProvince(province.getName());
                rv.add(division);
            }
        }

        return rv;
    }

    public ArrayList<Division> getCounties(String code)  {
        ArrayList<Division> rv = new ArrayList<>();

        if (!Pattern.matches("^\\d+[1-9]0{2,3}$", code)) {
            throw new RuntimeException("Invalid prefecture code", new Throwable());
        }

        if (!data.containsKey(code)) {
            throw new RuntimeException("Prefecture code not found", new Throwable());
        }

        Division prefecture = getDivision(code);
        Division province = getDivision(code.substring(0, 2) + PROVINCE_END_WITH);

        Pattern pattern = Pattern.compile("^" + code.substring(0, 4) + "\\d+$");
        for (String key : data.keySet()) {
            if (pattern.matcher(key).matches()) {
                Division division = getDivision(key);
                division.setProvince(province.getName());
                division.setPrefecture(prefecture.getName());
                rv.add(division);
            }
        }

        return rv;
    }
}
