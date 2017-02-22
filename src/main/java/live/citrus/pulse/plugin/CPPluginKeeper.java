package live.citrus.pulse.plugin;

import java.util.ArrayList;

public class CPPluginKeeper
{
    private ArrayList<CPPluginService> plugins;
    
    
    /**
     * constructor
     */
    public CPPluginKeeper()
    {
        this.plugins = new ArrayList<CPPluginService>();
    }
    
    
    /**
     * call plugin service list
     * 
     * @return
     */
    public ArrayList<CPPluginService> callPlugins()
    {
        return this.plugins;
    }
    
    
    /**
     * add plugin service
     * 
     * @param plugin
     */
    public void addPlugin(CPPluginService plugin)
    {
        this.plugins.add(plugin);
    }
}
