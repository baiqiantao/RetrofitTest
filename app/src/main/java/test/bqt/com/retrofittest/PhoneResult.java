package test.bqt.com.retrofittest;

public class PhoneResult {

	/**
	 * {
	 * "errNum": 0,
	 * "retMsg": "success",
	 * "retData": {
	 * "phone": "13671953787",
	 * "prefix": "1367195",
	 * "supplier": "移动",
	 * "province": "上海",
	 * "city": "上海",
	 * "suit": "136卡"
	 * }
	 */
	public int errNum;
	public String retMsg;
	public RetDataEntity retData;

	public static class RetDataEntity {
		public String phone;
		public String prefix;
		public String supplier;
		public String province;
		public String city;
		public String suit;
	}
}