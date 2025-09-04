package cn.puehmc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * 配置管理器类
 * 负责处理插件配置文件的加载和管理
 */
public class ConfigManager {
    
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private boolean enableForAll;
    private List<String> whitelist;
    
    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }
    
    /**
     * 加载配置文件
     */
    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();
        enableForAll = config.getBoolean("enable-for-all", false);
        whitelist = config.getStringList("whitelist");
    }
    
    /**
     * 重新加载配置文件
     */
    public void reloadConfig() {
        loadConfig();
    }
    
    /**
     * 获取是否为全员启用模式
     * @return 是否为全员启用
     */
    public boolean isEnableForAll() {
        return enableForAll;
    }
    
    /**
     * 获取白名单列表
     * @return 白名单列表
     */
    public List<String> getWhitelist() {
        return whitelist;
    }
    
    /**
     * 获取配置文件对象
     * @return 配置文件
     */
    public FileConfiguration getConfig() {
        return config;
    }
}