package cn.puehmc;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * 聊天监听器类
 * 负责监听玩家聊天事件并处理颜色代码
 */
public class ChatListener implements Listener {
    
    private final PermissionChecker permissionChecker;
    private final ColorProcessor colorProcessor;
    
    public ChatListener(PermissionChecker permissionChecker, ColorProcessor colorProcessor) {
        this.permissionChecker = permissionChecker;
        this.colorProcessor = colorProcessor;
    }
    
    /**
     * 处理玩家聊天事件 (Paper API)
     * @param event 聊天事件
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        
        // 将Adventure Component转换为字符串
        String originalMessage = LegacyComponentSerializer.legacySection().serialize(event.message());
        
        // 检查玩家是否有权限使用颜色代码
        if (permissionChecker.canUseColors(player)) {
            // 转换颜色代码
            String coloredMessage = colorProcessor.translateColorCodes(originalMessage);
            
            // 将处理后的字符串转换回Adventure Component
            Component newMessage = LegacyComponentSerializer.legacySection().deserialize(coloredMessage);
            event.message(newMessage);
            
            // 调试信息 - 输出到控制台
            System.out.println("[ChatColour] 玩家: " + player.getName() + ", 原始消息: " + originalMessage + ", 处理后: " + coloredMessage);
        } else {
            System.out.println("[ChatColour] 玩家 " + player.getName() + " 没有颜色代码权限");
        }
    }
}