package org.cneko.endrodsocket;

import net.fabricmc.api.ModInitializer;
import org.cneko.endrodsocket.commands.ERSCommand;
import org.cneko.endrodsocket.events.UseOnPlayerEvent;
import org.cneko.endrodsocket.events.UseOnSelfEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndRodSocket implements ModInitializer {
    public static Logger logger = LoggerFactory.getLogger("EndRodSocket");
    @Override
    public void onInitialize() {
        UseOnPlayerEvent.Companion.init();
        UseOnSelfEvent.Companion.init();
        ERSCommand.Companion.init();
    }
}
