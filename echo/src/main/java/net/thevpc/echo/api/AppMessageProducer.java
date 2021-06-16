package net.thevpc.echo.api;

import net.thevpc.common.msg.Message;
import net.thevpc.common.props.WritableList;
import net.thevpc.echo.Application;

public interface AppMessageProducer {
    void produceMessages(Application application, WritableList<Message> messages);
}
