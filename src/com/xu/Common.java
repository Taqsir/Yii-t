package com.xu;

import org.jetbrains.annotations.NotNull;

class Common {
    static String getYiiTName(String filePath){
        String[] pathArray = filePath.split("/");
        String module = "";
        String controller = "";
        String name = "site";
        int modulePosition = 0;
        for (int i=0;i<pathArray.length;i++){
            if(pathArray[i].contentEquals("modules")){
                module = pathArray[i+1];
                modulePosition = i;
            }
            if(pathArray[i].contentEquals("controllers")){
                controller = pathArray[pathArray.length-1].replace("Controller.php","");
            }
            if(pathArray[i].contentEquals("views")){
                controller = toUpperCaseFirstOne(pathArray[i+1]);
            }
        }
        if(module.length() >0  && controller.length()>0){
            name = module+controller;
        }
        if(module.length()==0 && controller.length()>0){
            name = toLowerCaseFirstOne(controller);
        }
        if(module.length()>0 && controller.length()==0){
            name = pathArray[modulePosition+3];
        }
        if(module.length()==0 && controller.length()==0){
            name = reverse(pathArray)[1];
        }
        return name;
    }

    private static String toUpperCaseFirstOne(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

    private static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    private static String[] reverse(String[] arr) {
        String[] rarr = new String[0];
        if (arr != null) {
            rarr = new String[arr.length];
            int j = 0;
            for (int i = arr.length - 1; i >= 0; i--) {
                rarr[j++] = arr[i];
            }
        }
        return rarr;
    }

}
