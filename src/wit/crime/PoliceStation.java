/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wit.crime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Shubham369
 */
class PoliceStation
{

    public String name;

    public List<String> verifiers = new ArrayList<>();
    public List<String> uploaders = new ArrayList<>();
    
    public Map<String, CaseReport> caseReportMap=new HashMap<>();
}
