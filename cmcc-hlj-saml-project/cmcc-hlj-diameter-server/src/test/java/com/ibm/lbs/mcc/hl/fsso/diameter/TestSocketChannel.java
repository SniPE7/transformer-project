package com.ibm.lbs.mcc.hl.fsso.diameter;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import junit.framework.TestCase;

public class TestSocketChannel extends TestCase {
	public void atest1() throws IOException {
		SocketChannel sc = null;
		sc = SocketChannel.open();
		sc.configureBlocking(true);
		Socket s = sc.socket();
	}

	public void atest2() throws IOException {
		SocketChannel sc = null;
		sc = SelectorProvider.provider().openSocketChannel();
		SSLEngine ssle;
	}

	public void test3() throws IOException, InterruptedException {
		SSLServerSocketFactory sslssf = (SSLServerSocketFactory) SSLServerSocketFactory
				.getDefault();
		System.out.println(sslssf);
		SSLServerSocket sslss = (SSLServerSocket) sslssf
				.createServerSocket(199);
		System.out.println(sslss);
		
		ServerSocketChannel ssc = sslss.getChannel();
		System.out.println(ssc);

		down();
	}

	private void down() throws IOException, InterruptedException {
		System.in.read();
		System.out.println("Shutdown in 3 seconds...");
		Thread.sleep(3000);
	}
}
