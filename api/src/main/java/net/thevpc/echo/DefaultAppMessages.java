/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritablePList;
import net.thevpc.common.props.impl.ReadOnlyPList;
import net.thevpc.common.msg.Message;

/**
 *
 * @author thevpc
 */
public class DefaultAppMessages extends ReadOnlyPList<Message> implements AppMessages {

    protected WritablePList<AppMessageProducer> messageProducers = Props.of("producers").listOf(AppMessageProducer.class);
    private int maxMessageEntries = 1000;
    private boolean _updateMessages;
    private Application app;

    public DefaultAppMessages(Application app) {
        super(Props.of("messages").linkedListOf(Message.class));
        this.app = app;
        base().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                switch (event.getAction()) {
                    case ADD: {
                        while (maxMessageEntries > 0 && base().size() > maxMessageEntries) {
                            base().remove(0);
                        }
                        break;
                    }
                }
            }
        });
    }

    @Override
    public WritablePList<AppMessageProducer> producers() {
        return messageProducers;
    }

    private WritablePList<Message> base() {
        return (WritablePList<Message>) base;
    }

    @Override
    public void update() {
        if (_updateMessages) {
            return;
        }
        try {
            _updateMessages = true;
            base().removeAll();
            for (AppMessageProducer messageProducer : messageProducers) {
                messageProducer.produceMessages(app, base());
            }
        } finally {
            _updateMessages = false;
        }
    }

}
