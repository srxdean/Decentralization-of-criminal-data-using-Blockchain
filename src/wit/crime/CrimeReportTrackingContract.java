/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wit.crime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Shubham369
 */
public class CrimeReportTrackingContract implements SmartContract
{

    private String superAdmin;
    private Map<String, CaseReport> caseReportMap = new HashMap<>();
    private Map<String, State> stateMap = new HashMap<>();

    @Override
    public void create(Context context)
    {
        this.superAdmin = context.msgSender;
    }

    @Override
    public void update(Context context, String command, Object... params)
    {
        if ("ADDCASEREPORT".equalsIgnoreCase(command))
        {
            CaseReport cr = new CaseReport();
            cr.caseno = (String) params[0];
            cr.state = (String) params[1];
            cr.district = (String) params[2];
            cr.taluka = (String) params[3];
            cr.city = (String) params[4];
            cr.policestation = (String) params[5];

//            String caseno=params[1];
//            String caseno=params[2];
            addCaseReport(context, cr);
        } else if ("ADDCASEADMIN".equalsIgnoreCase(command))
        {
            Admin ad = new Admin();
            ad.area = (String) params[0];
            ad.stationname = (String) params[1];
            ad.name = (String) params[2];

        } else if ("ADDVERIFIER".equalsIgnoreCase(command))
        {
            Verifier v = new Verifier();
            v.area = (String) params[0];
            v.stationname = (String) params[1];
            v.name = (String) params[2];

        } else if ("ADDTALUKA".equalsIgnoreCase(command))
        {
            String state = ((String) params[0]).toUpperCase();
            String district = ((String) params[1]).toUpperCase();
            String taluka = ((String) params[2]).toUpperCase();

            State s = stateMap.get(state);
            District d = s.districtMap.get(district);

            if (d.talukaMap.containsKey(taluka))
            {

            } else
            {
                Taluka t = new Taluka();
                t.name = taluka;
                d.talukaMap.put(taluka, t);
            }
        } 
        else if ("ADDCITY".equalsIgnoreCase(command))
        {
            String state = ((String) params[0]).toUpperCase();
            String district = ((String) params[1]).toUpperCase();
            String taluka = ((String) params[2]).toUpperCase();
            String city = ((String) params[3]).toUpperCase();
            
            
            State s = stateMap.get(state);
            District d = s.districtMap.get(district);
            Taluka t = d.talukaMap.get(taluka);

            
            if (t.cityMap.containsKey(city))
            {

            } else
            {
                City c = new City();
                c.name = city;
                t.cityMap.put(city, c);
            }
        } 
        
//        else if ("ADDPOLICESTATION".equalsIgnoreCase(command))
//        {
//            String state = ((String) params[0]).toUpperCase();
//            String district = ((String) params[1]).toUpperCase();
//            String taluka = ((String) params[2]).toUpperCase();
//            String city = ((String) params[3]).toUpperCase();
//            String policestation=((String) params[4]).toUpperCase();
//            
//            State s = stateMap.get(state);
//            District d = s.districtMap.get(district);
//            Taluka t = d.talukaMap.get(taluka);
//            
//            
//            if (t.cityMap.containsKey(city))
//            {
//
//            } else
//            {
//                City c = new City();
//                c.name = city;
//                t.cityMap.put(city, c);
//            }
//        }
        
        
        else
        {
            throw new RuntimeException("Unknown command " + command);
        }

    }

    @Override
    public Object query(Context context, String command, Object... params)
    {
        if (command.equalsIgnoreCase("GETCASEREPORT"))
        {
            String caseno = (String) params[0];

            return getCaseReport(context, caseno);
        } else if ("GETSTATES".equalsIgnoreCase(command))
        {
            final ArrayList<String> list = new ArrayList<>(stateMap.keySet());
            list.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
            return list;
        } else if ("GETDISTRICTS".equalsIgnoreCase(command))
        {
            String state = (String) params[0];

            State s = stateMap.get(state);
            final ArrayList<String> list = new ArrayList<>(s.districtMap.keySet());
            list.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
            return list;
        } else if ("GETTALUKAS".equalsIgnoreCase(command))
        {
            String state = (String) params[0];
            String district = (String) params[0];

            State s = stateMap.get(state);
            District d = s.districtMap.get(district);

            final ArrayList<String> list = new ArrayList<>(d.talukaMap.keySet());
            list.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
            return list;
        }
        else if ("GETCITY".equalsIgnoreCase(command))
        {
            String state = (String) params[0];
            String district = (String) params[1];
            String taluka=(String) params[2];
            
            
            State s = stateMap.get(state);
            District d = s.districtMap.get(district);
            Taluka t=d.talukaMap.get(taluka);
            
            final ArrayList<String> list = new ArrayList<>(t.cityMap.keySet());
            list.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
            return list;
        }
        
        
        
        else
        {
            throw new RuntimeException("Unknown command " + command);
        }
    }

    private void addCaseReport(Context context, CaseReport cr)
    {
//        if (!superAdmin.equals(context.msgSender))
//        {
//            throw new RuntimeException("Must be super admin to add case reports");
//        }

        PoliceStation ps = stateMap.get(cr.state).districtMap.get(cr.district).talukaMap.get(cr.taluka).cityMap.get(cr.city).policeStationMap.get(cr.policestation);

        if (!ps.uploaders.contains(context.msgSender))
        {
            throw new RuntimeException("");
        }

        caseReportMap.put(cr.caseno, cr);
        ps.caseReportMap.put(cr.caseno, cr);
    }

    private Object getCaseReport(Context context, String caseno)
    {
        if (!superAdmin.equals(context.msgSender))
        {
            throw new RuntimeException("Must be super admin to add case reports");
        }

        return caseReportMap.get(caseno);
    }

}
