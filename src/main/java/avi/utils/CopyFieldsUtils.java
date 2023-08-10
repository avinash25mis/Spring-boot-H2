package avi.utils;

import org.apache.commons.beanutils.PropertyUtils;

import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author avinash.a.mishra
 */
public class CopyFieldsUtils {

    public static void copyAllProperties(Object dest, Object orig) {
        try {
            PropertyUtils.copyProperties(dest, orig);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try{
            String val=null;
            if(!StringUtils.isEmpty(val)){
                System.out.println(val);
            }else{
                System.out.println("fine");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
