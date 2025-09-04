package cn.puehmc;

import org.bukkit.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.BaseComponent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 颜色处理器类
 * 负责处理颜色代码的转换，支持&传统颜色代码和#十六进制颜色代码
 */
public class ColorProcessor {
    
    // 十六进制颜色代码的正则表达式
    private static final Pattern HEX_PATTERN = Pattern.compile("#([A-Fa-f0-9]{6})");
    
    /**
     * 转换颜色代码
     * 同时支持 & 符号的传统颜色代码和 # 十六进制颜色代码
     * @param message 原始消息
     * @return 转换后的消息
     */
    public String translateColorCodes(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }
        
        // 先处理十六进制颜色代码
        message = translateHexColorCodes(message);
        
        // 再处理传统的&颜色代码
        message = ChatColor.translateAlternateColorCodes('&', message);
        
        return message;
    }
    
    /**
     * 转换十六进制颜色代码
     * 将 #RRGGBB 格式的颜色代码转换为 Bukkit 可识别的格式
     * @param message 原始消息
     * @return 转换后的消息
     */
    public String translateHexColorCodes(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }
        
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        
        while (matcher.find()) {
            String group = matcher.group(1);
            try {
                // 使用 net.md_5.bungee.api.ChatColor 来支持十六进制颜色
                net.md_5.bungee.api.ChatColor hexColor = net.md_5.bungee.api.ChatColor.of("#" + group);
                matcher.appendReplacement(buffer, hexColor.toString());
            } catch (Exception e) {
                // 如果十六进制颜色不支持，保持原样
                matcher.appendReplacement(buffer, "#" + group);
            }
        }
        
        return matcher.appendTail(buffer).toString();
    }
    
    /**
     * 移除颜色代码
     * @param message 包含颜色代码的消息
     * @return 移除颜色代码后的纯文本消息
     */
    public String stripColorCodes(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }
        return ChatColor.stripColor(message);
    }
    
    /**
     * 检查消息是否包含颜色代码（包括&和#格式）
     * @param message 要检查的消息
     * @return 是否包含颜色代码
     */
    public boolean hasColorCodes(String message) {
        if (message == null || message.isEmpty()) {
            return false;
        }
        
        // 检查传统&颜色代码
        boolean hasTraditionalCodes = message.contains("&") && message.matches(".*&[0-9a-fk-or].*");
        
        // 检查十六进制颜色代码
        boolean hasHexCodes = HEX_PATTERN.matcher(message).find();
        
        return hasTraditionalCodes || hasHexCodes;
    }
    
    /**
     * 检查消息是否包含十六进制颜色代码
     * @param message 要检查的消息
     * @return 是否包含十六进制颜色代码
     */
    public boolean hasHexColorCodes(String message) {
        if (message == null || message.isEmpty()) {
            return false;
        }
        return HEX_PATTERN.matcher(message).find();
    }
    
    /**
     * 验证十六进制颜色代码是否有效
     * @param hexCode 十六进制颜色代码（包含#）
     * @return 是否为有效的十六进制颜色代码
     */
    public boolean isValidHexColor(String hexCode) {
        if (hexCode == null || hexCode.isEmpty()) {
            return false;
        }
        return hexCode.matches("^#[A-Fa-f0-9]{6}$");
    }
}