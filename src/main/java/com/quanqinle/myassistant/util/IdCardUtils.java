package com.quanqinle.myassistant.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 中华人民共和国身份证号码校验工具
 *
 * @author quanql
 * @version 2021/7/30
 */
public class IdCardUtils {

    /**
     * 18位身份证规则
     * 1、身份证号码结构：
     * 17位数字和1位校验码：6位地址码数字，8位生日数字，3位出生时间顺序号，1位校验码。
     * 2、地址码（前6位）：
     * 表示对象常住户口所在县（市、镇、区）的行政区划代码，按GB/T2260的规定执行。
     * 3、出生日期码（第7位至14位）：
     * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
     * 4、顺序码（第15位至17位）：
     * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
     * 5、校验码（第18位数）
     * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
     * Ai:表示第i位置上的身份证号码数字值
     * Wi:表示第i位置上的加权因子
     * Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * （2）计算模 Y = mod(S, 11)
     * （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     * <p>
     * 15位身份证规则：与较18位：
     * 1、出生日期码没有世纪，即没有19
     * 2、没有最后1位校验码
     */

    private static final Logger log = LoggerFactory.getLogger(IdCardUtils.class);

    final static Map<String, String> provinceCode = new HashMap<>();
    static {
        provinceCode.put("11", "北京");
        provinceCode.put("12", "天津");
        provinceCode.put("13", "河北");
        provinceCode.put("14", "山西");
        provinceCode.put("15", "内蒙古");
        provinceCode.put("21", "辽宁");
        provinceCode.put("22", "吉林");
        provinceCode.put("23", "黑龙江");
        provinceCode.put("31", "上海");
        provinceCode.put("32", "江苏");
        provinceCode.put("33", "浙江");
        provinceCode.put("34", "安徽");
        provinceCode.put("35", "福建");
        provinceCode.put("36", "江西");
        provinceCode.put("37", "山东");
        provinceCode.put("41", "河南");
        provinceCode.put("42", "湖北");
        provinceCode.put("43", "湖南");
        provinceCode.put("44", "广东");
        provinceCode.put("45", "广西");
        provinceCode.put("46", "海南");
        provinceCode.put("50", "重庆");
        provinceCode.put("51", "四川");
        provinceCode.put("52", "贵州");
        provinceCode.put("53", "云南");
        provinceCode.put("54", "西藏");
        provinceCode.put("61", "陕西");
        provinceCode.put("62", "甘肃");
        provinceCode.put("63", "青海");
        provinceCode.put("64", "宁夏");
        provinceCode.put("65", "新疆");
        provinceCode.put("71", "台湾");
        provinceCode.put("81", "香港");
        provinceCode.put("82", "澳门");
        provinceCode.put("91", "外国");
    }

    /**
     * Check if it is a valid Chinese ID card number
     *
     * @param idNumber the string to check
     * @return true if it is valid, otherwise false
     */
    public static boolean isChineseIdCardNumber(String idNumber) {
        if (idNumber == null || idNumber.isEmpty() || idNumber.isBlank()) {
            log.warn("The ID card number is empty!");
            return false;
        }

        String idNumWithoutWhitespace = idNumber.replaceAll(" ", "");
        int len = idNumWithoutWhitespace.length();

        if (len == 18) {
            return check18DigitIdCardNumber(idNumWithoutWhitespace);
        } else if (len == 15) {
            return check15DigitIdCardNumber(idNumWithoutWhitespace);
        } else {
            log.warn("The ID card number is not 18-digit or 15-digit!");
            return false;
        }

    }

    /**
     * An old ID card only has 15 digits in the format RRRRRRYYMMDDIII
     *
     * @param idNumberOf15Digit The 15 digits ID card number
     * @return -
     */
    private static boolean check15DigitIdCardNumber(String idNumberOf15Digit) {
        if (!isNumeric(idNumberOf15Digit)) {
            log.warn("The 15-digit ID card number should be number!");
            return false;
        }

        String addressCode = idNumberOf15Digit.substring(0, 6);
        String dateOfBirthCode = "19" + idNumberOf15Digit.substring(6, 12);
        String orderCode = idNumberOf15Digit.substring(12, 15);

        if (!isAddressCode(addressCode)) {
            return false;
        }
        if (!isDate(dateOfBirthCode)) {
            return false;
        }
        if (!isOrderCode(orderCode)) {
            return false;
        }

        return true;
    }

    /**
     * The 18 digits is in the format RRRRRRYYYYMMDDSSSC
     *
     * @param idNumberOf18Digit The 18 digits ID card number
     * @return -
     */
    private static boolean check18DigitIdCardNumber(String idNumberOf18Digit) {
        String top17OfIdNumber = idNumberOf18Digit.substring(0, 17);
        String lastOneOfIdNumber = idNumberOf18Digit.substring(17).toUpperCase();

        if (!isNumeric(idNumberOf18Digit) && !(isNumeric(top17OfIdNumber) && "X".equals(lastOneOfIdNumber))) {
            log.warn("The 18-digit ID card number can only contain number and letter X!");
            return false;
        }

        String addressCode = idNumberOf18Digit.substring(0, 6);
        String dateOfBirthCode = idNumberOf18Digit.substring(6, 14);
        String orderCode = idNumberOf18Digit.substring(14, 17);
        String checksum = lastOneOfIdNumber;

        int centuryOfBirth = Integer.parseInt(dateOfBirthCode.substring(0, 2));
        if (centuryOfBirth < 19) {
            log.warn("The PRC has not existed yet!");
            return false;
        }

        if (!isAddressCode(addressCode)) {
            return false;
        }
        if (!isDate(dateOfBirthCode)) {
            return false;
        }
        if (!isOrderCode(orderCode)) {
            return false;
        }

        String sChecksum = calculateChecksum(top17OfIdNumber);
        if (sChecksum == null || !sChecksum.equals(checksum)) {
            return false;
        }

        return true;
    }

    /**
     * Check if address code is valid.
     *
     * @param addressCode The top 6 digits of ID card number
     * @return -
     */
    public static boolean isAddressCode(String addressCode) {

        if (provinceCode.containsKey(addressCode.substring(0, 2))) {
            return true;
        } else {
            // todo To check the whole addressCode, not only the province code
            log.warn("invalidate province code!");
            return false;
        }

    }

    /**
     * Check if date code is valid.
     *
     * @param date The date string which must be ISO date formatter, such as '20210729'
     * @return -
     */
    public static boolean isDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Check if order code is valid.
     *
     * @param orderCode -
     * @return -
     */
    private static boolean isOrderCode(String orderCode) {
        // todo Is there something can be checked?
        return true;
    }

    /**
     * Calculate the checksum which is the last digit of ID card number
     *
     * @param top17OfIdNumber The top 17 digits of ID card number
     * @return the 1-digit checksum, or null if fail
     */
    public static String calculateChecksum(String top17OfIdNumber) {
        if (top17OfIdNumber == null || top17OfIdNumber.isEmpty() || top17OfIdNumber.isBlank()) {
            log.warn("The param is empty!");
            return null;
        }
        top17OfIdNumber = top17OfIdNumber.replaceAll(" ", "");
        if (top17OfIdNumber.length() != 17 || !isNumeric(top17OfIdNumber)) {
            log.warn("The param does not contain 17 numeric!");
            return null;
        }

        final String[] PARITY_BIT = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
        final int[] WEIGHT_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

        int sum = 0;
        char[] cDigit = top17OfIdNumber.toCharArray();
        /*
         * [RULE]
         * To calculate the checksum, each digit in order is multiplied by a weight in the ordered set [7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2] and summed together.
         * The sum modulus 11 is used as an index into the ordered set [1 0 X 9 8 7 6 5 4 3 2], with the first index being zero. The indexed value is the checksum digit.
         * In 15 digit IDs, III is an identification number created through certain mathematical methods (the last digit might be an English letter, such as X).
         */
        for (int i = 0; i < cDigit.length; i++) {
            sum += (cDigit[i] - '0') * WEIGHT_LIST[i];
        }

        return PARITY_BIT[sum % 11];
    }

    /**
     * check if all the characters of the string are numeric
     *
     * @param str -
     * @return -
     */
    private static boolean isNumeric(String str) {
        // return StringUtils.isNumeric(str);
        try {
            Long.parseLong(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Check if it is a male's id.
     * The param must be a valid ID first.
     *
     * @param id a valid ID card number, 18-digit or 15-digit
     * @return -
     */
    public static boolean isMale(String id) {
        if (id.length() == 15) {
            return Integer.parseInt(id.substring(14, 15)) % 2 != 0;
        } else /*if (id.length() == 18)*/ {
            return Integer.parseInt(id.substring(16, 17)) % 2 != 0;
        }
    }

}