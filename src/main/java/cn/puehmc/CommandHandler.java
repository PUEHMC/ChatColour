package cn.puehmc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 命令处理器类
 * 负责处理插件的命令执行
 */
public class CommandHandler implements CommandExecutor {
    
    private final ConfigManager configManager;
    private final PermissionChecker permissionChecker;
    
    public CommandHandler(ConfigManager configManager, PermissionChecker permissionChecker) {
        this.configManager = configManager;
        this.permissionChecker = permissionChecker;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ccr")) {
            return handleReloadCommand(sender);
        }
        return false;
    }
    
    /**
     * 处理重载配置命令
     * @param sender 命令发送者
     * @return 命令是否成功处理
     */
    private boolean handleReloadCommand(CommandSender sender) {
        // 检查权限
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!permissionChecker.canReloadConfig(player)) {
                sender.sendMessage(ChatColor.RED + "你没有权限使用此命令！");
                return true;
            }
        } else {
            // 控制台默认有权限
        }
        
        // 重载配置
        configManager.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "ChatColour 配置已重新加载！");
        return true;
    }
}