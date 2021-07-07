package org.geektimes.configuration.micriprofile.config.converter;

public class CharacterConverter extends AbstractConverter<Character>{
    @Override
    protected Character deConvert(String value) throws Throwable {
        if (value == null||value.isEmpty()){
            return null;
        }
        return Character.valueOf(value.charAt(0));
    }
}
