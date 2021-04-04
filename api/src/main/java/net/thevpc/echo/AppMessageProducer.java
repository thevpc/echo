package net.thevpc.echo;

import net.thevpc.common.msg.Message;
import net.thevpc.common.props.WritableList;

public interface AppMessageProducer {
    void produceMessages(Application application, WritableList<Message> messages);
}
