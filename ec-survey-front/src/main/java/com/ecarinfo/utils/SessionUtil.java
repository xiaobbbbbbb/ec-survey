package com.ecarinfo.utils;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @Description: Session管理工具类
 * @Author Dawn
 * @Date 2012-11-6
 * @Version V1.0
 */

public class SessionUtil {
	/**
	 * @Description: 添加Session
	 * @param： session
	 * @param： sessionName session的名称
	 * @param： sessionVal session的值
	 */
	public static void addSession(HttpSession session, String sessionName, Object sessionVal) {
		session.setAttribute(sessionName, sessionVal);
	}

	/**
	 * @Description: 删除Session
	 * @param： session
	 * @param： sessionName session的名称
	 */
	public static void removeSesssion(HttpSession session, String sessionName) {
		session.removeAttribute(sessionName);
	}

	/**
	 * @Description: 获得Shiro中存在的Session
	 * @param： session
	 * @param： sessionName session的名称
	 */
	public static Session getShiroSession() {
		Subject subject = SecurityUtils.getSubject();
		return subject.getSession();
	}
}
