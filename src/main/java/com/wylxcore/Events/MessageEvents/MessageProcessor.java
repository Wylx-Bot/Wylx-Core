package com.wylxcore.Events.MessageEvents;

public class MessageProcessor {
    // Step one is always to check that the server has approved the use of message reading, no processing is
    // to be done on messages before it has been evaluated if this is enabled for a server
    // Step two is to pass messages to regex based message events
    // Step three is to check if there is a prefix and matching command
}
