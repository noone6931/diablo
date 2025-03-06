package org.example.diablo.util;

import io.protostuff.Input;
import io.protostuff.Output;
import io.protostuff.Schema;

import java.io.IOException;

public class HaoDateSchema implements Schema<HaoDate> {
    private static final String FIELD_NAME = "time";
    private static final int FIELD_NUMBER = 1;

    @Override
    public String getFieldName(int number) {
        return FIELD_NAME;
    }

    @Override
    public int getFieldNumber(String name) {
        return FIELD_NUMBER;
    }

    @Override
    public boolean isInitialized(HaoDate message) {
        return true;
    }

    @Override
    public HaoDate newMessage() {
        return new HaoDate();
    }

    @Override
    public String messageName() {
        return HaoDate.class.getSimpleName();
    }

    @Override
    public String messageFullName() {
        return HaoDate.class.getName();
    }

    @Override
    public Class<? super HaoDate> typeClass() {
        return HaoDate.class;
    }

    @Override
    public void mergeFrom(Input input, HaoDate message) throws IOException {
        if (input.readFieldNumber(this) == FIELD_NUMBER) {
            message.setTime(input.readFixed64());
        }
    }

    @Override
    public void writeTo(Output output, HaoDate message) throws IOException {
        output.writeFixed64(FIELD_NUMBER, message.getTime(), false);
    }
}
