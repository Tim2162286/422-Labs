import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgrammerFilter implements NodeFilter {
    Map<String,String[]> params;
    public void setParams(Map<String,String[]> params){
        this.params = params;
    }
    @Override
    public short acceptNode(Node n) {
        if (n instanceof Element) {
            Element e = (Element) n;
            if(e.getTagName().equals("programmer")) {
                if(checkFname(e)&&checkLname(e)&&checkLangs(e)&&checkDays(e)&&checkAge(e))
                    return FILTER_ACCEPT;
            }
        }
        return FILTER_REJECT;
    }
     private boolean checkFname(Element e){
        String[] param = params.get("firstname");
        if(param!=null){
            System.out.println();
            if(!e.getElementsByTagName("firstname").item(0).getTextContent().contains(param[0]))
                return false;
        }
        return true;

     }
    private boolean checkLname(Element e){
        String[] param = params.get("lastname");
        if(param!=null){
            if(!e.getElementsByTagName("lastname").item(0).getTextContent().contains(param[0]))
                return false;
        }
        return true;
    }
    private boolean checkLangs(Element e){
        String[] param = params.get("languages");
        if(param==null)
            return true;
        List<String> langs =Arrays.asList(param[0].split(","));
        NodeList nList = e.getElementsByTagName("language");
        Node n;
        for(int i=0;i<nList.getLength();i++){
            n=nList.item(i);
            if (langs.contains(n.getTextContent()))
                return true;
        }
        return false;
    }
    private boolean checkDays(Element e){
        String[] param = params.get("days");
        if(param==null)
            return true;
        ArrayList<String> days = new ArrayList<String>();
        Pattern p = Pattern.compile("((M)?(Tu)?(W)?(Th)?(F)?(Sa)?(Su)?)");
        Matcher m = p.matcher(param[0]);

        if(m.find()){
            for(int i=2;i<=8;i++){
                System.out.println("Group "+i+": "+m.group(i));
                if(m.group(i)!=null)
                    days.add(m.group(i));
            }
        }
        NodeList nList = e.getElementsByTagName("day");
        Node n;
        for(int i=0;i<nList.getLength();i++){
            n=nList.item(i);
            if (days.contains(n.getTextContent()))
                return true;
        }
        return false;
    }
    private boolean checkAge(Element e){
        String[] param = params.get("focus");
        if(param!=null){
            if(!e.getElementsByTagName("focus").item(0).getTextContent().contains(param[0]))
                return false;
        }
        return true;
    }
}
