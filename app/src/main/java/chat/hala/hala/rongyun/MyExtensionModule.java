package chat.hala.hala.rongyun;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

public class MyExtensionModule extends DefaultExtensionModule {

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules =  super.getPluginModules(conversationType);
         VideoCallPlugin myPlugin=new VideoCallPlugin();
         VoiceCallPlugin voicePlugin=new VoiceCallPlugin();
         pluginModules.add(myPlugin);
         pluginModules.add(voicePlugin);

        return pluginModules;
    }
}