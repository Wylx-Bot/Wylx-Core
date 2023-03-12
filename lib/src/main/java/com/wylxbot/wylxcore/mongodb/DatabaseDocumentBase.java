package com.wylxbot.wylxcore.mongodb;

import org.bson.codecs.pojo.annotations.BsonId;

public class DatabaseDocumentBase {
    @BsonId
    String id;
}
