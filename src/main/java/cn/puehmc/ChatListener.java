package cn.puehmc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
     * 处理玩家聊天事件
     * @param event 聊天事件
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        
        // 检查玩家是否有权限使用颜色代码
        if (permissionChecker.canUseColors(player)) {
            // 转换颜色代码
            String coloredMessage = colorProcessor.translateColorCodes(message);
            event.setMessage(coloredMessage);
        }
    }
}