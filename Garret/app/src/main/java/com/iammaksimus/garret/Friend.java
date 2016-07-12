package com.iammaksimus.garret;

/**
 * Created by 111 on 23.01.2016.
 */
public class Friend {
    String nick, name, fname, tmp;
   // ArrayList<String> tvshow = new ArrayList<>();

    Friend(String tmp){
        if(tmp.length() > 0) {
            String[] t = tmp.split("###");
            this.nick = t[0];
            this.name = t[1];
            this.fname = t[2];
            this.tmp = tmp;

        }
       /*  String[] tmp;
        tmp = tvshow.split("@@@");
       for(int i = 0; i < tmp.length; i++){
            this.tvshow.add(tmp[i]);
        }
        */
    }


    public String getName() {
        return name;
    }

    public String getFname() {
        return fname;
    }

    public String getNick() {
        return nick;
    }

    public String getTmp() {
        return tmp;
    }
}
