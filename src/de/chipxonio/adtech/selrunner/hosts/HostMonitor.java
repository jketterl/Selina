package de.chipxonio.adtech.selrunner.hosts;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import de.chipxonio.adtech.selrunner.engine.SelRunnerThread;

public class HostMonitor extends SelRunnerThread {
	private Host host;
	private boolean shouldExit = false;
	private boolean sleeping = false;
	
	public HostMonitor(Host host) {
		this.host = host;
		this.start();
	}

	@Override
	public void terminate() {
		this.shouldExit = true;
		this.interrupt();
	}
	
	@Override
	public void run() {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter("http.socket.timeout", 1000);
		while (!shouldExit) {
			host.setStatus(host.getStatus() | Host.QUERYING);
			HttpGet get = new HttpGet("http://" + host.getHostName() + ":" + host.getPort() + "/");
			try {
				client.execute(get).getEntity().consumeContent();
				host.setStatus(host.getStatus() | Host.UP);
			} catch (ClientProtocolException e1) {
				host.setStatus(host.getStatus() & ~Host.UP);
			} catch (IOException e1) {
				host.setStatus(host.getStatus() & ~Host.UP);
			}
			host.setStatus(host.getStatus() & ~Host.QUERYING);
			try {
				this.sleeping = true;
				Thread.sleep(60000);
			} catch (InterruptedException e) {
			}
			this.sleeping = false;
		}
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
		this.interrupt();
	}

	@Override
	public void interrupt() {
		if (!this.sleeping) return;
		super.interrupt();
	}
}
