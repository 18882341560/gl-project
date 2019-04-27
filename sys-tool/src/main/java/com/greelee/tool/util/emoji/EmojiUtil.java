package com.greelee.tool.util.emoji;

import com.github.binarywang.java.emoji.EmojiConverter;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
public class EmojiUtil {

    private EmojiUtil() { }

    /**
     * 转换
     *
     * @param emojiString 原始字符串
     * @return 转换后的字符串
     */
    public static String toHtml(String emojiString) {
        return emojiString == null ? null : EmojiConverter.getInstance().toHtml(emojiString);
    }
}
