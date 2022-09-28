package io.github.gafarrell.koth;

import java.util.HashMap;

public class KothStorage {
    private final static HashMap<String, KothObject> currentKoths = new HashMap<>();

    public static boolean put(KothObject koth){
        return (currentKoths.putIfAbsent(koth.getName(), koth) == null);
    }

    public static boolean remove(KothObject object){
        return currentKoths.remove(object.getName()) == null;
    }

    public static boolean remove(String name){
        return currentKoths.remove(name) == null;
    }

    public static String getKothNames(){
        StringBuilder builder = new StringBuilder();
        for (String s : currentKoths.keySet()){
            builder.append(s);
            builder.append(", ");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    public static KothObject getKothByName(String name){
        return currentKoths.get(name);
    }

    public static KothController makeController(String name){
        KothObject koth = currentKoths.get(name);
        if (koth == null) return null;
        else return new KothController(koth);
    }

    public static int GetKothCount(){return currentKoths.size();}
}
