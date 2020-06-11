package com.yimian.tree;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * RoleTest
 * chengshaohua
 *
 * @date 2020/6/11 16:00
 */
@Slf4j
public class RoleTest {
    public static int write = new Double(Math.pow(2, 0)).intValue();
    public static int read = new Double(Math.pow(2, 1)).intValue();
    public static int show = new Double(Math.pow(2, 2)).intValue();

    public static void setShowRole(Tree tree) {
        tree.setRole(tree.getRole() | show);
    }

    public static void setWriteRole(Tree tree) {
        tree.setRole(tree.getRole() | write);
    }

    public static void setReadRole(Tree tree) {
        tree.setRole(tree.getRole() | read);
    }

    public static void main(String[] args) {
        log.info("write = {}", Integer.toString(write));
        log.info("read = {}", Integer.toString(read));
        log.info("show = {}", Integer.toString(show));

        log.info("write | read = {}",Integer.toString(write | read));
        log.info(Integer.toString(write | read | show));

        //去除权限
        log.info("去除权限 " + Integer.toString((write | read | show) & (~write)));


        //判断权限
        boolean canShow = ((write | read | show) & show) == show;
        log.info("canShow {}", canShow);

        boolean canRead = ((write | read | show) & read) == read;
        log.info("canRead {}", canRead);

        boolean canWrite = ((read | show) & write) == write;
        log.info("canWrite {}", canWrite);
    }
}
