package udpSocketTrading;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import udpSocketTrading.sub.TradeInfoEty;
import udpSocketTrading.sub.Util;

public class TradeInfoReceiver implements Runnable {

	private static final Logger lgr = LoggerFactory.getLogger(TradeInfoReceiver.class);

	int portNumber;
	String socketName = "";

	public TradeInfoReceiver(int portNumber, String socketName) {
		this.portNumber = portNumber;
		this.socketName = socketName;
	}

	@SuppressWarnings("resource")
	public void run() {
		DatagramSocket socket = null;
		DatagramPacket inPacket;
		byte[] inMsg = new byte[300];

		try {
			socket = new DatagramSocket(portNumber);
		} catch (SocketException e) {
			lgr.error("[socket = new DatagramSocket(portNumber)] failed with [{}]", socketName);
			e.printStackTrace();
		}

		while (true) {
			// create packet to store the received data
			inPacket = new DatagramPacket(inMsg, inMsg.length);

			lgr.info("[{}] is waitng for packet...", socketName);
			try {
				// store the data in the packet
				socket.receive(inPacket);
				// lgr.info("socket [{}] recieved data.", socketName);
			} catch (IOException e) {
				lgr.error("[socket.receive(inPacket)] failed with [{}]", socketName);
				e.printStackTrace();
			}

			TradeInfoEty trEty = new TradeInfoEty(inPacket);
			lgr.info(trEty.toString());
			Util.TRADE_INFO_Q.add(trEty);
		}
	}
}