package org.banana.demo.leetcode;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CodingBox {
    private static int tot;

    public static void main(String[] args) {
        String path = "C:\\Users\\92187\\Desktop\\1";

        File file = new File(path);
        File[] array = file.listFiles();
        for (File file1 : array) {
            String name = file1.getName();
            String index = name.substring(name.indexOf("_")+1,name.indexOf("."));
            System.out.println(name);
            System.out.println(index);
            Integer number = Integer.valueOf(index)+1708;
            String newName = number+".jpg";
            System.out.println(newName);
            System.out.println("---------");
            File newFile  = new File(path+"\\"+newName);
            file1.renameTo(newFile);
        }
    }
}
