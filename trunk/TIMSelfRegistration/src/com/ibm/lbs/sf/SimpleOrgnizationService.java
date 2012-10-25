/**
 * 
 */
package com.ibm.lbs.sf;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.xml.enc.dom.Base64;

/**
 * @author Administrator
 *
 */
public class SimpleOrgnizationService implements OrgnizationService {
  
  private static String text = "dn: erglobalid=5863252657375949336,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l: Beijing\n" +
      "erparent: erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 5863252657375949336\n" +
      "\n" +
      "dn: erglobalid=5863428992052385037,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l: NewYork\n" +
      "erparent: erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 5863428992052385037\n" +
      "\n" +
      "dn: erglobalid=5864048567618023557,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l: North America\n" +
      "erparent: erglobalid=5863428992052385037,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 5864048567618023557\n" +
      "\n" +
      "dn: erglobalid=5864081106642906658,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l: Sourth America\n" +
      "erparent: erglobalid=5863428992052385037,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 5864081106642906658\n" +
      "\n" +
      "dn: erglobalid=5864178670063749386,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l: AP\n" +
      "erparent: erglobalid=5863252657375949336,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 5864178670063749386\n" +
      "\n" +
      "dn: erglobalid=8199815378139025686,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=5864178670063749386,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l:: 5pel5pys5YiG6KGMSmFwYW4=\n" +
      "erglobalid: 8199815378139025686\n" +
      " \n" +
      "dn: erglobalid=8200072298478652819,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l:: 5r6z5aSn5Yip5Lqa5YiG6KGMQXVzdHJhbGlh\n" +
      "erparent: erglobalid=5864178670063749386,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8200072298478652819\n" +
      "\n" +
      "dn: erglobalid=8200249354801694877,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l:: 5paw5Yqg5Z2h5YiG6KGMU2luZ2Fwb3Jl\n" +
      "erparent: erglobalid=5864178670063749386,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8200249354801694877\n" +
      "\n" +
      "dn: erglobalid=8200767812178731696,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l:: 6Z+p5Zu95YiG6KGMS29yZWE=\n" +
      "erparent: erglobalid=5864178670063749386,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8200767812178731696\n" +
      " \n" +
      "dn: erglobalid=8201436359233015423,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l:: 5Y+w5rm+5YiG6KGMVGFpd2Fu\n" +
      "erparent: erglobalid=5864178670063749386,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8201436359233015423\n" +
      "\n" +
      "dn: erglobalid=8201924083131112867,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l: US\n" +
      "erparent: erglobalid=5864048567618023557,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8201924083131112867\n" +
      "\n" +
      "dn: erglobalid=8201961345019080669,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l: Canada\n" +
      "erparent: erglobalid=5864048567618023557,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8201961345019080669\n" +
      "\n" +
      "dn: erglobalid=8202311874634366111,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l:: 5Lic5LqsKFRva3lvKQ==\n" +
      "erparent: erglobalid=8199815378139025686,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8202311874634366111\n" +
      "\n" +
      "dn: erglobalid=8202379031259127378,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l:: 5aSn6ZiqKE9zYWthKQ==\n" +
      "erparent: erglobalid=8199815378139025686,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8202379031259127378\n" +
      "\n" +
      "dn: erglobalid=8202447560889631212,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l:: 6aaW5bCUU2VvdWw=\n" +
      "erparent: erglobalid=8200767812178731696,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8202447560889631212\n" +
      "\n" +
      "dn: erglobalid=8202785416365564951,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202447560889631212,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8202785416365564951\n" +
      "ou:: 5Ye65Y+jKEVYUE9SVCk=\n" +
      "\n" +
      "dn: erglobalid=8202827623222562590,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202447560889631212,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8202827623222562590\n" +
      "ou:: 6L+b5Y+jKElNUE9SVCk=\n" +
      "\n" +
      "dn: erglobalid=8202898532019966434,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202447560889631212,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8202898532019966434\n" +
      "ou:: 5L+d5Ye9KEdVQVJBTlRFRSk=\n" +
      "\n" +
      "dn: erglobalid=8202962445339696771,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202447560889631212,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8202962445339696771\n" +
      "ou:: 5rGH5qy+KFJFTUlUVEFOQ0Up\n" +
      "\n" +
      "dn: erglobalid=8203035529610766133,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202447560889631212,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 8203035529610766133\n" +
      "ou:: 5pS25LuY5riF566XKFBBWU1FTlQgTUFOQUdFTUVOVCk=\n" +
      "\n" +
      "dn: erglobalid=824424763083227341,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 824424763083227341\n" +
      "ou: Administrators(Domain)\n" +
      "    \n" +
      "dn: erglobalid=828130537867776488,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202311874634366111,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 828130537867776488\n" +
      "ou:: 5Ye65Y+jKEVYUE9SVCk=\n" +
      "\n" +
      "dn: erglobalid=828221802301582915,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202311874634366111,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 828221802301582915\n" +
      "ou:: 5L+d5Ye9KEdVQVJBTlRFRSk=\n" +
      "\n" +
      "dn: erglobalid=828305034577875882,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202311874634366111,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 828305034577875882\n" +
      "ou:: 5pS25LuY5riF566XKFBBWU1FTlQgTUFOQUdFTUVOVCk=\n" +
      "\n" +
      "dn: erglobalid=828346947732143467,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202311874634366111,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 828346947732143467\n" +
      "ou:: 6L+b5Y+jKElNUE9SVCk=\n" +
      "\n" +
      "dn: erglobalid=4438337573983219651,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "l: Europe&Africa\n" +
      "erparent: erglobalid=5863252657375949336,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 4438337573983219651\n" +
      "\n" +
      "dn: erglobalid=4440954881292674256,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202311874634366111,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 4440954881292674256\n" +
      "ou:: 5Zu96ZmF57uT566XKEdMT0JFIFRSQURFIFNFUlZJQ0UgREVQVCAp\n" +
      "\n" +
      "dn: erglobalid=4441317874406621625,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202447560889631212,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 4441317874406621625\n" +
      "ou:: 5Zu96ZmF57uT566XKEdMT0JFIFRSQURFIFNFUlZJQ0UgREVQVCk=\n" +
      "\n" +
      "dn: erglobalid=4494592630718517439,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202379031259127378,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 4494592630718517439\n" +
      "ou:: 5Ye65Y+jKEVYUE9SVCk=\n" +
      "\n" +
      "dn: erglobalid=4494631535508472589,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202379031259127378,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 4494631535508472589\n" +
      "ou:: 6L+b5Y+jKElNUE9SVCk=\n" +
      "\n" +
      "dn: erglobalid=4494664950734449798,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erparent: erglobalid=8202379031259127378,ou=orgChart,erglobalid=00000000000000000000,ou=IBM,dc=tim,dc=com\n" +
      "erglobalid: 4494664950734449798\n" +
      "ou:: 5L+d5Ye9KEdVQVJBTlRFRSk=\n";

  /**
   * 
   */
  public SimpleOrgnizationService() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.lbs.sf.OrgnizationService#getAllOrganization()
   */
  private Map<String, Organization> orgsIndexMap = new HashMap<String, Organization>();
  public List<Organization> getAllOrganization() throws Exception {
    List<Organization> topNodes = new ArrayList<Organization>();
    BufferedReader reader = new BufferedReader(new StringReader(text));
    String line = reader.readLine();
    Organization o = null;
    while (line != null) {
      if (line.startsWith("dn: ")) {
         o = new Organization();
         o.setDn(line.trim().substring(4));
      } else if (line.startsWith("erparent: ")) {
        String s = line.substring("erparent: ".length());
        Organization parrent = orgsIndexMap.get(s);
        o.setParrent(parrent);
      } else if (line.startsWith("erglobalid: ")) {
        o.setId(line.substring("erglobalid: ".length()));
      } else if (line.startsWith("ou:")) {
        String s = line.trim().substring("ou:".length());
        if (s.startsWith(":")) {
           s = s.substring(1);
           s = new String(Base64.decode(s.getBytes()), "UTF-8");
        }
        o.setName(s);
      } else if (line.startsWith("o:")) {
        String s = line.trim().substring("o:".length());
        if (s.startsWith(":")) {
           s = s.substring(1);
           s = new String(Base64.decode(s.getBytes()), "UTF-8");
        }
        o.setName(s);
      } else if (line.startsWith("l:")) {
        String s = line.trim().substring("l:".length());
        if (s.startsWith(":")) {
           s = s.substring(1);
           s = new String(Base64.decode(s.getBytes()), "UTF-8");
        }
        o.setName(s);
      } else if (line.trim().length() == 0) {
        this.orgsIndexMap.put(o.getDn(), o);
        
        if (o.getParrent() == null) {
          topNodes.add(o);
        }
      }
      line = reader.readLine();
    }
    return topNodes;
  }

}
