package com.studykt.controller;


import com.csvreader.CsvWriter;
import org.n3r.idworker.Sid;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestController {

    /**
     *生成随机数组
     *@param size 目标数组大小
     *@param max 目标数最大值
     */
    public static Set<Integer> getRandomSet(int size, int max) {
        Random random = new Random();
        Set<Integer> result = new LinkedHashSet<Integer>();
        while (result.size() < size) {
            Integer next = random.nextInt(max);
            result.add(next);
        }
        return result;
    }

    public static void readData(String filePath) {
        String [] courseIds = { "210410F276B7FZHH", "210410F276D63FK4", "210410F276G4P1KP",
                "210410F276HKMCX4", "210410F276M60B2W", "210410F276NSA98H", "210410F276RF3SCH",
                "210410F276TCP9D4", "210410F276X127MW", "210410F276Z6DWH0", "210410F2776MKBTC",
                "210410F2778ZCGMW", "210410F277CWKKP0", "210410F277H4R11P", "210410F277NNNF80",
                "210410F2780Z01KP", "210410F2782MRFRP", "210410F2784AFZXP", "210410F278693FY8",
                "210410F27880X028", "210410F278BN706W", "210410F278DR725P", "210410F278FTYWM8" };
        Random random = new Random();
        CsvWriter writer = new CsvWriter(filePath, ',', StandardCharsets.UTF_8);
        Sid sid = new Sid();
        try {
            for (int i = 0; i < 500; i++) {
                // 8-15
                int cnt = random.nextInt(8) + 8;
                // 用户id
                String userId = sid.nextShort();
                // 课程id集合
                Set<Integer> set = getRandomSet(cnt, 23);
                for (Integer cid : set) {
                    String id = UUID.randomUUID().toString().replace("-", "");
                    String [] data = { id, userId, courseIds[cid] };
                    // System.out.println(data[0] + "," + data[1]);
                    writer.writeRecord(data);
                }
            }
        } catch (IOException e ) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    /**
     * 数据格式：
     * 类别
     * 该类别题目数量
     * @param filePath
     */
    public static void importProblems(String filePath) {

    }

    public static void main(String[] args) {

    }
}
