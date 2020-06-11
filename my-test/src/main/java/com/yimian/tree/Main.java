package com.yimian.tree;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Main
 * chengshaohua
 *
 * @date 2020/6/11 14:53
 */
@Slf4j
public class Main {
    private static final String TRACE_FORMAT = "-%d-";
    public static final Pattern PATTERN_TRACE = Pattern.compile("-(\\d+)-");
    private static final String[] IGNORE_PROPERTIES = new String[]{"trees"};
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    private static final Set<Tree> ROLE_TREE = new HashSet<Tree>(10);
    private static final Set<Tree> ALL_TREE = new HashSet<Tree>();

    public static void main(String[] args) {
        Tree tree = initTree();
        log.info(new Gson().toJson(tree));
        print(tree);

        log.info("tree size = {}", ALL_TREE.size());
        Tree trees = convert2Tree(ALL_TREE);
        log.info("my {}", new Gson().toJson(tree));

        Set<Integer> roleTreeIds = new HashSet<>();
        roleTreeIds.add(2);
        log.info("ROLE_TREE = {}", ROLE_TREE.stream().map(Tree::getId).collect(Collectors.toList()));
        ROLE_TREE.stream().forEach(item -> getAllIds(item.getTrace(), roleTreeIds));
        log.info("roleTreeIds = {}", roleTreeIds);

        tagging(tree, roleTreeIds);

        Tree result = Tree.builder().build();
        roleTree(tree, result, true);
        log.info("result = {}", new Gson().toJson(result));
    }

    /**
     * 转成tree结构
     */
    private static Tree convert2Tree(Set<Tree> trees) {
        Tree root = Tree.builder().build();
        root.setId(ATOMIC_INTEGER.incrementAndGet());
        root.setLevel(-1);
        root.setPid(0);
        root.setTrace(String.format(TRACE_FORMAT, root.getId()));

        convert2Tree(trees, root.getLevel() + 1, root);
        return root;
    }

    private static void convert2Tree(Set<Tree> trees, int level, Tree ptree) {
        List<Tree> treesLevel = trees.stream()
            .filter(item -> item.getLevel() == level && item.getPid().equals(ptree.getId()))
            .collect(Collectors.toList());

        if(treesLevel == null || treesLevel.size() == 0) {
            return;
        }

        treesLevel.stream().sorted(Comparator.comparing(Tree::getId)/*.reversed()*/);

        if(ptree != null) {
            ptree.setTrees(treesLevel);
        }

        treesLevel.stream().forEach(item -> convert2Tree(trees, level + 1, item));
    }

    /**
     * 获取全路径ids
     */
    private static void getAllIds(String trace, Set<Integer> ids) {
        if(StringUtils.isEmpty(trace)) {
            return;
        }

        Matcher m = PATTERN_TRACE.matcher(trace);
        while(m.find()) {
            ids.add(Integer.parseInt(m.group(1)));
        }
    }


    /**
     * 输出结果
     */
    private static void roleTree(Tree tree, Tree result, boolean checkRole) {
        Tree treeCopy = null;

        if(tree == null) {
            return;
        }

        if(tree.getLevel() == -1) {
            BeanUtils.copyProperties(tree, result, IGNORE_PROPERTIES);
            treeCopy = result;
        }
        else {
            boolean show = !checkRole || (checkRole && tree.getRole() > 0);

            if(show) {
                treeCopy = Tree.builder().build();
                BeanUtils.copyProperties(tree, treeCopy, IGNORE_PROPERTIES);
                result.addItem(treeCopy);
            }
        }

        if(treeCopy == null) {
            return;
        }

        if(tree.getTrees() != null) {
            for(Tree item : tree.getTrees()) {
                boolean notCheckRole = !checkRole || ((item.getRole() & RoleTest.write) == RoleTest.write) ||
                    ((item.getRole() & RoleTest.read) == RoleTest.read);
                roleTree(item, treeCopy, !notCheckRole);
            }
        }
    }

    /**
     * 标注,遍历tree
     */
    private static void tagging(Tree tree, Set<Integer> roleTreeIds) {
        if(tree == null) {
            return;
        }
        else if(roleTreeIds.contains(tree.getId())) {
            RoleTest.setShowRole(tree);
        }

        if(tree.getTrees() != null) {
            for(Tree item : tree.getTrees()) {
                tagging(item, roleTreeIds);
            }
        }
    }

    /**
     * 层序输出
     */
    private static void print(Tree tree) {
        log.info("层序输出");

        Queue<Tree> queue = new LinkedList<Tree>();
        queue.offer(tree);
        int level = -1;

        while(queue.size() > 0) {
            int size = queue.size();

            for(int i = 0; i < size; i++) {
                Tree treeLevel = queue.poll();
                ALL_TREE.add(treeLevel);

                if(treeLevel.getLevel() != -1) {
                    int randomInt = ThreadLocalRandom.current().nextInt(100);

                    if(randomInt < 2) {
                        RoleTest.setWriteRole(treeLevel);
                        ROLE_TREE.add(treeLevel);
                    }
                    else if(randomInt < 5) {
                        RoleTest.setShowRole(treeLevel);
                        ROLE_TREE.add(treeLevel);
                    }
                }

                if(treeLevel.getTrees() != null) {
                    for(Tree item : treeLevel.getTrees()) {
                        queue.offer(item);
                    }
                }
            }

            log.info("------------------- {}", level);
            level++;
        }
    }

    /**
     * 初始化数据
     */
    private static Tree initTree() {
        Tree root = Tree.builder().build();
        root.setId(ATOMIC_INTEGER.incrementAndGet());
        root.setLevel(-1);
        root.setPid(0);
        root.setTrace(String.format(TRACE_FORMAT, root.getId()));

        Tree ptree = root;

        for(int i = 0; i < 10; i++) {
            Tree item = Tree.builder()
                .id(ATOMIC_INTEGER.incrementAndGet())
                .level(ptree.getLevel() + 1)
                .pid(ptree.getId())
                .build();
            item.setTrace(ptree.getTrace() + String.format(TRACE_FORMAT, item.getId()));
            //RoleTest.setReadRole(item);

            int size = new Random().nextInt(10);

            for(int j = 0; j < size; j++) {
                Tree item2 = Tree.builder()
                    .id(ATOMIC_INTEGER.incrementAndGet())
                    .level(item.getLevel() + 1)
                    .pid(item.getId())
                    .build();
                item2.setTrace(item.getTrace() + String.format(TRACE_FORMAT, item2.getId()));

                item.addItem(item2);
            }

            ptree.addItem(item);
        }

        return root;
    }
}
