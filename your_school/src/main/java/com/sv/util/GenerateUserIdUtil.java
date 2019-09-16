package com.sv.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sv.dao.impl.UserDao;

@Component
public class GenerateUserIdUtil {

	private static final Logger logger = LoggerFactory.getLogger(GenerateUserIdUtil.class);

	private static String[] A1 = { "U", "M", "N", "O", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z", "A", "B", "C",
			"D", "E", "F", "G", "H", "I", "J", "K", "L", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	private static String[] A2 = { "S", "J", "K", "L", "0", "1", "M", "N", "O", "P", "Q", "R", "I", "T", "U", "V", "W",
			"X", "Y", "Z", "A", "B", "C", "D", "E", "F", "G", "H", "2", "3", "4", "5", "6", "7", "8", "9" };

	private static String[] A3 = { "R", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "M", "N", "O", "P", "Q",
			"R", "S", "T", "A", "B", "C", "U", "E", "F", "G", "H", "I", "J", "K", "L", "6", "7", "8", "9" };

	private static String[] A4 = { "0", "1", "2", "3", "4", "5", "6", "M", "N", "O", "P", "Q", "X", "Y", "Z", "A", "B",
			"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "7", "8", "9", "R", "S", "T", "U", "V", "W" };

	private static String[] A5 = { "0", "1", "2", "3", "4", "5", "6", "M", "N", "O", "P", "Q", "X", "Y", "Z", "A", "B",
			"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "7", "8", "9", "R", "S", "T", "U", "V", "W" };

	static String lastUniqueUserId = null;

	@Autowired
	UserDao userDao;

	public String generateUniqueId() throws Exception {
		logger.info("inside generateUniqueId()");
		synchronized (GenerateUserIdUtil.class) {
			logger.info("inside generateUniqueId() synchronized block..");
			return getUniqueId();
		}

	}

	private String getUniqueId() throws Exception {
		logger.info("inside getUniqueId()");
		String nextUniqueId = null;
		logger.info("inside getUniqueId(), user id generating block,,,");
		String prUniqueId = lastUniqueUserId;
		logger.info("Last user id is :" + prUniqueId);
		if (prUniqueId == null || prUniqueId.isEmpty() || "null".equalsIgnoreCase(prUniqueId)) {
			logger.info("Prvs Id null , fetching latest id from Database,,,");
			prUniqueId = userDao.getLastUserId();
			logger.info("Previoues id from DataBase is:" + prUniqueId);
		}
		if (prUniqueId != null && prUniqueId.length() >= 5) {
			logger.info("Prevs id found, generating next..");
			nextUniqueId = generateUniqueUserId(prUniqueId.substring(0, 1), prUniqueId.substring(1, 2),
					prUniqueId.substring(2, 3), prUniqueId.substring(3, 4), prUniqueId.substring(4, 5));
			lastUniqueUserId = nextUniqueId;
		} else {
			logger.info("Prs not found, creating new id from starting..");
			nextUniqueId = A1[0] + A2[0] + A3[0] + A4[0] + A5[0];
			lastUniqueUserId = nextUniqueId;
		}
		logger.info("Next user unique Id:" + nextUniqueId);
		return nextUniqueId;
	}

	private static String generateUniqueUserId(String a1, String a2, String a3, String a4, String a5) throws Exception {
		String finalCode = null;
		try {
			int pos = 0;
			pos = getPosition(a5, A5);
			if (pos < A5.length - 1) {
				finalCode = a1 + a2 + a3 + a4 + A5[pos + 1];
			} else {
				pos = getPosition(a4, A4);
				if (pos < A4.length - 1) {
					finalCode = a1 + a2 + a3 + A4[pos + 1] + A5[0];
				} else {
					pos = getPosition(a3, A3);
					if (pos < A3.length - 1) {
						finalCode = a1 + a2 + A3[pos + 1] + A4[0] + A5[0];
					} else {
						pos = getPosition(a2, A2);
						if (pos < A2.length - 1) {
							finalCode = a1 + A2[pos + 1] + A3[0] + A4[0] + A5[0];
						} else {
							pos = getPosition(a1, A1);
							if (pos < A1.length - 1) {
								finalCode = A1[pos + 1] + A2[0] + A3[0] + A4[0] + A5[0];
							} else {
								throw new Exception("No more unique USER id's");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("error while generating USER id..", e);
			throw new Exception("Unable generate Unique User Id");
		}
		return finalCode;
	}

	private static int getPosition(String letterPosition, String[] array) {
		int pos1 = 0;
		for (int i = 0; i < array.length; i++) {
			if (letterPosition.equalsIgnoreCase(array[i])) {
				pos1 = i;
			}
		}
		return pos1;
	}

	public static void main(String[] a) {
		GenerateUserIdUtil util = new GenerateUserIdUtil();
		try {
			for (int i = 0; i < 72; i++) {
				System.out.println("" + i + ":" + util.generateUniqueId());
			}
		} catch (Exception e) {
			logger.error("error while generating USER id..", e);
		}
	}
}
