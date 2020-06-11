package com.yimian.tree;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.ArrayList;
import java.util.List;

/**
 * Tree
 * chengshaohua
 *
 * @date 2020/6/11 14:43
 */
@Data
@Builder
/*@NoArgsConstructor*/
@Accessors(chain = true)
public class Tree {
    private Integer id;
    private Integer pid;
    private Integer level;
    private String trace;
    private int role;
    private List<Tree> trees;

    public void addItem(Tree item) {
        if(trees == null) {
            trees = new ArrayList<Tree>();
        }

        trees.add(item);
    }
}
