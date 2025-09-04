package cn.puehmc;

import org.bukkit.entity.Player;

/**
 * 权限检查器类
 * 负责检查玩家是否有权限使用颜色代码
 */
public class PermissionChecker {
    
    private final ConfigManager configManager;
    
    public PermissionChecker(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    /**
     * 检查玩家是否可以使用颜色代码
     * @param player 玩家
     * @return 是否可以使用
     */
    public boolean canUseColors(Player player) {
        // 如果玩家有bypass权限，直接允许
        if (player.hasPermission("chatcolour.bypass")) {
            return true;
        }
        
        // 如果启用了全员模式
        if (configManager.isEnableForAll()) {
            return true;
        }
        
        // 检查权限节点或白名单
        if (player.hasPermission("chatcolour.use") || 
            configManager.getWhitelist().contains(player.getName().toLowerCase())) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 检查玩家是否有重载配置的权限
     * @param player 玩家
     * @return 是否有权限
     */
    public boolean canReloadConfig(Player player) {
        return player.hasPermission("chatcolour.reload");
    }
}