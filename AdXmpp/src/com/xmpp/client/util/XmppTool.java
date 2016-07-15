package com.xmpp.client.util;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XmppTool {

	private static XMPPConnection con = null;
	static {

		try {
			Class.forName("org.jivesoftware.smack.ReconnectionManager");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private static void openConnection() {
		try {
			ConnectionConfiguration connConfig = new ConnectionConfiguration("192.168.2.104", 5222);
			connConfig.setSecurityMode(SecurityMode.disabled);
			connConfig.setReconnectionAllowed(true);
			connConfig.setSASLAuthenticationEnabled(false);
			connConfig.setSendPresence(true);
			con = new XMPPConnection(connConfig);
			con.connect();
			con.addConnectionListener(connectionlistener);
		} catch (XMPPException xe) {
			xe.printStackTrace();
		}
	}

	public static XMPPConnection getConnection() {
		if (con == null) {
			openConnection();
		}
		return con;
	}

	public static void closeConnection() {
		con.removeConnectionListener(connectionlistener);
		con.disconnect();
		con = null;
	}
	
	public static ConnectionListener connectionlistener = new ConnectionListener() {
		
		@Override
		public void reconnectionSuccessful() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void reconnectionFailed(Exception arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void reconnectingIn(int arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void connectionClosedOnError(Exception arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void connectionClosed() {
			// TODO Auto-generated method stub
			
		}
	};
	
}
