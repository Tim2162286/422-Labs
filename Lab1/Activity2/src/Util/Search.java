package Util;
enum dayNums {M,Tu,W,Th,F,Sa,Su;}
public class Search {

    private String fname;
    private String lname;
    private String langs;
    private boolean[] days = new boolean[7];
    private String focus;

    public  Search(String fname, String lname, String langs,String[] days,String focus){
        this.fname = fname;
        this.lname = lname;
        this.langs = langs;
        if(days!=null) {
            for (String s : days) {
                dayNums day = dayNums.valueOf(s);
                this.days[day.ordinal()] = true;
            }
        }
        this.focus = focus;
    }
    public Search(String str){
        String[] parts = new String[5];
        String[] split = str.split(":");
        for(int i=0;i<split.length;i++){
            if(split[i]!=null)
                parts[i]=split[i];
        }
        if(parts.length==5){
            this.fname = parts[0];
            this.lname = parts[1];
            this.langs = parts[2];
            System.out.println(parts[3]);
            if(parts[3]!=null) {
                String[] splitDays = parts[3].split("`");
                System.out.println(splitDays.length);
                for (String s : splitDays) {
                    System.out.println(s);
                    dayNums day = dayNums.valueOf(s);
                    this.days[day.ordinal()] = true;
                }
            }
            this.focus=parts[4];
        }
        System.out.println("string: "+getCookieString());
    }

    public String getFname() {
        return (fname!=null?fname:"");
    }
    public String getLname() {
        return (lname!=null?lname:"");
    }

    public String getLangs() {
        return (langs!=null?langs:"");
    }

    public boolean[] getDays() {
        return days;
    }

    public String getFocus() {
        return (focus!=null?focus:"");
    }

    public String getCookieString(){
        String dayString = "";
        dayNums[] dayNames = dayNums.values();
        for(int i=0;i<days.length;i++){
            if(days[i])
                dayString+=dayNames[i].name()+"`";
        }
        if(dayString.length()>0)
            dayString=dayString.substring(0,dayString.length()-1);
        System.out.println(dayString);
        return fname+":"+lname+":"+langs+":"+dayString+":"+focus;
    }
}
