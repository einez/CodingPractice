package einez.practice.miscellaneous;

import java.util.LinkedList;
import java.util.Queue;

public class SerializeTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private static final String LEVEL_SEP = "@";
    private static final String NODE_SEP = "#";
    private static final char HAS_LEFT = 'L';
    private static final char HAS_RIGHT = 'R';
    private static final char HAS_NONE = 'N';
    private static final int NODE_SUFFIX = 2;
    private static final String NULL_TREE = "NULL";
    private static final TreeNode DUMMY_CHILD = new TreeNode(Integer.MIN_VALUE);

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return NULL_TREE;
        }
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            for (int s = q.size(); s > 0; s--) {
                TreeNode h = q.poll();
                sb.append(h.val);
                if (h.left != null) {
                    q.offer(h.left);
                    sb.append(HAS_LEFT);
                } else {
                    sb.append(HAS_NONE);
                }
                if (h.right != null) {
                    q.offer(h.right);
                    sb.append(HAS_RIGHT);
                } else {
                    sb.append(HAS_NONE);
                }
                sb.append(NODE_SEP);
            }
            sb.append(LEVEL_SEP);
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (NULL_TREE.equals(data)) {
            return null;
        }
        String[] levels = data.split(LEVEL_SEP);
        String rootStr = levels[0];
        TreeNode root = nodeFromString(rootStr.substring(0, rootStr.length() - 1));
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        for (int i = 1; i < levels.length; i++) {
            TreeNode[] children = nodesFromStrings(levels[i].split(NODE_SEP));
            int j = 0;
            for (int size = q.size(); size > 0; size--) {
                TreeNode h = q.poll();
                if (h.left == DUMMY_CHILD) {
                    h.left = children[j];
                    j++;
                }
                if (h.right == DUMMY_CHILD) {
                    h.right = children[j];
                    j++;
                }
            }
            for (TreeNode ch : children) {
                q.offer(ch);
            }
        }
        return root;
    }

    private TreeNode nodeFromString(String nodeStr) {
        int len = nodeStr.length();
        TreeNode node = new TreeNode(Integer.parseInt(nodeStr.substring(0, len - NODE_SUFFIX)));
        if (nodeStr.charAt(len - 2) == HAS_LEFT) {
            node.left = DUMMY_CHILD;
        }
        if (nodeStr.charAt(len - 1) == HAS_RIGHT) {
            node.right = DUMMY_CHILD;
        }
        return node;
    }

    private TreeNode[] nodesFromStrings(String[] nodeStrs) {
        int len = nodeStrs.length;
        TreeNode[] res = new TreeNode[len];
        for (int i = 0; i < len; i++) {
            res[i] = nodeFromString(nodeStrs[i]);
        }
        return res;
    }
}
