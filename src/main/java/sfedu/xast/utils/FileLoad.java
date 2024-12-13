package sfedu.xast.utils;

import sfedu.xast.exceptions.FileLoadErrorException;

import java.io.FileInputStream;
import java.io.IOException;

public class FileLoad {
    public void loadFile(String fileName) throws FileLoadErrorException {
        try{
            FileInputStream fis = new FileInputStream(fileName);
            fis.close();
        }catch (IOException e){
            throw new FileLoadErrorException("Could not load file: " + fileName);
        }
    }
}
