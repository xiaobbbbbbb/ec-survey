package com.ecarinfo.utils;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IPUtil {

	/**
	 * 得到操作系统主机ip。 通过此方法可以通用NetworkInterface的getNetworkInterfaces得到操作系统所有网络接口信息
	 * 但由于一个网络接口上可以绑定多个ip地址,需要对所有的ip地址做筛选,通常 <code>127.0.0.1</code>的地址要过滤。
	 * 在没有192开头的ip地址存在的情况下, 非192的地址是首选,其次才是以192开头的地址
	 */
	public static String getInetAddress() {
		List<String> ip = new ArrayList<String>();
		List<String> ipother = new ArrayList<String>();
		Enumeration<NetworkInterface> nis = null;// 网络接口类
		try {
			nis = NetworkInterface.getNetworkInterfaces(); // 得到所有的的网络接口
			while (nis.hasMoreElements()) {
				NetworkInterface ni = nis.nextElement();
				Enumeration<InetAddress> ias = ni.getInetAddresses();// 得到所有的InetAddress
				while (ias.hasMoreElements()) {
					InetAddress ia = ias.nextElement();
					if (ia instanceof Inet4Address && !ia.getHostAddress().equals("127.0.0.1")) {
						// System.out.println("--操作系统主机IPV4之一--【" +
						// ia.getHostAddress() + "】");//操作系统主机ip
						// 以192开头的ip通常是内部使用ip
						if (!ia.getHostAddress().startsWith("192")) {
							ip.add(ia.getHostAddress());
						} else {
							ipother.add(ia.getHostAddress());
						}
					} else if (ia instanceof Inet6Address && !ias.equals("")) {
						// System.out.println("--操作系统主机IPV6之一--【"
						// +ia.getHostAddress() + "】");//操作系统主机ip
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ip.size() == 0 && ipother.size() != 0) {
			return ipother.get(0);
		} else if (ip.size() > 0) {
			return ip.get(0);
		} else {
			return "";
		}
	}

	/**
	 * 主类
	 */
	public static void main(String[] a) {
		System.out.println(getInetAddress());
	}
}