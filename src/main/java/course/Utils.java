package course;

public class Utils {

    public static Long generateIntId(){
        return (long)(Math.random() * Utils.commonIdLength);
    }

    private static final Long commonIdLength = 10000L;

}
