package com.ibm.siam.am.idp.authn.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.siam.am.idp.mcc.MatchCodeClient;

/**
 * Match Code Service Interface
 * 
 * @author Jin Kaifeng
 * @since 2013-11-20
 */
public class MatchCodeServiceMccImpl implements MatchCodeService {
	private final Logger log = LoggerFactory
			.getLogger(MatchCodeServiceMccImpl.class);

	//private String wsdlLocation1 = "http://10.6.69.4:8000/MatchCode?wsdl";
	//private String wsdlLocation2 = "http://10.6.69.4:8000/MatchCode?wsdl";
	
	private String wsdlLocation1 = "http://192.168.134.1:8000/BasicHttpBinding_ICodeTrans?WSDL";
    private String wsdlLocation2 = "http://192.168.134.1:8000/BasicHttpBinding_ICodeTrans?WSDL";
	
	private String reverse = "0";

	public String getMatchCode(String cardUid) throws Exception {
		log.debug("enter getMatchCode");

		try {
			String cid;
			if ("1".equals(reverse)) {
				cid = cardUid.substring(6, 8) + cardUid.substring(4, 6)
						+ cardUid.substring(2, 4) + cardUid.substring(0, 2);
			} else {
				cid = cardUid;
			}

			String info;

			/*Com3Service wsClient;
			ICodeTrans wsInterface;

			URL url = null;
			try {
				url = new URL(wsdlLocation1);
				wsClient = new Com3Service(url);
				wsInterface = wsClient.getBasicHttpBindingICodeTrans();

				// 12345678->38261475->53168472
				String code = wsInterface.askMatch2(cid);
				info = code.substring(4, 5) + code.substring(2, 3)
						+ code.substring(0, 1) + code.substring(5, 6)
						+ code.substring(7, 8) + code.substring(3, 4)
						+ code.substring(6, 7) + code.substring(1, 2);

			} catch (Exception ex) {
				try {
					url = new URL(wsdlLocation2);
					wsClient = new Com3Service(url);
					wsInterface = wsClient.getBasicHttpBindingICodeTrans();

					// 12345678->38261475->53168472
					String code = wsInterface.askMatch2(cid);
					info = code.substring(4, 5) + code.substring(2, 3)
							+ code.substring(0, 1) + code.substring(5, 6)
							+ code.substring(7, 8) + code.substring(3, 4)
							+ code.substring(6, 7) + code.substring(1, 2);

				} catch (Exception e) {
					throw e;
				}
			}*/
			
			MatchCodeClient client = null;
			try {
			    client = new MatchCodeClient(wsdlLocation1);
                // 12345678->38261475->53168472
                String code = client.getMatchCode(cid);
                info = code.substring(4, 5) + code.substring(2, 3)
                        + code.substring(0, 1) + code.substring(5, 6)
                        + code.substring(7, 8) + code.substring(3, 4)
                        + code.substring(6, 7) + code.substring(1, 2);

            } catch (Exception ex) {
                try {
                    client = new MatchCodeClient(wsdlLocation2);
                    // 12345678->38261475->53168472
                    String code = client.getMatchCode(cid);
                    
                    info = code.substring(4, 5) + code.substring(2, 3)
                            + code.substring(0, 1) + code.substring(5, 6)
                            + code.substring(7, 8) + code.substring(3, 4)
                            + code.substring(6, 7) + code.substring(1, 2);

                } catch (Exception e) {
                    throw e;
                }
            }

			log.info("Get match code success.");
			return info;
		} catch (Exception e) {
			log.info("Get match code error.");
			log.error(e.getMessage());
			log.error(e.toString());
			throw e;
		} finally {
			log.debug("exit getMatchCode");
		}
	}

	public static void main(String[] args) {
		try {
			MatchCodeServiceMccImpl o = new MatchCodeServiceMccImpl();
			String code = o.getMatchCode("9E70DFCA");
			System.out.println("9E70DFCA----" + code);
			code = o.getMatchCode("9E840F0A");
			System.out.println("9E840F0A----" + code);

			o.setReverse("1");
			code = o.getMatchCode("CADF709E");
			System.out.println("CADF709E----" + code);
			code = o.getMatchCode("0A0F849E");
			System.out.println("0A0F849E----" + code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getWsdlLocation1() {
		return wsdlLocation1;
	}

	public void setWsdlLocation1(String wsdlLocation1) {
		this.wsdlLocation1 = wsdlLocation1;
	}

	public String getWsdlLocation2() {
		return wsdlLocation2;
	}

	public void setWsdlLocation2(String wsdlLocation2) {
		this.wsdlLocation2 = wsdlLocation2;
	}

	public String getReverse() {
		return reverse;
	}

	public void setReverse(String reverse) {
		this.reverse = reverse;
	}

}
