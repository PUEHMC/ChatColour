package cn.puehmc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * ChatColour 插件主类
 * 负责插件的初始化和各组件的协调
 */
public class Main extends JavaPlugin {
    
    private ConfigManager configManager;
    private PermissionChecker permissionChecker;
    private ColorProcessor colorProcessor;
    private ChatListener chatListener;
    private CommandHandler commandHandler;
    
    @Override
    public void onEnable() {
        // 初始化各个组件
        initializeComponents();
        
        // 注册事件监听器
        registerListeners();
        
        // 注册命令处理器
        registerCommands();
        
        getLogger().info("ChatColour 插件已启用！");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("ChatColour 插件已禁用！");
    }
    
    /**
     * 初始化各个组件
     */
    private void initializeComponents() {
        // 初始化配置管理器
        configManager = new ConfigManager(this);
        
        // 初始化权限检查器
        permissionChecker = new PermissionChecker(configManager);
        
        // 初始化颜色处理器
        colorProcessor = new ColorProcessor();
        
        // 初始化聊天监听器
        chatListener = new ChatListener(permissionChecker, colorProcessor);
        
        // 初始化命令处理器
        commandHandler = new CommandHandler(configManager, permissionChecker);
    }
    
    /**
     * 注册事件监听器
     */
    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(chatListener, this);
    }
    
    /**
     * 注册命令处理器
     */
    private void registerCommands() {
        Objects.requireNonNull(getCommand("ccr")).setExecutor(commandHandler);
    }
    
    /**
     * 获取配置管理器
     * @return 配置管理器实例
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    /**
     * 获取权限检查器
     * @return 权限检查器实例
     */
    public PermissionChecker getPermissionChecker() {
        return permissionChecker;
    }
    
    /**
     * 获取颜色处理器
     * @return 颜色处理器实例
     */
    public ColorProcessor getColorProcessor() {
        return colorProcessor;
    }
}