package com.lab3.info.service.save;

public abstract class FileCreatorFactory {

    public static Savable newInstance(SaveFormats format) {
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
