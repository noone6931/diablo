package org.example.diablo.sonar;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class SftpUtils {

    private ChannelSftp sftp;
    private Channel channel;
    private Session session;

    private String username;
    private String password;

    private String host;
    private int port;
    private String keyFile;


    public SftpUtils() {
    }

    public void login() {
        try {
            if (session == null || !session.isConnected()) {
                JSch jSch = new JSch();
                session = jSch.getSession(username, host, port);
                session.setPassword(password);
                Properties properties = new Properties();
                properties.put("StrictHostKeyChecking", "no");
                session.setConfig(properties);
                session.setTimeout(30000);
                session.connect();
            }
            channel = session.openChannel("sftp");
            channel.connect();
            this.sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(){
        if (sftp!=null&& sftp.isConnected()){
            sftp.disconnect();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
        if (channel != null && channel.isConnected()) {
            channel.disconnect();
        }
    }
}
