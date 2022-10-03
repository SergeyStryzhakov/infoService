package com.lab3.info.util;

import java.io.IOException;

public abstract class FileCreatorFactory {


    public static SaveFile newInstance(SaveFormats format) throws IOException {
        switch (format) {
            case WORD:
                return new WordFileCreator();
            case XML:
                return new XmlFileCreator();
            default:
                return new JsonFileCreator();
        }
    }

}
